package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.GrantPeriodAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.MaxDaysRetention;

/**
 * 積立年休集計期間WORK
 * @author shuichu_ishida
 */
@Getter
@Setter
@AllArgsConstructor
public class RsvLeaAggrPeriodWork {

	/** 期間 */
	private DatePeriod period;
	
	/** 付与 */
	private ReserveLeaveGrantWork grantWork;
	
	/** 消滅 */
	private ReserveLeaveLapsedWork lapsedAtr;
	
	/** 終了日 */
	private RsvLeaNextDayAfterPeriodEndWork endWork;
	
	/**
	 * コンストラクタ
	 */
	public RsvLeaAggrPeriodWork(){
		this.period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.grantWork = new ReserveLeaveGrantWork();
		this.lapsedAtr = new ReserveLeaveLapsedWork();
		this.endWork = new RsvLeaNextDayAfterPeriodEndWork();	
	}

	/**
	 * ファクトリー 
	 * @param period 期間
	 * @param nextDayAfterPeriodEnd 期間終了後翌日
	 * @param grantAtr 付与フラグ
	 * @param afterGrant 付与後
	 * @param lapsedAtr 消滅フラグ
	 * @param maxDays 上限日数
	 * @param reserveLeaveGrant 積立年休付与
	 * @return 積立年休集計期間WORK
	 */
	public static RsvLeaAggrPeriodWork of(
			DatePeriod period,
			RsvLeaNextDayAfterPeriodEndWork endWork,
			boolean grantAtr,
			GrantPeriodAtr grantPeriodAtr,
			boolean lapsedAtr,
			MaxDaysRetention maxDays,
			Optional<NextReserveLeaveGrant> reserveLeaveGrant){

		RsvLeaAggrPeriodWork domain = new RsvLeaAggrPeriodWork();
		domain.period = period;
		domain.grantWork = ReserveLeaveGrantWork.of(
				grantAtr, 0, maxDays, grantPeriodAtr, reserveLeaveGrant);
		domain.lapsedAtr = new ReserveLeaveLapsedWork(lapsedAtr);
		domain.endWork = endWork;
		
		return domain;
	}

}
