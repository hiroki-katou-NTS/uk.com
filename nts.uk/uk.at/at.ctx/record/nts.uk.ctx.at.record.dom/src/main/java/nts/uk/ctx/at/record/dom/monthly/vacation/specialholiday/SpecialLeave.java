package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
//import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.SpecialLeave;
//import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.SpecialLeaveRemainingNumberInfo;
//import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.SpecialLeaveUsedInfo;
//import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.SpecialLeaveUsedNumber;
//import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.SpecialLeaveGrantRemaining;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.param.SpecialLeaveGrantRemaining;

/**
 * 特別休暇
 * @author do_dt
 *
 */
@AllArgsConstructor
@Setter
@Getter
public class SpecialLeave extends DomainObject implements Cloneable, Serializable {
	
	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 特別休暇使用情報
	 */
	private SpecialLeaveUsedInfo usedNumberInfo;
	
	/**
	 * 特別休暇残数情報
	 */
	private SpecialLeaveRemainingNumberInfo remainingNumberInfo;
	
	/**
	 * コンストラクタ
	 */
	public SpecialLeave(){
		
		this.usedNumberInfo = new SpecialLeaveUsedInfo();
		this.remainingNumberInfo = new SpecialLeaveRemainingNumberInfo();
	}
	
	/**
	 * ファクトリー
	 * @param usedNumberInfo 特別休暇使用情報
	 * @param remainingNumberInfo 残数
	 * @return 実特休
	 */
	public static SpecialLeave of(
			SpecialLeaveUsedInfo usedNumberInfo,
			SpecialLeaveRemainingNumberInfo remainingNumberInfo){
		
		SpecialLeave domain = new SpecialLeave();
		domain.usedNumberInfo = usedNumberInfo;
		domain.remainingNumberInfo = remainingNumberInfo;
		return domain;
	}
	
	@Override
	public SpecialLeave clone() {
		SpecialLeave cloned = new SpecialLeave();
		try {
			cloned.usedNumberInfo = this.usedNumberInfo.clone();
			cloned.remainingNumberInfo = this.remainingNumberInfo.clone();
		}
		catch (Exception e){
			throw new RuntimeException("SpecialLeave clone error.");
		}
		return cloned;
	}
	
	/**
	 * データをクリア
	 */
	public void clear(){
		usedNumberInfo.clear();
		remainingNumberInfo.clear();
	}

	/**
	 * 特別休暇付与残数データから実特別休暇の特別休暇残数を作成
	 * @param remainingDataList 特休付与残数データリスト
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void createRemainingNumberFromGrantRemaining(
			List<SpecialLeaveGrantRemaining> remainingDataList, boolean afterGrantAtr){
		
		remainingNumberInfo.createRemainingNumberFromGrantRemaining(remainingDataList, afterGrantAtr);
		
		
		
		// 特休付与残数データから残数を作成
		this.remainingNumberInfo.createRemainingNumberFromGrantRemaining(remainingDataList);
		
		// 「付与後フラグ」をチェック
		if (afterGrantAtr){
			
			// 残数付与後　←　残数
			this.remainingNumberAfterGrant = Optional.of(this.remainingNumberInfo.clone());
		}
		else {
			
			// 残数付与前　←　残数
			this.remainingNumberBeforeGrant = this.remainingNumberInfo.clone();
		}
	}
	
	/**
	 * 使用数を加算する
	 * @param days 日数
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void addUsedNumber(SpecialLeaveUseNumber usedNumber, boolean afterGrantAtr){
	
		this.usedNumberInfo.addUsedNumber(usedNumber, afterGrantAtr);
		

	}
	
//	/**
//	 * 付与前退避処理
//	 */
//	public void saveStateBeforeGrant(){
//		// 合計残数を付与前に退避する
//		this.usedNumberInfo.saveStateBeforeGrant();
//		this.remainingNumberInfo.saveStateBeforeGrant();
//	}
//	
//	/**
//	 * 付与後退避処理
//	 */
//	public void saveStateAfterGrant(){
//		// 合計残数を付与後に退避する
//		this.usedNumberInfo.saveStateAfterGrant();
//		this.remainingNumberInfo.saveStateAfterGrant();
//	}
	
}
