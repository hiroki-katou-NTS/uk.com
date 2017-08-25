/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.totaltimes;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.shift.totaltimes.TotalTimesFinder;
import nts.uk.ctx.at.schedule.app.find.shift.totaltimes.dto.TotalTimesDetailDto;
import nts.uk.ctx.at.schedule.app.find.shift.totaltimes.dto.TotalTimesItemDto;

/**
 * The Class TotalTimesWS.
 */
@Path("ctx/at/schedule/shift/totaltimes")
@Produces("application/json")
public class TotalTimesWS extends WebService {

	/** The total times finder. */
	@Inject
	private TotalTimesFinder totalTimesFinder;

	/**
	 * Gets the all total times.
	 *
	 * @return the all total times
	 */
	@POST
	@Path("getallitem")
	public List<TotalTimesItemDto> getAllTotalTimes() {
		return this.totalTimesFinder.getAllTotalTimesItems();
	}

	/**
	 * Gets the total times detail.
	 *
	 * @param totalCountNo
	 *            the total count no
	 * @return the total times detail
	 */
	@POST
	@Path("getdetail/{totalCountNo}")
	public TotalTimesDetailDto getTotalTimesDetail(
			@PathParam("totalCountNo") Integer totalCountNo) {
		return this.totalTimesFinder.getTotalTimesDetails(totalCountNo);
	}
}
