/**
 * 4:27:34 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.ws.holiday;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.holiday.PublicHolidayDto;
import nts.uk.ctx.at.schedule.app.find.holiday.PublicHolidayFinder;

/**
 * @author hungnm
 *
 */
@Path("at/schedule/holiday")
@Produces("application/json")
public class PublicHolidayWebService extends WebService {

	@Inject
	private PublicHolidayFinder publicHolidayFinder;

	@POST
	@Path("getHolidayByListDate")
	public List<PublicHolidayDto> getHolidayByListDate(List<BigDecimal> lstDate) {
		return this.publicHolidayFinder.getHolidaysByListDate(lstDate);
	}
}
