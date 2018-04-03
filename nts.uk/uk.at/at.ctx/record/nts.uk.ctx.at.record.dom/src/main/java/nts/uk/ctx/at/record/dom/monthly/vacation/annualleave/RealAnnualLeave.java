package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import lombok.Getter;

/**
 * 実年休
 * @author shuichu_ishida
 */
@Getter
public class RealAnnualLeave {

	/** 残数 */
	private AnnualLeaveRemainingNumber remainingNumber;
	/** 使用数 */
	private AnnualLeaveUsedNumber usedNumber;
	
	/**
	 * コンストラクタ
	 */
	public RealAnnualLeave(){
		
		this.remainingNumber = new AnnualLeaveRemainingNumber();
		this.usedNumber = new AnnualLeaveUsedNumber();
	}
	
	/**
	 * ファクトリー
	 * @param remainingNumber 残数
	 * @param usedNumber 使用数
	 * @return 実年休
	 */
	public static RealAnnualLeave of(
			AnnualLeaveRemainingNumber remainingNumber,
			AnnualLeaveUsedNumber usedNumber){
		
		RealAnnualLeave domain = new RealAnnualLeave();
		domain.remainingNumber = remainingNumber;
		domain.usedNumber = usedNumber;
		return domain;
	}
}
