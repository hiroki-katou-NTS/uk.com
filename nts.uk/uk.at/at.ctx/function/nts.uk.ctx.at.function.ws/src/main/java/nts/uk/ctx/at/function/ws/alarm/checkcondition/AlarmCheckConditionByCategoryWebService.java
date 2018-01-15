package nts.uk.ctx.at.function.ws.alarm.checkcondition;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.AlarmCheckConditionByCategoryCommand;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.DeleteAlarmCheckConditionByCategoryCommandHandler;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.RegisterAlarmCheckCondtionByCategoryCommandHandler;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.AlarmCheckConditionByCategoryDto;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.AlarmCheckConditionByCategoryFinder;

/**
 * 
 * @author HungTT
 *
 */

@Path("at/function/alarm/checkcondition")
@Produces("application/json")
public class AlarmCheckConditionByCategoryWebService extends WebService {

	@Inject
	private AlarmCheckConditionByCategoryFinder finder;
	
	@Inject
	private RegisterAlarmCheckCondtionByCategoryCommandHandler regHandler;

	@Inject
	private DeleteAlarmCheckConditionByCategoryCommandHandler delHandler;
	
	@POST
	@Path("findAll")
	public List<AlarmCheckConditionByCategoryDto> findAll(int category){
		return this.finder.getAllData(category);
	}
	
	@POST
	@Path("register")
	public void register(AlarmCheckConditionByCategoryCommand command){
		this.regHandler.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delete(AlarmCheckConditionByCategoryCommand command){
		this.delHandler.handle(command);
	}
}
