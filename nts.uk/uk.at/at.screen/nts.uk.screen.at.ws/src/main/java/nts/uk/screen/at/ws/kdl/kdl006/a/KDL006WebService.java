package nts.uk.screen.at.ws.kdl.kdl006.a;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.kdl.kdl006.KDL006CommandHandler;
import nts.uk.ctx.at.record.app.command.kdl.kdl006.WorkPlaceConfirmCommand;
import nts.uk.screen.at.app.query.kdl.kdl006.a.WorkConfirmationFinder;
import nts.uk.screen.at.app.query.kdl.kdl006.a.dto.ClosureInforDto;
import nts.uk.screen.at.app.query.kdl.kdl006.a.dto.WorkConfirmationDto;
import nts.uk.screen.at.app.query.kdl.kdl006.a.dto.WorkPlaceConfirmDto;

@Path("screen/at/kdl006")
@Produces("application/json")
public class KDL006WebService extends WebService {
	
	@Inject
	private WorkConfirmationFinder workConfirmationFinder;
	
	@Inject
	private KDL006CommandHandler commandHandler;
	
	@POST
	@Path("startpage")
	public WorkConfirmationDto startPage(KDL006Param param) {
		return this.workConfirmationFinder.DisplayOfWorkConfirmationDialog(param.closureId);
	}
	
	@POST
	@Path("getworkplace")
	public List<WorkPlaceConfirmDto> getWorkPlace(ClosureInforDto param) {
		return this.workConfirmationFinder.getWorkPlace(param);
	}
	
	@POST
	@Path("save")
	public void registerICCardStamp(List<WorkPlaceConfirmCommand> command) {
		this.commandHandler.register(command);
	}
	
}

class KDL006Param{
	public Integer closureId;
}