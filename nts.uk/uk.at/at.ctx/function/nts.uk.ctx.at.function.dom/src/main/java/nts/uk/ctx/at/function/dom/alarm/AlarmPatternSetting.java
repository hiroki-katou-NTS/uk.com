package nts.uk.ctx.at.function.dom.alarm;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;

/**
 * Domain アラームリストパターン設定<br>
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.アラーム.アラームリストパターン設定
 *
 * @author dxthuong
 */
@Getter
public class AlarmPatternSetting  extends AggregateRoot {
	/**
	 * list check condition.<br>
	 * カテゴリ別チェック条件
	 */
	private List<CheckCondition> checkConList = new ArrayList<CheckCondition>();
	/**
	 * alarm pattern code.<br>
	 * アラームリストパターンコード
	 */
	private AlarmPatternCode alarmPatternCD;
	/**
	 * companyId.<br>
	 * 会社ID
	 */
	private String companyID;
	/**
	 * alarm permission setting.<br>
	 * アラームリスト権限設定
	 */
	private AlarmPermissionSetting alarmPerSet;
	/**
	 * alarm pattern name.<br>
	 * アラームリストパターン名称
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
		if(this.checkConList.isEmpty()) {
			throw new BusinessException("Msg_811");
		}
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
