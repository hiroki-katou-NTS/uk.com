package nts.uk.ctx.pr.proto.ws.paymentdata;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.proto.app.find.paymentdata.CommuteNotaxLimitDto;
import nts.uk.ctx.pr.proto.app.find.paymentdata.CommuteNotaxLimitFinder;

@Path("pr/proto/paymentdata")
@Produces("application/json")
public class CommuteNotaxLimitWebService extends WebService {
	
	@Inject
	private CommuteNotaxLimitFinder commuteNotaxLimitFinder;
	
	@POST
	@Path("findCommuteNotaxLimit/{commuNotaxLimitCode}")
	public CommuteNotaxLimitDto findCommuteNotaxLimit(@PathParam("commuNotaxLimitCode") String commuNotaxLimitCode){
		return commuteNotaxLimitFinder.find(commuNotaxLimitCode).orElse(null);
	}
}
