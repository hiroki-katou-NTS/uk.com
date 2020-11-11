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
import nts.uk.ctx.at.function.dom.workledgeroutputitem.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.UpdateWorkLedgerSettingDomainService;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItem;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItemRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * COMMAND 勤務台帳の設定の詳細を更新する
 *
 * @author chinh.hm
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateWorkLedgerSettingCommandHandler extends CommandHandler<UpdateWorkLedgerSettingCommand> {
    @Inject
    private WorkLedgerOutputItemRepo workLedgerOutputItemRepo;
    @Override
    protected void handle(CommandHandlerContext<UpdateWorkLedgerSettingCommand> commandHandlerContext) {

        val command = commandHandlerContext.getCommand();
        String id = command.getId();
        OutputItemSettingCode code = new OutputItemSettingCode(command.getCode());
        OutputItemSettingName name = new OutputItemSettingName(command.getName());
        SettingClassificationCommon settingCategory =
                EnumAdaptor.valueOf(command.getSettingCategory(), SettingClassificationCommon.class);
        List<Integer> rankingList = command.getRankingList();
        List<Integer> attendanceIdList = command.getAttendanceIdList();

        RequireImpl require = new RequireImpl(workLedgerOutputItemRepo);

        AtomTask persist = UpdateWorkLedgerSettingDomainService
                .updateSetting(require,id,code,name,settingCategory,rankingList,attendanceIdList);
        transaction.execute(persist::run);

    }

    @AllArgsConstructor
    public class RequireImpl implements UpdateWorkLedgerSettingDomainService.Require {
        private WorkLedgerOutputItemRepo workLedgerOutputItemRepo;

        @Override
        public Optional<WorkLedgerOutputItem> getOutputSettingDetail(String id) {
            val rs = workLedgerOutputItemRepo.getWorkStatusOutputSettings(AppContexts.user().companyId(), id);
            if (rs != null) {
                return Optional.of(rs);
            }
            return Optional.empty();
        }

        @Override
        public void updateWorkLedgerOutputItem(String id, WorkLedgerOutputItem outputSetting,
                                               List<AttendanceItemToPrint> outputItemList) {
            workLedgerOutputItemRepo.update(AppContexts.user().companyId(), id, outputSetting, outputItemList);

        }
    }
}
