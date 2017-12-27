package nts.uk.ctx.at.function.dom.alarm;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;

/**
 * @author dxthuong
 * アラームリストパターン設定
 */
@Getter
public class AlarmPatternSetting  extends AggregateRoot {
	/**
	 * list check condition
	 */
	private List<CheckCondition> checkConList = new ArrayList<CheckCondition>();
	/**
	 * alarm pattern code
	 */
	private AlarmPatternCode alarmPatternCD;
	/**
	 * companyId
	 */
	private String companyID;
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
		this.companyID = companyID;
		this.alarmPerSet = alarmPerSet;
		this.alarmPatternName = new AlarmPatternName(alarmPatternName);
	}
	

	public boolean selectedCheckCodition() {
		if(this.checkConList.isEmpty())
			throw new BusinessException("Msg_811");
		return true;
	}


	public void setCheckConList(List<CheckCondition> checkConList) {
		this.checkConList = checkConList;
	}


	public void setAlarmPerSet(AlarmPermissionSetting alarmPerSet) {
		this.alarmPerSet = alarmPerSet;
	}


	public void setAlarmPatternName(String alarmPatternName) {
		this.alarmPatternName = new AlarmPatternName(alarmPatternName);
	}
	
	

}
