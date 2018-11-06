package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.RegisterStateCorrelationHisPositionCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateCorrelationHisPositionCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisPositionDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisPositionFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateLinkSettingDateDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateLinkSettingMasterDto;


import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateCorrelationHisPositionWebService extends WebService {

    @Inject
    private StateCorrelationHisPositionFinder stateCorrelationHisPositionFinder;

    @Inject
    private RegisterStateCorrelationHisPositionCommandHandler  registerStateCorrelationHisPositionCommandHandler;

    @POST
    @Path("getStateCorrelationHisPosition")
    public List<StateCorrelationHisPositionDto> getStateCorrelationHisPosition() {
        return stateCorrelationHisPositionFinder.getStateCorrelationHisPositionByCid();
    }

    @POST
    @Path("registerHisPosition")
    public void registerHisPosition(StateCorrelationHisPositionCommand command) {
        registerStateCorrelationHisPositionCommandHandler.handle(command);
    }

    @Path("getDateBase/{hisId}")
    @POST
    public StateLinkSettingDateDto getDateBase(@PathParam("hisId") String hisId) {
        return stateCorrelationHisPositionFinder.getDateBase(hisId);
    }

    @POST
    @Path("getStateLinkMasterPosition/{hisId}/{start}")
    public List<StateLinkSettingMasterDto> getStateLinkMaster(@PathParam("hisId") String hisId, @PathParam("start") int start) {
        return stateCorrelationHisPositionFinder.getStateLinkSettingMaster(hisId, start);
    }



}
