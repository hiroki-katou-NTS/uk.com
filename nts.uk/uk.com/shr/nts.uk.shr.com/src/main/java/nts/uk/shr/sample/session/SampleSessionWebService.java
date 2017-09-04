package nts.uk.shr.sample.session;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.arc.scoped.session.SessionContextProvider;

@Path("/sample/session")
@Produces("application/json")
public class SampleSessionWebService extends WebService {

	@POST
	@Path("setvalue/{value}")
	public void setValue(@PathParam("value") String value) {
		SessionContextProvider.get().put("SAMPLE_SESSION", value);
	}
	
	@POST
	@Path("getvalue")
	public JavaTypeResult<String> getValue() {
		return new JavaTypeResult<>((String)SessionContextProvider.get().get("SAMPLE_SESSION"));
	}
}
