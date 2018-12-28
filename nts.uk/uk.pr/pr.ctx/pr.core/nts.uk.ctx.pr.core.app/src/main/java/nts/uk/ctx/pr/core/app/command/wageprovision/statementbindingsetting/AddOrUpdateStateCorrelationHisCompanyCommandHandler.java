package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.RegisterMode;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisComService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class AddOrUpdateStateCorrelationHisCompanyCommandHandler extends CommandHandler<StateCorrelationHisCompanySettingCommand> {

    @Inject
    private StateCorreHisComService stateCorreHisComService;

    @Override
    protected void handle(CommandHandlerContext<StateCorrelationHisCompanySettingCommand> commandHandlerContext) {
        String cid = AppContexts.user().companyId();

        StateLinkSettingCompanyCommand stateLinkSettingCompanyCommand = commandHandlerContext.getCommand().getStateLinkSettingCompanyCommand();

        StateCorrelationHisCompanyCommand stateCorrelationHisCompanyCommand = commandHandlerContext.getCommand().getStateCorrelationHisCompanyCommand();

        int mode = commandHandlerContext.getCommand().getMode();
        if(mode == nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.RegisterMode.NEW.value){
            stateCorreHisComService.addStateCorrelationHisCompany(cid,stateCorrelationHisCompanyCommand.getHistoryID(),
                    new YearMonth(stateCorrelationHisCompanyCommand.getStartYearMonth()),new YearMonth(stateCorrelationHisCompanyCommand.getEndYearMonth()),
                    stateLinkSettingCompanyCommand.getSalaryCode(),
                    stateLinkSettingCompanyCommand.getBonusCode());
        }else if(mode == RegisterMode.UPDATE.value){
            stateCorreHisComService.updateStateCorrelationHisCompany(cid,stateCorrelationHisCompanyCommand.getHistoryID(),
                    new YearMonth(stateCorrelationHisCompanyCommand.getStartYearMonth()),new YearMonth(stateCorrelationHisCompanyCommand.getEndYearMonth()),
                    stateLinkSettingCompanyCommand.getSalaryCode(),
                    stateLinkSettingCompanyCommand.getBonusCode());
        }

    }
}

