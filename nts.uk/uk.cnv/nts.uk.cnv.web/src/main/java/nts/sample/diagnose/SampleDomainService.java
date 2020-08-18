package nts.sample.diagnose;

import nts.arc.task.tran.AtomTask;

public class SampleDomainService {
	
	public static AtomTask sample(Require require, int x) {
		
		require.hoge(5, 10);
		if (x % 2 == 0) {
			require.fuga(5, 10);
		}
		
		return AtomTask.of(() -> require.save(100));
	}
	
	public static int sample2(Require2 require, int x) {
		return x * require.piyo(77);
	}

	public static interface Require {
		int hoge(int a, int b);
		int fuga(int a, int b);
		void save(int a);
	}
	
	public static interface Require2 {
		int piyo(int a);
	}
}
