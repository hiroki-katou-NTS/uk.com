package nts.uk.screen.com.ws.cmm002;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.com.app.find.cmm002.AccessRestrictionsDto;
import nts.uk.screen.com.app.find.cmm002.CMM022ScreenQuery;

@Path("com/screen/cmm002")
@Produces("application/json")
public class CMM002WebService {

	@Inject 
	private CMM022ScreenQuery screenQuery;
	
	@POST
	@Path("get")
	public AccessRestrictionsDto get() {
		return screenQuery.get();
	}
}
