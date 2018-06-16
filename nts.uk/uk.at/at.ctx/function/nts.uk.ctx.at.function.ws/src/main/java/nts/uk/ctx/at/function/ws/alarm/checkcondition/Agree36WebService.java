package nts.uk.ctx.at.function.ws.alarm.checkcondition;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.UpdateAgreeCondOtCommand;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.UpdateAgreeCondOtCommandHandler;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.UpdateAgreeConditionErrorCommand;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.UpdateAgreeConditionErrorCommandHandler;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36.AgreeCondOtDto;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36.AgreeCondOtFinder;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36.AgreeConditionErrorDto;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36.AgreeConditionErrorFinder;

@Path("at/function/alarm/checkcondition/agree36")
@Produces("application/json")
public class Agree36WebService extends WebService{
	@Inject
	private AgreeCondOtFinder condOtFinder;
	@Inject
	private AgreeConditionErrorFinder condErrorFinder;
	@Inject
	private UpdateAgreeConditionErrorCommandHandler updateConditionError;
	@Inject
	private UpdateAgreeCondOtCommandHandler updateConOt;
	
//	@POST
//	@Path("findcondot")
//	public List<AgreeCondOtDto> finder(){
//		return this.condOtFinder.finder();
//	}
	
//	@POST
//	@Path("finderror")
//	public List<AgreeConditionErrorDto> findError(){
//		return this.condErrorFinder.finder();
//	}
	
	@POST
	@Path("updateerror")
	public void updateConditionError(UpdateAgreeConditionErrorCommand command){
		this.updateConditionError.handle(command);
	}
	
	@POST
	@Path("updateot")
	public void updateConditionOt(UpdateAgreeCondOtCommand command){
		this.updateConOt.handle(command);
	}
	
}
