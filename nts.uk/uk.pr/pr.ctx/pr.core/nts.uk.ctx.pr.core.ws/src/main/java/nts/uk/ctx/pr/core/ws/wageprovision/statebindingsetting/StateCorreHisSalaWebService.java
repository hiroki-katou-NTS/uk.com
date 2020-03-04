package nts.uk.ctx.pr.core.ws.wageprovision.statebindingsetting;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.RegisterStateCorrelationHisSalaryCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateCorrelationHisSalaryCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateCorreHisSalaDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateCorreHisSalaFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateLinkSetMasterDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateCorreHisSalaWebService extends WebService {

    @Inject
    private StateCorreHisSalaFinder stateCorreHisSalaFinder;

    @Inject
    private RegisterStateCorrelationHisSalaryCommandHandler  registerStateCorrelationHisSalaryCommandHandler;

    @POST
    @Path("getStateCorrelationHisSalary")
    public List<StateCorreHisSalaDto> getStateCorrelationHisSalary() {
        return stateCorreHisSalaFinder.getStateCorrelationHisSalaryByCid();
    }

    @POST
    @Path("registerHisSalary")
    public void registerHisSalary(StateCorrelationHisSalaryCommand command) {
        registerStateCorrelationHisSalaryCommandHandler.handle(command);
    }

    @POST
    @Path("getStateLinkMasterSalary/{hisId}/{start}")
    public List<StateLinkSetMasterDto> getStateLinkMaster(@PathParam("hisId") String hisId, @PathParam("start") int start) {
        return stateCorreHisSalaFinder.getStateLinkSettingMaster(hisId, start);
    }



}
