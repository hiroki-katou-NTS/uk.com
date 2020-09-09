package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.specialholiday.export.NextSpecialLeaveGrant;

/**
 * 処理単位分割日
 * @author masaaki_jinno
 *
 */
@Getter
public class SpecialLeaveDividedDayEachProcess {

	/** 年月日 */
	private GeneralDate ymd;
	
	/** 次回年休付与 */
	@Setter
	private Optional<NextSpecialLeaveGrant> nextSpecialLeaveGrant;
	/** 期間終了内 */
	@Setter
	private boolean dayBeforePeriodEnd;
	/** 期間終了後翌日 */
	@Setter
	private boolean nextDayAfterPeriodEnd;
	/** 付与フラグ */
	@Setter
	private boolean grantAtr;
	/** 消滅フラグ */
	@Setter
	private boolean lapsedAtr;
	
	/**
	 * コンストラクタ
	 * @param ymd 年月日
	 */
	public SpecialLeaveDividedDayEachProcess(GeneralDate ymd){
		
		this.ymd = ymd;
		
		this.nextSpecialLeaveGrant = Optional.empty();
		this.dayBeforePeriodEnd = true;
		this.nextDayAfterPeriodEnd = false;
		this.grantAtr = false;
		this.lapsedAtr = false;
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
	