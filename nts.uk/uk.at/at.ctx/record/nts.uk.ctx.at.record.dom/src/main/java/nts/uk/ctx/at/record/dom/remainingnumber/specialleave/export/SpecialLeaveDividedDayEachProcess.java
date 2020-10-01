package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.param.SpecialLeaveGrantWork;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.param.SpecialLeaveLapsedWork;
import nts.uk.ctx.at.shared.dom.specialholiday.export.NextSpecialLeaveGrant;

/**
 * 処理単位分割日
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class SpecialLeaveDividedDayEachProcess {

	/** 年月日 */
	private GeneralDate ymd;
	/** 消滅情報WORK */
	private SpecialLeaveLapsedWork lapsedWork;
	/** 付与情報WORK */
	private SpecialLeaveGrantWork grantWork;
	/** 次回年休付与 */
	private Optional<NextSpecialLeaveGrant> nextSpecialLeaveGrant;
	/** 終了日の期間かどうか */
	private boolean dayBeforePeriodEnd;
	/** 終了日の翌日の期間かどうか */
	private boolean nextDayAfterPeriodEnd;
	/** 付与後 */
	private boolean afterGrant;
//	/** 何回目の付与なのか */
//	@Setter
//	private int grantNumber = 0;	
	
	
	/**
	 * コンストラクタ
	 * @param ymd 年月日
	 */
	public SpecialLeaveDividedDayEachProcess(GeneralDate ymd){
		
		this.ymd = ymd;
		this.lapsedWork = new SpecialLeaveLapsedWork();
		this.grantWork = new SpecialLeaveGrantWork();
		this.nextSpecialLeaveGrant = Optional.empty();
		this.dayBeforePeriodEnd = true;
		this.nextDayAfterPeriodEnd = false;
		this.afterGrant = false;
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
	