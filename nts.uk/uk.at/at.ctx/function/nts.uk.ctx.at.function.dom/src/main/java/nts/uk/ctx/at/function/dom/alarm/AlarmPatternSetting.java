package nts.uk.ctx.at.function.dom.alarm;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;

import java.util.List;

/**
 * Domain アラームリストパターン設定<br>
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.アラーム.アラームリストパターン設定
 *
 * @author nws-minhnb
 */
@Getter
public class AlarmPatternSetting extends AggregateRoot {

	/**
	 * カテゴリ別チェック条件
	 */
	private List<CheckCondition> checkConList;

	/**
	 * アラームリストパターンコード
	 */
	private AlarmPatternCode alarmPatternCD;

	/**
	 * 会社ID
	 */
	private String companyID;

	/**
	 * アラームリスト権限設定
	 */
	private AlarmPermissionSetting alarmPerSet;

	/**
	 * アラームリストパターン名称
	 */
	private AlarmPatternName alarmPatternName;

	/**
	 * No args constructor.
	 */
	private AlarmPatternSetting() {
	}

	/**
	 * Creates domain from memento.
	 *
	 * @param companyID the company id
	 * @param memento   the Memento getter
	 * @return the domain Alarm pattern setting
	 */
	public static AlarmPatternSetting createFromMemento(String companyID, MementoGetter memento) {
		AlarmPatternSetting domain = new AlarmPatternSetting();
		domain.getMemento(memento);
		domain.companyID = companyID;
		return domain;
	}

	/**
	 * Gets memento.
	 *
	 * @param memento the Memento getter
	 */
	public void getMemento(MementoGetter memento) {
		this.checkConList = memento.getCheckConList();
		this.alarmPatternCD = new AlarmPatternCode(memento.getAlarmPatternCD());
//		this.companyID = memento.getCompanyID();
		this.alarmPerSet = memento.getAlarmPerSet();
		this.alarmPatternName = new AlarmPatternName(memento.getAlarmPatternName());
	}

	/**
	 * Sets memento.
	 *
	 * @param memento the Memento setter
	 */
	public void setMemento(MementoSetter memento) {
		memento.setCheckConList(this.checkConList);
		memento.setAlarmPatternCD(this.alarmPatternCD.v());
		memento.setCompanyID(this.companyID);
		memento.setAlarmPerSet(this.alarmPerSet);
		memento.setAlarmPatternName(this.alarmPatternName.v());
	}

	/**
	 * The interface Memento setter.
	 */
	public static interface MementoSetter {

		/**
		 * Sets check condition list.
		 *
		 * @param checkConList the check condition list
		 */
		void setCheckConList(List<CheckCondition> checkConList);

		/**
		 * Sets alarm pattern code.
		 *
		 * @param alarmPatternCD the alarm pattern code
		 */
		void setAlarmPatternCD(String alarmPatternCD);

		/**
		 * Sets company id.
		 *
		 * @param companyID the company id
		 */
		void setCompanyID(String companyID);

		/**
		 * Sets alarm permission setting.
		 *
		 * @param alarmPerSet the alarm permission setting
		 */
		void setAlarmPerSet(AlarmPermissionSetting alarmPerSet);

		/**
		 * Sets alarm pattern name.
		 *
		 * @param alarmPatternName the alarmPatternName
		 */
		void setAlarmPatternName(String alarmPatternName);

	}

	/**
	 * The interface Memento getter.
	 */
	public static interface MementoGetter {

		/**
		 * Gets check condition list.
		 *
		 * @return the check condition list
		 */
		List<CheckCondition> getCheckConList();

		/**
		 * Gets alarm pattern code.
		 *
		 * @return the alarm pattern code
		 */
		String getAlarmPatternCD();

		/**
		 * Gets company id.
		 *
		 * @return the company id
		 */
		String getCompanyID();

		/**
		 * Gets alarm permission setting.
		 *
		 * @return the alarm permission setting
		 */
		AlarmPermissionSetting getAlarmPerSet();

		/**
		 * Gets alarm pattern name.
		 *
		 * @return the alarmPatternName
		 */
		String getAlarmPatternName();

	}

	/**
	 * Is check condition list not empty.
	 *
	 * @return {@code true} if the list is not empty
	 * @throws BusinessException if the list is empty
	 */
	public boolean isCheckConListNotEmpty() throws BusinessException {
		// 使用するチェック条件が1つ以上選択されていなければならない
		if (this.checkConList.isEmpty()) {
			throw new BusinessException("Msg_811");
		}
		return true;
	}

}
