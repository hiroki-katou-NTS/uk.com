package nts.uk.ctx.at.function.app.command.alarmworkplace;

import nts.arc.enums.EnumAdaptor;
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
 * パターン設定を登録する
 */
@Stateless
public class RegisterAlarmPatternSettingWorkPlaceCommandHandler extends CommandHandler<RegisterAlarmPatternSettingWorkPlaceCommand> {

    @Inject
    private AlarmPatternSettingWorkPlaceRepository repository;

    @Override
    protected void handle(CommandHandlerContext<RegisterAlarmPatternSettingWorkPlaceCommand> context) {
        RegisterAlarmPatternSettingWorkPlaceCommand command = context.getCommand();

        List<CheckCondition> checkConList = new ArrayList<>();

        command.getCheckConList().forEach(x -> {
            if (x.getAlarmCategory() == WorkplaceCategory.MONTHLY.value) {
                checkConList.add(new CheckCondition(
                    EnumAdaptor.valueOf(x.getAlarmCategory(), WorkplaceCategory.class),
                    x.getCheckConditionCodes().stream().map(AlarmCheckConditionCode::new).collect(Collectors.toList()),
                    SingleMonthCommand.toDomain(x.getSingleMonth())
                ));
            } else if (x.getAlarmCategory() == WorkplaceCategory.MASTER_CHECK_BASIC.value || x.getAlarmCategory() == WorkplaceCategory.MASTER_CHECK_WORKPLACE.value) {
                checkConList.add(new CheckCondition(
                    EnumAdaptor.valueOf(x.getAlarmCategory(), WorkplaceCategory.class),
                    x.getCheckConditionCodes().stream().map(AlarmCheckConditionCode::new).collect(Collectors.toList()),
                    ExtractionPeriodMonthlyCommand.toDomain(x.getListExtractionMonthly())
                ));
            } else if (x.getAlarmCategory() == WorkplaceCategory.MASTER_CHECK_DAILY.value || x.getAlarmCategory() == WorkplaceCategory.SCHEDULE_DAILY.value ||
                x.getAlarmCategory() == WorkplaceCategory.APPLICATION_APPROVAL.value) {
                checkConList.add(new CheckCondition(
                    EnumAdaptor.valueOf(x.getAlarmCategory(), WorkplaceCategory.class),
                    x.getCheckConditionCodes().stream().map(AlarmCheckConditionCode::new).collect(Collectors.toList()),
                    ExtractionPeriodDailyCommand.toDomain(x.getExtractionDaily())
                ));
            }
        });

        AlarmPatternSettingWorkPlace domain = new AlarmPatternSettingWorkPlace(
            checkConList,
            command.getAlarmPatternCD(),
            AppContexts.user().companyId(),
            new AlarmPermissionSetting(command.getAlarmPerSet().isAuthSetting(), command.getAlarmPerSet().getRoleIds()),
            command.getAlarmPatternName()
        );

        Optional<AlarmPatternSettingWorkPlace> settingWorkPlace = repository.getBy(AppContexts.user().companyId(), new AlarmPatternCode(command.getAlarmPatternCD()));

        if (settingWorkPlace.isPresent()) {
            repository.update(domain);
        } else {
            repository.create(domain);
        }
    }
}
