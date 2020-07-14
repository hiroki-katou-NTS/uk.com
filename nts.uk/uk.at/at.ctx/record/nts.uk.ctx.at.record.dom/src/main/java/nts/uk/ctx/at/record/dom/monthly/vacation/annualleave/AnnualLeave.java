package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveGrantRemaining;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;

/**
 * 年休
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AnnualLeave implements Cloneable, Serializable {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/** 使用数 */
	private AnnualLeaveUsedInfo usedNumberInfo;
	/** 残数 */
	private AnnualLeaveRemainingNumberInfo remainingNumberInfo;
	
//	/** 残数付与前 */
//	private AnnualLeaveRemainingNumber remainingNumberBeforeGrant;
//	/** 残数付与後 */
//	private Optional<AnnualLeaveRemainingNumber> remainingNumberAfterGrant;
//	
	/**
	 * コンストラクタ
	 */
	public AnnualLeave(){
		
		this.usedNumberInfo = new AnnualLeaveUsedInfo();
		this.remainingNumberInfo = new AnnualLeaveRemainingNumberInfo();
//		this.remainingNumberBeforeGrant = new AnnualLeaveRemainingNumber();
//		this.remainingNumberAfterGrant = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param usedNumberInfo 使用数
	 * @param remainingNumberInfo 残数
	 * @return 実年休
	 */
	public static AnnualLeave of(
			AnnualLeaveUsedInfo usedNumberInfo,
			AnnualLeaveRemainingNumberInfo remainingNumberInfo){
		
		AnnualLeave domain = new AnnualLeave();
		domain.usedNumberInfo = usedNumberInfo;
		domain.remainingNumberInfo = remainingNumberInfo;
//		domain.remainingNumberBeforeGrant = remainingNumberBeforeGrant;
//		domain.remainingNumberAfterGrant = remainingNumberAfterGrant;
		return domain;
	}
	
	@Override
	public AnnualLeave clone() {
		AnnualLeave cloned = new AnnualLeave();
		try {
			cloned.usedNumberInfo = this.usedNumberInfo.clone();
			cloned.remainingNumberInfo = this.remainingNumberInfo.clone();
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeave clone error.");
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
		
		remainingNumberInfo.createRemainingNumberFromGrantRemaining(remainingDataList, afterGrantAtr);
//		
//		// 年休付与残数データから残数を作成
//		this.remainingNumberInfo.createRemainingNumberFromGrantRemaining(remainingDataList);
//		
//		// 「付与後フラグ」をチェック
//		if (afterGrantAtr){
//			
//			// 残数付与後　←　残数
//			this.remainingNumberAfterGrant = Optional.of(this.remainingNumberInfo.clone());
//		}
//		else {
//			
//			// 残数付与前　←　残数
//			this.remainingNumberBeforeGrant = this.remainingNumberInfo.clone();
//		}
	}
	
	/**
	 * 使用数を加算する
	 * @param days 日数
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void addUsedNumber(AnnualLeaveUsedNumber usedNumber, boolean afterGrantAtr){
	
		this.usedNumberInfo.addUsedNumber(usedNumber, afterGrantAtr);
		
//		// 使用数．使用日数．使用日数に加算
//		this.usedNumberInfo.getUsedDays().addUsedDays(days);
//		
//		// 「付与後フラグ」をチェック
//		if (afterGrantAtr){
//		
//			// 使用数．使用日数．使用日数付与後に加算
//			this.usedNumberInfo.getUsedDays().addUsedDaysAfterGrant(days);
//		}
//		else {
//			
//			// 使用数．使用日数．使用日数付与前に加算
//			this.usedNumberInfo.getUsedDays().addUsedDaysBeforeGrant(days);
//		}
	}
	
	/**
	 * 付与前退避処理
	 */
	public void saveStateBeforeGrant(){
		// 合計残数を付与前に退避する
		this.usedNumberInfo.saveStateBeforeGrant();
		this.remainingNumberInfo.saveStateBeforeGrant();
	}
	
	/**
	 * 付与後退避処理
	 */
	public void saveStateAfterGrant(){
		// 合計残数を付与後に退避する
		this.usedNumberInfo.saveStateAfterGrant();
		this.remainingNumberInfo.saveStateAfterGrant();
	}
}
