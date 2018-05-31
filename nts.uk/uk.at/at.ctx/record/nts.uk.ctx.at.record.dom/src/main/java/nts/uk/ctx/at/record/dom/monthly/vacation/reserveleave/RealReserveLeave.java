package nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * 実積立年休
 * @author shuichu_ishida
 */
@Getter
@Setter
public class RealReserveLeave {

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
}
