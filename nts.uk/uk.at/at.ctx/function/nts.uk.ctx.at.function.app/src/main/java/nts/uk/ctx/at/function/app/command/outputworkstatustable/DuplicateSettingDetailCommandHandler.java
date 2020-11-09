package nts.uk.ctx.at.function.app.command.outputworkstatustable;


import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.DuplicateWorkStatusSettingDomainService;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettingsRepository;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * Command: 勤務状況表の出力項目の詳細を複製する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DuplicateSettingDetailCommandHandler extends CommandHandler<DuplicateSettingDetailCommand> {
    @Inject
    private WorkStatusOutputSettingsRepository settingsRepository;

    @Override
    protected void handle(CommandHandlerContext<DuplicateSettingDetailCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val outPutSettingCode = new OutputItemSettingCode(command.getSettingCode());
        val outPutSettingName = new OutputItemSettingName(command.getSettingName());
        val settingCategory = EnumAdaptor.valueOf(command.getSettingCategory(), SettingClassificationCommon.class);
        RequireImpl require = new RequireImpl(settingsRepository);
        AtomTask persist = DuplicateWorkStatusSettingDomainService
                .duplicate(require,settingCategory,command.getSettingId(),outPutSettingCode,outPutSettingName);
        transaction.execute(persist::run);
    }

    @AllArgsConstructor
    public class RequireImpl implements DuplicateWorkStatusSettingDomainService.Require{
        private WorkStatusOutputSettingsRepository settingsRepository;
        @Override
        public WorkStatusOutputSettings getWorkStatusOutputSettings(String cid, String settingId) {
            return this.settingsRepository.getWorkStatusOutputSettings(cid,settingId);
        }

        @Override
        public void duplicateConfigurationDetails(String cid, String replicationSourceSettingId,
                                                  String replicationDestinationSettingId,
                                                  OutputItemSettingCode duplicateCode,
                                                  OutputItemSettingName copyDestinationName) {
            this.settingsRepository.duplicateConfigurationDetails(cid,replicationSourceSettingId,
                    replicationDestinationSettingId,duplicateCode,copyDestinationName);
        }

        @Override
        public boolean checkTheStandard(String code, String cid) {
            return this.settingsRepository.exist(new OutputItemSettingCode(code),cid);
        }

        @Override
        public boolean checkFreedom(String code, String cid, String employeeId) {
            return this.settingsRepository.exist(new OutputItemSettingCode(code),cid,employeeId);
        }
    }

}
