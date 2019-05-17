package nts.uk.ctx.hr.develop.ws.careermgmt.careerpath;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.dto.DateHistoryItemDto;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.find.CareerPathHistoryFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("careermgmt/careerpath")
@Produces(MediaType.APPLICATION_JSON)
public class CareerPartHistoryWS {

	@Inject
	private CareerPathHistoryFinder finder; 
	
	@POST
	@Path("/getDateHistoryItem")
	public List<DateHistoryItemDto> getDateHistoryItem(){
		String cId = AppContexts.user().companyId();
		List<DateHistoryItemDto> a = finder.getCareerPathHistory(cId);
		return a;
	}
}
