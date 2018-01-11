package nts.uk.ctx.at.record.ws.workrecord.erroralarm;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.AddFixedConWorkRecordCmdHandler;
import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.DeleteFixedConWorkRecordCmd;
import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.DeleteFixedConWorkRecordCmdHandler;
import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.FixedConditionWorkRecordCmd;
import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.UpdateFixedConWorkRecordCmdHandler;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.FixedConditionWorkRecordDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.FixedConditionWorkRecordFinder;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.InputParamGetFixedCon;

@Path("at/record/erroralarm")
@Produces(MediaType.APPLICATION_JSON)
public class FixedConWorkRecordWS {
	
	@Inject
	private FixedConditionWorkRecordFinder finder;
	
	@Inject
	private AddFixedConWorkRecordCmdHandler add;
	
	@Inject
	private UpdateFixedConWorkRecordCmdHandler update;
	
	@Inject
	private DeleteFixedConWorkRecordCmdHandler delete;
	
	@POST
	@Path("getallfixedconwr")
	public List<FixedConditionWorkRecordDto> getAllFixedConditionWorkRecord(){
		List<FixedConditionWorkRecordDto> data = finder.getAllFixedConditionWorkRecord();
		return data;
	}
	
	@POST
	@Path("getallfixedconwrbyalarmid/{errorAlarmID}")
	public List<FixedConditionWorkRecordDto> getAllFixedConWRByAlarmID(@PathParam("errorAlarmID") String errorAlarmID){
		List<FixedConditionWorkRecordDto> data = finder.getAllFixedConWRByAlarmID(errorAlarmID);
		return data;
	}
	
	@POST
	@Path("getallfixedconwrbycode")
	public FixedConditionWorkRecordDto getFixedConWRByCode(InputParamGetFixedCon inputParamGetFixedCon){
		FixedConditionWorkRecordDto data = finder.getFixedConWRByCode(inputParamGetFixedCon);
		return data;
	}
	//command
	@POST
	@Path("addfixedconwr")
	public void addFixedConditionWorkRecord(FixedConditionWorkRecordCmd  command){
		this.add.handle(command);
	}
	
	@POST
	@Path("updatefixedconwr")
	public void updateFixedConditionWorkRecord(FixedConditionWorkRecordCmd  command){
		this.update.handle(command);
	}
	
	@POST
	@Path("deletefixedconwr")
	public void deleteFixedConditionWorkRecord(DeleteFixedConWorkRecordCmd  command){
		this.delete.handle(command);
	}
	
}
