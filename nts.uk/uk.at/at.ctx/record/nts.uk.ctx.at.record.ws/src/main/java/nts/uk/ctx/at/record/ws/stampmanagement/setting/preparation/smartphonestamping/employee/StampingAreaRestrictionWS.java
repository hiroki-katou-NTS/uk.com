package nts.uk.ctx.at.record.ws.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.stampmanagement.setting.preparation.smartphonestamping.employee.DeleteStampingAreaRestrictionCommand;
import nts.uk.ctx.at.record.app.command.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestrictionCommand;
import nts.uk.ctx.at.record.app.command.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestrictionCommandHandler;
import nts.uk.ctx.at.record.app.query.stampmanagement.setting.preparation.smartphonestamping.employee.StampingSettingEmplFindOneQuery;
import nts.uk.ctx.at.record.app.query.stampmanagement.setting.preparation.smartphonestamping.employee.StampingSettingEmployeeQuery;

@Path("at/record/stampmanagement/setting/preparation/smartphonestamping/employee")
@Produces("application/json")
public class StampingAreaRestrictionWS {
	@Inject 
	private StampingAreaRestrictionCommandHandler stampingAreaRestrictionCommandHandler;
	
	@Inject
	private DeleteStampingAreaRestrictionCommand seleteStampingAreaRestrictionCommand;
	
	@Inject 
	private StampingSettingEmployeeQuery stampingSettingEmployeeQuery;
	
	@Inject
	private StampingSettingEmplFindOneQuery emplFindOneQuery;
	
	@POST
	@Path("insertUpdateStampingSetting")
	public Boolean  insert(StampingAreaRestrictionCommand areaRestrictionCommand) {
		 this.stampingAreaRestrictionCommandHandler.handle(areaRestrictionCommand);
		 return true;
	}
	
	@POST
	@Path("getStatusStampingEmpl")
	public List<String> getNamesByCodes(List<String> listEmplId) {
		return this.stampingSettingEmployeeQuery.getStatuEmployee(listEmplId);
	}
	
	@POST
	@Path("delete")
	public void delete(StampingAreaRestrictionCommand command) {
		this.seleteStampingAreaRestrictionCommand.handle(command);
	}
	
	@POST
	@Path("findOneById")
	public StampingAreaRestrictionCommand findOneById (StampingAreaRestrictionCommand areaRestrictionCommand) {
		return this.emplFindOneQuery.getStatuEmployee(areaRestrictionCommand.getEmployeeId());
	}
	
}
