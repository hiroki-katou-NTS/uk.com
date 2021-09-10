package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.NextDayAfterPeriodEndWork;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantWork;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveLapsedWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.GrantPeriodAtr;

/**
 * 処理単位分割日
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class SpecialLeaveDividedDayEachProcess {

	/** 終了日 */
	private NextDayAfterPeriodEndWork 	endDay;
	/** 消滅（消滅情報WORK） */
	private SpecialLeaveLapsedWork lapsedWork;
	/** 年月日 */
	private GeneralDate ymd;
	/** 付与（付与情報WORK） */
	private SpecialLeaveGrantWork grantWork;
	/** 付与前か付与後か */
	private GrantPeriodAtr grantPeriodAtr;

//	/** 次回年休付与 */
//	private Optional<NextSpecialLeaveGrant> nextSpecialLeaveGrant;

	/**
	 * コンストラクタ
	 * @param endDay 終了日
	 * @param lapsedWork 消滅（消滅情報WORK）
	 * @param ymd 年月日
	 * @param grantWork 付与（付与情報WORK）
	 * @param grantPeriodAtr 付与前か付与後か
	 * @return  処理単位分割日
	 */
	public SpecialLeaveDividedDayEachProcess(GeneralDate ymd){

		this.endDay = new NextDayAfterPeriodEndWork();
		this.lapsedWork = new SpecialLeaveLapsedWork();
		this.ymd = ymd;
		this.grantWork = new SpecialLeaveGrantWork();
//		this.nextSpecialLeaveGrant = Optional.empty();
		this.grantPeriodAtr = GrantPeriodAtr.BEFORE_GRANT;
	}

//	/**
//	 * ファクトリー
//	 * @param ymd 年月日
//	 * @param nextAnnualLeaveGrant 次回年休付与
//	 * @param nextDayAfterPeriodEnd 期間終了後翌日
//	 * @param grantAtr 付与フラグ
//	 * @param lapsedAtr 消滅フラグ
//	 * @return 処理単位分割日
//	 */
//	public static SpecialLeaveDividedDayEachProcess of(
//			GeneralDate ymd,
//			Optional<NextAnnualLeaveGrant> nextAnnualLeaveGrant,
//			boolean dayBeforePeriodEnd,
//			boolean nextDayAfterPeriodEnd,
//			boolean grantAtr,
//			boolean lapsedAtr){
//
//		DividedDayEachProcess domain = new DividedDayEachProcess(ymd);
//		domain.nextAnnualLeaveGrant = nextAnnualLeaveGrant;
//		domain.dayBeforePeriodEnd = dayBeforePeriodEnd;
//		domain.nextDayAfterPeriodEnd = nextDayAfterPeriodEnd;
//		domain.grantAtr = grantAtr;
//		domain.lapsedAtr = lapsedAtr;
//		return domain;

}
