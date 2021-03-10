package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

/**
 * スケジュール日次の任意抽出条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExtractionCondScheduleDay extends AggregateRoot {

    // ID
    private String errorAlarmId;

    // スケジュール日次のチェック条件
    private ScheduleCheckCond scheduleCheckCond;

    // チェック項目種類
    private DaiCheckItemType checkItemType;

    // 並び順
    private int sortOrder;

    // 使用区分
    private boolean isUse;

    // 名称
    private NameAlarmExtractCond name;

    // 対象とする勤務種類
    private RangeToCheck targetWrkType;

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
    public static ExtractionCondScheduleDay create(String errorAlarmId, int sortOrder, boolean isUse, String name, String message) {
    	ExtractionCondScheduleDay domain = new ExtractionCondScheduleDay();
    	domain.errorAlarmId = errorAlarmId;
    	domain.sortOrder = sortOrder;
    	domain.isUse = isUse;
    	domain.name = new NameAlarmExtractCond(name);
    	if (StringUtils.isNotEmpty(message)) {
    		domain.errorAlarmMessage = Optional.of(new ErrorAlarmMessage(message));
    	}
    	return domain;
    }
}
