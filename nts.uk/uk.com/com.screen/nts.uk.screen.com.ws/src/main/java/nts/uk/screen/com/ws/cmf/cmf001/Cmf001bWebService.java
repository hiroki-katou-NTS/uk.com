package nts.uk.screen.com.ws.cmf.cmf001;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("exio/input/setting")
@Produces(MediaType.APPLICATION_JSON)
public class Cmf001bWebService {
	
	@POST
	@Path("get-layout")
	public void find(@PathParam("settingCode") String settingCode) {
		//return finder.find(settingCode);
	}
}
