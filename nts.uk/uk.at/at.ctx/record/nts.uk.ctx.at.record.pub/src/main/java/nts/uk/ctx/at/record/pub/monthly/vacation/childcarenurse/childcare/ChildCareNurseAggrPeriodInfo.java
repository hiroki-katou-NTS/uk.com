package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import java.util.Optional;

import lombok.Getter;

/**
 * 集計期間の子の看護介護休暇情報
  * @author yuri_tamakoshi
 */
@Getter
public class ChildCareNurseAggrPeriodInfo {
	/** 時間休暇使用回数 */
	private Integer usedCount;
	/** 時間休暇使用日数 */
	private Integer usedDays;
	/** 集計期間の使用数 */
	private  ChildCareNurseUsedNumberExport aggrPeriodUsedNumber;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseAggrPeriodInfo(){
		this.usedCount = new Integer(0);
		this.usedDays = new Integer(0);
		this.aggrPeriodUsedNumber = new ChildCareNurseUsedNumberExport(0.0, Optional.empty());
	}

	/**
	 * ファクトリー
	 * @param usedCount 時間休暇使用回数
	 * @param usedDays 時間休暇使用日数
	 * @param aggrPeriodUsedNumber 集計期間の使用数
	 * @return 集計期間の子の看護介護休暇情報
	 */
	public static ChildCareNurseAggrPeriodInfo of(
			Integer usedCount,
			Integer usedDays,
			ChildCareNurseUsedNumberExport aggrPeriodUsedNumber){

		ChildCareNurseAggrPeriodInfo domain = new ChildCareNurseAggrPeriodInfo();
		domain.usedCount = usedCount;
		domain.usedDays = usedDays;
		domain.aggrPeriodUsedNumber = aggrPeriodUsedNumber;
		return domain;
	}
}
