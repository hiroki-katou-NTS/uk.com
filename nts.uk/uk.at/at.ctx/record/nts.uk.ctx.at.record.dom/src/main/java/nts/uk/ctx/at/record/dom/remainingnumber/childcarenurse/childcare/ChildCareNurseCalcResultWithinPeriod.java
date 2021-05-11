package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseUsedInfo;

/**
* 期間ごとの計算結果
 * @author yuri_tamakoshi
*/
@Getter
@Setter
public class ChildCareNurseCalcResultWithinPeriod {
	/** エラー情報 */
	private List<ChildCareNurseErrors> errorsInfo;
	/** 起算日からの休暇情報 */
	private ChildCareNurseStartdateInfo startdateInfo;
	/** 集計期間の休暇情報 */
	private ChildCareNurseUsedInfo aggrPeriodInfo;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseCalcResultWithinPeriod(){

		this.errorsInfo = new ArrayList<>();
		this.startdateInfo =  new ChildCareNurseStartdateInfo();
		this.aggrPeriodInfo = new ChildCareNurseUsedInfo();
	}

	/**
	 * ファクトリー
	 * @param errorsInfo エラー情報
	 * @param startdateInfo 起算日からの休暇情報
	 * @param aggrPeriodInfo 集計期間の休暇情報
	 * @return 期間ごとの計算結果
	 */
	public static ChildCareNurseCalcResultWithinPeriod of(
			List<ChildCareNurseErrors> errorsInfo,
			ChildCareNurseStartdateInfo startdateInfo,
			ChildCareNurseUsedInfo aggrPeriodInfo){

		ChildCareNurseCalcResultWithinPeriod domain = new ChildCareNurseCalcResultWithinPeriod();
		domain.errorsInfo = errorsInfo;
		domain.startdateInfo = startdateInfo;
		domain.aggrPeriodInfo = aggrPeriodInfo;
		return domain;
	}
}
