package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.ChildCareLeaveRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareData;

@Getter
@AllArgsConstructor
public class CareLeaveDataInfo {

	/**
	 * 基本的な情報（上限の日数）介護
	 */
	private CareLeaveRemainingInfo careInfo;

	/**
	 * 基本的な情報（上限の日数）子の看護
	 */
	private ChildCareLeaveRemainingInfo childCareLeaveRemainingInfo;

//	private LeaveForCareData careData;
//	private ChildCareLeaveRemainingData childCareLeaveRemainingData;

	/**
	 * 介護使用数データ
	 */
	private CareUsedNumberData careData;

	/**
	 * 子の看護使用数データ
	 */
	private ChildCareLeaveRemainingData childCareLeaveRemainingData;
}
