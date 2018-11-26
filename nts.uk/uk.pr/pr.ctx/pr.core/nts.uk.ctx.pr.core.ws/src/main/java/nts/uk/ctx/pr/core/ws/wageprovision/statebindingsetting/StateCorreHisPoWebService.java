package nts.uk.ctx.pr.core.ws.wageprovision.statebindingsetting;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.RegisterStateCorrelationHisPositionCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateCorrelationHisPositionCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateLinkMasterParam;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateCorreHisPosDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateCorreHisPosFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateLinkSetDateDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateLinkSetMasterDto;


import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateCorreHisPoWebService extends WebService {

    @Inject
    private StateCorreHisPosFinder stateCorreHisPosFinder;

    @Inject
    private RegisterStateCorrelationHisPositionCommandHandler  registerStateCorrelationHisPositionCommandHandler;

    @POST
    @Path("getStateCorrelationHisPosition")
    public List<StateCorreHisPosDto> getStateCorrelationHisPosition() {
        return stateCorreHisPosFinder.getStateCorrelationHisPositionByCid();
    }

    @POST
    @Path("registerHisPosition")
    public void registerHisPosition(StateCorrelationHisPositionCommand command) {
        registerStateCorrelationHisPositionCommandHandler.handle(command);
    }

    @POST
    @Path("getDateBase/{hisId}")
    public StateLinkSetDateDto getDateBase(@PathParam("hisId") String hisId) {
        return stateCorreHisPosFinder.getDateBase(hisId);
    }

    @POST
    @Path("getStateLinkMasterPosition")
    public List<StateLinkSetMasterDto> getStateLinkMaster(StateLinkMasterParam params) {
        return stateCorreHisPosFinder.getStateLinkSettingMaster(params.getHisId(), params.getStartYearMonth(), params.getDate());
    }



}
