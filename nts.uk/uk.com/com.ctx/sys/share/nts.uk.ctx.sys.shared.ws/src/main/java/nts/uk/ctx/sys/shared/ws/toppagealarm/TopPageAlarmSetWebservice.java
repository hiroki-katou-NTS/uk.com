package nts.uk.ctx.sys.shared.ws.toppagealarm;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.shared.app.toppagealarmset.command.TopPageAlarmSetCommand;
import nts.uk.ctx.sys.shared.app.toppagealarmset.command.UpdateTopPageAlarmSetCommandHandler;
import nts.uk.ctx.sys.shared.app.toppagealarmset.find.TopPageAlarmSetDto;
import nts.uk.ctx.sys.shared.app.toppagealarmset.find.TopPageAlarmSetFinder;

@Path("sys/share/toppagealarm")
@Produces("application/json")
public class TopPageAlarmSetWebservice extends WebService{
	@Inject
	private TopPageAlarmSetFinder toppageAlarmFinder;
	
	@Inject
	private UpdateTopPageAlarmSetCommandHandler updateToppageSet;
	
	@Path("find")
	@POST
	public List<TopPageAlarmSetDto> find() {
		return toppageAlarmFinder.finder();
	}
	
	@Path("add")
	@POST
	public void update(TopPageAlarmSetCommand cmd) {
		this.updateToppageSet.handle(cmd);
	}
}
