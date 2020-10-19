package nts.uk.query.ws.user.information;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.query.app.user.information.UserInformationSettingDto;
import nts.uk.query.app.user.information.UserInformationSettingScreenQuery;

@Path("query/cmm049userinformationsetting")
@Produces(MediaType.APPLICATION_JSON)
public class Cmm049GetUserInformationSettingWs {
	@Inject
	private UserInformationSettingScreenQuery userInformationSettingScreenQuery;
	
	@POST
	@Path("/get")
	public UserInformationSettingDto get() {
		return this.userInformationSettingScreenQuery.getUserInformationSettings();
	}
	
}
