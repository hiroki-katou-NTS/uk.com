package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import lombok.Getter;
import lombok.Setter;

/**
* 期間ごとの計算結果
 * @author yuri_tamakoshi
*/
@Getter
@Setter
public class ChildCareNurseCalcResultWithinPeriod {
	/** エラー情報 */
	private ChildCareNurseErrors errorsInfo;
	/** 起算日からの休暇情報 */
	private ChildCareNurseStartdateInfo startdateInfo;
	/** 集計期間の休暇情報 */
	private ChildCareNurseAggrPeriodInfo aggrPeriodInfo;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseCalcResultWithinPeriod(){

		this.errorsInfo = new ChildCareNurseErrors();
		this.startdateInfo =  new ChildCareNurseStartdateInfo();
		this.aggrPeriodInfo = new ChildCareNurseAggrPeriodInfo();
	}

	/**
	 * ファクトリー
	 * @param errorsInfo エラー情報
	 * @param startdateInfo 起算日からの休暇情報
	 * @param childCareNurseAggrPeriodInfo 集計期間の休暇情報
	 * @return 期間ごとの計算結果
	 */
	public static ChildCareNurseCalcResultWithinPeriod of(
			ChildCareNurseErrors errorsInfo,
			ChildCareNurseStartdateInfo startdateInfo,
			ChildCareNurseAggrPeriodInfo aggrPeriodInfo){

		ChildCareNurseCalcResultWithinPeriod domain = new ChildCareNurseCalcResultWithinPeriod();
		domain.errorsInfo = errorsInfo;
		domain.startdateInfo = startdateInfo;
		domain.aggrPeriodInfo = aggrPeriodInfo;
		return domain;
	}
}
