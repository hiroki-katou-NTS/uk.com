package nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * 積立年休
 * @author shuichu_ishida
 */
@Getter
@Setter
public class ReserveLeave {

	/** 使用数 */
	private ReserveLeaveUsedNumber usedNumber;
	/** 残数 */
	private ReserveLeaveRemainingNumber remainingNumber;
	/** 残数付与前 */
	private ReserveLeaveRemainingNumber remainingNumberBeforeGrant;
	/** 残数付与後 */
	private Optional<ReserveLeaveRemainingNumber> remainingNumberAfterGrant;
	/** 未消化数 */
	private ReserveLeaveUndigestedNumber undigestedNumber;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeave(){
		
		this.usedNumber = new ReserveLeaveUsedNumber();
		this.remainingNumber = new ReserveLeaveRemainingNumber();
		this.remainingNumberBeforeGrant = new ReserveLeaveRemainingNumber();
		this.remainingNumberAfterGrant = Optional.empty();
		this.undigestedNumber = new ReserveLeaveUndigestedNumber();
	}
	
	/**
	 * ファクトリー
	 * @param usedNumber 使用数
	 * @param remainingNumber 残数
	 * @param remainingNumberBeforeGrant 残数付与前
	 * @param remainingNumberAfterGrant 残数付与後
	 * @param undigestedNumber 未消化数
	 * @return 積立年休
	 */
	public static ReserveLeave of(
			ReserveLeaveUsedNumber usedNumber,
			ReserveLeaveRemainingNumber remainingNumber,
			ReserveLeaveRemainingNumber remainingNumberBeforeGrant,
			Optional<ReserveLeaveRemainingNumber> remainingNumberAfterGrant,
			ReserveLeaveUndigestedNumber undigestedNumber){

		ReserveLeave domain = new ReserveLeave();
		domain.usedNumber = usedNumber;
		domain.remainingNumber = remainingNumber;
		domain.remainingNumberBeforeGrant = remainingNumberBeforeGrant;
		domain.remainingNumberAfterGrant = remainingNumberAfterGrant;
		domain.undigestedNumber = undigestedNumber;
		return domain;
	}
}
