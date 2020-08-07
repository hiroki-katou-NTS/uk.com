package nts.uk.ctx.at.request.ws.dialog.superholiday;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.dialog.superholiday.OverTimeIndicationInformationDetails;
import nts.uk.ctx.at.request.app.find.dialog.superholiday.SuperHolidayFinder;

/**
 * The Class SuperHolidayWebServices.
 */
@Path("at/request/dialog/superHoliday")
@Produces("application/json")
public class SuperHolidayWebServices extends WebService {
	
	/** The super holiday finder. */
	@Inject
	private SuperHolidayFinder superHolidayFinder;
	
	/**
	 * アルゴリズム「60H超休の表示」を実行する.
	 *
	 * @param employeeId 社員ID
	 * @param baseDate the base date
	 * @return the 60 h overtime display info detail
	 */
	@POST
	@Path("get60hOvertimeDisplayInfoDetail/{employeeId}/{baseDate}")
	public OverTimeIndicationInformationDetails get60hOvertimeDisplayInfoDetail(
									@PathParam("employeeId") String employeeId,
									@PathParam("baseDate") String baseDate) {
		// 60H超休の表示
		return this.superHolidayFinder.getOverTimeIndicationInformationDetails(employeeId, baseDate);
	}

}
