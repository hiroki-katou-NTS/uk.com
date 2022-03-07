package nts.uk.ctx.at.shared.ws.supportmanagement.supportalloworg;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.supportmanagement.supportalloworg.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/share/supportmanagement/supportalloworg")
@Produces("application/json")
public class SupportAllowOrgWebService extends WebService {

    @Inject
    private RegisterSupportAllowOrgCommandHandler registerSupportAllowOrgCommandHandler;

    @Inject
    private CopySupportAllowOrgCommandHandler copySupportAllowOrgCommandHandler;

    @Inject
    private DeleteSupportAllowOrgCommandHandler deleteSupportAllowOrgCommandHandler;

    @POST
    @Path("register")
    public void registSupportAllowOrg(RegisterSupportAllowOrgCommand command){
        registerSupportAllowOrgCommandHandler.handle(command);
    }

    @POST
    @Path("copy")
    public List<CopySupportAllowOrgResult> copySupportAllowOrg(CopySupportAllowOrgCommand command){
        return copySupportAllowOrgCommandHandler.handle(command);
    }


    @POST
    @Path("delete")
    public void deleteSupportAllowOrg(DeleteSupportAllowOrgCommand command){
        deleteSupportAllowOrgCommandHandler.handle(command);
    }
}
