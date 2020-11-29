package nts.uk.ctx.at.function.app.command.alarmworkplace;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition.*;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarmworkplace.CheckCondition;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RegisterAlarmPatternSettingWorkPlaceCommand {

    /**
     * アラームリストパターンコード
     */
    private String alarmPatternCD;

    /**
     * 名称
     */
    private String alarmPatternName;

    /**
     * 実行権限
     */
    private AlarmPermissionSettingCommand alarmPerSet;

    /**
     * カテゴリ別チェック条件
     */
    private List<CheckConditionCommand> checkConList;

    public List<CheckCondition> toDomain(){
        List<CheckCondition> result = new ArrayList<>();
        checkConList.forEach(x -> {
            if (x.getAlarmCategory() == WorkplaceCategory.MONTHLY.value) {
                result.add(new CheckCondition(
                        EnumAdaptor.valueOf(x.getAlarmCategory(), WorkplaceCategory.class),
                        x.getCheckConditionCodes().stream().map(AlarmCheckConditionCode::new).collect(Collectors.toList()),
                        SingleMonthCommand.toDomain(x.getSingleMonth())
                ));
            } else if (x.getAlarmCategory() == WorkplaceCategory.MASTER_CHECK_BASIC.value || x.getAlarmCategory() == WorkplaceCategory.MASTER_CHECK_WORKPLACE.value) {
                result.add(new CheckCondition(
                        EnumAdaptor.valueOf(x.getAlarmCategory(), WorkplaceCategory.class),
                        x.getCheckConditionCodes().stream().map(AlarmCheckConditionCode::new).collect(Collectors.toList()),
                        ExtractionPeriodMonthlyCommand.toDomain(x.getListExtractionMonthly())
                ));
            } else if (x.getAlarmCategory() == WorkplaceCategory.MASTER_CHECK_DAILY.value
                    || x.getAlarmCategory() == WorkplaceCategory.SCHEDULE_DAILY.value ||
                    x.getAlarmCategory() == WorkplaceCategory.APPLICATION_APPROVAL.value) {
                result.add(new CheckCondition(
                        EnumAdaptor.valueOf(x.getAlarmCategory(), WorkplaceCategory.class),
                        x.getCheckConditionCodes().stream().map(AlarmCheckConditionCode::new).collect(Collectors.toList()),
                        ExtractionPeriodDailyCommand.toDomain(x.getExtractionDaily())
                ));
            }
        });
        return result;
    }

}
