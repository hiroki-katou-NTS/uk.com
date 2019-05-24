package nts.uk.ctx.hr.develop.ws.careermgmt.careerpath;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.command.AddCareerPathHistoryCommandHandler;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.command.CareerPartHistoryCommand;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.command.RemoveCareerPathHistoryCommandHandler;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.command.UpdateCareerPathHistoryCommandHandler;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.dto.CareerPartDto;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.dto.DateHistoryItemDto;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.find.CareerPathFinder;
import nts.uk.ctx.hr.develop.app.careermgmt.careerpath.find.CareerPathHistoryFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("careermgmt/careerpath")
@Produces(MediaType.APPLICATION_JSON)
public class CareerPartWS {

	@Inject
	private CareerPathHistoryFinder finder;
	
	@Inject
	private AddCareerPathHistoryCommandHandler commandAdd;
	
	@Inject
	private UpdateCareerPathHistoryCommandHandler commandUpdate;
	
	@Inject
	private RemoveCareerPathHistoryCommandHandler commandRemove;
	
	@Inject
	private CareerPathFinder careerPathFinder;
	
	@POST
	@Path("/getDateHistoryItem")
	public List<DateHistoryItemDto> getDateHistoryItem(){
		String cId = AppContexts.user().companyId();
		return finder.getCareerPathHistory(cId);
	}
	
	@POST
	@Path("/saveDateHistoryItem")
	public JavaTypeResult<String> saveDateHistoryItem(CareerPartHistoryCommand command){
		return new JavaTypeResult<String>(commandAdd.add(GeneralDate.fromString(command.getStartDate(), "yyyy/MM/dd")));
	}
	
	@POST
	@Path("/updateDateHistoryItem")
	public void updateDateHistoryItem(CareerPartHistoryCommand command){
		commandUpdate.update(command.getHistoryId(), GeneralDate.fromString(command.getStartDate(), "yyyy/MM/dd"));
	}
	
	@POST
	@Path("/removeDateHistoryItem")
	public void removeDateHistoryItem(CareerPartHistoryCommand command){
		commandRemove.remove(command.getHistoryId());
	}
	
	@POST
	@Path("/getMaxClassLevel")
	public JavaTypeResult<Integer> getMaxClassLevel(){
		String cId = AppContexts.user().companyId();
		return new JavaTypeResult<Integer>(careerPathFinder.getMaxClassLevel(cId));
	}
	
	@POST
	@Path("/getCareerPart")
	public CareerPartDto getCareerPart(CareerPartHistoryCommand command){
		String cId = AppContexts.user().companyId();
		return careerPathFinder.getCareerPath(cId, command.getHistoryId(), GeneralDate.fromString(command.getStartDate(), "yyyy/MM/dd"));
	}
	
	
}
