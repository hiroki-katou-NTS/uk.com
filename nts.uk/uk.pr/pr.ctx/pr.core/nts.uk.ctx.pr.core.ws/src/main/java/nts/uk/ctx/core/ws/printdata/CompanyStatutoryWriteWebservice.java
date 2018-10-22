package nts.uk.ctx.core.ws.printdata;



import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("pr/core/printdata")
@Produces("application/json")
public class CompanyStatutoryWriteWebservice {

    @POST
    @Path("/start")
    public void startScreen() {

    }

}
