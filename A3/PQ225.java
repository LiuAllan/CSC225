/*
Allan Liu
V00806981
CSC 225 Fall 2016
Assignement 3
*/

import java.util.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;


class PQ225
{
	
	public int heapArray[];
	private int N;
	private int len;
	
	public PQ225(int n)
	{
		len = n+1;
		this.heapArray = new int [len]; 
		N = 0;
		heapArray[N] = 0;
	}
	
	//helper methods
	private void exchange(int i, int j)
	{
		int t = heapArray[i];
		heapArray[i] = heapArray[j];
		heapArray[j] = t;
	}
	
	//bubble up to satisfy the heap property
	private void swim(int k)
	{
		while (k > 1 && (heapArray[k/2] < heapArray[k]))
		{
			exchange(k/2, k);
			k = k/2;
		}
	}
	
	//bubble down to satisfy the heap property
	private void sink(int k)
	{
		while (2 * k <= N)
		{
			int j = 2 * k;
			if(j < N && (heapArray[j] < heapArray[j+1]))
			{
				j++;
			}
			if(heapArray[k] > heapArray[j])
			{
				break;
			}
			exchange(k, j);
			k = j;
		}
	}
	
	//doubles the size of the array when inserting a value
	private void doubleArray() 
	{
		int [] temp = new int [N * 2]; 
		for (int i = 1 ; i<N ; i++){
			temp[i] = heapArray[i];
		}
		len = temp.length;
		heapArray = temp;
    }

	//methods to implement
	//generate random numbers
	public void ranGen(int N, int LOW, int HIGH)
	{
		Random rand = new Random();
		
		int k = 0;
		while (k < N)
		{
			rand.setSeed(System.nanoTime());
			int rn = rand.nextInt((HIGH - LOW)+1) + LOW;
			insert(rn);
			k++;
		}
	}
	
	public int size()
	{
		return N;
	}
	
	//insert a number at the bottom of the array and bubble up to satisfy heap property
	public void insert(int i)
	{
		if(N == len-1)
		{
			doubleArray();
			heapArray[++N] = i;
			swim(N);
		}
		else
		{
			heapArray[++N] = i;
			swim(N);
		}
	}
	
	//remove root, move last element to top and bubble down to satisfy heap property
	public int deleteMin()
	{
		if(N == 0)
		{
			return 0; //exit - heap is empty
		}
		
		else
		{
			int min = heapArray[1]; //get the root of the heap which is the min
			exchange(1, N--);
			sink(1);
			return min;
		}
	}
	
	public void makeHeap()
	{
		for (int i = N / 2; i >= 0; i--)
		{
			sink(i);
		}
	}
	
	public int heapsort()
	{
		int [] tmpArray = new int [len];
		int tmp = N;
		for (int i = 1; i < len ; i++)
		{
			tmpArray[i]=deleteMin();
		}
		heapArray = tmpArray;
		N = tmp; //restores the N 
		
		for(int j=0; j < len/2; j++)
		{
			exchange(j, len - j - 1);
		}
		return 0;
	}

	//testing methods
	public void test(int test)
	{
		try
		{
			results(test);
		}
		catch (IOException e)
		{
			System.out.println("Cannot open file");
			return;
		}
	}
	
	//write results into pq_test.txt
	public void results(int test) throws IOException
	{
		FileWriter file = new FileWriter("pq_test.txt", true);
		BufferedWriter writer = new BufferedWriter (file);
		switch (test)
		{
			case 1:
					writer.append("\n" + "TEST 1: GENERATE HEAP SIZE 10 WITH INTEGERS FROM 0 TO 100" + "\n");
					writer.append("\n" + "OUTPUT:" + "\n");
					break;
			case 2:
					writer.append("\n" + "TEST 2: GENERATE HEAP SIZE 100 WITH INTEGERS FROM 0 TO 1000" + "\n");
					writer.append("\n" +"OUTPUT:" + "\n");
					break;
			case 3:
					writer.append("\n" + "TEST 3: GENERATE HEAP SIZE 1000 WITH INTEGERS FROM 0 TO 10000" + "\n");
					writer.append("\n" +"OUTPUT:" + "\n");
					break;
			case 4:
					writer.append("\n" + "TEST 4: SORT THE HEAPARRAY IN ASCENDING ORDER SIZE 100 + TEST deleteMin" + "\n");
					writer.append("\n" +"OUTPUT:" + "\n");
					break;
			case 5:
					writer.append("\n" + "TEST 5: TEST insert" + "\n");
					writer.append("\n" +"OUTPUT:" + "\n");
					break;
		}
		for(int i : heapArray)
		{
			writer.append( i +" ");
		}
		writer.append("\n" + "SIZE OF ARRAY N IS: " + N + "\n");
		writer.close();
	}
	
	public void print ()
	{
		System.out.print("OUTPUT: ");
		for (int i =0 ; i < len ; i++)
		{
			System.out.print(heapArray[i] + " "); 
		}
		System.out.println();
	}
	
	public static void main(String[] args)
	{
			//test ranGen
			int n1 = 10;
			int hiRange1 = 100;
			PQ225 test1 = new PQ225 (n1);
			test1.ranGen(n1, 0 ,hiRange1);
			test1.print();
			System.out.println();
			test1.test(1);
			
			int n2 = 100;
			int hiRange2 = 1000;
			PQ225 test2 = new PQ225 (n2);
			test2.ranGen(n2, 0 ,hiRange2);
			test2.print();
			System.out.println();
			test2.test(2);
			
			int n3 = 1000;
			int hiRange3 = 10000;
			PQ225 test3 = new PQ225 (n3);
			test3.ranGen(n3, 0 ,hiRange3);
			test3.print();
			System.out.println();
			test3.test(3);
		
			//test heapsort and deleteMin
			int n4 = 100;
			int hiRange4 = 1000;
			PQ225 test4 = new PQ225 (n4);
			test4.ranGen(n4, 0 ,hiRange4);
			System.out.println();
			test4.heapsort();
			test4.test(4);
			System.out.print("SORTING THE ARRAY IN ASCENDING ORDER + deleteMin: ");
			test4.print();
			System.out.println("SIZE N IS: " +test4.size() );
			
			//test insert
			int n5 = 10;
			int hiRange5 = 100;
			PQ225 test5 = new PQ225 (n5);
			test5.ranGen(n5, 0 ,hiRange5);
			System.out.println();
			test5.insert(5);
			test5.test(5);
			System.out.print("TESTING insert: ");
			test5.print();
			System.out.println("SIZE N IS: " +test5.size() );
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
