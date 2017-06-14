package nts.uk.ctx.sys.portal.ws.standardmenu;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.find.standardmenu.StandardMenuDto;
import nts.uk.ctx.sys.portal.app.find.standardmenu.StandardMenuFinder;

/**
 * @author tanlv
 */
@Path("sys/portal/standardmenu")
@Produces("application/json")
public class StandardMenuWebService extends WebService {
	@Inject
	private StandardMenuFinder finder;
	
	@POST
	@Path("findAll")
	public List<StandardMenuDto> findAll() {
		return finder.findAll();
	}
	
	@POST
	@Path("findAll")
	public List<StandardMenuDto> findAllWithAfterLoginDisplayIndicatorIsTrue() {
		return finder.findAllWithAfterLoginDisplayIndicatorIsTrue();
	}
}
