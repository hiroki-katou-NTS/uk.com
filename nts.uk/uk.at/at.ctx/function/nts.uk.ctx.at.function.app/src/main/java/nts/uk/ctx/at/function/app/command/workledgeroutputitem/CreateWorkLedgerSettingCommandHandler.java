package nts.uk.ctx.at.function.app.command.workledgeroutputitem;


import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.CreateWorkLedgerSettingDomainService;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItem;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItemRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

/**
 * COMMAND 勤務台帳の設定の詳細を作成する
 *
 * @author CHINH.HM
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CreateWorkLedgerSettingCommandHandler extends CommandHandler<CreateWorkLedgerSettingCommand> {
    @Inject
    private WorkLedgerOutputItemRepo workLedgerOutputItemRepo;

    @Override
    protected void handle(CommandHandlerContext<CreateWorkLedgerSettingCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val code = new OutputItemSettingCode(command.getCode());
        val name = new OutputItemSettingName(command.getName());
        SettingClassificationCommon settingCategory =
                EnumAdaptor.valueOf(command.getSettingCategory(), SettingClassificationCommon.class);
        List<AttendanceItemToPrint> outputItemList = command.getOutputItemList();
        RequireImpl require = new RequireImpl(workLedgerOutputItemRepo);

        AtomTask persist = CreateWorkLedgerSettingDomainService.createSetting(require, code, name,
                settingCategory, outputItemList);
        transaction.execute(persist::run);

    }

    @AllArgsConstructor
    public class RequireImpl implements CreateWorkLedgerSettingDomainService.Require {
        private WorkLedgerOutputItemRepo ledgerOutputItemRepo;

        @Override
        public void createWorkLedgerOutputSetting(WorkLedgerOutputItem outputSetting) {
            ledgerOutputItemRepo.createNew(AppContexts.user().companyId(), outputSetting);
        }

        @Override
        public boolean standardCheck(OutputItemSettingCode code) {
            return ledgerOutputItemRepo.exist(code, AppContexts.user().companyId());
        }

        @Override
        public boolean freeCheck(OutputItemSettingCode code, String employeeId) {
            return ledgerOutputItemRepo.exist(code, AppContexts.user().companyId(), employeeId);
        }
    }
}
