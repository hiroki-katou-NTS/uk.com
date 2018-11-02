package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.RegisterStateCorrelationHisClassificationCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateCorrelationHisClassificationCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateLinkSettingCompanyCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisClassificationDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisClassificationFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateLinkSettingMasterDto;


import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Optional;
import java.util.List;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateLinkClassificationWebService extends WebService {

    @Inject
    private StateCorrelationHisClassificationFinder stateLinkSettingClaficationFinder;

    @Inject
    private RegisterStateCorrelationHisClassificationCommandHandler registerCommandHandler;

    @POST
    @Path("getStateCorrelationHisClassification")
    public List<StateCorrelationHisClassificationDto> getStateCorrelationHisClassification() {
        return stateLinkSettingClaficationFinder.getStateCorrelationHisClassificationByCid();
    }

    @POST
    @Path("registerClassification")
    public void registerClassification(StateCorrelationHisClassificationCommand command) {
        registerCommandHandler.handle(command);
    }

    @POST
    @Path("getStateLinkMasterClassification/{hisId}/{start}")
    public List<StateLinkSettingMasterDto> getStateLinkMaster(@PathParam("hisId") String hisId, @PathParam("start") int start) {
        return stateLinkSettingClaficationFinder.getStateLinkSettingMaster(hisId, start);
    }


}
