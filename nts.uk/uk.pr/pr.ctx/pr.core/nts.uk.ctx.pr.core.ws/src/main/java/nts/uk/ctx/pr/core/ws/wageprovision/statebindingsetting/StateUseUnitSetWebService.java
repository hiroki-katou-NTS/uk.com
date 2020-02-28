package nts.uk.ctx.pr.core.ws.wageprovision.statebindingsetting;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateUseUnitSettingCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.UpdateStateUseUnitSettingCommandHandler;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateUseUnitSetDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateUseUnitSetFinder;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Optional;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateUseUnitSetWebService extends WebService {

    @Inject
    private StateUseUnitSetFinder stateUseUnitSetFinder;

    @Inject
    private UpdateStateUseUnitSettingCommandHandler updateStateUseUnitSettingCommandHandler;

    @POST
    @Path("getStateUseUnitSettingById")
    public StateUseUnitSetDto getStateUseUnitSettingById(){
        String cid = AppContexts.user().companyId();
        Optional<StateUseUnitSetDto>  stateUseUnitSettingDto = stateUseUnitSetFinder.getStateUseUnitSettingById(cid);
        if(stateUseUnitSettingDto.isPresent()){
            return stateUseUnitSettingDto.get();
        }
        return null;
    }

    @POST
    @Path("update")
    public void update(StateUseUnitSettingCommand command){
        updateStateUseUnitSettingCommandHandler.handle(command);
    }
}
