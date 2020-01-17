package nts.uk.ctx.hr.develop.ws.empregulationhistory;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.uk.ctx.hr.develop.app.empregulationhistory.command.EmploymentRegulationHistoryCommand;
import nts.uk.ctx.hr.develop.app.empregulationhistory.dto.DateHistoryItemDto;
import nts.uk.ctx.hr.develop.app.empregulationhistory.dto.DateHistoryItemDtoParam;
import nts.uk.ctx.hr.develop.app.empregulationhistory.find.EmploymentRegulationHistoryFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("employmentRegulationHistory")
@Produces(MediaType.APPLICATION_JSON)
public class EmploymentRegulationHistoryWS {

	@Inject
	private EmploymentRegulationHistoryFinder finder;
	
	@Inject
	private EmploymentRegulationHistoryCommand command;
	
	@POST
	@Path("/getDateHistoryItem")
	public List<DateHistoryItemDto> getDateHistoryItem(){
		String cId = AppContexts.user().companyId();
		return finder.getList(cId);
	}
	
	@POST
	@Path("/saveDateHistoryItem")
	public JavaTypeResult<String> saveDateHistoryItem(DateHistoryItemDtoParam dto){
		return new JavaTypeResult<String>(command.add(dto));
	}
	
	@POST
	@Path("/updateDateHistoryItem")
	public void updateDateHistoryItem(DateHistoryItemDtoParam dto){
		command.update(dto);
	}
	
	@POST
	@Path("/removeDateHistoryItem")
	public void removeDateHistoryItem(DateHistoryItemDtoParam dto){
		command.remove(dto);
	}
	
	@POST
	@Path("/getLatestHistId")
	public JavaTypeResult<String> getLatestHistId(){
		String cId = AppContexts.user().companyId();
		return new JavaTypeResult<String> (finder.getLatestEmpRegulationHist(cId));
	}
	
}
