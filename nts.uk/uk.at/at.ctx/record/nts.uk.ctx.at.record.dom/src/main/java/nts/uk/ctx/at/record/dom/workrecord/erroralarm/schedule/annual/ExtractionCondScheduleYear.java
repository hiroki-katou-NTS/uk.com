package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.NameAlarmExtractCond;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedCondition;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

/**
 * スケジュール年間の任意抽出条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExtractionCondScheduleYear extends AggregateRoot {

    // ID
    private String errorAlarmId;

    // スケジュール年間チェック条件
    @Setter
    private ScheduleYearCheckCond scheCheckConditions;

    // チェック条件
    @Setter
    private CheckedCondition checkConditions;

    // チェック項目の種類
    private YearCheckItemType checkItemType;

    // 並び順
    private int sortOrder;

    // 使用区分
    private boolean isUse;

    // 名称
    private NameAlarmExtractCond name;

    // メッセージ
    private Optional<ErrorAlarmMessage> errorAlarmMessage;
    
    /**
     * Create new domain
     * @param errorAlarmId error alarm check id
     * @param sortOrder sort
     * @param isUse is use
     * @param name name
     * @param message error alarm message
     * @return domain
     */
    public static ExtractionCondScheduleYear create(String errorAlarmId, int sortOrder, boolean isUse, String name, String message, YearCheckItemType checkType) {
    	ExtractionCondScheduleYear domain = new ExtractionCondScheduleYear();
    	domain.errorAlarmId = errorAlarmId;
    	domain.sortOrder = sortOrder;
    	domain.isUse = isUse;
    	domain.name = new NameAlarmExtractCond(name);
    	if (StringUtils.isNotEmpty(message)) {
    		domain.errorAlarmMessage = Optional.of(new ErrorAlarmMessage(message));
    	}
    	domain.checkItemType = checkType;
    	return domain;
    }  
}
