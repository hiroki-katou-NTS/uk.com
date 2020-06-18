package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.util.Optional;

/**
 * 年休使用情報
 * @author masaaki_jinno
 *
 */
public class AnnualLeaveUsedInfo implements Cloneable {

	/** 付与前 */
	private AnnualLeaveUsedNumber usedNumberBeforeGrant;
	
	/** 合計 */
	private AnnualLeaveUsedNumber usedNumber;
	
	/** 使用回数 */
	
	
	/** 付与後 */
	private Optional<AnnualLeaveUsedNumber> usedNumberAfterGrantOpt;
	
	/**
	 * クローン
	 */
	public AnnualLeaveUsedInfo clone() {
		AnnualLeaveUsedInfo cloned = new AnnualLeaveUsedInfo();
		try {
			if ( usedNumberBeforeGrant != null ){
				cloned.usedNumberBeforeGrant = this.usedNumberBeforeGrant.clone();
			}
			if ( usedNumber != null ){
				cloned.usedNumber = this.usedNumber.clone();
			}
			
			/** 使用回数 */ 
			// ooooo
			
			
			
			if (this.usedNumberAfterGrantOpt.isPresent()){
				cloned.usedNumberAfterGrantOpt = Optional.of(this.usedNumberAfterGrantOpt.get().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveUsedInfo clone error.");
		}
		return cloned;
	}
	
}
