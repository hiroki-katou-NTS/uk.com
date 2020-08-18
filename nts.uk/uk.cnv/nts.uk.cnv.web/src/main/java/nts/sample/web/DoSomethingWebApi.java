package nts.sample.web;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import nts.sample.parallel.ParallelTest;

@SuppressWarnings("unused")
@Path("/test/dosome/")
public class DoSomethingWebApi {

	@Inject
	ParallelTest parallelTest;
	
	@GET
	public void doSomething() {
		this.parallelTest.run();
		//CDI.current().select(SampleScheduler.class).get().schedule();
	}
}
