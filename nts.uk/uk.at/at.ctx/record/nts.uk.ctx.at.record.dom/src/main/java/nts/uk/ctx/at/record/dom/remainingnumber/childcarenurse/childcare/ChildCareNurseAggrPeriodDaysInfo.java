package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseUsedInfo;

/**
 * 集計期間の休暇情報
  * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareNurseAggrPeriodDaysInfo {
	/** 子の看護介護使用情報（本年） */
	private ChildCareNurseUsedInfo thisYear;
	/** 子の看護介護使用情報（翌年） */
	private Optional<ChildCareNurseUsedInfo> nextYear;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseAggrPeriodDaysInfo(){
		this.thisYear = new ChildCareNurseUsedInfo();
		this.nextYear =  Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param thisYear 子の看護介護使用情報（本年）
	 * @param nextYear 子の看護介護使用情報（翌年）
	 * @return 集計期間の休暇情報
	 */
	public static ChildCareNurseAggrPeriodDaysInfo of(
			ChildCareNurseUsedInfo thisYear,
			Optional<ChildCareNurseUsedInfo> nextYear){

		ChildCareNurseAggrPeriodDaysInfo domain = new ChildCareNurseAggrPeriodDaysInfo();
		domain.thisYear = thisYear;
		domain.nextYear = nextYear;
		return domain;
	}
}
