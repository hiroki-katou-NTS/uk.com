package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;

/**
 * 子の看護介護計算使用数
 * @author yuri_tamakoshi
*/
@Getter
@Setter
public class ChildCareNurseCalcUsedNumber {
	/** 起算日からの使用数 */
	private ChildCareNurseUsedNumber startdateInfo;
	/** 時間休暇使用回数 */
	private UsedTimes usedCount;
	/** 時間休暇使用日数 */
	private UsedTimes usedDays;
	/** 集計期間の使用数 */
	private ChildCareNurseUsedNumber aggrPeriodUsedNumber;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseCalcUsedNumber(){
		this.startdateInfo =new ChildCareNurseUsedNumber();
		this.usedCount = new UsedTimes(0);
		this.usedDays = new UsedTimes(0);
		this.aggrPeriodUsedNumber = new ChildCareNurseUsedNumber();
	}

	/**
	 * ファクトリー
	 * @param startdateInfo 起算日からの使用数
	 * @param usedCount 時間休暇使用回数
	 * @param usedDays 時間休暇使用日数
	 * @param aggrPeriodUsedNumber 集計期間の使用数
	 * @return 子の看護介護計算使用数
	 */
	public static ChildCareNurseCalcUsedNumber of(
			ChildCareNurseUsedNumber startdateInfo,
			UsedTimes usedCount,
			UsedTimes usedDays,
			ChildCareNurseUsedNumber aggrPeriodUsedNumber){

		ChildCareNurseCalcUsedNumber domain = new ChildCareNurseCalcUsedNumber();
		domain.startdateInfo = startdateInfo;
		domain.usedCount = usedCount;
		domain.usedDays = usedDays;
		domain.aggrPeriodUsedNumber = aggrPeriodUsedNumber;
		return domain;
	}
}
