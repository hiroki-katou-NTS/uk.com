package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.NextReserveLeaveGrant;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveGrantWork;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveLapsedWork;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.RsvLeaNextDayAfterPeriodEndWork;

/**
 * 処理単位分割日　（積立年休用）
 * @author shuichu_ishida
 */
@Getter
public class RsvLeaDividedDay {

	/** 年月日 */
	private GeneralDate ymd;
	/** 付与 */
	@Setter
	private ReserveLeaveGrantWork grantWork;
	/** 消滅 */
	@Setter
	private ReserveLeaveLapsedWork lapsedWork;
	/** 終了日 */
	@Setter
	private RsvLeaNextDayAfterPeriodEndWork endWork;

	/**
	 * コンストラクタ
	 * @param ymd 年月日
	 */
	public RsvLeaDividedDay(GeneralDate ymd){
		this.ymd = ymd;
		this.grantWork = new ReserveLeaveGrantWork();
		this.lapsedWork = new ReserveLeaveLapsedWork();
		this.endWork = new RsvLeaNextDayAfterPeriodEndWork();
	}

	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param nextReserveLeaveGrant 次回積立年休付与
	 * @param nextDayAfterPeriodEnd 期間終了後翌日
	 * @param grantAtr 付与フラグ
	 * @param lapsedAtr 消滅フラグ
	 * @return 処理単位分割日　（積立年休用）
	 */
	public static RsvLeaDividedDay of(
			GeneralDate ymd,
			Optional<NextReserveLeaveGrant> nextReserveLeaveGrant,
			RsvLeaNextDayAfterPeriodEndWork endWork,
			boolean grantAtr,
			boolean lapsedAtr){

		RsvLeaDividedDay domain = new RsvLeaDividedDay(ymd);
		domain.getGrantWork().setReserveLeaveGrant(nextReserveLeaveGrant);
		domain.getGrantWork().setGrantAtr(grantAtr);
		domain.getLapsedWork().setLapsedAtr(lapsedAtr);
		domain.endWork = endWork;
		return domain;
	}
}
