package nts.uk.ctx.sys.portal.ws.toppagesetting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.portal.app.command.toppagesetting.RemoveTopPagePersonSetCommandHandler;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.TopPagePersonSetCommandBase;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.UpdateTopPagePersonSetCommandHandler;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.TopPagePersonSetDto;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.TopPagePersonSetFinder;

/**
 * 
 * @author sonnh1
 *
 */
@Path("sys/portal/toppagesetting/personset")
@Produces("application/json")
public class TopPagePersonSetWebService {

	@Inject
	TopPagePersonSetFinder topPagePersonSetFinder;

	@Inject
	UpdateTopPagePersonSetCommandHandler updateTopPagePersonSetCommandHandler;
	
	@Inject
	RemoveTopPagePersonSetCommandHandler removeTopPagePersonSetCommandHandler;

	@POST
	@Path("findBySid")
	public List<TopPagePersonSetDto> find(List<String> listSid) {
		return this.topPagePersonSetFinder.find(listSid);
	}

	@POST
	@Path("update")
	public void update(TopPagePersonSetCommandBase topPagePersonSetCommandBase) {
		this.updateTopPagePersonSetCommandHandler.handle(topPagePersonSetCommandBase);
	}
	
	@POST
	@Path("remove")
	public void remove(TopPagePersonSetCommandBase topPagePersonSetCommandBase) {
		this.removeTopPagePersonSetCommandHandler.handle(topPagePersonSetCommandBase);;
	}
}
