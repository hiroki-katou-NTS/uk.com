package nts.uk.ctx.link.smile.ws.smilelinked.cooperationacceptance;

import nts.uk.ctx.link.smile.app.smilelinked.cooperationacceptance.GenerateSmileAcceptDefaultDataCommand;
import nts.uk.ctx.link.smile.app.smilelinked.cooperationacceptance.GenerateSmileAcceptDefaultDataCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Path("ctx/link/smile/accept/setting")
public class SmileCooperationAcceptanceSettingWebService {

    @Inject
    private GenerateSmileAcceptDefaultDataCommandHandler generateHandler;

    @POST
    @Path("generatedata")
    public void generateDefaultData(GenerateSmileAcceptDefaultDataCommand command) {
        generateHandler.handle(command);
    }
}
