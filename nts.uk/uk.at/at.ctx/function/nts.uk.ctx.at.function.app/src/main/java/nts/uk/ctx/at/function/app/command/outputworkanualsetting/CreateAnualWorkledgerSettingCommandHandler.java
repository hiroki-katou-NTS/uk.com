package nts.uk.ctx.at.function.app.command.outputworkanualsetting;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.app.command.outputworkstatustable.CreateConfigdetailCommand;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSetting;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSettingRepository;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.CreateAnualWorkLedgerDomainService;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.DailyOutputItemsAnnualWorkLedger;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.CreateWorkStatusSettingDomainService;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettingsRepository;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CreateAnualWorkledgerSettingCommandHandler extends CommandHandler<CreateAnualWorkledgerSettingCommand> {

    @Inject
    AnnualWorkLedgerOutputSettingRepository repository;

    @Override
    protected void handle(CommandHandlerContext<CreateAnualWorkledgerSettingCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();

        RequireImpl require = new RequireImpl(repository);
        List<DailyOutputItemsAnnualWorkLedger> dailyOutputItems = command.getDailyOutputItems().stream().map(DailyOutputItemsCommand::toDomain).collect(Collectors.toList());
        List<OutputItem> monthlyOutputItems = command.getMonthlyOutputItems().stream().map(MonthlyOutputItemsCommand::toDomain).collect(Collectors.toList());
        AtomTask persist = CreateAnualWorkLedgerDomainService.createSetting(
            require, new OutputItemSettingCode(command.getCode()),
            new OutputItemSettingName(command.getName()),
            EnumAdaptor.valueOf(command.getSettingCategory(), SettingClassificationCommon.class),
            dailyOutputItems,monthlyOutputItems);

        transaction.execute(persist::run);
    }



    @AllArgsConstructor
    public class RequireImpl implements CreateAnualWorkLedgerDomainService.Require {
        private AnnualWorkLedgerOutputSettingRepository repository;

        @Override
        public void createNewSetting(AnnualWorkLedgerOutputSetting outputSettings) {
            repository.createNew(AppContexts.user().companyId(),outputSettings);
        }

        @Override
        public boolean checkTheStandard(OutputItemSettingCode code, String cid) {
            return repository.exist(code,cid);
        }

        @Override
        public boolean checkFreedom(OutputItemSettingCode code, String cid, String employeeId) {
            return repository.exist(code,cid,employeeId);
        }
    }
}
