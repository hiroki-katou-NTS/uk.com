package nts.uk.ctx.at.function.ws.supportworklist.outputsetting;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.supportworklist.outputsetting.SupportWorkOutputSettingCommand;
import nts.uk.ctx.at.function.app.command.supportworklist.outputsetting.SupportWorkOutputSettingDeleteCommandHandler;
import nts.uk.ctx.at.function.app.command.supportworklist.outputsetting.SupportWorkOutputSettingRegisterCommandHandler;
import nts.uk.ctx.at.function.app.find.statement.outputitemsetting.OutputConditionOfEmbossingDto;
import nts.uk.ctx.at.function.app.find.supportworklist.outputsetting.SupportWorkOutputSettingDto;
import nts.uk.ctx.at.function.app.find.supportworklist.outputsetting.SupportWorkOutputSettingFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("at/function/supportworklist/outputsetting")
@Produces(MediaType.APPLICATION_JSON)
public class SupportWorkOutputSettingWebService extends WebService {
    @Inject
    private SupportWorkOutputSettingFinder finder;

    @Inject
    private SupportWorkOutputSettingRegisterCommandHandler regCommandHandler;

    @Inject
    private SupportWorkOutputSettingDeleteCommandHandler delCommandHandler;

    @POST
    @Path("all")
    public List<SupportWorkOutputSettingDto> getAll(){
        return this.finder.getAll();
    }

    @POST
    @Path("{code}")
    public SupportWorkOutputSettingDto getOne(@PathParam("code") String code){
        return this.finder.getOne(code);
    }

    @POST
    @Path("register")
    public void register(SupportWorkOutputSettingCommand command){
        this.regCommandHandler.handle(command);
    }

    @POST
    @Path("delete/{code}")
    public void delete(@PathParam("code") String code){
        this.delCommandHandler.handle(code);
    }
}
