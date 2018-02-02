package nts.uk.ctx.at.function.dom.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

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
	private ExtractionRangeBase extractPeriod;
	
	public CheckCondition(AlarmCategory alarmCategory,
			List<String> checkConditionList, ExtractionRangeBase  extractPeriod) {
		super();
		this.alarmCategory = alarmCategory;
		this.checkConditionList = checkConditionList;
		this.extractPeriod = extractPeriod;
	}
	
	public boolean isDaily() {
		return this.alarmCategory == AlarmCategory.DAILY;
	}
	
	

}
