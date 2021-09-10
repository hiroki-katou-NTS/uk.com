package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.GrantPeriodAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveRemainingNumber;

/**
 * 積立年休残情報
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class ReserveLeaveRemainingInfo {

	/** 付与前 */
	private ReserveLeaveRemainingNumber remainingNumberBeforeGrant;

	/** 付与後 */
	private Optional<ReserveLeaveRemainingNumber> remainingNumberAfterGrantOpt;

	/**
	 * ファクトリ
	 * @param remainingNumberBeforeGrant 付与前
	 * @param remainingNumberAfterGrantOpt 付与後
	 * @return
	 */
	public static ReserveLeaveRemainingInfo of(
			ReserveLeaveRemainingNumber remainingNumberBeforeGrant,
			Optional<ReserveLeaveRemainingNumber> remainingNumberAfterGrantOpt
			){

		ReserveLeaveRemainingInfo domain = new ReserveLeaveRemainingInfo();
		domain.remainingNumberBeforeGrant = remainingNumberBeforeGrant;
		domain.remainingNumberAfterGrantOpt= remainingNumberAfterGrantOpt;
		return domain;
	}

	/** コンストラクタ  */
	public ReserveLeaveRemainingInfo(){
		this.remainingNumberBeforeGrant = new ReserveLeaveRemainingNumber();
		this.remainingNumberAfterGrantOpt = Optional.empty();
	}

	/**
	 * 積休付与残数データから実積休休暇の特別休暇残数を作成
	 * @param remainingDataList 積休付与残数データリスト
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void createRemainingNumberFromGrantRemaining(
			List<ReserveLeaveGrantRemainingData> remainingDataList,
			GrantPeriodAtr grantPeriodAtr){

		// 積休付与残数データから残数を作成
		ReserveLeaveRemainingNumber remainingNumber = new ReserveLeaveRemainingNumber();
		remainingNumber.createRemainingNumberFromGrantRemaining(remainingDataList);

		// 「付与後フラグ」をチェック
		if (grantPeriodAtr.equals(GrantPeriodAtr.AFTER_GRANT)){
			// 残数付与後　←　残数
			this.remainingNumberAfterGrantOpt = Optional.of(remainingNumber.clone());
		}
		else {
			// 残数付与前　←　残数
			this.remainingNumberBeforeGrant = remainingNumber.clone();
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
	 * 残数を取得
	 * @return
	 */
	public ReserveLeaveRemainingNumber getRemainingNumber(){
		if(this.remainingNumberAfterGrantOpt.isPresent()) {
			return this.remainingNumberAfterGrantOpt.get();
		}
		return this.remainingNumberBeforeGrant;
	}

}
