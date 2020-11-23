package nts.uk.ctx.at.function.app.command.outputworkanualsetting;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSetting;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSettingRepository;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.DuplicateAnualWorkLedgerDomainService;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Command : 年間勤務台帳の出力項目の詳細を複製する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DuplicateAnualWorkledgerSettingCommandHandler extends CommandHandler<DuplicateAnualWorkledgerSettingCommand> {

    @Inject
    AnnualWorkLedgerOutputSettingRepository repository;

    @Override
    protected void handle(CommandHandlerContext<DuplicateAnualWorkledgerSettingCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        RequireImpl require = new RequireImpl(repository);
        AtomTask persist = DuplicateAnualWorkLedgerDomainService.duplicate(
            require,
            EnumAdaptor.valueOf(command.getSettingCategory(), SettingClassificationCommon.class),
            command.getSettingId(),
            new OutputItemSettingCode(command.getSettingCode()),
            new OutputItemSettingName(command.getSettingName())
        );

        transaction.execute(persist::run);
    }

    @AllArgsConstructor
    public class RequireImpl implements DuplicateAnualWorkLedgerDomainService.Require {
        private AnnualWorkLedgerOutputSettingRepository repository;

        @Override
        public boolean checkTheStandard(OutputItemSettingCode code, String cid) {
            return repository.exist(code, cid);
        }

        @Override
        public boolean checkFreedom(OutputItemSettingCode code, String cid, String employeeId) {
            return repository.exist(code, cid, employeeId);
        }

        @Override
        public Optional<AnnualWorkLedgerOutputSetting> getSetting(String settingId) {
            return repository.getDetailsOfTheOutputSettings(AppContexts.user().companyId(), settingId);
        }

        @Override
        public void duplicate(String emId, String replicationSourceSettingId,
                                                  String replicationDestinationSettingId,
                                                  OutputItemSettingCode duplicateCode, OutputItemSettingName copyDestinationName) {
            this.repository.duplicateConfigurationDetail(AppContexts.user().companyId(), emId, replicationSourceSettingId,
                replicationDestinationSettingId, duplicateCode, copyDestinationName);
        }
    }
}
