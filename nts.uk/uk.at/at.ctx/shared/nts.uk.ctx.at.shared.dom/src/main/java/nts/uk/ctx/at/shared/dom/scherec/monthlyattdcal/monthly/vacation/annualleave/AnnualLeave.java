package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;

/**
 * 年休
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class AnnualLeave implements Cloneable, Serializable {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/** 使用情報 */
	private AnnualLeaveUsedInfo usedNumberInfo;

	/** 残数情報 */
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
			List<AnnualLeaveGrantRemainingData> remainingDataList, boolean afterGrantAtr){

		remainingNumberInfo.createRemainingNumberFromGrantRemaining(remainingDataList, afterGrantAtr);

	}

	/**
	 * 使用数を加算する
	 * @param days 日数
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void addUsedNumber(AnnualLeaveUsedNumber usedNumber, boolean afterGrantAtr){

		this.usedNumberInfo.addUsedNumber(usedNumber, afterGrantAtr);

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
