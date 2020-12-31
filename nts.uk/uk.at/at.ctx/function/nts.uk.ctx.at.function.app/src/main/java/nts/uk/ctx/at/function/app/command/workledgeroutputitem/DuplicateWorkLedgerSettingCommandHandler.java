package nts.uk.ctx.at.function.app.command.workledgeroutputitem;


import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.DuplicateWorkLedgerSettingDomainService;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItem;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItemRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DuplicateWorkLedgerSettingCommandHandler extends CommandHandler<DuplicateWorkLedgerSettingCommand> {
    @Inject
    private WorkLedgerOutputItemRepo workLedgerOutputItemRepo;

    @Override
    protected void handle(CommandHandlerContext<DuplicateWorkLedgerSettingCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        SettingClassificationCommon settingCategory =
                EnumAdaptor.valueOf(command.getSettingCategory(), SettingClassificationCommon.class);
        String dupSrcId = command.getDupSrcId();
        OutputItemSettingCode dupCode = new OutputItemSettingCode(command.getDupCode());
        OutputItemSettingName dupName = new OutputItemSettingName(command.getDupName());

        RequireImpl require = new RequireImpl(workLedgerOutputItemRepo);
        AtomTask persist = DuplicateWorkLedgerSettingDomainService
                .duplicateSetting(require, settingCategory, dupSrcId, dupCode, dupName);
        transaction.execute(persist::run);
    }

    @AllArgsConstructor
    public class RequireImpl implements DuplicateWorkLedgerSettingDomainService.Require {
        private WorkLedgerOutputItemRepo workLedgerOutputItemRepo;

        @Override
        public Optional<WorkLedgerOutputItem> getOutputItemDetail(String id) {
            val rs = workLedgerOutputItemRepo.getWorkStatusOutputSettings(AppContexts.user().companyId(), id);
            if (rs != null) {
                return Optional.of(rs);
            }
            return Optional.empty();
        }

        @Override
        public void duplicateWorkLedgerOutputItem(String employeeId, String dupSrcId, String dupDestId,
                                                  OutputItemSettingCode dupCode, OutputItemSettingName dupName) {

            workLedgerOutputItemRepo.duplicateConfigDetails(AppContexts.user().companyId(), employeeId, dupSrcId,
                    dupDestId, dupCode, dupName);

        }

        @Override
        public boolean standardCheck(OutputItemSettingCode code) {
            return workLedgerOutputItemRepo.exist(code, AppContexts.user().companyId());
        }

        @Override
        public boolean freeCheck(OutputItemSettingCode code, String employeeId) {
            return workLedgerOutputItemRepo.exist(code, AppContexts.user().companyId(), employeeId);
        }
    }
}
