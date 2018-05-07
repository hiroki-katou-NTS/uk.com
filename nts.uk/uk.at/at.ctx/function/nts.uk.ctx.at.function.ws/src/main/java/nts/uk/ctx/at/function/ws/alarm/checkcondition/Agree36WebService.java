package nts.uk.ctx.at.function.ws.alarm.checkcondition;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36.AgreeCondOtDto;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36.AgreeCondOtFinder;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36.AgreeConditionErrorDto;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36.AgreeConditionErrorFinder;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36.AgreeNameErrorDto;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36.AgreeNameErrorFinder;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36.ParamFindName;

@Path("at/function/alarm/checkcondition/agree36")
@Produces("application/json")
public class Agree36WebService extends WebService{
	@Inject
	private AgreeCondOtFinder condOtFinder;
	@Inject
	private AgreeNameErrorFinder nameFinder;
	@Inject
	private AgreeConditionErrorFinder condErrorFinder;
	
	@POST
	@Path("findCondOt")
	public List<AgreeCondOtDto> finder(){
		return this.condOtFinder.finder();
	}
	
	@POST
	@Path("findNameError")
	public List<AgreeNameErrorDto> findName(ParamFindName param){
		return this.nameFinder.finder(param);
	}
	
	@POST
	@Path("findNameError")
	public List<AgreeConditionErrorDto> findError(){
		return this.condErrorFinder.finder();
	}
}
