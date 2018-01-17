package nts.uk.ctx.at.record.ws.workrecord.erroralarm.condition;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.condition.AddWorkRecordExtraConCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.condition.DeleteWorkRecordExtraConCommand;
import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.condition.DeleteWorkRecordExtraConCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.condition.UpdateWorkRecordExtraConCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.condition.WorkRecordExtraConCommand;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.condition.WorkRecordExtraConDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.condition.WorkRecordExtraConFinder;

@Path("at/record/condition/workRecordextracon")
@Produces(MediaType.APPLICATION_JSON)
public class WorkRecordExtraConWS {
	
	@Inject
	private WorkRecordExtraConFinder finder;
	
	@Inject
	private AddWorkRecordExtraConCommandHandler addWorkRecordExtraCon;
	
	@Inject
	private UpdateWorkRecordExtraConCommandHandler updateWorkRecordExtraCon;
	
	@Inject
	private DeleteWorkRecordExtraConCommandHandler deleteWorkRecordExtraCon;
	
	
	@POST
	@Path("getallworkRecordextracon")
	public List<WorkRecordExtraConDto> getAllWorkRecordExtraCon(){
		List<WorkRecordExtraConDto> data = finder.getAllWorkRecordExtraCon();
		return data;
	}
	
	@POST
	@Path("getworkRecordextraconbyid/{errorAlarmCheckID}")
	public WorkRecordExtraConDto getWorkRecordExtraConByID(@PathParam("errorAlarmCheckID") String errorAlarmCheckID){
		WorkRecordExtraConDto data = finder.getWorkRecordExtraConById(errorAlarmCheckID);
		return data;
	}
	
	@POST
	@Path("addworkRecordextracon")
	public void addWorkRecordExtraCon(WorkRecordExtraConCommand command){
		this.addWorkRecordExtraCon.handle(command);
	}
	
	@POST
	@Path("updateworkRecordextracon")
	public void updateWorkRecordExtraCon(WorkRecordExtraConCommand command){
		this.updateWorkRecordExtraCon.handle(command);
	}
	

	@POST
	@Path("deleteworkRecordextracon")
	public void deleteWorkRecordExtraCon(DeleteWorkRecordExtraConCommand command){
		this.deleteWorkRecordExtraCon.handle(command);
	}
}
