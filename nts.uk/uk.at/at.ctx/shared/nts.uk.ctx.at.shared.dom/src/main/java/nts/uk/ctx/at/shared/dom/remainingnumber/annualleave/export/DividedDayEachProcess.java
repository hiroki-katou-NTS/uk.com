package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;

/**
 * 処理単位分割日
 * @author shuichu_ishida
 */
@Getter
public class DividedDayEachProcess {

	/** 年月日 */
	private GeneralDate ymd;
	
	/** 次回年休付与 */
	@Setter
	private Optional<NextAnnualLeaveGrant> nextAnnualLeaveGrant;
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
	public DividedDayEachProcess(GeneralDate ymd){
		
		this.ymd = ymd;
		
		this.nextAnnualLeaveGrant = Optional.empty();
		this.nextDayAfterPeriodEnd = false;
		this.grantAtr = false;
		this.lapsedAtr = false;
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param nextAnnualLeaveGrant 次回年休付与
	 * @param nextDayAfterPeriodEnd 期間終了後翌日
	 * @param grantAtr 付与フラグ
	 * @param lapsedAtr 消滅フラグ
	 * @return 処理単位分割日
	 */
	public static DividedDayEachProcess of(
			GeneralDate ymd,
			Optional<NextAnnualLeaveGrant> nextAnnualLeaveGrant,
			boolean nextDayAfterPeriodEnd,
			boolean grantAtr,
			boolean lapsedAtr){
		
		DividedDayEachProcess domain = new DividedDayEachProcess(ymd);
		domain.nextAnnualLeaveGrant = nextAnnualLeaveGrant;
		domain.nextDayAfterPeriodEnd = nextDayAfterPeriodEnd;
		domain.grantAtr = grantAtr;
		domain.lapsedAtr = lapsedAtr;
		return domain;
	}
}
