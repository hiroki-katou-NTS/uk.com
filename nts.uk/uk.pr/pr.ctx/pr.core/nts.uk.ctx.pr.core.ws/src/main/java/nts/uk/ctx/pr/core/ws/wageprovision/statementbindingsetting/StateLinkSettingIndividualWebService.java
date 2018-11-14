package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.RegisterHisIndividualCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.RegisterStateCorrelationHisPositionCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateCorrelationHisClassificationCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateLinkSettingIndividualCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisIndividualDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisIndividualFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateLinkSettingIndividualDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateLinkSettingIndividualFinder;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingIndividual;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.util.Optional;
import java.util.List;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateLinkSettingIndividualWebService extends WebService {

    @Inject
    private StateCorrelationHisIndividualFinder stateCorrelationHisIndividualFinder;

    @Inject
    private RegisterHisIndividualCommandHandler registerCommandHandler;

    @Inject
    private StateLinkSettingIndividualFinder stateLinkSettingIndividualFinder;

    @POST
    @Path("getStateCorrelationHisIndividual")
    public List<StateCorrelationHisIndividualDto> getLinkingHistoryIndividual(@PathParam("hisId") String empId){
        return stateCorrelationHisIndividualFinder.getLinkingHistoryIndividual(empId);
    }

    @POST
    @Path("getEmployee/{hisId}")
    public List<StateLinkSettingIndividualDto> getStatementLinkingSetting(@PathParam("hisId") String empId){
        return null;
    }

    @POST
    @Path("registerHisIndividual")
    public void registerHisIndividual(StateLinkSettingIndividualCommand command) {
        registerCommandHandler.handle(command);
    }

}
