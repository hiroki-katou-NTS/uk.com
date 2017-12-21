package nts.uk.ctx.at.function.dom.alarm.checkcondition;

import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author dxthuong
 * チェック条件
 */
@Getter
public class CheckCondition {
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
	private List<AlarmCheckConditionCode> checkConditionList;
	/**
	 *  extraction Id
	 */
	private String extractionId;
	/**
	 * enum Extraction Range
	 */
	private int extractionRange;
	
	public CheckCondition(String alarmPatternCD, String companyID, int alarmCategory,
			List<AlarmCheckConditionCode> checkConditionList, String extractionId, int extractionRange) {
		super();
		this.alarmPatternCD = new AlarmPatternCode(alarmPatternCD);
		this.companyID = new CompanyId(companyID);
		this.alarmCategory = AlarmCategory.valueOf(alarmCategory);
		this.checkConditionList = checkConditionList;
		this.extractionId = extractionId;
		this.extractionRange = extractionRange;
	}

	public CheckCondition(String alarmPatternCD, String companyID, int alarmCategory) {
		super();
		this.alarmPatternCD = new AlarmPatternCode(alarmPatternCD);
		this.companyID = new CompanyId(companyID);
		this.alarmCategory = AlarmCategory.valueOf(alarmCategory);
	}
	
	public boolean selectedCheckCodition() {
		if(this.checkConditionList==null || this.checkConditionList.isEmpty())
			throw new BusinessException("Msg_811");
		return true;
	}

}
