package nts.uk.ctx.at.record.ws.stamp;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.stamp.StampDto;
import nts.uk.ctx.at.record.app.find.stamp.StampFinder;

@Path("at/record/stamp")
@Produces("application/json")
public class StampReferenceWebService extends WebService {
	@Inject
	private StampFinder getStamp;

	/**
	 * get Stam Info
	 * 
	 * @return
	 */
	@POST
	@Path("getstampbyempcode/{cardNumber}/{startDate}/{endDate}")
	public List<StampDto> getStampReference(@PathParam("cardNumber") String cardNumber,
			@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
		return this.getStamp.findByEmployeeCode(cardNumber, startDate, endDate);
	}

}
