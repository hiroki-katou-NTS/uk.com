package nts.uk.screen.at.ws.ksu.ksu008;

import nts.uk.screen.at.app.command.ksu008B.OutPutLayoutDeleteKsu008BCommand;
import nts.uk.screen.at.app.command.ksu008B.OutPutLayoutDeleteKsu008BHandler;
import nts.uk.screen.at.app.command.ksu008B.OutPutLayoutRegisterKsu008BCommand;
import nts.uk.screen.at.app.command.ksu008B.OutPutLayoutRegisterKsu008BHandler;
import nts.uk.screen.at.app.command.ksu008B.OutPutLayoutUpdateKsu008BHandler;
import nts.uk.screen.at.app.command.ksu008B.WorkScheduleSameKsu008BHandler;
import nts.uk.screen.at.app.command.ksu008B.WorkScheduleSaveKsu008BCommand;
import nts.uk.screen.at.app.ksu008.a.GetOutPutItemForKsu008B;
import nts.uk.screen.at.app.ksu008.a.dto.Form9LayoutDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author rafiqul.islam
 */

@Path("screen/at/ksu008/b")
@Produces(MediaType.APPLICATION_JSON)
public class Ksu008BWebServive {

    @Inject
    private OutPutLayoutRegisterKsu008BHandler handleCreated;

    @Inject
    private OutPutLayoutUpdateKsu008BHandler updateKsu008BHandler;

    @Inject
    private WorkScheduleSameKsu008BHandler systemUpdate;

    @Inject
    private OutPutLayoutDeleteKsu008BHandler deleteKsu008BHandler;

    @Inject
    private GetOutPutItemForKsu008B getOutPutItemForKsu008B;

    @POST
    @Path("register")
    public void register(OutPutLayoutRegisterKsu008BCommand command) {
        this.handleCreated.handle(command);
    }

    @POST
    @Path("update")
    public void update(OutPutLayoutRegisterKsu008BCommand command) {
        this.updateKsu008BHandler.handle(command);
    }

    @POST
    @Path("delete")
    public void delete(OutPutLayoutDeleteKsu008BCommand command) {
        this.deleteKsu008BHandler.handle(command);
    }


    @POST
    @Path("system-fixed")
    public void systemFixed(WorkScheduleSaveKsu008BCommand command) {
        this.systemUpdate.handle(command);
    }

    @POST
    @Path("get-layouts/{isSystemFixed}")
    public List<Form9LayoutDto> getItemList(@PathParam("isSystemFixed") boolean isSystemFixed) {
        return getOutPutItemForKsu008B.get(isSystemFixed);
    }

    @POST
    @Path("get-layout/{code}")
    public Form9LayoutDto getItemList(@PathParam("code") String code) {
        return getOutPutItemForKsu008B.get(code);
    }
}
