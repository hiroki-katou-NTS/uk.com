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
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Optional;

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
        public Optional<WorkStatusOutputSettings> getWorkStatusOutputSettings(String settingId) {
            val rs =  this.settingsRepository.getWorkStatusOutputSettings(AppContexts.user().companyId(),settingId);
            if (rs != null) {
                return Optional.of(rs);
            }
            return Optional.empty();
        }

        @Override
        public void duplicateConfigurationDetails(String replicationSourceSettingId,
                                                  String replicationDestinationSettingId,
                                                  OutputItemSettingCode duplicateCode,
                                                  OutputItemSettingName copyDestinationName) {
            this.settingsRepository.duplicateConfigurationDetails(AppContexts.user().companyId(),replicationSourceSettingId,
                    replicationDestinationSettingId,duplicateCode,copyDestinationName);
        }

        @Override
        public boolean exist(String code) {
            return this.settingsRepository.exist(new OutputItemSettingCode(code),AppContexts.user().companyId());
        }

        @Override
        public boolean exist(String code, String employeeId) {
            return this.settingsRepository.exist(new OutputItemSettingCode(code),AppContexts.user().companyId(),employeeId);
        }
    }

}
