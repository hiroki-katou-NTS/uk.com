package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 年休集計期間WORK
 * @author shuichu_ishida
 */
@Getter
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
	
	/**
	 * コンストラクタ
	 */
	public AggregatePeriodWork(){
		
		this.period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.nextDayAfterPeriodEnd = false;
		this.grantAtr = false;
		this.afterGrant = false;
		this.lapsedAtr = false;
	}
	
	/**
	 * ファクトリー
	 * @param period 期間
	 * @param nextDayAfterPeriodEnd 期間終了後翌日
	 * @param grantAtr 付与フラグ
	 * @param afterGrant 付与後
	 * @param lapsedAtr　消滅フラグ
	 * @return 年休集計期間WORK
	 */
	public static AggregatePeriodWork of(
			DatePeriod period,
			boolean nextDayAfterPeriodEnd,
			boolean grantAtr,
			boolean afterGrant,
			boolean lapsedAtr){
		
		AggregatePeriodWork domain = new AggregatePeriodWork();
		domain.period = period;
		domain.nextDayAfterPeriodEnd = nextDayAfterPeriodEnd;
		domain.grantAtr = grantAtr;
		domain.afterGrant = afterGrant;
		domain.lapsedAtr = lapsedAtr;
		return domain;
	}
}
