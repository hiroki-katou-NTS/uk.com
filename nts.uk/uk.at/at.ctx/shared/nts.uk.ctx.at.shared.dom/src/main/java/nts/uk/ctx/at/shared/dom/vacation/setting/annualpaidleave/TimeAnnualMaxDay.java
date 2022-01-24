/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.Builder;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdDays;

/**
 * 時間年休の上限日数 The Class YearVacationTimeMaxDay.
 *
 */
@Builder
public class TimeAnnualMaxDay implements Serializable {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/** The manage max day vacation. */
	// 管理区分
	public ManageDistinct manageType;

	/** The reference. */
	// 参照先
	public MaxDayReference reference;

	/** The max time day. */
	// 会社一律上限日数
	public MaxTimeDay maxNumberUniformCompany;

	
	public boolean isManaged(){
		return this.manageType.equals(ManageDistinct.YES);
	}
	
	/**
	 * C-0
	 */
	public TimeAnnualMaxDay(ManageDistinct manageType, MaxDayReference reference, MaxTimeDay maxNumberUniformCompany) {
		super();
		this.manageType = manageType;
		this.reference = reference;
		this.maxNumberUniformCompany = maxNumberUniformCompany;
	}
	
	/**
	 * [1] 時間年休の上限日数に対応する月次の勤怠項目を取得する
	 */
	public List<Integer> acquiremonthAttendItemMaximumNumberDaysAnnualLeave() {
		return Arrays.asList(1442,1443,1444,1445);
	}
	
	/**
	 * [2] 利用できない月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthAttendItemsNotAvailable(ManageDistinct manageType, ManageDistinct timeManageType) {
		if (!this.isManageMaximumNumberDays(manageType, timeManageType)) { 
			return this.acquiremonthAttendItemMaximumNumberDaysAnnualLeave();
		}
		return new ArrayList<>();
	}
	
	/**
	 * [3] 時間年休上限日数を取得
	 * @param fromGrantTableDays
	 * @return
	 */
	public Optional<LimitedTimeHdDays> getLimitedTimeHdDays(Optional<LimitedTimeHdDays> fromGrantTableDays) {
		if (!this.isManaged())
			return Optional.empty();

		return this.reference.equals(MaxDayReference.CompanyUniform)
				? Optional.of(this.maxNumberUniformCompany.toLimitedTimeHdDays()) : fromGrantTableDays;
	}
	
	/**
	 * [4] 上限日数を管理するか
	 */
	public boolean isManageMaximumNumberDays(ManageDistinct manageDistinctType, ManageDistinct timeManageType) {
		if (manageDistinctType == ManageDistinct.YES && timeManageType == ManageDistinct.YES && manageType == ManageDistinct.YES) {
			return true;
		}
		return false;
	}
}
