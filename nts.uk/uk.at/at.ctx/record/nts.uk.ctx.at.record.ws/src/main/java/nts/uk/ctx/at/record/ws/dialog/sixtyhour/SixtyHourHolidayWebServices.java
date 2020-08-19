package nts.uk.ctx.at.record.ws.dialog.sixtyhour;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.dialog.sixtyhourholiday.OverTimeIndicationInformationDetails;
import nts.uk.ctx.at.record.app.find.dialog.sixtyhourholiday.SixtyHourHolidayFinder;

/**
 * The Class SuperHolidayWebServices.
 */
@Path("at/record/dialog/sixtyhour")
@Produces("application/json")
public class SixtyHourHolidayWebServices extends WebService {
	
	/** The super holiday finder. */
	@Inject
	private SixtyHourHolidayFinder superHolidayFinder;
	
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