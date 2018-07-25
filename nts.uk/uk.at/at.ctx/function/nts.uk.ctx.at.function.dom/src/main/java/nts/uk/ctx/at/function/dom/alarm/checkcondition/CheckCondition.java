package nts.uk.ctx.at.function.dom.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;

/**
 * @author dxthuong
 * チェック条件
 */
@Getter
public class CheckCondition  extends DomainObject {
	/**
	 * Alarm category
	 */
	private AlarmCategory alarmCategory;
	/**
	 * list alarm check codition code
	 * @see AlarmCheckConditionByCategory
	 */
	private List<String> checkConditionList = new ArrayList<String>();

	/**
	 * Extraction Range abstract class
	 */
	private List<ExtractionRangeBase> extractPeriodList;
	
	public CheckCondition(AlarmCategory alarmCategory,
			List<String> checkConditionList, List<ExtractionRangeBase>  extractPeriodList) {
		super();
		this.alarmCategory = alarmCategory;
		this.checkConditionList = checkConditionList;
		this.extractPeriodList = extractPeriodList;
	}
	
	public boolean isDaily() {
		return this.alarmCategory == AlarmCategory.DAILY;
	}
	
	public boolean isMonthly() {
		return this.alarmCategory == AlarmCategory.MONTHLY;
	}
	public boolean isMultipleMonth() {
		return this.alarmCategory == AlarmCategory.MULTIPLE_MONTH;
	}
	public boolean is4W4D() {
		return this.alarmCategory == AlarmCategory.SCHEDULE_4WEEK;
	}
	
	public boolean isManHourCheck() {
		return this.alarmCategory == AlarmCategory.MAN_HOUR_CHECK;
	}
	
	public boolean isAgrrement() {
		return this.alarmCategory== AlarmCategory.AGREEMENT;
	}
}
