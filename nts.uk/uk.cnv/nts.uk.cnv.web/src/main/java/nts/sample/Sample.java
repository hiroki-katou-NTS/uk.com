package nts.sample;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class Sample {

	public Supplier<Integer> adding(int x, int y) {
		return () -> {
			return x + y;
		};
	}
}

interface CanHop {}
class Frog implements CanHop {
    public static void test() {
    	String str = "hello";
    	List<String> list = new ArrayList<String>();
    	list.add(str.toUpperCase());

    }
}
class BrazilianHornedFrog extends Frog {}
class TurtleFrog extends Frog {}
