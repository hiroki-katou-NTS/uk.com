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

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.MonthVacationGrantDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedHalfHdCnt;
/**
 * 半日年休管理 The Class HalfDayManage.
 */
@Builder
public class HalfDayManage implements Serializable {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/** The manage type. */
	// 管理区分
	@Getter
	public ManageDistinct manageType;

	/** The reference. */
	// 参照先
	public MaxDayReference reference;

	/** The max number uniform company. */
	// 会社一律上限回数
	public AnnualNumberDay maxNumberUniformCompany;

	// 端数処理区分
	public RoundProcessingClassification roundProcesCla;

	public boolean isManaged() {
		return this.getManageType().equals(ManageDistinct.YES);
	}
	/**
	 * [3] 半日年休上限回数を取得
	 * @param fromGrantTableCount
	 * @return
	 */
	public Optional<LimitedHalfHdCnt> getLimitedHalfCount(Optional<LimitedHalfHdCnt> fromGrantTableCount) {
		if (!this.isManaged())
			return Optional.empty();

		return this.reference.equals(MaxDayReference.CompanyUniform)
				? Optional.of(this.maxNumberUniformCompany.toLimitedTimeHdDays()) : fromGrantTableCount;
	}
	
	/**
	 * [1] 半日回数上限に対応する月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthlyAttendanceItemsCorresHalfDayLimit() {
		// 半日回数上限に対応する月次の勤怠項目
		return Arrays.asList(1434, 1435, 1436, 1437, 1438, 1439, 1440, 1441);
	}
	
	/**
	 * [2] 利用できない月次の勤怠項目を取得する
	 */
	public List<Integer> getMonthlyAttendanceItems(ManageDistinct timeManageType) {
		if (!this.isManageHalfDayAnnualLeave(timeManageType)) {
			return this.getMonthlyAttendanceItemsCorresHalfDayLimit();
		}
		
		return new ArrayList<>();
	}
	
	/**
	 * 	[4] 半日年休を管理するか
	 */
	public boolean isManageHalfDayAnnualLeave(ManageDistinct timeManageType) {
		if (timeManageType == ManageDistinct.YES && manageType == ManageDistinct.YES)
			return true;
		return false;
	}	 
	/**
	 * 積立年休の付与数を取得する
	 */
	protected MonthVacationGrantDay getAnnualLeavGrant(ManageDistinct yearManageType, Double dayRemains) {
		if (!yearManageType.isManaged()) {
			return new MonthVacationGrantDay(0.0);
		}
		if (this.roundProcesCla == RoundProcessingClassification.TruncateOnDay0) {
			return MonthVacationGrantDay.createWithTruncate(dayRemains);
		} else if (this.roundProcesCla == RoundProcessingClassification.RoundUpToTheDay) {
			return MonthVacationGrantDay.createWithRoundUp(dayRemains);
		}
		return new MonthVacationGrantDay(dayRemains);
	}
}
