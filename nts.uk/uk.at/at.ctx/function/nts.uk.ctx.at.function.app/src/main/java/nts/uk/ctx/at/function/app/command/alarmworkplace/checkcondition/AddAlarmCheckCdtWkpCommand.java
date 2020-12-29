package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import lombok.Data;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition.command.ExtractionOptionalItemsCmd;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWorkplaceCategory;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.CheckMonthlyItemsType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.CheckDayItemsType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.ExtractionScheduleCon;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CheckConditions;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareRange;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareSingleValue;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AddAlarmCheckCdtWkpCommand {

    private AlarmCheckWkpCommand alarmCheck;

    private List<AlarmExConditionCommand> alarmCheckCon;

    private List<ExtractionOptionalItemsCmd> opItems;

    public List<ExtractionMonthlyCon> toDomainMon() {
        List<ExtractionMonthlyCon> result = new ArrayList<>();

        if (!this.opItems.isEmpty()) {

            result = this.opItems.stream().map(i -> {
                CheckConditions<Integer> con;
                if (i.getMaxValue() == null) {
                    val compareSingleValue = new CompareSingleValue<Integer>(i.getOperator(), 0);
                    compareSingleValue.setValue(Integer.valueOf(i.getMinValue()));
                    con = compareSingleValue;
                } else {
                    con = new CompareRange<>(Integer.valueOf(i.getMinValue()), Integer.valueOf(i.getMaxValue()), i.getOperator());
                }
                return ExtractionMonthlyCon.create(
                        IdentifierUtil.randomUniqueId(),
                        i.getCheckItem(),
                        i.getNo(),
                        i.getUseAtr() == 0,
                        IdentifierUtil.randomUniqueId(),
                        con,
                        (i.getCheckItem() == CheckMonthlyItemsType.AVERAGE_TIME.value
                                || i.getCheckItem() == CheckMonthlyItemsType.AVERAGE_NUMBER_DAY.value
                                || i.getCheckItem() == CheckMonthlyItemsType.AVERAGE_NUMBER_TIME.value) ?
                                i.getCheckCond() : null,
                        i.getCheckItem() == CheckMonthlyItemsType.AVERAGE_DAY_FREE.value ? i.getCheckCondB() : null,
                        i.getCheckItem() == CheckMonthlyItemsType.AVERAGE_TIME_FREE.value ? i.getCheckCondB() : null,
                        i.getCheckItem() == CheckMonthlyItemsType.TIME_FREEDOM.value ? i.getCheckCondB() : null,
                        i.getCheckItem() == CheckMonthlyItemsType.AVERAGE_RATIO.value ? i.getCheckCondB() : null,
                        i.getName(),
                        i.getMessage()
                );
            }).collect(Collectors.toList());
        }

        return result;
    }

    public List<ExtractionScheduleCon> toDomainSchedule() {
        List<ExtractionScheduleCon> result = new ArrayList<>();

        if (!this.opItems.isEmpty()) {

            result = this.opItems.stream().map(i -> {
                CheckConditions<Integer> con;
                if (i.getMaxValue() == null) {
                    val compareSingleValue = new CompareSingleValue<Integer>(i.getOperator(), 0);
                    compareSingleValue.setValue(Integer.valueOf(i.getMinValue()));
                    con = compareSingleValue;
                } else {
                    con = new CompareRange<>(Integer.valueOf(i.getMinValue()), Integer.valueOf(i.getMaxValue()), i.getOperator());
                }
                return ExtractionScheduleCon.create(
                        IdentifierUtil.randomUniqueId(),
                        i.getNo(),
                        i.getCheckItem(),
                        i.getUseAtr() == 0,
                        IdentifierUtil.randomUniqueId(),
                        con,
                        i.getCheckItem() != CheckDayItemsType.CONTRAST.value ? i.getCheckCond() : null,
                        i.getCheckItem() == CheckDayItemsType.CONTRAST.value ? i.getCheckCondB() : null,
                        i.getName(),
                        i.getMessage()
                );
            }).collect(Collectors.toList());
        }

        return result;
    }

    private <V> CheckConditions<V> convertCondition(int checkItem, String maxValue, String minValue, int operator, boolean isSingleValue) {
        CheckConditions result = null;
        CheckMonthlyItemsType check = EnumAdaptor.valueOf(checkItem, CheckMonthlyItemsType.class);
        switch (check) {
            case AVERAGE_TIME: {

                break;
            }
            case AVERAGE_NUMBER_DAY: {
                break;
            }
            case AVERAGE_NUMBER_TIME: {
                break;
            }
            case AVERAGE_RATIO: {
                break;
            }

        }
        return result;
    };


}
