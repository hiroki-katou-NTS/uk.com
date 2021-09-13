package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.GrantPeriodAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;

/**
 * 特別休暇残数情報
 * @author masaaki_jinno
 *
 */
@Getter
@AllArgsConstructor
public class SpecialLeaveRemainingNumberInfo implements Cloneable {

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
			SpecialLeaveRemainingNumber remainingNumberBeforeGrant,
			Optional<SpecialLeaveRemainingNumber> remainingNumberAfterGrantOpt
			){

		SpecialLeaveRemainingNumberInfo domain = new SpecialLeaveRemainingNumberInfo();
		domain.remainingNumberBeforeGrant = remainingNumberBeforeGrant;
		domain.remainingNumberAfterGrantOpt= remainingNumberAfterGrantOpt;
		return domain;
	}

	/** コンストラクタ  */
	public SpecialLeaveRemainingNumberInfo(){
		this.remainingNumberBeforeGrant = new SpecialLeaveRemainingNumber();
		this.remainingNumberAfterGrantOpt = Optional.empty();
	}

	/**
	 * 特別休暇付与残数データから実特別休暇の特別休暇残数を作成
	 * @param remainingDataList 特休付与残数データリスト
	 * @param grantPeriodAtr 付与前付与後
	 */
	public void createRemainingNumberFromGrantRemaining(
			List<SpecialLeaveGrantRemainingData> remainingDataList,
			GrantPeriodAtr grantPeriodAtr){

		// 特別休暇付与残数データから特別休暇残数を作成
		SpecialLeaveRemainingNumber remainingNumber = new SpecialLeaveRemainingNumber();
		remainingNumber.createRemainingNumberFromGrantRemaining(remainingDataList);

		// 「付与後フラグ」をチェック
		if (grantPeriodAtr.equals(GrantPeriodAtr.AFTER_GRANT)){
			// 残数付与後　←　残数
			this.remainingNumberAfterGrantOpt = Optional.of(remainingNumber);
		}
		else {
			// 残数付与前　←　残数
			this.remainingNumberBeforeGrant = remainingNumber;
		}
	}

	/**
	 * クローン
	 */
	public SpecialLeaveRemainingNumberInfo clone() {
		SpecialLeaveRemainingNumberInfo cloned = new SpecialLeaveRemainingNumberInfo();

		if ( remainingNumberBeforeGrant != null ){
			cloned.remainingNumberBeforeGrant = this.remainingNumberBeforeGrant.clone();
		}

		if (this.remainingNumberAfterGrantOpt.isPresent()){
			cloned.remainingNumberAfterGrantOpt = Optional.of(this.remainingNumberAfterGrantOpt.get().clone());
		}

		return cloned;
	}

	/**
	 * クリア
	 */
	public void clear(){
		remainingNumberBeforeGrant.clear();
		remainingNumberAfterGrantOpt = Optional.empty();
	}

	/**
	 * 明細をクリア。（要素数を０にする）
	 */
	public void clearDetails(){
		remainingNumberBeforeGrant.clearDetails();
		if ( remainingNumberAfterGrantOpt.isPresent() ){
			remainingNumberAfterGrantOpt.get().clearDetails();
		}
	}

	/**
	 * 合計を取得
	 * @return
	 */
	public SpecialLeaveRemainingNumber getRemainingNumber(){
		if(this.remainingNumberAfterGrantOpt.isPresent()) {
			return this.remainingNumberAfterGrantOpt.get();
		}
		return this.remainingNumberBeforeGrant;
	}

}

