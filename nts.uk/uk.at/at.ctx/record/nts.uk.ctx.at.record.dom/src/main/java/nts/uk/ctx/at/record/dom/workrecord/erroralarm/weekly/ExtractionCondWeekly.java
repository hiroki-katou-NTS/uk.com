package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlCategory;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.WorkCheckResult;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.LogicalOperator;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly.algorithm.WeeklyAttendanceItemCondition;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.AttendanceTimeOfWeeklyKey;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.converter.WeeklyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import org.apache.commons.lang3.StringUtils;

/**
 * エラーアラームのカテゴリ別抽出条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExtractionCondWeekly extends AggregateRoot {

    /** チェック条件コード **/
    private ErrorAlarmWorkRecordCode code;

    /** 名称 **/
    private ErrorAlarmWorkRecordName name;

    /** メッセージ */
    private String message;

    /** 勤怠項目の条件 **/
    private AttendanceItemCondition atdItemCondition;

    /**
     * Create new domain
     * @param code code
     * @param name name
     * @return domain
     */
    public static ExtractionCondWeekly create(String code, String name, String message, AttendanceItemCondition atdItemCond) {
    	ExtractionCondWeekly domain = new ExtractionCondWeekly(
                new ErrorAlarmWorkRecordCode(code),
                new ErrorAlarmWorkRecordName(name),
                message,
                atdItemCond);
    	return domain;
    } 

    public WorkCheckResult checkTarget(Require require, AttendanceTimeOfWeekly targetData) {
        return this.getAtdItemCondition().check(itemNolist -> {
            WeeklyRecordToAttendanceItemConverter weeklyConvert = require.createWeeklyConverter();
            weeklyConvert.withAttendanceTime(targetData);

            return weeklyConvert.convert(itemNolist).stream()
                    .map(itemValue -> itemValue.doubleOrDefault())
                    .collect(Collectors.toList());
        });
    }

    public interface Require{
        WeeklyRecordToAttendanceItemConverter createWeeklyConverter();
    }
}
