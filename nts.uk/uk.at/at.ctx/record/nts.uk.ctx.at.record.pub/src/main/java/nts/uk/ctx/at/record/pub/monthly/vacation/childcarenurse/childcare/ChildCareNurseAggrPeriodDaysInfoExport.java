package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import java.util.Optional;

import lombok.Getter;

/**
 * 集計期間の休暇情報
  * @author yuri_tamakoshi
 */
@Getter
public class ChildCareNurseAggrPeriodDaysInfoExport {
	/** 子の看護休暇情報（本年） */
	private ChildCareNurseAggrPeriodInfoExport thisYear;
	/** 子の看護休暇情報（翌年） */
	private Optional<ChildCareNurseAggrPeriodInfoExport> nextYear;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseAggrPeriodDaysInfoExport(){
		this.thisYear = new ChildCareNurseAggrPeriodInfoExport();
		this.nextYear =  Optional.empty();
	}
	/**
	 * ファクトリー
	 * @param thisYear 子の看護休暇情報（本年）
	 * @param nextYear 子の看護休暇情報（翌年）
	 * @return 集計期間の休暇情報
	 */
	public static ChildCareNurseAggrPeriodDaysInfoExport of(
			ChildCareNurseAggrPeriodInfoExport thisYear,
			Optional<ChildCareNurseAggrPeriodInfoExport> nextYear){

		ChildCareNurseAggrPeriodDaysInfoExport domain = new ChildCareNurseAggrPeriodDaysInfoExport();
		domain.thisYear = thisYear;
		domain.nextYear = nextYear;
		return domain;
	}
}