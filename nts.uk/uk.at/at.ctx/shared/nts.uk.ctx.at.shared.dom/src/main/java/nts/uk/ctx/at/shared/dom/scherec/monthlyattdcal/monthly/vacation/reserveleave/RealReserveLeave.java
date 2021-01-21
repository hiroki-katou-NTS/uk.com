package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remain.ReserveLeaveGrantRemaining;

/**
 * 実積立年休
 * @author shuichu_ishida
 */
@Getter
@Setter
public class RealReserveLeave implements Cloneable {

	/** 使用数 */
	private ReserveLeaveUsedNumber usedNumber;
	/** 残数 */
	private ReserveLeaveRemainingNumber remainingNumber;
	/** 残数付与前 */
	private ReserveLeaveRemainingNumber remainingNumberBeforeGrant;
	/** 残数付与後 */
	private Optional<ReserveLeaveRemainingNumber> remainingNumberAfterGrant;
	
	/**
	 * コンストラクタ
	 */
	public RealReserveLeave(){
		
		this.usedNumber = new ReserveLeaveUsedNumber();
		this.remainingNumber = new ReserveLeaveRemainingNumber();
		this.remainingNumberBeforeGrant = new ReserveLeaveRemainingNumber();
		this.remainingNumberAfterGrant = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param usedNumber 使用数
	 * @param remainingNumber 残数
	 * @param remainingNumberBeforeGrant 残数付与前
	 * @param remainingNumberAfterGrant 残数付与後
	 * @return 実積立年休
	 */
	public static RealReserveLeave of(
			ReserveLeaveUsedNumber usedNumber,
			ReserveLeaveRemainingNumber remainingNumber,
			ReserveLeaveRemainingNumber remainingNumberBeforeGrant,
			Optional<ReserveLeaveRemainingNumber> remainingNumberAfterGrant){
		
		RealReserveLeave domain = new RealReserveLeave();
		domain.usedNumber = usedNumber;
		domain.remainingNumber = remainingNumber;
		domain.remainingNumberBeforeGrant = remainingNumberBeforeGrant;
		domain.remainingNumberAfterGrant = remainingNumberAfterGrant;
		return domain;
	}
	
	@Override
	public RealReserveLeave clone() {
		RealReserveLeave cloned = new RealReserveLeave();
		try {
			cloned.usedNumber = this.usedNumber.clone();
			cloned.remainingNumber = this.remainingNumber.clone();
			cloned.remainingNumberBeforeGrant = this.remainingNumberBeforeGrant.clone();
			if (this.remainingNumberAfterGrant.isPresent()){
				cloned.remainingNumberAfterGrant = Optional.of(this.remainingNumberAfterGrant.get().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("RealReserveLeave clone error.");
		}
		return cloned;
	}
	
	/**
	 * 積立年休付与残数データから積立年休残数を作成
	 * @param remainingDataList 積立年休付与残数データリスト
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void createRemainingNumberFromGrantRemaining(
			List<ReserveLeaveGrantRemaining> remainingDataList, boolean afterGrantAtr){
		
		// 積立年休付与残数データから残数を作成
		this.remainingNumber.createRemainingNumberFromGrantRemaining(remainingDataList);
		
		// 「付与後フラグ」をチェック
		if (afterGrantAtr){
			
			// 残数付与後　←　残数
			this.remainingNumberAfterGrant = Optional.of(this.remainingNumber.clone());
		}
		else {
			
			// 残数付与前　←　残数
			this.remainingNumberBeforeGrant = this.remainingNumber.clone();
		}
	}
	
	/**
	 * 使用数を加算する
	 * @param days 日数
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void addUsedNumber(double days, boolean afterGrantAtr){
	
		// 使用数．使用日数に加算
		this.usedNumber.addUsedDays(days);
		
		// 「付与後フラグ」をチェック
		if (afterGrantAtr){
		
			// 使用数．使用日数付与後に加算
			this.usedNumber.addUsedDaysAfterGrant(days);
		}
		else {
			
			// 使用数．使用日数付与前に加算
			this.usedNumber.addUsedDaysBeforeGrant(days);
		}
	}
}
