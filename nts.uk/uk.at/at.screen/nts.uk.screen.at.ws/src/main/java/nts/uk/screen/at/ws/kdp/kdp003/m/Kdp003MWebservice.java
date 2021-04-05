package nts.uk.screen.at.ws.kdp.kdp003.m;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.command.kdp.kdp003.m.SelectingWorkplacesForStampInput;
import nts.uk.screen.at.app.command.kdp.kdp003.m.SelectingWorkplacesForStampInputDto;
import nts.uk.screen.at.app.command.kdp.kdp003.m.SelectingWorkplacesForStampInputParam;

/**
 * 
 * @author chungnt
 *
 */

@Path("screen/at/kdp003")
@Produces("application/json")
public class Kdp003MWebservice extends WebService {

	@Inject
	private SelectingWorkplacesForStampInput forStampInput;

	@POST
	@Path("workplace-info")
	public SelectingWorkplacesForStampInputDto get(SelectingWorkplacesForStampInputParam param) {
		return forStampInput.get(param);
	}

}
