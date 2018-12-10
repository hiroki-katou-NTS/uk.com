package nts.uk.shr.sample.batchserver;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Value;
import lombok.val;
import nts.uk.shr.com.communicate.PathToWebApi;
import nts.uk.shr.com.communicate.batch.BatchServer;

@Stateless
public class APBatchTask {

	@Inject
	private BatchTask service;

	@Inject
	private BatchServer batchServer;
	
	public void execute() {

		if (this.batchServer.exists()) {
			val webApi = this.batchServer.webApi(PathToWebApi.com("/batch/task"), SampleRequest.class);
			this.batchServer.request(webApi, c -> c.entity(new SampleRequest("hello")).succeeded(x -> {
			}));
		} else {
			service.doSomething();
		}
		
	}

	@Value
	public static class SampleRequest {
		private final String value;
	}
}
