package nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;

/**
 * 集計期間の子の看護介護休暇情報
  * @author yuri_tamakoshi
 */
@Getter
public class ChildCareNurseAggrPeriodInfoImport {
	/** 子の看護介護休暇の時間休暇使用回数 */
	private Integer usedCount;
	/** 子の看護介護休暇の時間休暇使用日数 */
	private Integer usedDays;
	/** 集計期間の子の看護介護休暇使用数 */
	private  ChildCareNurseUsedNumberImport aggrPeriodUsedNumber;


	/**
	 * コンストラクタ
	 */
	public ChildCareNurseAggrPeriodInfoImport(){
		this.usedCount = new Integer(0);
		this.usedDays = new Integer(0);
		this.aggrPeriodUsedNumber = new ChildCareNurseUsedNumberImport();
	}

	/**
	 * ファクトリー
	 * @param usedCount 子の看護介護休暇の時間休暇使用回数
	 * @param usedDays 子の看護介護休暇の時間休暇使用日数
	 * @param aggrPeriodUsedNumber集計期間の子の看護介護休暇使用数
	 * @return 集計期間の子の看護介護休暇情報
	 */
	public static ChildCareNurseAggrPeriodInfoImport of(
			Integer usedCount,
			Integer usedDays,
			ChildCareNurseUsedNumberImport aggrPeriodUsedNumber){

		ChildCareNurseAggrPeriodInfoImport domain = new ChildCareNurseAggrPeriodInfoImport();
		domain.usedCount = usedCount;
		domain.usedDays = usedDays;
		domain.aggrPeriodUsedNumber = aggrPeriodUsedNumber;
		return domain;
	}
}
