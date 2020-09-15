package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.param;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggregatePeriodWork;
import nts.uk.ctx.at.shared.dom.specialholiday.export.NextSpecialLeaveGrant;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;

/**
 * 特別休暇集計期間WORK
 * @author masaaki_jinno
 *SpecialLeaveLapsedWork
 */
@Getter
@Setter
public class SpecialLeaveAggregatePeriodWork {

	/** 期間 */
	private DatePeriod period;
	/** 期間終了内 */
	private boolean dayBeforePeriodEnd;
	/** 期間終了後翌日 */
	private boolean nextDayAfterPeriodEnd;
//	/** 付与フラグ */
//	private boolean grantAtr;
//	/** 何回目の付与なのか */
//	private int grantNumber = 0;	
	/** 付与後 */
	private boolean afterGrant;
	/** 消滅情報WORK */
	private SpecialLeaveLapsedWork lapsedWork;
	/** 特休付与 */
	private Optional<NextSpecialLeaveGrant> specialLeaveGrant;
	
	/**
	 * コンストラクタ
	 */
	public SpecialLeaveAggregatePeriodWork(){
		this.period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.dayBeforePeriodEnd = false;
		this.nextDayAfterPeriodEnd = false;
		this.afterGrant = false;
		this.lapsedWork = new SpecialLeaveLapsedWork();
		this.specialLeaveGrant = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param period 期間
	 * @param nextDayAfterPeriodEnd 期間終了後翌日
	 * @param afterGrant 付与後
	 * @param lapsedAtr 消滅フラグ
	 * @param specialLeaveGrant 特休付与
	 * @return 年休集計期間WORK
	 */
	public static SpecialLeaveAggregatePeriodWork of(
			DatePeriod period,
			boolean dayBeforePeriodEnd,
			boolean nextDayAfterPeriodEnd,
			boolean afterGrant,
			SpecialLeaveLapsedWork lapsedAtr,
			Optional<NextSpecialLeaveGrant> specialLeaveGrant){
		
		SpecialLeaveAggregatePeriodWork domain = new SpecialLeaveAggregatePeriodWork();
		domain.period = period;
		domain.dayBeforePeriodEnd = dayBeforePeriodEnd;
		domain.nextDayAfterPeriodEnd = nextDayAfterPeriodEnd;
		domain.afterGrant = afterGrant;
		domain.lapsedWork = lapsedAtr;
		domain.specialLeaveGrant = specialLeaveGrant;
		return domain;
	}
}