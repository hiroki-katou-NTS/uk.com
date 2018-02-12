package nts.uk.screen.at.ws.dailyperformance.correction;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.dailyperformance.correction.dto.DPDataDto;
import nts.uk.screen.at.app.dailyperformance.correction.lazyload.LazyLoadProcess;

@Path("/kdw003/lazyload")
@Produces("application/json")
public class DailyLoadGridWebService {

	@Inject
	private LazyLoadProcess lazyLoadProcess;

	@POST
	@Path("keys")
	public List<String> keys() {
		return lazyLoadProcess.keys();
	}

	@POST
	@Path("data")
	public List<DPDataDto> getData(List<String> keys) throws InterruptedException {
		return lazyLoadProcess.getData(keys);
	}
}
