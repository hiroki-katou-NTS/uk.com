package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 年休集計期間WORK
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AggregatePeriodWork {

	/** 期間 */
	private DatePeriod period;
	/** 期間終了後翌日 */
	private boolean nextDayAfterPeriodEnd;
	/** 付与フラグ */
	private boolean grantAtr;
	/** 付与後 */
	private boolean afterGrant;
	/** 消滅フラグ */
	private boolean lapsedAtr;
	/** 年休付与 */
	private Optional<NextAnnualLeaveGrant> annualLeaveGrant;
	
	/**
	 * コンストラクタ
	 */
	public AggregatePeriodWork(){
		
		this.period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.nextDayAfterPeriodEnd = false;
		this.grantAtr = false;
		this.afterGrant = false;
		this.lapsedAtr = false;
		this.annualLeaveGrant = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param period 期間
	 * @param nextDayAfterPeriodEnd 期間終了後翌日
	 * @param grantAtr 付与フラグ
	 * @param afterGrant 付与後
	 * @param lapsedAtr 消滅フラグ
	 * @param annualLeaveGrant 年休付与
	 * @return 年休集計期間WORK
	 */
	public static AggregatePeriodWork of(
			DatePeriod period,
			boolean nextDayAfterPeriodEnd,
			boolean grantAtr,
			boolean afterGrant,
			boolean lapsedAtr,
			Optional<NextAnnualLeaveGrant> annualLeaveGrant){
		
		AggregatePeriodWork domain = new AggregatePeriodWork();
		domain.period = period;
		domain.nextDayAfterPeriodEnd = nextDayAfterPeriodEnd;
		domain.grantAtr = grantAtr;
		domain.afterGrant = afterGrant;
		domain.lapsedAtr = lapsedAtr;
		domain.annualLeaveGrant = annualLeaveGrant;
		return domain;
	}
}
