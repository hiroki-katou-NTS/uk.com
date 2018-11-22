package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.RegisterStateCorrelationHisSalaryCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateCorrelationHisSalaryCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisSalaryDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisSalaryFinder;
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
public class StateCorrelationHisSalaryWebService extends WebService {

    @Inject
    private StateCorrelationHisSalaryFinder stateCorrelationHisSalaryFinder;

    @Inject
    private RegisterStateCorrelationHisSalaryCommandHandler  registerStateCorrelationHisSalaryCommandHandler;

    @POST
    @Path("getStateCorrelationHisSalary")
    public List<StateCorrelationHisSalaryDto> getStateCorrelationHisSalary() {
        return stateCorrelationHisSalaryFinder.getStateCorrelationHisSalaryByCid();
    }

    @POST
    @Path("registerHisSalary")
    public void registerHisSalary(StateCorrelationHisSalaryCommand command) {
        registerStateCorrelationHisSalaryCommandHandler.handle(command);
    }

    @POST
    @Path("getStateLinkMasterSalary/{hisId}/{start}")
    public List<StateLinkSettingMasterDto> getStateLinkMaster(@PathParam("hisId") String hisId, @PathParam("start") int start) {
        return stateCorrelationHisSalaryFinder.getStateLinkSettingMaster(hisId, start);
    }



}
