package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.export;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.export.param.NextReserveLeaveGrant;

/**
 * 処理単位分割日　（積立年休用）
 * @author shuichu_ishida
 */
@Getter
public class RsvLeaDividedDay {

	/** 年月日 */
	private GeneralDate ymd;
	
	/** 次回積立年休付与 */
	@Setter
	private Optional<NextReserveLeaveGrant> nextReserveLeaveGrant;
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
	public RsvLeaDividedDay(GeneralDate ymd){
		
		this.ymd = ymd;
		
		this.nextReserveLeaveGrant = Optional.empty();
		this.nextDayAfterPeriodEnd = false;
		this.grantAtr = false;
		this.lapsedAtr = false;
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
			boolean nextDayAfterPeriodEnd,
			boolean grantAtr,
			boolean lapsedAtr){
		
		RsvLeaDividedDay domain = new RsvLeaDividedDay(ymd);
		domain.nextReserveLeaveGrant = nextReserveLeaveGrant;
		domain.nextDayAfterPeriodEnd = nextDayAfterPeriodEnd;
		domain.grantAtr = grantAtr;
		domain.lapsedAtr = lapsedAtr;
		return domain;
	}
}
