package nts.uk.ctx.at.function.dom.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author dxthuong
 * チェック条件
 */
@Getter
public class CheckCondition  extends AggregateRoot {
	/**
	 * alarm pattern code
	 */
	private AlarmPatternCode alarmPatternCD;
	/**
	 *  companyID
	 */
	private CompanyId companyID;
	/**
	 * Alarm category
	 */
	private AlarmCategory alarmCategory;
	/**
	 * list alarm check codition code
	 */
	private List<AlarmCheckConditionCode> checkConditionList= new ArrayList<AlarmCheckConditionCode>();

	/**
	 * Extraction Range abstract class
	 */
	private ExtractionRangeBase extractPeriod;
	
	public CheckCondition(String alarmPatternCD, String companyID, int alarmCategory,
			List<AlarmCheckConditionCode> checkConditionList, ExtractionRangeBase  extractPeriod) {
		super();
		this.alarmPatternCD = new AlarmPatternCode(alarmPatternCD);
		this.companyID = new CompanyId(companyID);
		this.alarmCategory = EnumAdaptor.valueOf(alarmCategory, AlarmCategory.class);
		this.checkConditionList = checkConditionList;
		this.extractPeriod = extractPeriod;
	}

}
