package nts.uk.screen.com.ws.user.information;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.uk.screen.com.app.find.user.information.ReferContactInformationDto;
import nts.uk.screen.com.app.find.user.information.ReferContactInformationScreenQuery;
import nts.uk.screen.com.app.find.user.information.ReferContactInformationScreenQuery.StartMode;

@Path("query/refercontactinfor")
@Produces(MediaType.APPLICATION_JSON)
public class ReferContactInformationWs {

	@Inject
	private ReferContactInformationScreenQuery screenQuerry;
	
	@POST
	@Path("get")
	public ReferContactInformationDto getContactInfomation(ReferContactInformationParams params) {
		return this.screenQuerry.getContactInfomation(params.getEmployeeId(), EnumAdaptor.valueOf(params.getStartMode(), StartMode.class));
	}
}
