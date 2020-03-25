package nts.uk.screen.hr.ws.jcg004;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.hr.app.jcg004.Jcg004Dto;
import nts.uk.screen.hr.app.jcg004.Jcg004Finder;

@Path("jcg004")
@Produces(MediaType.APPLICATION_JSON)
public class Jcg004WS {

	@Inject
	private Jcg004Finder finder;
	
	@POST
	@Path("/start")
	public Jcg004Dto getGuideDispSetting(){
		return finder.Start();
	}

}
