package nts.uk.ctx.at.function.dom.alarm;

import java.util.List;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author dxthuong
 * アラームリストパターン設定
 */
@Getter
public class AlarmPatternSetting {
	/**
	 * list check condition
	 */
	private List<CheckCondition> checkConList;
	/**
	 * alarm pattern code
	 */
	private AlarmPatternCode alarmPatternCD;
	/**
	 * companyId
	 */
	private CompanyId companyID;
	/**
	 * alarm permission setting
	 */
	private AlarmPermissionSetting alarmPerSet;
	/**
	 * alarm pattern name
	 */
	private AlarmPatternName alarmPatternName;
	
	public AlarmPatternSetting(List<CheckCondition> checkConList, String alarmPatternCD, String companyID,
			AlarmPermissionSetting alarmPerSet, String alarmPatternName) {
		super();
		this.checkConList = checkConList;
		this.alarmPatternCD = new AlarmPatternCode(alarmPatternCD);
		this.companyID = new CompanyId(companyID);
		this.alarmPerSet = alarmPerSet;
		this.alarmPatternName = new AlarmPatternName(alarmPatternName);
	}
	public AlarmPatternSetting(String alarmPatternCD, String companyID,
			String alarmPatternName) {
		super();
		this.alarmPatternCD = new AlarmPatternCode(alarmPatternCD);
		this.companyID = new CompanyId(companyID);
		this.alarmPatternName = new AlarmPatternName(alarmPatternName);
	}
}
