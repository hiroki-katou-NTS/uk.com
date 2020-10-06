package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remain.ReserveLeaveGrantRemaining;

/**
 * 積立年休残情報
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class ReserveLeaveRemainingInfo {
	
	/** 合計 */
	private ReserveLeaveRemainingNumber remainingNumber;
	
	/** 付与前 */
	private ReserveLeaveRemainingNumber remainingNumberBeforeGrant;
	
	/** 付与後 */
	private Optional<ReserveLeaveRemainingNumber> remainingNumberAfterGrantOpt;
	
	/**
	 * ファクトリ
	 * @param remainingNumber 合計
	 * @param remainingNumberBeforeGrant 付与前
	 * @param remainingNumberAfterGrantOpt 付与後
	 * @return
	 */
	public static ReserveLeaveRemainingInfo of(
			ReserveLeaveRemainingNumber remainingNumber,
			ReserveLeaveRemainingNumber remainingNumberBeforeGrant,
			Optional<ReserveLeaveRemainingNumber> remainingNumberAfterGrantOpt
			){
		
		ReserveLeaveRemainingInfo domain = new ReserveLeaveRemainingInfo();
		domain.remainingNumber = remainingNumber;
		domain.remainingNumberBeforeGrant = remainingNumberBeforeGrant;
		domain.remainingNumberAfterGrantOpt= remainingNumberAfterGrantOpt;
		return domain;
	}
	
	/** コンストラクタ  */
	public ReserveLeaveRemainingInfo(){
		this.remainingNumber = new ReserveLeaveRemainingNumber();
		this.remainingNumberBeforeGrant = new ReserveLeaveRemainingNumber();
		this.remainingNumberAfterGrantOpt = Optional.empty();
	}
	
	/**
	 * 特別休暇付与残数データから実特別休暇の特別休暇残数を作成
	 * @param remainingDataList 特休付与残数データリスト
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void createRemainingNumberFromGrantRemaining(
			List<ReserveLeaveGrantRemaining> remainingDataList, 
			boolean afterGrantAtr){
		
		// 特休付与残数データから残数を作成
		this.remainingNumber.createRemainingNumberFromGrantRemaining(remainingDataList);
		
		// 「付与後フラグ」をチェック
		if (afterGrantAtr){
			// 残数付与後　←　残数
			this.remainingNumberAfterGrantOpt = Optional.of(this.remainingNumber.clone());
		}
		else {
			// 残数付与前　←　残数
			this.remainingNumberBeforeGrant = this.remainingNumber.clone();
		}
	}
	
	/**
	 * クローン
	 */
	public ReserveLeaveRemainingInfo clone() {
		ReserveLeaveRemainingInfo cloned = new ReserveLeaveRemainingInfo();
		try {
			if ( remainingNumberBeforeGrant != null ){
				cloned.remainingNumberBeforeGrant = this.remainingNumberBeforeGrant.clone();
			}
			if ( remainingNumber != null ){
				cloned.remainingNumber = this.remainingNumber.clone();
			}
			
			if (this.remainingNumberAfterGrantOpt.isPresent()){
				cloned.remainingNumberAfterGrantOpt = Optional.of(this.remainingNumberAfterGrantOpt.get().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("ReserveLeaveRemainingInfo clone error.");
		}
		return cloned;
	}
	
	/**
	 * クリア
	 */
	public void clear(){
		remainingNumber.clear();
		remainingNumberBeforeGrant.clear();
		remainingNumberAfterGrantOpt = Optional.empty();
	}

	/**
	 * 付与前退避処理
	 */
	public void saveStateBeforeGrant(){
		// 合計残数を付与前に退避する
		remainingNumberBeforeGrant = remainingNumber.clone();
	}
	
	/**
	 * 付与後退避処理
	 */
	public void saveStateAfterGrant(){
		// 合計残数を付与後に退避する
		remainingNumberAfterGrantOpt = Optional.of(remainingNumber.clone());
	}
	
	/**
	 * 明細をクリア。（要素数を０にする）
	 */
	public void clearDetails(){
		remainingNumber.clearDetails();
		remainingNumberBeforeGrant.clearDetails();
		if ( remainingNumberAfterGrantOpt.isPresent() ){
			remainingNumberAfterGrantOpt.get().clearDetails();
		}
	}
	
}
