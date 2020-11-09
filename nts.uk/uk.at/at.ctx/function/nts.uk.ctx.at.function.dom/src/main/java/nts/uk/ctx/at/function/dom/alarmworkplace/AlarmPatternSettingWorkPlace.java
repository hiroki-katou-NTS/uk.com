package nts.uk.ctx.at.function.dom.alarmworkplace;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternName;
import nts.uk.ctx.at.function.dom.alarm.AlarmPermissionSetting;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * AggregateRoot : アラームリストパターン設定(職場別)
 */
@Getter
public class AlarmPatternSettingWorkPlace extends AggregateRoot {

	/**
	 * カテゴリ別チェック条件
	 */
	private List<CheckCondition> checkConList = new ArrayList<CheckCondition>();

	/**
	 * アラームリストパターンコード
	 */
	private AlarmPatternCode alarmPatternCD;

	/**
	 * companyId
	 */
	private String companyID;

	/**
	 * 実行権限
	 */
	private AlarmPermissionSetting alarmPerSet;

	/**
	 * 名称
	 */
	private AlarmPatternName alarmPatternName;

	public AlarmPatternSettingWorkPlace(List<CheckCondition> checkConList, String alarmPatternCD, String companyID,
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
