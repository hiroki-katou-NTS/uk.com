package nts.uk.shr.sample.task.schedule;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.val;
import nts.arc.task.AsyncTaskInfoRepository;
import nts.arc.time.GeneralDateTime;

@Path("/sample/schedule")
@Produces("application/json")
public class SampleScheduleWebService {

	@Inject
	private AsyncTaskInfoRepository repo;
	
	@POST
	@Path("test")
	public void test() {
		val baseDateTime = GeneralDateTime.ymdhms(2018, 2, 1, 0, 0, 0);
		this.repo.removeOldTasks(baseDateTime);
	}
}
