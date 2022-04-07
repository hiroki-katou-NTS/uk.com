package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedTarget;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

/**
 * 週別実績の抽出条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExtractionCondWeekly extends AggregateRoot {

    // ID
    private String errorAlarmId;

    // チェック条件
    @Setter
    private CheckedCondition checkConditions;

    // チェック項目の種類
    private WeeklyCheckItemType checkItemType;

    // 並び順
    private int sortOrder;

    // 使用区分
    private boolean isUse;

    // 名称
    private ErrorAlarmWorkRecordName name;

    // メッセージ
    private Optional<ErrorAlarmMessage> errorAlarmMessage;

    // 連続期間
    @Setter
    private Optional<ContinuousPeriod> continuousPeriod;
    
    /**
     * チェック対象
     */
    @Setter
    private Optional<CheckedTarget> checkedTarget;
    
    /**
     * Create new domain
     * @param errorAlarmId error alarm check id
     * @param sortOrder sort
     * @param isUse is use
     * @param name name
     * @param message error alarm message
     * @return domain
     */
    public static ExtractionCondWeekly create(String errorAlarmId, int sortOrder, boolean isUse, String name, String message, WeeklyCheckItemType checkType, Integer continuousPeriod) {
    	ExtractionCondWeekly domain = new ExtractionCondWeekly();
    	domain.errorAlarmId = errorAlarmId;
    	domain.sortOrder = sortOrder;
    	domain.isUse = isUse;
    	domain.name = new ErrorAlarmWorkRecordName(name);
    	if (StringUtils.isNotEmpty(message)) {
    		domain.errorAlarmMessage = Optional.of(new ErrorAlarmMessage(message));
    	}
    	boolean enableContinuousPeriod = WeeklyCheckItemType.CONTINUOUS_DAY == checkType || WeeklyCheckItemType.CONTINUOUS_TIME == checkType || WeeklyCheckItemType.CONTINUOUS_TIMES == checkType;
    	if (continuousPeriod != null && enableContinuousPeriod) {
    		domain.continuousPeriod = Optional.of(new ContinuousPeriod(continuousPeriod));
    	}
    	domain.checkItemType = checkType;
    	return domain;
    } 
    
    public boolean isContinuos() {
    	return this.checkItemType == WeeklyCheckItemType.CONTINUOUS_TIME
    			|| this.checkItemType == WeeklyCheckItemType.CONTINUOUS_TIMES
    			|| this.checkItemType == WeeklyCheckItemType.CONTINUOUS_DAY;
    }
}
