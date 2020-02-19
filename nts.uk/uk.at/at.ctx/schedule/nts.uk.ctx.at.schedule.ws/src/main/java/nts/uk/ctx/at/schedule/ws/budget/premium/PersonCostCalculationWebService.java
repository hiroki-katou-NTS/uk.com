package nts.uk.ctx.at.schedule.ws.budget.premium;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.premium.DeletePersonCostCalculationCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.premium.InsertPersonCostCalculationCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.premium.UpdatePersonCostCalculationCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.premium.UpdatePremiumItemCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.premium.command.PersonCostCalculationCommand;
import nts.uk.ctx.at.schedule.app.command.budget.premium.command.UpdatePremiumItemCommand;
import nts.uk.ctx.at.schedule.app.find.budget.premium.PersonCostCalculationFinder;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.PersonCostCalculationSettingDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.PremiumItemDto;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.AttendanceNamePriniumDto;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.AttendanceTypePriServiceDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Path("at/schedule/budget/premium")
@Produces("application/json")
public class PersonCostCalculationWebService extends WebService{
	
	@Inject
	private InsertPersonCostCalculationCommandHandler insertPersonCostCalculationSettingCommandHandler;
	
	@Inject
	private PersonCostCalculationFinder personCostCalculationSettingFinder;
	
	@Inject
	private UpdatePersonCostCalculationCommandHandler updatePersonCostCalculationSettingCommandHandler;
	
	@Inject
	private DeletePersonCostCalculationCommandHandler deletePersonCostCalculationSettingCommandHandler;
	
	@Inject
	private UpdatePremiumItemCommandHandler updatePremiumItemCommandHandler;
	
	@POST
	@Path("insertPersonCostCalculation")
	public void insert(PersonCostCalculationCommand command){
		this.insertPersonCostCalculationSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("findPersonCostCalculationByCompanyID")
	public List<PersonCostCalculationSettingDto> findPersonCostCalculationByCompanyID(){
		return this.personCostCalculationSettingFinder.findPersonCostCalculationByCompanyID();
	}
	
	@POST
	@Path("findByHistoryID")
	public PersonCostCalculationSettingDto findByHistoryID(String historyID){
		return this.personCostCalculationSettingFinder.findByHistoryID(historyID);
	}
	
	@POST
	@Path("updatePersonCostCalculation")
	public void update(PersonCostCalculationCommand command){
		this.updatePersonCostCalculationSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("deletePersonCostCalculation")
	public void delete(PersonCostCalculationCommand command){
		this.deletePersonCostCalculationSettingCommandHandler.handle(command);
	}

	@POST
	@Path("findPremiumItemByCompanyID")
	public List<PremiumItemDto> findPremiumItemByCompanyID(){
		return this.personCostCalculationSettingFinder.findPremiumItemByCompanyID();
	}
	
	@POST
	@Path("updatePremiumItem")
	public void update(List<UpdatePremiumItemCommand> command){
		this.updatePremiumItemCommandHandler.handle(command);
	}
	
	@POST
	@Path("attendancePremiumItem")
	public List<AttendanceTypePriServiceDto> findAttendanceType(){
		//人数：0
		return this.personCostCalculationSettingFinder.atTypes(0);
	}
	@POST
	@Path("attendancePremiumName")
	public List<AttendanceNamePriniumDto> findAttendanceName(List<Integer> dailyAttendanceItemIds){
		return this.personCostCalculationSettingFinder.atNames(dailyAttendanceItemIds);
	}
	
	@POST
	@Path("getByCIdAndLangId/{langId}")
	public List<PremiumItemDto> findWorkTypeLanguage(@PathParam("langId") String langId) {
		return this.personCostCalculationSettingFinder.findWorkTypeLanguage(langId);
	}
}
