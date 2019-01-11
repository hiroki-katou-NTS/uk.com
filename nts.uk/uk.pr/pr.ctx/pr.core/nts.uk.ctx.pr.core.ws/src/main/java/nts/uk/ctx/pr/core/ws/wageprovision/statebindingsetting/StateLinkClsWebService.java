package nts.uk.ctx.pr.core.ws.wageprovision.statebindingsetting;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.RegisterStateCorrelationHisClassificationCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateCorrelationHisClassificationCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.ClassificationImportDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateCorreHisClsDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateCorreHisClsFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateLinkSetMasterDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateLinkClsWebService extends WebService {

    @Inject
    private StateCorreHisClsFinder stateLinkSettingClaficationFinder;

    @Inject
    private RegisterStateCorrelationHisClassificationCommandHandler registerCommandHandler;

    @POST
    @Path("getStateCorrelationHisClassification")
    public List<StateCorreHisClsDto> getStateCorrelationHisClassification() {
        return stateLinkSettingClaficationFinder.getStateCorrelationHisClassificationByCid();
    }

    @POST
    @Path("registerClassification")
    public void registerClassification(StateCorrelationHisClassificationCommand command) {
        registerCommandHandler.handle(command);
    }

    @POST
    @Path("getStateLinkMasterClassification/{hisId}/{start}")
    public List<StateLinkSetMasterDto> getStateLinkMaster(@PathParam("hisId") String hisId, @PathParam("start") int start) {
        return stateLinkSettingClaficationFinder.getStateLinkSettingMaster(hisId, start);
    }

    @POST
    @Path("getAllClassificationByCid")
    public List<ClassificationImportDto> getAllClassificationByCid() {
        return stateLinkSettingClaficationFinder.getAllClassificationByCid();
    }

}
