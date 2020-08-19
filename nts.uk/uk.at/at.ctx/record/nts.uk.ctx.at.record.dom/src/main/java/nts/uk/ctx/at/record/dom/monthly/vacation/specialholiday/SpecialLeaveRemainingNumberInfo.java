package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.param.SpecialLeaveGrantRemaining;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;

/**
 * 特休残数情報
 * @author masaaki_jinno
 *
 */
@Getter
@AllArgsConstructor
public class SpecialLeaveRemainingNumberInfo implements Cloneable {

	/** 合計 */
	private SpecialLeaveRemainingNumber remainingNumber;
	
	/** 付与前 */
	private SpecialLeaveRemainingNumber remainingNumberBeforeGrant;
	
	/** 付与後 */
	private Optional<SpecialLeaveRemainingNumber> remainingNumberAfterGrantOpt;
	
	/**
	 * ファクトリ
	 * @param remainingNumber 合計
	 * @param remainingNumberBeforeGrant 付与前
	 * @param remainingNumberAfterGrantOpt 付与後
	 * @return
	 */
	public static SpecialLeaveRemainingNumberInfo of(
			SpecialLeaveRemainingNumber remainingNumber,
			SpecialLeaveRemainingNumber remainingNumberBeforeGrant,
			Optional<SpecialLeaveRemainingNumber> remainingNumberAfterGrantOpt
			){
		
		SpecialLeaveRemainingNumberInfo domain = new SpecialLeaveRemainingNumberInfo();
		domain.remainingNumber = remainingNumber;
		domain.remainingNumberBeforeGrant = remainingNumberBeforeGrant;
		domain.remainingNumberAfterGrantOpt= remainingNumberAfterGrantOpt;
		return domain;
	}
	
	/** コンストラクタ  */
	public SpecialLeaveRemainingNumberInfo(){
		this.remainingNumber = new SpecialLeaveRemainingNumber();
		this.remainingNumberBeforeGrant = new SpecialLeaveRemainingNumber();
		this.remainingNumberAfterGrantOpt = Optional.empty();
	}
	
	/**
	 * 特休付与残数データから特休残数を作成
	 * @param remainingDataList 特休付与残数データリスト
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void createRemainingNumberFromGrantRemaining(
			List<SpecialLeaveGrantRemaining> remainingDataList, boolean afterGrantAtr){
		
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
	public SpecialLeaveRemainingNumberInfo clone() {
		SpecialLeaveRemainingNumberInfo cloned = new SpecialLeaveRemainingNumberInfo();
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
			throw new RuntimeException("SpecialLeaveRemainingInfo clone error.");
		}
		return cloned;
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

