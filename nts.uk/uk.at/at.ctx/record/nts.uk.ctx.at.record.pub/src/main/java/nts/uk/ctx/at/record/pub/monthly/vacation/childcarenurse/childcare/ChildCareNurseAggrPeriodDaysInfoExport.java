package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseAggrPeriodDaysInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseAggrPeriodInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;

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

	/**
	 * ドメインへ変換
	 * @return
	 */
	public ChildCareNurseAggrPeriodDaysInfo toDomain() {
		return ChildCareNurseAggrPeriodDaysInfo.of(
				ChildCareNurseAggrPeriodInfo.of(
						new UsedTimes(thisYear.getUsedCount()),
						new UsedTimes(thisYear.getUsedDays()),
						thisYear.getAggrPeriodUsedNumber().toDomain()
				),
				nextYear.map(i -> ChildCareNurseAggrPeriodInfo.of(
						new UsedTimes(i.getUsedCount()),
						new UsedTimes(i.getUsedDays()),
						i.getAggrPeriodUsedNumber().toDomain()
				))
		);
	}
}