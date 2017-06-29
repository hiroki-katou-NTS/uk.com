package nts.uk.ctx.sys.portal.ws.toppagesetting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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

	@POST
	@Path("findBySid")
	public List<TopPagePersonSetDto> find(List<String> listSid) {
		return this.topPagePersonSetFinder.find(listSid);
	}
}
