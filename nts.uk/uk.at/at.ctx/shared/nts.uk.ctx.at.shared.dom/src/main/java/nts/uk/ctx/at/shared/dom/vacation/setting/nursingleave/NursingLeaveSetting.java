/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.shr.com.time.calendar.MonthDay;

/**
 * The Class NursingLeaveSetting.
 */
// 介護看護休暇設定
@Getter
public class NursingLeaveSetting extends AggregateRoot {

	/** 会社ID */
	private String companyId;

	/** 管理区分 */
	private ManageDistinct manageType;

	/** 介護看護区分 */
	private NursingCategory nursingCategory;

	/** 起算日 */
	private MonthDay startMonthDay;

	/** 上限人数設定 */
	private MaxPersonSetting maxPersonSetting;

	/** 特別休暇枠NO */
	private Optional<Integer> specialHolidayFrame;

	/** 欠勤枠NO */
	private Optional<Integer> workAbsence;

	/**
	 * Checks if is managed.
	 *
	 * @return true, if is managed
	 */
	public boolean isManaged() {
		return this.manageType.equals(ManageDistinct.YES);
	}

	/**
	 * Instantiates a new nursing vacation setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public NursingLeaveSetting(NursingLeaveSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.manageType = memento.getManageType();
		this.nursingCategory = memento.getNursingCategory();
		this.startMonthDay = memento.getStartMonthDay();
		this.maxPersonSetting = memento.getMaxPersonSetting();
		this.specialHolidayFrame = memento.getSpecialHolidayFrame();
		this.workAbsence = memento.getWorkAbsence();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(NursingLeaveSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setManageType(this.manageType);
		memento.setNursingCategory(this.nursingCategory);
		memento.setStartMonthDay(this.startMonthDay);
		memento.setMaxPersonSetting(this.maxPersonSetting);
		memento.setSpecialHolidayFrame(specialHolidayFrame);
		memento.setWorkAbsence(workAbsence);
	}

	/**
	 * 次回起算日を求める
	 * @param companyId 会社ID
	 * @param criteriaDate 基準日
	 * @return 次回起算日
	 */
	public  GeneralDate algorithm(
			GeneralDate criteriaDate) {

		// 「次回起算日」を求める
		GeneralDate nextStartMonthDay = null;

		// 基準日の月日と起算日の月日を比較
		if(criteriaDate.beforeOrEquals(this.startMonthDay.toDate(criteriaDate.year()))) { //基準日．月日　＜＝　起算日
			// 基準日の年で次回起算日を求める
			// --- 次回起算日 =｛年：基準日．年、月：起算日．月、日：起算日．日｝
			nextStartMonthDay = GeneralDate.ymd(criteriaDate.year(), this.startMonthDay.getMonth(), this.startMonthDay.getDay());
		} else {
			// 基準日の年に＋１年し次回起算日を求める
			// --- 次回起算日 =｛年：基準日．年　＋　１、月：起算日．月、日：起算日．日｝
			nextStartMonthDay = GeneralDate.ymd(criteriaDate.year() + 1, this.startMonthDay.getMonth(), this.startMonthDay.getDay());
		}
		// 次回起算日を返す
		return nextStartMonthDay;
	}
}