package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.export.param;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remain.ReserveLeaveGrantRemaining;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.RealReserveLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeave;

/**
 * 積立年休情報残数
 * @author shuichu_ishida
 */
@Getter
public class ReserveLeaveRemainingNumber implements Cloneable {

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
	
	@Override
	public ReserveLeaveRemainingNumber clone() {
		ReserveLeaveRemainingNumber cloned = new ReserveLeaveRemainingNumber();
		try {
			cloned.reserveLeaveNoMinus = this.reserveLeaveNoMinus.clone();
			cloned.reserveLeaveWithMinus = this.reserveLeaveWithMinus.clone();
		}
		catch (Exception e){
			throw new RuntimeException("ReserveLeaveRemainingNumber clone error.");
		}
		return cloned;
	}
	
	/**
	 * 積立年休付与情報を更新
	 * @param remainingDataList 積立年休付与残数データリスト
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void updateRemainingNumber(
			List<ReserveLeaveGrantRemaining> remainingDataList, boolean afterGrantAtr){
		
		// 積立年休付与残数データから積立年休（マイナスあり）を作成
		this.reserveLeaveWithMinus.createRemainingNumberFromGrantRemaining(remainingDataList, afterGrantAtr);
		
		// 積立年休（マイナスなし）を積立年休（マイナスあり）で上書き　＆　積立年休からマイナスを削除
		this.reserveLeaveNoMinus.setValueFromRealReserveLeave(this.reserveLeaveWithMinus);
	}
}
