package nts.uk.ctx.sys.portal.ws.toppagesetting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.portal.app.command.toppagesetting.TopPageJobSetBase;
import nts.uk.ctx.sys.portal.app.command.toppagesetting.UpdateTopPageJobSetCommandHandler;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.TopPageJobSetDto;
import nts.uk.ctx.sys.portal.app.find.toppagesetting.TopPageJobSetFinder;

/**
 * 
 * @author sonnh1
 *
 */
@Path("sys/portal/toppagesetting/jobset")
@Produces("application/json")
public class TopPageJobSetWebService {

	@Inject
	UpdateTopPageJobSetCommandHandler updateTopPageJobSetCommandHandler;

	@Inject
	TopPageJobSetFinder topPageJobSetFinder;

	@POST
	@Path("find")
	public List<TopPageJobSetDto> find(List<String> listJobId) {
		return this.topPageJobSetFinder.find(listJobId);
	}

	@POST
	@Path("updateTopPageJobSet")
	public void update(TopPageJobSetBase command) {
		this.updateTopPageJobSetCommandHandler.handle(command);
	}
}
