package nts.uk.ctx.pr.core.ws.wageprovision.statebindingsetting;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.RegisterHisIndividualCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateLinkSettingIndividualCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateCorreHisIndiviDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateCorreHisIndiviFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateLinkSetIndiviDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateLinkSetIndiviFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateLinkSetIndiWebService extends WebService {

    @Inject
    private StateCorreHisIndiviFinder stateCorreHisIndiviFinder;

    @Inject
    private RegisterHisIndividualCommandHandler registerCommandHandler;

    @Inject
    private StateLinkSetIndiviFinder stateLinkSetIndiviFinder;

    @POST
    @Path("getStateCorrelationHisIndividual/{empId}")
    public List<StateCorreHisIndiviDto> getLinkingHistoryIndividual(@PathParam("empId") String empId){
        return stateCorreHisIndiviFinder.getLinkingHistoryIndividual(empId);
    }

    @POST
    @Path("getStateLinkSettingMasterIndividual/{empId}/{hisId}/{start}")
    public StateLinkSetIndiviDto getStatementLinkingSetting(@PathParam("empId") String empId, @PathParam("hisId") String hisId, @PathParam("start") int start){
        return stateLinkSetIndiviFinder.getStatementLinkingSetting(empId, hisId, start);
    }

    @POST
    @Path("registerHisIndividual")
    public void registerHisIndividual(StateLinkSettingIndividualCommand command) {
        registerCommandHandler.handle(command);
    }

}
