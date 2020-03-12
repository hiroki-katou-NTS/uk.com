package nts.uk.ctx.hr.develop.ws.jcg004;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.hr.develop.app.guidance.dto.GuidanceDto;
import nts.uk.ctx.hr.develop.app.guidance.find.GuidanceFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("jcg004")
@Produces(MediaType.APPLICATION_JSON)
public class Jcg004WS {

	@Inject
	private GuidanceFinder finder;
	
	@POST
	@Path("/start")
	public GuidanceDto getGuideDispSetting(){
		String cId = AppContexts.user().companyId();
		return finder.getGuideDispSetting(cId);
	}

}
