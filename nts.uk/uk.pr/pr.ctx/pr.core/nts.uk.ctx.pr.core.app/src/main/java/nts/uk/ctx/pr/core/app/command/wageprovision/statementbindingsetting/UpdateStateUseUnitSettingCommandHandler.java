package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateUseUnitSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateUseUnitSetRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.Optional;

@Stateless
@Transactional
public class UpdateStateUseUnitSettingCommandHandler extends CommandHandler<StateUseUnitSettingCommand>{
    
    @Inject
    private StateUseUnitSetRepository repository;

    
    @Override
    protected void handle(CommandHandlerContext<StateUseUnitSettingCommand> context) {
        StateUseUnitSettingCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();

        Optional<StateUseUnitSet> stateUseUnitSetting =  repository.getStateUseUnitSettingById(cid);
        if(stateUseUnitSetting.isPresent()){
            repository.update(new StateUseUnitSet(cid, command.getMasterUse(), command.getIndividualUse(), command.getUsageMaster()));
        }else{
            repository.add(new StateUseUnitSet(cid, command.getMasterUse(), command.getIndividualUse(), command.getUsageMaster()));
        }
    }
}
