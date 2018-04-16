package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;

/**
 * 実年休
 * @author shuichu_ishida
 */
@Getter
@Setter
public class RealAnnualLeave {

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
	
	/**
	 * 年休付与残数データから年休残数を作成
	 * @param remainingDataList 年休付与残数データリスト
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void createRemainingNumberFromGrantRemaining(
			List<AnnualLeaveGrantRemainingData> remainingDataList, boolean afterGrantAtr){
		
		// 年休付与残数データから残数を作成
		this.remainingNumber.createRemainingNumberFromGrantRemaining(remainingDataList);
		
		// 「付与後フラグ」をチェック
		if (afterGrantAtr){
			
			// 残数付与後　←　残数
			this.remainingNumberAfterGrant = Optional.of(this.remainingNumber);
		}
		else {
			
			// 残数付与前　←　残数
			this.remainingNumberBeforeGrant = this.remainingNumber;
		}
	}
}
