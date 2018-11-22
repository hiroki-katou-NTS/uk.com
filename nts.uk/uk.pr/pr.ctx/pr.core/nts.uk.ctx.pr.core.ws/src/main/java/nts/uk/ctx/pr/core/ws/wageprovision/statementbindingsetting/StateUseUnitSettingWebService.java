package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateUseUnitSettingCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.UpdateStateUseUnitSettingCommandHandler;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateUseUnitSettingDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateUseUnitSettingFinder;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Optional;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateUseUnitSettingWebService extends WebService {

    @Inject
    private StateUseUnitSettingFinder stateUseUnitSettingFinder;

    @Inject
    private UpdateStateUseUnitSettingCommandHandler updateStateUseUnitSettingCommandHandler;

    @POST
    @Path("getStateUseUnitSettingById")
    public StateUseUnitSettingDto getStateUseUnitSettingById(){
        String cid = AppContexts.user().companyId();
        Optional<StateUseUnitSettingDto>  stateUseUnitSettingDto = stateUseUnitSettingFinder.getStateUseUnitSettingById(cid);
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
