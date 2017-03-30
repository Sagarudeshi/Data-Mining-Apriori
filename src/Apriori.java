

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;


public class Apriori {
	
	private static ArrayList<String> itemSet = new ArrayList<String>();
	public static ArrayList<String> transaction = new ArrayList<String>();
	private static ArrayList<String> association = new ArrayList<String>();
	public static double minSupport = 0;
	public static double minConfidence = 0;
	public static MyController object = new MyController();
	private static ArrayList<String> frequentItemSet = new ArrayList<String>();
	public static ArrayList<String> saveInput = new ArrayList<String>();
	public static ArrayList<String> saveOutput = new ArrayList<String>();
		
	public static void displayTransaction(File Database){
		
		Scanner input = null;
		try {
			input = new Scanner(Database);
		} catch (FileNotFoundException e) {
			
			System.err.println("File not Found");
		}
		

		while (input.hasNextLine()) {
            String line = input.nextLine();
            String temp[] = line.split(" ");
            transaction.add(temp[1]);
           
            saveInput.add(temp[0]+" | "+temp[1]);

        }
        input.close();
		
	}
	public static void candidateSet(ArrayList<String> list)
	{
		HashSet<String> set = new HashSet<String>();
		
		for(int i=0; i<list.size(); i++)
		{
			String s = (String)list.get(i);
			String temp[] = s.split(",");
			for(int j=0;j<temp.length;j++)			{
				set.add(temp[j]);
			}
		}
		
		Object[] uniqueArr=set.toArray();	//Array elements - unique
		
		for(int i=0;i<uniqueArr.length;i++){
			itemSet.add(uniqueArr[i].toString());
		}
		
		generate(itemSet);
	}

	
	public static void generate(ArrayList<String> items)
	{
		ArrayList<String> combinations = new ArrayList<String>();
		
		
		for(int p=0; p<items.size(); p++) {
			combinations.add(items.get(p));
		}
		
		ArrayList<String> temp=new ArrayList<String>();
		
		//initializing temperory list 
		for(int p=0; p<items.size(); p++) {
			temp.add(items.get(p));
		}
		
		ArrayList<String> temp1=new ArrayList<String>();
		ArrayList<String> assign=new ArrayList<String>();
		
		// generating all possible combinations
		for(int i=1;i<items.size();i++)
		{
			temp1.clear();
			for(int j=0;j<items.size();j++)
			{	
				for(int k=0;k<temp.size();k++)
				{
					String str = items.get(j).toString()+","+temp.get(k).toString();
					if(checkDuplicate(str))
						temp1.add(str);
				}
			}
			
			temp.clear();
				
			assign=checkElements(temp1);
			
			//removed duplicate items
			for(int a=0;a<assign.size();a++){
				temp.add(assign.get(a));
			}
			
			//concatenated result of temporary
			for(int m=0;m<temp.size();m++){
				combinations.add(temp.get(m));
			}
		}
		
		//calculating support values
		for(int comb=0;comb<combinations.size();comb++)
		{
			//calculating the support of each combination
			double support = (calcSupport(combinations.get(comb).toString()) *100)/ (double)transaction.size();
			
			if(support>minSupport)
				frequentItemSet.add(combinations.get(comb));
		}
		
		//System.out.println("frequent items: "+frequentItemSet);  //frequent itemsets
		getassociation(frequentItemSet);	
	}

	//create association rules
	public static void getassociation(ArrayList<String> itemset)
	{
		ArrayList<String> itemset1 = itemset;	//new itemset 

		int ctr=0;
		for(int i=0;i<itemset.size();i++)
		{
			for(int j=0;j<itemset1.size();j++)
			{
				if(itemset.get(i).toString().length() >= itemset1.get(j).toString().length())//check length
				{
					if(!itemset.get(i).toString().equals(itemset1.get(j).toString()))//check for duplicate values
					{
						String []arr = itemset1.get(j).toString().split(",");
						for(int k=0; k<arr.length; k++)
						{
							if(itemset.get(i).toString().contains(arr[k]))// check if contains
							{
								ctr++;
							}
						}
						
						if(ctr==0){
							association.add(itemset.get(i).toString()+" -> "+itemset1.get(j).toString());
							
						}
					}
					ctr=0;
				}
			}
		}
		checkConfidence(association);
	}
	
	
	public static void checkConfidence(ArrayList<String> association)	//checks confidence and prints association
	{
		double support=0, confidence=0;
		

		
		for(int i=0;i<association.size();i++)
		{
			String arr[] = association.get(i).toString().split(" -> ");
			String str = arr[0].concat(",").concat(arr[1]);
			
			support = calcSupport(str)/(double) transaction.size();
			
			
			confidence = support/(calcSupport(arr[0])/(double) transaction.size());
			
			
			support = Math.round(support*10000.0)/100.0;	
			confidence = Math.round(confidence*10000.0)/100.0;	
			
			if(support>minSupport && confidence>minConfidence)
				saveOutput.add(association.get(i)+" ("+support+","+confidence+")");
//				System.out.println(association.get(i)+" ("+support+","+confidence+")");
			
		}
	}
	
	
	public static boolean checkDuplicate(String S)	// removes duplicate combinations 
	{
		String arr[] = S.split(",");
		for(int i=0; i<arr.length; i++)
		{
			for(int j=i+1; j<arr.length; j++)
			{
				if(arr[i].equals(arr[j]))
					return false;
			}
		}
		return true;
	}
	
	
	public static int calcSupport(String S)		// calculate support of an itemset
	{
		int ctr = 0;
		int count=0;
		
		String arr[] = S.split(",");
		
		for(int i=0;i<transaction.size();i++)
		{
			for(int j=0;j<arr.length;j++){
				if(transaction.get(i).toString().contains(arr[j]))
					ctr++;
			}

			if(ctr==arr.length)
				count++;

			ctr = 0;
		}
		return count;
	}
		
		
	public static ArrayList<String> checkElements(ArrayList<String> temp1)	// checks permutations and combinations
	{
		HashSet<String> set1,set2;
		
		for(int i=0;i<temp1.size();i++)
		{
			String arr1[] = temp1.get(i).toString().split(",");
			set1=new HashSet<String>(Arrays.asList(arr1));
			
			for(int j=i+1;j<temp1.size();j++)
			{
				String arr2[] = temp1.get(j).toString().split(",");
				
				set2=new HashSet<String>(Arrays.asList(arr2));
				if(set1.equals(set2))
				{
					temp1.remove(j);
					continue;
				}
			}
		}							
		
		
		return temp1;
	}
	
}
