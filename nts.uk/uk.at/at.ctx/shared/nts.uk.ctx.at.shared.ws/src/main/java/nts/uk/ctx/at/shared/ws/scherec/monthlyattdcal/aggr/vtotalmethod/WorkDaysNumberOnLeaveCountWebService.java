package nts.uk.ctx.at.shared.ws.scherec.monthlyattdcal.aggr.vtotalmethod;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.scherec.monthlyattdcal.aggr.vtotalmethod.RegisterWorkDaysNumberOnLeaveCountCommand;
import nts.uk.ctx.at.shared.app.command.scherec.monthlyattdcal.aggr.vtotalmethod.RegisterWorkDaysNumberOnLeaveCountCommandHandler;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattdcal.aggr.vtotalmethod.WorkDaysNumberOnLeaveCountDto;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattdcal.aggr.vtotalmethod.WorkDaysNumberOnLeaveCountFinder;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;

@Path("at/shared/scherec/leaveCount")
@Produces("application/json")
public class WorkDaysNumberOnLeaveCountWebService extends WebService {

	@Inject
	private WorkDaysNumberOnLeaveCountFinder workDaysNumberOnLeaveCountFinder;
	
	@Inject
	private RegisterWorkDaysNumberOnLeaveCountCommandHandler registerCommandHandler;
	
	@POST
	@Path("/get")
	public WorkDaysNumberOnLeaveCountDto getByCid() {
		return this.workDaysNumberOnLeaveCountFinder.findByCid();
	}
	
	@POST
	@Path("/register")
	public void register(RegisterWorkDaysNumberOnLeaveCountCommand command) {
		this.registerCommandHandler.handle(command);
	}
	
    @POST
    @Path("/timemanagementdistinct")
    public List<EnumConstant> findTimeManagementDistinct() {
        return EnumAdaptor.convertToValueNameList(ManageDistinct.class);
    }
    
    @POST
    @Path("/timeunit")
    public List<EnumConstant> findTimeVacationDigestiveUnit() {
        return EnumAdaptor.convertToValueNameList(TimeDigestiveUnit.class);
    }
}
