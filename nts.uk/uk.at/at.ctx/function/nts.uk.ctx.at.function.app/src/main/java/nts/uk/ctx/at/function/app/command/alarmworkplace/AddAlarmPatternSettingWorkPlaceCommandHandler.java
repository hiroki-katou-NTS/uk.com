package nts.uk.ctx.at.function.app.command.alarmworkplace;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition.ExtractionPeriodDailyCommand;
import nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition.ExtractionPeriodMonthlyCommand;
import nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition.SingleMonthCommand;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlaceRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPermissionSetting;
import nts.uk.ctx.at.function.dom.alarmworkplace.CheckCondition;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * パターン設定を新規する。
 */
@Stateless
public class AddAlarmPatternSettingWorkPlaceCommandHandler extends CommandHandler<RegisterAlarmPatternSettingWorkPlaceCommand> {

    @Inject
    private AlarmPatternSettingWorkPlaceRepository repository;

    @Override
    protected void handle(CommandHandlerContext<RegisterAlarmPatternSettingWorkPlaceCommand> context) {
        RegisterAlarmPatternSettingWorkPlaceCommand command = context.getCommand();

        Optional<AlarmPatternSettingWorkPlace> settingWorkPlace = repository.getBy(AppContexts.user().companyId(), new AlarmPatternCode(command.getAlarmPatternCD()));

        if (settingWorkPlace.isPresent()){
            throw new BusinessException("Msg_3");
        }

        List<CheckCondition> checkConList = command.toDomain();

        AlarmPatternSettingWorkPlace domain = new AlarmPatternSettingWorkPlace(
            checkConList,
            command.getAlarmPatternCD(),
            AppContexts.user().companyId(),
            new AlarmPermissionSetting(command.getAlarmPerSet().getAuthSetting() == 1, command.getAlarmPerSet().getRoleIds()),
            command.getAlarmPatternName()
        );

        repository.create(domain);
    }
}
