package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remain.AnnualLeaveGrantRemaining;

/**
 * 実年休
 * @author shuichu_ishida
 */
@Getter
@Setter
public class RealAnnualLeave implements Cloneable {

	/** 使用数 */
	private AnnualLeaveUsedNumber usedNumber;
	/** 残数 */
	private AnnualLeaveRemainingNumber remainingNumber;
	/** 残数付与前 */
	private AnnualLeaveRemainingNumber remainingNumberBeforeGrant;
	/** 残数付与後 */
	private Optional<AnnualLeaveRemainingNumber> remainingNumberAfterGrant;
	
	/**
	 * コンストラクタ
	 */
	public RealAnnualLeave(){
		
		this.usedNumber = new AnnualLeaveUsedNumber();
		this.remainingNumber = new AnnualLeaveRemainingNumber();
		this.remainingNumberBeforeGrant = new AnnualLeaveRemainingNumber();
		this.remainingNumberAfterGrant = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param usedNumber 使用数
	 * @param remainingNumber 残数
	 * @param remainingNumberBeforeGrant 残数付与前
	 * @param remainingNumberAfterGrant 残数付与後
	 * @return 実年休
	 */
	public static RealAnnualLeave of(
			AnnualLeaveUsedNumber usedNumber,
			AnnualLeaveRemainingNumber remainingNumber,
			AnnualLeaveRemainingNumber remainingNumberBeforeGrant,
			Optional<AnnualLeaveRemainingNumber> remainingNumberAfterGrant){
		
		RealAnnualLeave domain = new RealAnnualLeave();
		domain.usedNumber = usedNumber;
		domain.remainingNumber = remainingNumber;
		domain.remainingNumberBeforeGrant = remainingNumberBeforeGrant;
		domain.remainingNumberAfterGrant = remainingNumberAfterGrant;
		return domain;
	}
	
	@Override
	public RealAnnualLeave clone() {
		RealAnnualLeave cloned = new RealAnnualLeave();
		try {
			cloned.usedNumber = this.usedNumber.clone();
			cloned.remainingNumber = this.remainingNumber.clone();
			cloned.remainingNumberBeforeGrant = this.remainingNumberBeforeGrant.clone();
			if (this.remainingNumberAfterGrant.isPresent()){
				cloned.remainingNumberAfterGrant = Optional.of(this.remainingNumberAfterGrant.get().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("RealAnnualLeave clone error.");
		}
		return cloned;
	}
	
	/**
	 * 年休付与残数データから年休残数を作成
	 * @param remainingDataList 年休付与残数データリスト
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void createRemainingNumberFromGrantRemaining(
			List<AnnualLeaveGrantRemaining> remainingDataList, boolean afterGrantAtr){
		
		// 年休付与残数データから残数を作成
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
	
		// 使用数．使用日数．使用日数に加算
		this.usedNumber.getUsedDays().addUsedDays(days);
		
		// 「付与後フラグ」をチェック
		if (afterGrantAtr){
		
			// 使用数．使用日数．使用日数付与後に加算
			this.usedNumber.getUsedDays().addUsedDaysAfterGrant(days);
		}
		else {
			
			// 使用数．使用日数．使用日数付与前に加算
			this.usedNumber.getUsedDays().addUsedDaysBeforeGrant(days);
		}
	}
}
