package nts.uk.ctx.at.record.ws.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.stampmanagement.setting.preparation.smartphonestamping.employee.DeleteStampingAreaRestrictionCommandHandler;
import nts.uk.ctx.at.record.app.command.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaCmd;
import nts.uk.ctx.at.record.app.command.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestrictionCommandHandler;
import nts.uk.ctx.at.record.app.query.stampmanagement.setting.preparation.smartphonestamping.employee.CommonObjectParam;
import nts.uk.ctx.at.record.app.query.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestrictionDto;
import nts.uk.ctx.at.record.app.query.stampmanagement.setting.preparation.smartphonestamping.employee.StampingSettingEmplFindOneQuery;
import nts.uk.ctx.at.record.app.query.stampmanagement.setting.preparation.smartphonestamping.employee.StampingSettingEmployeeQuery;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.EmployeeStampingAreaRestrictionSetting;

@Path("at/record/stampmanagement/setting/preparation/smartphonestamping/employee")
@Produces("application/json")
public class StampingAreaRestrictionWS {
	@Inject
	private StampingAreaRestrictionCommandHandler stampingAreaRestrictionCommandHandler;

	@Inject
	private DeleteStampingAreaRestrictionCommandHandler deleteStampingAreaRestrictionCommandHandler;

	@Inject
	private StampingSettingEmployeeQuery stampingSettingEmployeeQuery;

	@Inject
	private StampingSettingEmplFindOneQuery emplFindOneQuery;

	@POST
	@Path("insertUpdateStampingSetting")
	public Boolean insert(StampingAreaCmd cmd) {
		this.stampingAreaRestrictionCommandHandler.handle(cmd);
		return true;
	}

	@POST
	@Path("getStatusStampingEmpl")
	public List<String> getNamesByCodes(CommonObjectParam listEmplId) {
		return this.stampingSettingEmployeeQuery.getStatuEmployee(listEmplId.listId);
	}

	@POST
	@Path("delete")
	public void delete(StampingAreaCmd cmd) {
		this.deleteStampingAreaRestrictionCommandHandler.handle(cmd);
	}

	@POST
	@Path("findOneById/{selectedEmpl}")
	public StampingAreaRestrictionDto findOneById(@PathParam("selectedEmpl") String employeeId) {
		return this.emplFindOneQuery.getStatuEmployee(employeeId);
	}

}
