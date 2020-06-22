package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;

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
	
	/** 時間年休使用回数 （1日2回使用した場合２回でカウント）*/
	private UsedTimes annualLeaveUsedTimes;
	
	/** 時間年休使用日数 （1日2回使用した場合１回でカウント） */
	private UsedTimes annualLeaveUsedDayTimes;
	
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
			
			/** 時間年休使用回数 */ 
			cloned.annualLeaveUsedTimes = new UsedTimes(this.annualLeaveUsedTimes.v());
			
			/** 時間年休使用日数 */ 
			cloned.annualLeaveUsedDayTimes = new UsedTimes(this.annualLeaveUsedDayTimes.v());
			
			if (this.usedNumberAfterGrantOpt.isPresent()){
				cloned.usedNumberAfterGrantOpt = Optional.of(this.usedNumberAfterGrantOpt.get().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveUsedInfo clone error.");
		}
		return cloned;
	}
	
	/**
	 * 使用数を加算する
	 * @param usedNumber 使用数
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void addUsedNumber(AnnualLeaveUsedNumber usedNumber, boolean afterGrantAtr){
	
		// 使用数に加算
		this.usedNumber.addUsedNumber(usedNumber);
		
		// 「付与後フラグ」をチェック
		if (afterGrantAtr){
		
			// 使用日数付与後に加算
			if ( this.usedNumberAfterGrantOpt.isPresent() ){
				this.usedNumberAfterGrantOpt.get().addUsedNumber(usedNumber);
			} else {
				this.usedNumberAfterGrantOpt = Optional.of(usedNumber.clone());
			}
			
		}
		else {
			
			// 使用日数付与前に加算
			this.usedNumberBeforeGrant.addUsedNumber(usedNumber);
			
		}
	}
	
}
