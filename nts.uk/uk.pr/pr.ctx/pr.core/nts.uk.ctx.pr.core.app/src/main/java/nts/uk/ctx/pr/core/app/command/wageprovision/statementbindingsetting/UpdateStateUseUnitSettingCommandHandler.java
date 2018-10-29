package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateUseUnitSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateUseUnitSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.Optional;

@Stateless
@Transactional
public class UpdateStateUseUnitSettingCommandHandler extends CommandHandler<StateUseUnitSettingCommand>
{
    
    @Inject
    private StateUseUnitSettingRepository repository;

    
    @Override
    protected void handle(CommandHandlerContext<StateUseUnitSettingCommand> context) {
        StateUseUnitSettingCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();

        Optional<StateUseUnitSetting> stateUseUnitSetting =  repository.getStateUseUnitSettingById(cid);
        if(stateUseUnitSetting.isPresent()){
            repository.update(new StateUseUnitSetting(cid, command.getMasterUse(), command.getIndividualUse(), command.getUsageMaster()));
        }else{
            repository.add(new StateUseUnitSetting(cid, command.getMasterUse(), command.getIndividualUse(), command.getUsageMaster()));
        }
    }
}
