package nts.uk.ctx.at.schedule.ws.shift.total.times;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.shift.total.times.TotalTimesFinder;
import nts.uk.ctx.at.schedule.app.find.shift.total.times.dto.TotalTimesDto;

@Path("ctx/at/schedule/shift/total/times")
@Produces("application/json")
public class TotalTimesWS extends WebService {

	@Inject
	private TotalTimesFinder getAllTotalTimes;	
	
	
	
	
	
	
	
	@POST
	@Path("getalltotaltimes")
	public List<TotalTimesDto> getAllTotalTimes(){
		return this.getAllTotalTimes.getAllTotalTimes();
	}
	
		
	
	
	
}
