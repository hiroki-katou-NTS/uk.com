package nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse;

import lombok.Getter;

import java.util.Optional;

/**
 * 集計期間の休暇情報
  * @author yuri_tamakoshi
 */
@Getter
public class ChildCareNurseAggrPeriodDaysInfoImport {
	/** 子の看護休暇情報（本年） */
	private ChildCareNurseAggrPeriodInfoImport thisYear;
	/** 子の看護休暇情報（翌年） */
	private Optional<ChildCareNurseAggrPeriodInfoImport> nextYear;


	/**
	 * コンストラクタ
	 */
	public ChildCareNurseAggrPeriodDaysInfoImport(){

		this.thisYear = new ChildCareNurseAggrPeriodInfoImport();
		this.nextYear =  Optional.empty();

	}
	/**
	 * ファクトリー
	 * @param thisYear 子の看護休暇情報（本年）
	 * @param nextYear 子の看護休暇情報（翌年）
	 * @return 集計期間の休暇情報
	 */
	public static ChildCareNurseAggrPeriodDaysInfoImport of(
			ChildCareNurseAggrPeriodInfoImport thisYear,
			Optional<ChildCareNurseAggrPeriodInfoImport> nextYear){

		ChildCareNurseAggrPeriodDaysInfoImport domain = new ChildCareNurseAggrPeriodDaysInfoImport();
		domain.thisYear = thisYear;
		domain.nextYear = nextYear;
		return domain;
	}
}