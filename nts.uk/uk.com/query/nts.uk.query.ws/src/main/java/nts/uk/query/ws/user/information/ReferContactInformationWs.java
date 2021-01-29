package nts.uk.query.ws.user.information;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.query.app.user.information.ReferContactInformationDto;
import nts.uk.query.app.user.information.ReferContactInformationScreenQuery;

@Path("query/refercontactinfor")
@Produces(MediaType.APPLICATION_JSON)
public class ReferContactInformationWs {

	@Inject
	private ReferContactInformationScreenQuery screenQuerry;
	
	@POST
	@Path("get")
	public ReferContactInformationDto getContactInfomation(String employeeId) {
		return this.screenQuerry.getContactInfomation(employeeId);
	}
}
