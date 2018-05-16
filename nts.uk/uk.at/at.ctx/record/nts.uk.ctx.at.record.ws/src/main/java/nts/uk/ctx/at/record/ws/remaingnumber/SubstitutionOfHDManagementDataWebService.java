package nts.uk.ctx.at.record.ws.remaingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana.DeletePayoutManagementDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana.DeleteSubstitutionOfHDManaDataCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana.UpdateSubstitutionOfHDManaDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana.UpdateSubstitutionOfHDManaDataCommandHandler;
import nts.uk.ctx.at.record.app.find.remainingnumber.paymana.SubstitutionOfHDManagementDataDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.paymana.SubstitutionOfHDManagementDataFinder;

@Path("at/record/remainnumber/subhd")
@Produces("application/json")
public class SubstitutionOfHDManagementDataWebService extends WebService {

	@Inject
	private DeleteSubstitutionOfHDManaDataCommandHandler deleteSub;
	
	@Inject
	private UpdateSubstitutionOfHDManaDataCommandHandler updateSub;
	
	@Inject
	SubstitutionOfHDManagementDataFinder finder;
	

	@POST
	@Path("update")
	public void update(UpdateSubstitutionOfHDManaDataCommand command){
		updateSub.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delete(DeletePayoutManagementDataCommand command){
		deleteSub.handle(command);
	}
	
	@POST
	@Path("getBysiDRemCod/{empId}")
	// get SubstitutionOfHDManagement by SID and remainsDays > 0
	public List<SubstitutionOfHDManagementDataDto> getBysiDRemCod(@PathParam("empId") String employeeId) {
		return finder.getBysiDRemCod(employeeId);
	}
	
	@POST
	@Path("getBySidDatePeriod/{empId}/{startDate}/{endDate}")
	public List<SubstitutionOfHDManagementDataDto> getBySidDatePeriod(@PathParam("empId")String sid, @PathParam("startDate")GeneralDate startDate,@PathParam("endDate") GeneralDate endDate) {
		return finder.getBySidDatePeriod(sid, startDate, endDate);
	}
}
