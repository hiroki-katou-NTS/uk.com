package nts.uk.ctx.at.function.app.command.outputworkstatustable;


import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.*;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

/**
 * Command: 勤務状況表の設定の詳細を更新する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateSettingDetailCommandHandler extends CommandHandler<UpdateSettingDetailCommand>{


    @Inject
    private WorkStatusOutputSettingsRepository workStatusOutputSettingsRepository;
    @Override
    protected void handle(CommandHandlerContext<UpdateSettingDetailCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val code = new OutputItemSettingCode(command.getCode());
        val name = new OutputItemSettingName(command.getName());
        val settingCategory = EnumAdaptor.valueOf(command.getSettingCategory(), SettingClassificationCommon.class);
        val outputItemList = command.getOutputItemList();
        RequireImpl require = new RequireImpl(workStatusOutputSettingsRepository);
        AtomTask persist = UpdateWorkStatusSettingDomainService
                .updateSetting(require,command.getSettingId(),code,name,settingCategory,outputItemList);
        transaction.execute(persist::run);
    }

    @AllArgsConstructor
    public class RequireImpl implements UpdateWorkStatusSettingDomainService.Require{
        private WorkStatusOutputSettingsRepository workStatusOutputSettingsRepository;
        @Override
        public WorkStatusOutputSettings getWorkStatusOutputSettings(String cid, String settingId) {
            return this.workStatusOutputSettingsRepository.getWorkStatusOutputSettings(cid,settingId);
        }

        @Override
        public void updateBoilerplateSelection(String cid, String settingId, WorkStatusOutputSettings outputSettings,
                                               List<OutputItem> outputItemList,
                                               List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {
            this.workStatusOutputSettingsRepository
                    .updateBoilerplateSelection(cid,settingId,outputSettings,outputItemList,attendanceItemList);
        }

        @Override
        public void updateFreeSettings(String cid, String settingId, WorkStatusOutputSettings outputSettings,
                                       List<OutputItem> outputItemList,
                                       List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {
            this.workStatusOutputSettingsRepository
                    .updateFreeSettings(cid,settingId,outputSettings,outputItemList,attendanceItemList);
        }

        @Override
        public boolean checkTheStandard(String code, String cid) {
            return workStatusOutputSettingsRepository.exist(new OutputItemSettingCode(code), cid);
        }

        @Override
        public boolean checkFreedom(String code, String cid, String employeeId) {
            return workStatusOutputSettingsRepository.exist(new OutputItemSettingCode(code), cid, employeeId);
        }
    }
}
