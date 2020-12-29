/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.ConfirmLeavePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
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
		this.specialHolidayFrame = memento.getHdspFrameNo();
		this.workAbsence = memento.getAbsenceFrameNo();
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
		memento.setHdspFrameNo(specialHolidayFrame);
		memento.setAbsenceFrameNo(workAbsence);
	}
//	/**
//	 * 家族情報から対象人数を履歴で求める
//	 * @param employeeId 社員ID
//	 * @param criteriaDate 基準日
//	 * @param Require｛
//	 * 								個人IDが一致する家族情報を取得（個人ID）
//	 * 								介護対象管理データ（家族ID）
//	 * 							｝
//	 * @return 上限日数分割日（List）
//	 */
//	public GeneralDate getHistoryCountFromFamilyInfo(
//			String employeeId,
//			GeneralDate criteriaDate,
//			Require require) {
//
//		// 子の看護か介護か
//		NursingLeaveSetting comNursingLeaveSet = require.nursingLeaveSet(String companyId, NursingCategory.Nursing);
//
//		// 子の看護休暇の管理区分を確認する
//		Optional<NursingLeaveSetting> nursingSetOpt = Optional.empty();
//		if (comNursingLeaveSet.isManaged()) { //子の看護介護休暇：管理する
//			//個人IDが一致する家族情報を取得（個人ID）
//			// 家族．個人ID = パラメータ「個人ID」
//			// ================10/19RequestList待ち
//
//			// 「家族（List）」を返す
//		}
//		else{ //子の看護介護休暇：管理しない
//			Optional.empty();
//		}
//
//		// 子の看護対象人数を求める
//
//		// 介護対象人数を履歴で求める
//
//		// 上限日数分割日を求める
//
//		// 「上限日数分割日（List）」を返す
//
//	}
//
//
//	/**
//	 * 期間の子の看護対象人数を求める
//	 * @param employeeId 社員ID
//	 * @param period 期間
//	 * @param Require｛
//	 * 								個人IDが一致する家族情報を取得（個人ID）
//	 * 							｝
//	 * @return 看護介護対象人数変更日（List）
//	 */
//	public GeneralDate getCountChildCareWithinPeriod(
//			String employeeId,DatePeriod period,Require require) {
//
//		// 子の看護本年と翌年の対象人数を求める
//		ChildCareNurseTargetCount targetCount(employeeId, period,require);
//
//		// 期間の対象人数を求める
//		ChildCareTargetChanged getCountWithinPeriod(period, criteriaDate, targetCount ,getNextStartMonthDay);
//
//		List<ChildCareTargetChanged> changeDateList =
//
//		// 「看護介護対象人数変更日（List）」を返す
//		return changeDateList;
//
//	}
//
//
//	/**
//	 * 期間の対象人数を求める
//	 * @param period 期間
//	 * @param numOfPeople 本年と次回の対象人数
//	 * @param nextStartMonthDay 次回起算日
//	 * @return 看護介護対象人数変更日（List）
//	 */
//	public ChildCareTargetChanged getCountWithinPeriod(
//			DatePeriod period,
//			GeneralDate nextStartMonthDay) {
//		// 期間に次回起算日があるか
//		// -----期間．開始日＜＝　パラメータ「次回起算日」　＜＝期間．終了日
//		if(period.beforeOrEquals(this.startMonthDay.toDate(period.year().end()))) {
//			// 本年と次回の対象人数を設定
////			// 本年と次回の2件作成
////			変更日←パラメータ「期間．開始日」
////			人数←本年と翌年の対象人数．本年対象人数
//
////			変更日←次回起算日
////			人数←本年と次回の対象人数．次回対象人数
//		}else {
//			//本年の対象人数を設定
////			変更日←パラメータ「期間．開始日」
////			人数←本年と次回の対象人数．本年対象人数
//		}
//
//	}


	/**
	 * 次回起算日を求める
	 * @param companyId 会社ID
	 * @param criteriaDate 基準日
	 * @return 次回起算日
	 */
	public  GeneralDate getNextStartMonthDay(
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
	/**
	 * 本年起算日を求める
	 * @param companyId 会社ID
	 * @param criteriaDate 基準日
	 * @return 本年起算日
	 */
	public  GeneralDate getThisYearStartMonthDay(
			GeneralDate criteriaDate) {

		// 「本年起算日」を求める
		GeneralDate thisYearStartMonthDay = null;

		// 基準日の月日と起算日の月日を比較
		if(criteriaDate.beforeOrEquals(this.startMonthDay.toDate(criteriaDate.year()))) { //基準日．月日　＜＝　起算日

			// 基準日の年に-1年して本年起算日を求める
			// ---本年起算日 =｛年：基準日．年　-　１、月：起算日．月、日：起算日．日｝
			thisYearStartMonthDay = GeneralDate.ymd(criteriaDate.year() - 1, this.startMonthDay.getMonth(), this.startMonthDay.getDay());
		} else {
			// 基準日の年で本年起算日を求める
			// --- 本年起算日 =｛年：基準日．年、月：起算日．月、日：起算日．日｝
			thisYearStartMonthDay = GeneralDate.ymd(criteriaDate.year(), this.startMonthDay.getMonth(), this.startMonthDay.getDay());
		}
		// 本年起算日を返す
		return thisYearStartMonthDay;
	}

	// Require
	public static interface Require {

		// 介護看護休暇設定を取得する（会社ID、介護看護区分）
		NursingLeaveSetting nursingLeaveSetting(String companyId, NursingCategory nursingCategory);

		// 子の看護・介護休暇基本情報を取得する（社員ID）
		NursingCareLeaveRemainingInfo employeeInfo(String employeeId);

		// 年休の契約時間を取得する（社員ID、基準日）
		LaborContractTime contractTime(String employeeId, GeneralDate criteriaDate);

//		// 個人IDが一致する家族情報を取得（個人ID）
//		FamilyMember personInfo(String personId);
//
//		// 介護対象管理データ（家族ID）
//		CareManagementDate careData(String FamilyID);

		// 期間の上限日数取得する（会社ID、社員ID、期間、介護看護区分、Require）
		NursingCareLeaveRemainingInfo UpperLimitPeriod (String companyId, String employeeId, DatePeriod period, NursingCategory nursingCategory, Require require);
	}
}