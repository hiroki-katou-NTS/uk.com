package nts.uk.ctx.at.shared.ws.specialholidaynew;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.specialholidaynew.ElapseYearDto;
import nts.uk.ctx.at.shared.app.find.specialholidaynew.GrantDateTblDto;
import nts.uk.ctx.at.shared.app.find.specialholidaynew.GrantDateTblFinder;

/**
 * 
 * @author tanlv
 *
 */
@Path("shared/grantdatetbl")
@Produces("application/json")
public class GrantDateTblWebService extends WebService {
	@Inject
	private GrantDateTblFinder finder;
	
	@Path("findBySphdCd/{specialHolidayCode}")
	@POST
	public List<GrantDateTblDto> findBySphdCd(@PathParam("specialHolidayCode") int specialHolidayCode) {
		return finder.findBySphdCd(specialHolidayCode);
	}
	
	@Path("findByGrantDateCd/{grantDateCode}")
	@POST
	public List<ElapseYearDto> findByGrantDateCd(@PathParam("grantDateCode") int grantDateCode) {
		return finder.findByGrantDateCd(grantDateCode);
	}
}
