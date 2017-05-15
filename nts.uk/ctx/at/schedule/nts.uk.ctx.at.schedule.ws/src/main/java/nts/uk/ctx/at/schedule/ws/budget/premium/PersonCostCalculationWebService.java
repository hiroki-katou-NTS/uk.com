package nts.uk.ctx.at.schedule.ws.budget.premium;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.premium.DeletePersonCostCalculationSettingCommand;
import nts.uk.ctx.at.schedule.app.command.budget.premium.DeletePersonCostCalculationSettingCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.premium.InsertPersonCostCalculationSettingCommand;
import nts.uk.ctx.at.schedule.app.command.budget.premium.InsertPersonCostCalculationSettingCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.premium.UpdatePersonCostCalculationSettingCommand;
import nts.uk.ctx.at.schedule.app.command.budget.premium.UpdatePersonCostCalculationSettingCommandHandler;
import nts.uk.ctx.at.schedule.app.find.budget.premium.PersonCostCalculationSettingDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.PersonCostCalculationSettingFinder;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Path("at/budget/premium")
@Produces("application/json")
public class PersonCostCalculationWebService extends WebService{
	
	@Inject
	private InsertPersonCostCalculationSettingCommandHandler insertPersonCostCalculationSettingCommandHandler;
	
	@Inject
	private PersonCostCalculationSettingFinder personCostCalculationSettingFinder;
	
	@Inject
	private UpdatePersonCostCalculationSettingCommandHandler updatePersonCostCalculationSettingCommandHandler;
	
	@Inject
	private DeletePersonCostCalculationSettingCommandHandler deletePersonCostCalculationSettingCommandHandler;
	
	@POST
	@Path("insert")
	public void insert(InsertPersonCostCalculationSettingCommand command){
		this.insertPersonCostCalculationSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("findBycompanyID")
	public List<PersonCostCalculationSettingDto> find(){
		return this.personCostCalculationSettingFinder.findByCompanyID();
	}
	
	@POST
	@Path("update")
	public void update(UpdatePersonCostCalculationSettingCommand command){
		this.updatePersonCostCalculationSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delete(DeletePersonCostCalculationSettingCommand command){
		this.deletePersonCostCalculationSettingCommandHandler.handle(command);
	}

}
