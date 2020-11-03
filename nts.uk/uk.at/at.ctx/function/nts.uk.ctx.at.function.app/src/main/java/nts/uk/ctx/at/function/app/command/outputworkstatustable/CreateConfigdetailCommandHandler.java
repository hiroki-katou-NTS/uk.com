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
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CreateConfigdetailCommandHandler extends CommandHandler<CreateConfigdetailCommand> {

    @Inject
    WorkStatusOutputSettingsRepository workStatusOutputSettingsRepository;

    @Override
    protected void handle(CommandHandlerContext<CreateConfigdetailCommand> commandHandlerContext) {
        RequireImpl require = new RequireImpl(workStatusOutputSettingsRepository);
        val command = commandHandlerContext.getCommand();
        val code = new OutputItemSettingCode(command.getCode());
        val name = new OutputItemSettingName(command.getName());
        val settingCategory = EnumAdaptor.valueOf(command.getSettingCategory(), SettingClassificationCommon.class);
        val listItem = command.getOutputItemList();
        AtomTask persist = CreateWorkStatusSettingDomainService.createSetting(require, code, name, settingCategory, listItem);

        transaction.execute(persist::run);
    }



    @AllArgsConstructor
    public class RequireImpl implements CreateWorkStatusSettingDomainService.Require {
        private WorkStatusOutputSettingsRepository workStatusOutputSettingsRepository;

        @Override
        public void createNewFixedPhrase(String cid, WorkStatusOutputSettings outputSettings,
                                         List<OutputItem> outputItemList,
                                         List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {
            workStatusOutputSettingsRepository.createNew(cid, outputSettings, outputItemList, attendanceItemList);
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
