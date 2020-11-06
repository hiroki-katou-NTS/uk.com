package nts.uk.ctx.at.shared.ws.remaingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.remainingnumber.paymana.DeleteSubstitutionOfHDManaDataCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.paymana.DeleteSubstitutionOfHDManaDataCommandHandler;
import nts.uk.ctx.at.shared.app.command.remainingnumber.paymana.PayoutSubofHDManagementCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.paymana.PayoutSubofHDManagementCommandHandler;
import nts.uk.ctx.at.shared.app.command.remainingnumber.paymana.UpdateSubstitutionOfHDManaDataCommand;
import nts.uk.ctx.at.shared.app.command.remainingnumber.paymana.UpdateSubstitutionOfHDManaDataCommandHandler;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.SubstitutionOfHDManagementDataDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.SubstitutionOfHDManagementDataFinder;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.SubstitutionManagementDataFinder;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.SubDataSearchConditionDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.SubstituteDataManagementDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.DisplayRemainingNumberDataInformation;

@Path("at/record/remainnumber/subhd")
@Produces("application/json")
public class SubstitutionOfHDManagementDataWebService extends WebService {

	@Inject
	private DeleteSubstitutionOfHDManaDataCommandHandler deleteSub;
	
	@Inject
	private UpdateSubstitutionOfHDManaDataCommandHandler updateSub;
	
	@Inject
	private SubstitutionOfHDManagementDataFinder finder;
	
	@Inject
	private PayoutSubofHDManagementCommandHandler payoutSubofHDManagementCommandHandler;
	
	@Inject
	private SubstitutionManagementDataFinder subManagementFinder;
	@POST
	@Path("update")
	public List<String> update(UpdateSubstitutionOfHDManaDataCommand command){
		return updateSub.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delete(DeleteSubstitutionOfHDManaDataCommand command){
		deleteSub.handle(command);
	}
	
	@POST
	@Path("getBysiDRemCod/{empId}")
	// get SubstitutionOfHDManagement by SID and remainsDays > 0
	public List<SubstitutionOfHDManagementDataDto> getBysiDRemCod(@PathParam("empId")String employeeId) {
		return finder.getBysiDRemCod(employeeId);
	}
	
	@POST
	@Path("getBySidRemainDayAndInPayout/{empId}")
	// get SubstitutionOfHDManagement by SID and remainsDays != 0, and in PayoutMng 
	public List<SubstitutionOfHDManagementDataDto> getBySidRemainDayAndInPayout(@PathParam("empId")String employeeId) {
		return finder.getBySidRemainDayAndInPayout(employeeId);
	}

//	@POST
//	@Path("getBySidDatePeriod/{empId}/{payoutID}")
//	public List<SubstitutionOfHDManagementDataDto> getBySidDatePeriod(@PathParam("empId")String sid,@PathParam("payoutID")String payoutID) {
//		return finder.getBySidDatePeriod(sid,payoutID);
//	}
	
	@POST
	@Path("insertSubOfHDMan")
	public void insertSubOfHDMan(PayoutSubofHDManagementCommand command){
		payoutSubofHDManagementCommandHandler.handle(command);
	}
	
	@POST
	@Path("getSubsitutionData")
	public SubstituteDataManagementDto getSubsitutionData(SubDataSearchConditionDto dto){
		return subManagementFinder.getSubstituteManagementData(dto);
	}
	
	@POST
	@Path("getExtraHolidayData")
	public DisplayRemainingNumberDataInformation getExtraHolidayData(SubDataSearchConditionDto dto){
		return subManagementFinder.getExtraHolidayManagementDataUpdate(dto);
	}	
}