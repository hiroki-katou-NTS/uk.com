package nts.uk.screen.com.ws.user.information;

import nts.uk.screen.com.app.find.user.information.UserInformationDto;
import nts.uk.screen.com.app.find.user.information.UserInformationScreenQuery;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("query/cmm048userinformation")
@Produces(MediaType.APPLICATION_JSON)
public class Cmm048GetUserInformationWs {

    @Inject
    private UserInformationScreenQuery screenQuery;

    @POST
    @Path("find")
    public UserInformationDto Ccg029SearchEmployee() {
        return screenQuery.getUserInformation();
    }
}
