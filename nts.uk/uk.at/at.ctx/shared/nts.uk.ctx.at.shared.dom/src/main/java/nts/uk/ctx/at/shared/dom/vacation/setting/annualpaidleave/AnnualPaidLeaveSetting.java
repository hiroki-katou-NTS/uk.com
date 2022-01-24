/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedHalfHdCnt;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdTime;

/**
 * The Class AnnualVacationSetting.
 */
// 年休設定
@Getter
@EqualsAndHashCode(callSuper = true, of = { "companyId" })
public class AnnualPaidLeaveSetting extends AggregateRoot implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The acquisition setting. */
	// 取得設定
	private AcquisitionSetting acquisitionSetting;

	/** The year manage type. */
	// 年休管理区分
	private ManageDistinct yearManageType;

	/** The manage annual setting. */
	// 年休管理設定
	private ManageAnnualSetting manageAnnualSetting;

	/** The time setting. */
	// 時間年休管理設定
	private TimeAnnualSetting timeSetting;

	/**
	 * Checks if is managed.
	 *
	 * @return true, if is managed
	 */
	public boolean isManaged() {
		return this.yearManageType.equals(ManageDistinct.YES);
	}

	/**
	 * Instantiates a new annual paid leave setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public AnnualPaidLeaveSetting(AnnualPaidLeaveSettingGetMemento memento) {
		super();
		this.companyId = memento.getCompanyId();
		this.acquisitionSetting = memento.getAcquisitionSetting();
		this.yearManageType = memento.getYearManageType();
		this.manageAnnualSetting = memento.getManageAnnualSetting();
		this.timeSetting = memento.getTimeSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(AnnualPaidLeaveSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setAcquisitionSetting(this.acquisitionSetting);
		memento.setYearManageType(this.yearManageType);
		memento.setManageAnnualSetting(this.manageAnnualSetting);
		memento.setTimeSetting(this.timeSetting);
	}

	/**
	 * 期限日計算
	 * 
	 * @param grantDate
	 *            付与日
	 * @return 期限日
	 */
	// 2018.7.24 add shuichi_ishida
	public GeneralDate calcDeadline(GeneralDate grantDate) {

		// 保持年数を取得
		int retentionYear = this.manageAnnualSetting.getRemainingNumberSetting().retentionYear.v();

		// 期限日を計算する
		if (grantDate.after(GeneralDate.max().addYears(-retentionYear)))
			return GeneralDate.max();
		GeneralDate deadline = grantDate.addYears(retentionYear).addDays(-1);

		// 期限日を返す
		return deadline;
	}
	/**
	 * [C-0] 年休設定
	 */
	public AnnualPaidLeaveSetting(String companyId, AcquisitionSetting acquisitionSetting,
			ManageDistinct yearManageType, ManageAnnualSetting manageAnnualSetting, TimeAnnualSetting timeSetting) {
		super();
		this.companyId = companyId;
		this.acquisitionSetting = acquisitionSetting;
		this.yearManageType = yearManageType;
		this.manageAnnualSetting = manageAnnualSetting;
		this.timeSetting = timeSetting;
	}
	
	/**
	 * [1] 年休に対応する日次の勤怠項目を取得する
	 */
	public List<Integer> getDailyAttendanceItemsAnnualLeave() {
		List<Integer> lstId = new ArrayList<>();
		// 年休に対応する日次の勤怠項目
		lstId.addAll(Arrays.asList(539, 540));
		// @時間年休管理設定.時間年休に対応する日次の勤怠項目を取得する()
		lstId.addAll(timeSetting.getDailyAttdItemsCorrespondAnnualLeave());
		return lstId;
	}
	
	/**
	 * [2] 年休に対応する月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthlyAttendanceItemsAnnualLeave() {
		List<Integer> lstId = new ArrayList<>();
		// [prv-1] 使用数を含まない年休に対応する月次の勤怠項目を取得する	
		lstId.addAll(this.getMonthlyAttendanceItemsNotInclude());
		
		// 常に表示する年休の月次の勤怠項目
		lstId.addAll(Arrays.asList(185, 789, 800));
		return lstId;
	}
	
	/**
	 * [3] 利用できない日次の勤怠項目を取得する
	 */
	public List<Integer> getDailyAttendanceItemsNotAvailable() {
		List<Integer> lstId = new ArrayList<>();
		if (this.yearManageType == ManageDistinct.NO)
			// 年休に対応する日次の勤怠項目
			lstId.addAll(Arrays.asList(539, 540));
		
		lstId.addAll(timeSetting.getDailyAttendItemsNotAvailable(yearManageType));
		
		return lstId;
	}
	
	/**
	 * [4] 利用できない月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthlyAttendanceItemsNotAvailable() {
		List<Integer> lstId = new ArrayList<>();
		if (this.yearManageType == ManageDistinct.NO)
			// 年休に対応する月次の勤怠項目
			lstId.addAll(Arrays.asList(189, 794, 798, 799, 790, 801, 805, 809, 1427, 1428, 1432, 1433, 1780, 1781, 1782,
					1783, 1784, 1785, 1786, 1787, 1788, 1789));
		
		// $半日年休 
		lstId.addAll(manageAnnualSetting.getMonthlyAttendanceItems(yearManageType));
		
		// $時間年休
		lstId.addAll(timeSetting.getMonthlyAttendItemsNotAvailable(yearManageType));
				
		return lstId;
	}
	
	/**
	 * [prv-1] 使用数を含まない年休に対応する月次の勤怠項目を取得する
	 */
	private List<Integer> getMonthlyAttendanceItemsNotInclude() {
		List<Integer> lstId = new ArrayList<>();
		// 年休に対応する月次の勤怠項目
		lstId.addAll(Arrays.asList(189, 794, 798, 799, 790, 801, 805, 809, 1427, 1428, 1432, 1433, 1780, 1781, 1782,
				1783, 1784, 1785, 1786, 1787, 1788, 1789));
		
		// @年休管理設定.半日回数上限に対応する月次の勤怠項目を取得する()
		lstId.addAll(manageAnnualSetting.getMonthlyAttendanceItemsHalfDayLimit());
		
		// @時間年休管理設定.時間年休に対応する月次の勤怠項目を取得する()
		lstId.addAll(timeSetting.acquireMonthAttdItemsHourlyAnnualLeave());
		
		return lstId;
	}
	
	
	/**
	 * [6]時間年休上限日数を取得する
	 * @param fromGrantTableDays
	 * @return
	 */
	public Optional<LimitedTimeHdDays> getLimitedTimeHdDays(Optional<LimitedTimeHdDays> fromGrantTableDays) {
		if (!this.isManaged())
			return Optional.empty();

		return this.getTimeSetting().getLimitedTimeHdDays(fromGrantTableDays);
	}
	/**
	 * [7] 半日年休上限回数を取得
	 * @param fromGrantTableCount
	 * @return
	 */
	public Optional<LimitedHalfHdCnt> getLimitedHalfCount(Optional<LimitedHalfHdCnt> fromGrantTableCount) {
		if (!this.isManaged())
			return Optional.empty();

		return this.getManageAnnualSetting().getLimitedHalfCount(fromGrantTableCount);
	}

	/**
	 * [8] 時間年休上限時間を求める
	 * @param fromGrantTableDays
	 * @param laborContractTimeOpt
	 * @return
	 */
	public Optional<LimitedTimeHdTime> getLimitedTimeHdTime(Optional<LimitedTimeHdDays> fromGrantTableDays,
			Optional<LaborContractTime> laborContractTimeOpt) {

		Optional<LimitedTimeHdDays> limitedTimeHdDays = getLimitedTimeHdDays(fromGrantTableDays);
		if (!limitedTimeHdDays.isPresent() || !laborContractTimeOpt.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(new LimitedTimeHdTime(limitedTimeHdDays.get().v() * laborContractTimeOpt.get().v()));

	}

}
