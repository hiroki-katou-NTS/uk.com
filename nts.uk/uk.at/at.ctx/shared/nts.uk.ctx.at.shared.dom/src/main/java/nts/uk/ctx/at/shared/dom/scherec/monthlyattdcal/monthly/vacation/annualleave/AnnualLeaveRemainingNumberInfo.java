package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.GrantPeriodAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainingNumber;

/**
 * 年休残数情報
 * @author masaaki_jinno
 *
 */
@Getter
@AllArgsConstructor
public class AnnualLeaveRemainingNumberInfo implements Cloneable {

	/** 付与前 */
	private AnnualLeaveRemainingNumber remainingNumberBeforeGrant;

	/** 付与後 */
	private Optional<AnnualLeaveRemainingNumber> remainingNumberAfterGrantOpt;

	/**
	 * ファクトリ
	 * @param remainingNumberBeforeGrant 付与前
	 * @param remainingNumberAfterGrantOpt 付与後
	 * @return
	 */
	public static AnnualLeaveRemainingNumberInfo of(
			AnnualLeaveRemainingNumber remainingNumberBeforeGrant,
			Optional<AnnualLeaveRemainingNumber> remainingNumberAfterGrantOpt
			){

		AnnualLeaveRemainingNumberInfo domain = new AnnualLeaveRemainingNumberInfo();
		domain.remainingNumberBeforeGrant = remainingNumberBeforeGrant;
		domain.remainingNumberAfterGrantOpt= remainingNumberAfterGrantOpt;
		return domain;
	}

	/** コンストラクタ  */
	public AnnualLeaveRemainingNumberInfo(){
		this.remainingNumberBeforeGrant = new AnnualLeaveRemainingNumber();
		this.remainingNumberAfterGrantOpt = Optional.empty();
	}

	/**
	 * 年休付与残数データから年休残数を作成
	 * @param remainingDataList 年休付与残数データリスト
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void createRemainingNumberFromGrantRemaining(
			List<AnnualLeaveGrantRemainingData> remainingDataList, GrantPeriodAtr grantPeriodAtr){

		// 年休付与残数データから残数を作成
		AnnualLeaveRemainingNumber remainingNumber = new AnnualLeaveRemainingNumber();
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
	public AnnualLeaveRemainingNumberInfo clone() {
		AnnualLeaveRemainingNumberInfo cloned = new AnnualLeaveRemainingNumberInfo();
		try {
			if ( remainingNumberBeforeGrant != null ){
				cloned.remainingNumberBeforeGrant = this.remainingNumberBeforeGrant.clone();
			}

			if (this.remainingNumberAfterGrantOpt.isPresent()){
				cloned.remainingNumberAfterGrantOpt = Optional.of(this.remainingNumberAfterGrantOpt.get().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveRemainingInfo clone error.");
		}
		return cloned;
	}

	/**
	 * 合計を取得
	 * @return
	 */
	public AnnualLeaveRemainingNumber getRemainingNumber(){
		if(this.remainingNumberAfterGrantOpt.isPresent()) {
			return this.remainingNumberAfterGrantOpt.get();
		}
		return this.remainingNumberBeforeGrant;
	}

}

