package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RealReserveLeave;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeave;

/**
 * 積立年休情報残数
 * @author shuichu_ishida
 */
@Getter
public class ReserveLeaveRemainingNumber {

	/** 積立年休（マイナスなし） */
	private ReserveLeave reserveLeaveNoMinus;
	/** 積立年休（マイナスあり） */
	private RealReserveLeave reserveLeaveWithMinus;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeaveRemainingNumber(){
		
		this.reserveLeaveNoMinus = new ReserveLeave();
		this.reserveLeaveWithMinus = new RealReserveLeave();
	}
	
	/**
	 * ファクトリー
	 * @param reserveLeaveNoMinus 積立年休（マイナスなし）
	 * @param reserveLeaveWithMinus 積立年休（マイナスあり）
	 * @return 積立年休情報残数
	 */
	public static ReserveLeaveRemainingNumber of(
			ReserveLeave reserveLeaveNoMinus,
			RealReserveLeave reserveLeaveWithMinus){
		
		ReserveLeaveRemainingNumber domain = new ReserveLeaveRemainingNumber();
		domain.reserveLeaveNoMinus = reserveLeaveNoMinus;
		domain.reserveLeaveWithMinus = reserveLeaveWithMinus;
		return domain;
	}
}
