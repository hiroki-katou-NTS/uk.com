package nts.uk.ctx.at.record.ws.remaingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana.AddPayManaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana.DeletePayoutManagementDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana.DeletePayoutManagementDataCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana.PayManaRemainCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana.PayoutManagementDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana.UpdatePayoutManagementDataCommandHandler;
import nts.uk.ctx.at.record.app.find.remainingnumber.paymana.PayoutManagementDataDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.paymana.PayoutManagementDataFinder;

@Path("at/record/remainnumber")
@Produces("application/json")
public class PayoutManagementDataWebService extends WebService{
   
	@Inject
	private AddPayManaCommandHandler addHandler;
	
	@Inject
    private DeletePayoutManagementDataCommandHandler deletePayout;
	
	@Inject
    private UpdatePayoutManagementDataCommandHandler updatePayout;
	
	@Inject
	private PayoutManagementDataFinder finder;

	@POST
	@Path("save")
	public void save(PayManaRemainCommand command) {
		addHandler.handle(command);
	}
	
	@POST
	@Path("update")
	public void update(PayoutManagementDataCommand command){
		System.out.println("******************update");
		updatePayout.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delete(DeletePayoutManagementDataCommand command){
		System.out.println("******************update");
		deletePayout.handle(command);
	}
	
	@POST
	@Path("getBysiDRemCod/{empId}/{state}")
	// get SubstitutionOfHDManagement by SID and stateAtr = ?
	public List<PayoutManagementDataDto> getBysiDRemCod(@PathParam("empId")String employeeId, @PathParam("state")int state) {
		return finder.getBysiDRemCod(employeeId, state);
	}
	
	@POST
	@Path("getBySidDatePeriod/{empId}/{subOfHDID}")
	public List<PayoutManagementDataDto> getBySidDatePeriod(@PathParam("empId")String sid,@PathParam("subOfHDID")String subOfHDID) {
		return finder.getBySidDatePeriod(sid, subOfHDID);
	}
	
	@POST
	@Path("getBySidDatePeriodNoDigestion/{empId}/{startDate}/{endDate}")
	public List<PayoutManagementDataDto> getBySidDatePeriodNoDigestion(@PathParam("empId")String sid, GeneralDate startDate, GeneralDate endDate) {
		return finder.getBySidDatePeriodNoDigestion(sid, startDate, endDate);
	}
}
