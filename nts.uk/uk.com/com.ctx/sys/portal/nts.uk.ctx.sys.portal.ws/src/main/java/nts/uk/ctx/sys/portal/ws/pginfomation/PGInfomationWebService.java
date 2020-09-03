package nts.uk.ctx.sys.portal.ws.pginfomation;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.query.pginfomation.PGInfomationDto;
import nts.uk.ctx.sys.portal.app.query.pginfomation.PGInfomationQuery;

@Path("sys/portal/pginfomation")
@Produces("application/json")
public class PGInfomationWebService extends WebService {

	@Inject
	private PGInfomationQuery pgInfomationQuery;

	@POST
	@Path("findBySystem")
	public List<PGInfomationDto> findBySystem(int systemType) {
		return pgInfomationQuery.findBySystem(systemType);
	}

}
