package nts.uk.ctx.at.record.ws.remaingnumber;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.remainingnumber.annleagrtremnum.AnnLeaGrantRemnNumDto;

@Path("at/record/remainnumber/resvLea")
@Produces("application/json")
public class ResvLeaGrantRemNumWebService extends WebService{

	
	//@Inject
	//private AnnLeaGrantRemnNumFinder finder;
	
	@POST
	@Path("getResvLea/{empId}")
	public List<AnnLeaGrantRemnNumDto> findResvLeaGrantRemnNum(@PathParam("empId") String employeeId) {
		return null;
	}
	
}
