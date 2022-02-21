package nts.uk.screen.at.ws.kdp.common;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.kdp.common.GetWorkLocationAndRegionalTimeDifference;
import nts.uk.screen.at.app.query.kdp.common.GetWorkLocationAndRegionalTimeDifferenceDto;
import nts.uk.screen.at.app.query.kdp.common.GetWorkLocationAndRegionalTimeDifferenceInput;
import nts.uk.screen.at.app.query.kdp.common.GetWorkPlaceRegionalTime;
import nts.uk.screen.at.app.query.kdp.common.GetWorkPlaceRegionalTimeDto;
import nts.uk.screen.at.app.query.kdp.common.GetWorkPlaceRegionalTimeInput;

/**
 * 
 * @author chungnt
 *
 */

@Path("at/record/kdp/common")
@Produces("application/json")
public class KdpCommonWebservice extends WebService {

	@Inject
	private GetWorkLocationAndRegionalTimeDifference getWorkLocationAndRegionalTimeDifference;
	
	@Inject
	private GetWorkPlaceRegionalTime getWorkPlaceRegionalTime;
	
	@POST
	@Path("get-work-location-regional-time")
	public GetWorkLocationAndRegionalTimeDifferenceDto getWorkLocationAndRegionalTimeDifference(GetWorkLocationAndRegionalTimeDifferenceInput param) {
		return this.getWorkLocationAndRegionalTimeDifference.getWorkLocationAndRegionalTimeDifference(param);
	}
	
	@POST
	@Path("get-work-place-regional-time")
	public GetWorkPlaceRegionalTimeDto getWorkPlaceRegionalTime(GetWorkPlaceRegionalTimeInput param) {
		return this.getWorkPlaceRegionalTime.getWorkPlaceRegionalTime(param);
	}
	
}
