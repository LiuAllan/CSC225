/* TripleSum.java
   CSC 225 - Summer 2016
   Asleftgnment 1 - Template for TripleSum

   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java TripleSum

   To conveniently test the algorithm with a large input, create a
   text file containing space-separated integer values and run the program with
	java TripleSum file.txt
   where file.txt is replaced by the name of the text file.

   B. Bird
*/

/*
CSC 225
Allan Liu
V00806981
*/

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;

//Do not change the name of the TripleSum class
public class TripleSum{
	/* TripleSum225()
		The input array A will contain non-negative integers. The function
		will search the input array A for three elements which sum to 225.
		If such a triple is found, return true. Otherwise, return false.
		The triple may contain the same element more than once.
		The function may modify the array A.

		Do not change the function signature (name/parameters).
	*/
	public static boolean TripleSum225(int[] A){
		int n = A.length;
		sort(A);// sort the array first using heapsort
		for (int i = 0; i < n - 2; i++) //look through the array using index i, left and right to get a triple that equals to 225
        {
			int left = i + 1; //index of the first element where it starts right after i
			int right = n - 1; //index of the last element
			while (left < right)
			{
				if (A[i] + A[left] + A[right] == 225)
                {
                    return true; //found a triple sum
                }
                else if (A[i] + A[left] + A[right] < 225) //if the triple sums to less than 225, increase left index and continue
				{
					left++;
				}
                else //if the triple sums to more than 225, decrease the right index and continue
				{
                    right--;
				}
			}
		}
		return false; //no triple sum was found
	}
	
	public static int n;
		
	public static void sort(int A[])// heapsort algorithm referenced from CSC 225 textbook
    {      
		heap(A);   
        for (int i = n; i > 0; i--)
        {
            swap(A,0, i);
            n = n-1;
            maxheap(A,0);
        }
    }  
	
    public static void heap(int A[])// builds a heap
    {
        n = A.length-1;
        for (int i = n/2; i >= 0; i--)
		{
            maxheap(A,i);        
		}
	}

    public static void maxheap(int A[], int i)// method swaps largest element in heap
    {
        int left = 2*i;
        int right = 2*i + 1;
        int max = i;
        if (left <= n && A[left] > A[i])
		{
            max = left;
		}
        if (right <= n && A[right] > A[max])
		{
            max = right;
		}
        if (max != i)
        {
            swap(A, i, max);
            maxheap(A, max);
        }
    }
	
    public static void swap(int A[], int i, int j)// method swaps two numbers in an array
    {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp; 
    }    
	
	/* main()
	   Contains code to test the TripleSum225 function. Nothing in this function
	   will be marked. You are free to change the provided code to test your
	   implementation, but only the contents of the TripleSum225() function above
	   will be considered during marking.
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
		}
		Vector<Integer> inputVector = new Vector<Integer>();

		int v;
		while(s.hasNextInt() && (v = s.nextInt()) >= 0)
			inputVector.add(v);

		int[] array = new int[inputVector.size()];

		for (int i = 0; i < array.length; i++)
			array[i] = inputVector.get(i);

		System.out.printf("Read %d values.\n",array.length);


		long startTime = System.currentTimeMillis();

		boolean tripleExists = TripleSum225(array);

		long endTime = System.currentTimeMillis();

		double totalTimeSeconds = (endTime-startTime)/1000.0;

		System.out.printf("Array %s a triple of values which add to 225.\n",tripleExists? "contains":"does not contain");
		System.out.printf("Total Time (seconds): %.2f\n",totalTimeSeconds);
	}
}
