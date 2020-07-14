package nts.uk.screen.at.app.query.kdl.kdl014.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestMap {
	
	public static void main(String[] args) throws Exception {
		testflatMap();
	}
	
	public static void testflatMap() throws Exception {
		ArrayList<ArrayList<Integer>> listOLists = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<Integer> singleList = new ArrayList<Integer>();
		singleList.add(1);
		singleList.add(2);
		singleList.add(3);
		listOLists.add(singleList);

		ArrayList<Integer> anotherList = new ArrayList<Integer>();
		anotherList.add(4);
		anotherList.add(5);
		anotherList.add(6);
		listOLists.add(anotherList);
		
		ArrayList<Integer> anotherList2 = new ArrayList<Integer>();
		anotherList2.add(7);
		anotherList2.add(8);
		anotherList2.add(9);
		listOLists.add(anotherList2);
		
		System.out.println(listOLists);
		
		List<Integer> a = listOLists.stream().flatMap(List::stream).map(i->i+1).collect(Collectors.toList());
		
		System.out.println(a);
		
		List<Integer> b = new ArrayList<Integer>();
		for (List<Integer> i : listOLists) {
			for (Integer x:i) {
				x += 1;
				b.add(x);
			}
 		}
		
		System.out.println(b);
		
	}

}
