/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        // base case
        if (list.size() <= 1) {
        	return list;
        }

        // split list into two halves
        List<T> firstHalf = new LinkedList<T>(list.subList(0, list.size()/2));
        List<T> secondHalf = new LinkedList<T>(list.subList(list.size()/2, list.size()));

        List<T> firstList = mergeSort(firstHalf, comparator);
        List<T> secondList = mergeSort(secondHalf, comparator);

        List<T> sorted = merge(firstList, secondList, comparator);

        return sorted;
	}

	private List<T> merge(List<T> firstList, List<T> secondList, Comparator<T> comparator) {
		List<T> helperList = new LinkedList<T>();

		// compare stuff inside each list starting with first element since each list is sorted
		int size = firstList.size() + secondList.size();

		for(int i = 0; i < size; i++) {
			//gets list
			List<T> one = getList(firstList, secondList, comparator);
			T chosenOne = one.remove(0);
			helperList.add(chosenOne);
		}

		return helperList;

	}
	// gets the list with the smaller first element cuz theyre supposed to be pre-sorted
	private List<T> getList(List<T> firstList, List<T> secondList, Comparator<T> comparator) {
			// empty = get other list
			if(firstList.size() == 0) {
				return secondList;
			} // same
			if (secondList.size() == 0) {
				return firstList;
			}
			int compare = comparator.compare(firstList.get(0), secondList.get(0));
			if (compare > 0) { // compare = 1 so first > second so get second one and insert it into helper
				return secondList;
			} else if (compare < 0) { // add from first list because first < second
				return firstList;
			} else { // add from one of the two lists because the first elements are the same, we'll eventually add from the second list anyway
				return secondList;
			}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> pQueue = new PriorityQueue<T>();

        for(T element: list) {
        	pQueue.offer(element);
        }

        list.clear();

        while(pQueue.size() != 0) {
        	T removed = pQueue.poll();
        	list.add(removed);
        }
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        heapSort(list, comparator);
        List<T> kList = new LinkedList<T>(list.subList(list.size() - k, list.size()));
        return kList;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
