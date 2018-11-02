package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateLinkSettingCompanyCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisPositionDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisPositionFinder;
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

    @POST
    @Path("getStateCorrelationHisPosition")
    public List<StateCorrelationHisPositionDto> getStateCorrelationHisPosition() {
        return stateCorrelationHisPositionFinder.getStateCorrelationHisPositionByCid();
    }

    @POST
    @Path("registerClassification")
    public void registerHisPosition(StateLinkSettingCompanyCommand command) {

    }

    @POST
    @Path("getStateLinkMasterPosition/{hisId}/{start}")
    public List<StateLinkSettingMasterDto> getStateLinkMaster(@PathParam("hisId") String hisId, @PathParam("start") int start) {
        return stateCorrelationHisPositionFinder.getStateLinkSettingMaster(hisId, start);
    }



}
