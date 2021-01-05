package nts.uk.ctx.at.shared.ws.workrule.workinghours;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.workrule.workinghours.CheckTimeIsIncorrect;
import nts.uk.ctx.at.shared.app.workrule.workinghours.CheckTimeIsIncorrectCmd;

/**
 * 
 * @author tutk
 *
 */
@Path("ctx/at/shared/workrule/workinghours")
@Produces("application/json")
public class CheckTimeIsIncorrectWS extends WebService {
	
	@Inject
	private  CheckTimeIsIncorrect  checkTimeIsIncorrect;
	
	@POST
	@Path("checkTimeIsIncorrect")
	public boolean checkTimeIsIncorrect(CheckTimeIsIncorrectCmd command){
		boolean check = checkTimeIsIncorrect.check(command.getWorkType(), command.getWorkTime(), command.getWorkTime1(), command.getWorkTime2());
		return check;
	}

}
