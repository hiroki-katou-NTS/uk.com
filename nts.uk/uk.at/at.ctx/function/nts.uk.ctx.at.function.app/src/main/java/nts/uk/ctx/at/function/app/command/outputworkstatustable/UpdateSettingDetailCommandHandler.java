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
import nts.uk.shr.com.context.AppContexts;

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
        public WorkStatusOutputSettings getWorkStatusOutputSettings( String settingId) {
            return this.workStatusOutputSettingsRepository.getWorkStatusOutputSettings(AppContexts.user().companyId(),settingId);
        }

        @Override
        public void update( WorkStatusOutputSettings outputSettings) {
            this.workStatusOutputSettingsRepository
                    .update(AppContexts.user().companyId(),outputSettings);
        }
        @Override
        public boolean checkTheStandard(String code) {
            return workStatusOutputSettingsRepository.exist(new OutputItemSettingCode(code), AppContexts.user().companyId());
        }

        @Override
        public boolean checkFreedom(String code, String employeeId) {
            return workStatusOutputSettingsRepository.exist(new OutputItemSettingCode(code), AppContexts.user().companyId(), employeeId);
        }
    }
}
