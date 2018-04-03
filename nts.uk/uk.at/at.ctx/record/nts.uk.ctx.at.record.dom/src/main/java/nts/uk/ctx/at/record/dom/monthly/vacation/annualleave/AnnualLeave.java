package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import lombok.Getter;

/**
 * 年休
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeave {

	/** 残数 */
	private AnnualLeaveRemainingNumber remainingNumber;
	/** 使用数 */
	private AnnualLeaveUsedNumber usedNumber;
	/** 未消化数 */
	private AnnualLeaveUndigestedNumber undigestedNumber;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeave(){
		
		this.remainingNumber = new AnnualLeaveRemainingNumber();
		this.usedNumber = new AnnualLeaveUsedNumber();
		this.undigestedNumber = new AnnualLeaveUndigestedNumber();
	}
	
	/**
	 * ファクトリー
	 * @param remainingNumber 残数
	 * @param usedNumber 使用数
	 * @param undigestedNumber 未消化数
	 * @return 年休
	 */
	public static AnnualLeave of(
			AnnualLeaveRemainingNumber remainingNumber,
			AnnualLeaveUsedNumber usedNumber,
			AnnualLeaveUndigestedNumber undigestedNumber){

		AnnualLeave domain = new AnnualLeave();
		domain.remainingNumber = remainingNumber;
		domain.usedNumber = usedNumber;
		domain.undigestedNumber = undigestedNumber;
		return domain;
	}
}
