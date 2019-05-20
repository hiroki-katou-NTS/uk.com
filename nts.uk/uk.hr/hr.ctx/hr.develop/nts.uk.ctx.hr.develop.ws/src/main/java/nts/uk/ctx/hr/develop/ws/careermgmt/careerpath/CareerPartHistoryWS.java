package nts.uk.ctx.hr.develop.ws.careermgmt.careerpath;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.command.AddCareerPathHistoryCommandHandler;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.command.RemoveCareerPathHistoryCommandHandler;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.command.UpdateCareerPathHistoryCommandHandler;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.find.CareerPathHistoryFinder;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.find.DateHistoryItemDto;
import nts.uk.shr.com.context.AppContexts;

@Path("careermgmt/careerpath")
@Produces(MediaType.APPLICATION_JSON)
public class CareerPartHistoryWS {

	@Inject
	private CareerPathHistoryFinder finder;
	
	@Inject
	private AddCareerPathHistoryCommandHandler commandAdd;
	
	@Inject
	private UpdateCareerPathHistoryCommandHandler commandUpdate;
	
	@Inject
	private RemoveCareerPathHistoryCommandHandler commandRemove;
	
	@POST
	@Path("/getDateHistoryItem")
	public List<DateHistoryItemDto> getDateHistoryItem(){
		String cId = AppContexts.user().companyId();
		return finder.getCareerPathHistory(cId);
	}
	
	@POST
	@Path("/saveDateHistoryItem")
	public String saveDateHistoryItem(GeneralDate startDate){
		return commandAdd.add(startDate);
	}
	
	@POST
	@Path("/updateDateHistoryItem")
	public void updateDateHistoryItem(String historyId, GeneralDate startDate){
		commandUpdate.update(historyId, startDate);
	}
	
	@POST
	@Path("/removeDateHistoryItem")
	public void removeDateHistoryItem(String historyId){
		commandRemove.remove(historyId);
	}
}
