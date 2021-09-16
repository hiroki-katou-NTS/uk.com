package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;

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

	/**
	 * コンストラクタ
	 */
	public AnnualLeave(){

		this.usedNumberInfo = new AnnualLeaveUsedInfo();
		this.remainingNumberInfo = new AnnualLeaveRemainingNumberInfo();
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
	 * @param grantPeriodAtr 付与前付与後
	 */
	public void createRemainingNumberFromGrantRemaining(
			List<AnnualLeaveGrantRemainingData> remainingDataList, GrantBeforeAfterAtr grantPeriodAtr){

		remainingNumberInfo.createRemainingNumberFromGrantRemaining(remainingDataList, grantPeriodAtr);

	}

	/**
	 * 使用数を加算する
	 * @param usedNumber 使用数
	 * @param grantPeriodAtr 付与前付与後
	 */
	public void addUsedNumber(AnnualLeaveUsedNumber usedNumber, GrantBeforeAfterAtr grantPeriodAtr){

		this.usedNumberInfo.addUsedNumber(usedNumber, grantPeriodAtr);

	}

}
