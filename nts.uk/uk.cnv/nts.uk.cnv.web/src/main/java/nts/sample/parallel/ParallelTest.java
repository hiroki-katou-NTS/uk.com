package nts.sample.parallel;

import java.util.Arrays;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import nts.arc.task.parallel.ManagedParallelWithContext;

@Stateless
public class ParallelTest {

	@Inject
	ManagedParallelWithContext parallel;
	
	public void run() {
		
		this.parallel.forEach(Arrays.asList(1, 2, 3), i -> {
			runSub(i);
		});
	}
	
	private static void runSub(int i) {
		Object obj = CDI.current().select(CdiInjectTest.class).get();
		CdiInjectTest inj = (CdiInjectTest) obj;
		inj.run();
	}
}
