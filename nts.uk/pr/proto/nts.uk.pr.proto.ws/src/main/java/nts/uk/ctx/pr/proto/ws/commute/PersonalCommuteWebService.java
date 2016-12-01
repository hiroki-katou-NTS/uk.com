package nts.uk.ctx.pr.proto.ws.commute;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.proto.app.find.personalinfo.commute.CommuteDto;
import nts.uk.ctx.pr.proto.app.find.personalinfo.commute.CommuteFinder;



@Path("pr/proto/commute")
@Produces("application/json")
public class PersonalCommuteWebService extends WebService {

	@Inject
	private CommuteFinder commuteFinder;

	@POST
	@Path("findCommute/{personId}/{startYearMonth}")
	public CommuteDto findCommute(@PathParam("personId") String personId,@PathParam("startYearMonth") int startYearMonth ){
		return this.commuteFinder.find(personId, startYearMonth).orElse(null) ;		
	}
}
