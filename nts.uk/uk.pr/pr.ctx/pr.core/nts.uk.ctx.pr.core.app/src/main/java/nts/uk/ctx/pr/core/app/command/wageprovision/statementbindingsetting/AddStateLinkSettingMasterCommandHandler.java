package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.MasterCode;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMasterRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StatementCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class AddStateLinkSettingMasterCommandHandler extends CommandHandler<ListStateLinkSettingMasterCommand> {
    
    @Inject
    private StateLinkSettingMasterRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<ListStateLinkSettingMasterCommand> context) {
        List<StateLinkSettingMasterCommand> command = context.getCommand().getListStateLinkSettingMasterCommand();
        List<StateLinkSettingMaster> stateLinkSettingMaster = new ArrayList<StateLinkSettingMaster>();
        if(command.size() > 0){
            stateLinkSettingMaster = command.stream().map(item ->{
                return new StateLinkSettingMaster(item.getHistoryID(),new MasterCode(item.getMasterCode()), new StatementCode(item.getSalaryCode()), new StatementCode(item.getBonusCode()));
            }).collect(Collectors.toList());
        }
        repository.addAll(stateLinkSettingMaster);
    }
}
