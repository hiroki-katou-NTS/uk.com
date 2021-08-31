package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.GrantPeriodAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;

/**
 * 積立年休
 * @author shuichu_ishida
 */
@Getter
@Setter
public class ReserveLeave implements Cloneable {

	/** 使用数 */
	private ReserveLeaveUsedNumber usedNumber;
	/** 残数 */
	private ReserveLeaveRemainingInfo remainingNumberInfo;

//	/** 残数付与前 */
//	private ReserveLeaveRemainingNumber remainingNumberInfoBeforeGrant;
//	/** 残数付与後 */
//	private Optional<ReserveLeaveRemainingNumber> remainingNumberInfoAfterGrant;
//	/** 未消化数 */
//	private ReserveLeaveUndigestedNumber undigestedNumber;

	/**
	 * コンストラクタ
	 */
	public ReserveLeave(){

		this.usedNumber = new ReserveLeaveUsedNumber();
		this.remainingNumberInfo = new ReserveLeaveRemainingInfo();
//		this.remainingNumberInfoBeforeGrant = new ReserveLeaveRemainingNumber();
//		this.remainingNumberInfoAfterGrant = Optional.empty();
//		this.undigestedNumber = new ReserveLeaveUndigestedNumber();
	}

	/**
	 * ファクトリー
	 * @param usedNumber 使用数
	 * @param remainingNumberInfo 残数
	 * @return 積立年休
	 */
	public static ReserveLeave of(
			ReserveLeaveUsedNumber usedNumber,
			ReserveLeaveRemainingInfo remainingInfo){

		ReserveLeave domain = new ReserveLeave();
		domain.usedNumber = usedNumber;
		domain.remainingNumberInfo = remainingInfo;
//		domain.remainingNumberInfoBeforeGrant = remainingNumberInfoBeforeGrant;
//		domain.remainingNumberInfoAfterGrant = remainingNumberInfoAfterGrant;
//		domain.undigestedNumber = undigestedNumber;
		return domain;
	}

	@Override
	public ReserveLeave clone() {
		ReserveLeave cloned = new ReserveLeave();
		try {
			cloned.usedNumber = this.usedNumber.clone();
			cloned.remainingNumberInfo = this.remainingNumberInfo.clone();
//			cloned.remainingNumberInfoBeforeGrant = this.remainingNumberInfoBeforeGrant.clone();
//			if (this.remainingNumberInfoAfterGrant.isPresent()){
//				cloned.remainingNumberInfoAfterGrant = Optional.of(this.remainingNumberInfoAfterGrant.get().clone());
//			}
//			cloned.undigestedNumber = this.undigestedNumber.clone();
		}
		catch (Exception e){
			throw new RuntimeException("ReserveLeave clone error.");
		}
		return cloned;
	}

	/**
	 * 実積立年休から値をセット　（積立年休（マイナスなし）を積立年休（マイナスあり）で上書き　＆　積立年休からマイナスを削除）
	 * @param realReserveLeave 実積立年休
	 */
	public void setValueFromRealReserveLeave(ReserveLeave realReserveLeave){

		// 実年休から上書き
		this.usedNumber = realReserveLeave.getUsedNumber().clone();
		this.remainingNumberInfo = realReserveLeave.getRemainingNumberInfo().clone();

		// 残数からマイナスを削除
		if (this.remainingNumberInfo.getRemainingNumber().getTotalRemainingDays().lessThan(0.0)){
			// 積立年休．使用数からマイナス分を引く
			double minusDays = this.remainingNumberInfo.getRemainingNumber().getTotalRemainingDays().v();
			double useDays = this.usedNumber.getUsedDays().v();
			useDays += minusDays;
			if (useDays < 0.0) useDays = 0.0;
			this.usedNumber.setUsedDays(new ReserveLeaveUsedDayNumber(useDays));
			// 残数．明細．日数　←　0
			this.remainingNumberInfo.getRemainingNumber().setDaysOfAllDetail(0.0);
			// 残数．合計残日数　←　0
			this.remainingNumberInfo.getRemainingNumber().setTotalRemainingDays(new ReserveLeaveRemainingDayNumber(0.0));
		}

		// 残数付与前からマイナスを削除
		if (this.getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTotalRemainingDays().lessThan(0.0)){
			// 積立年休．使用数（付与前）からマイナス分を引く
			double minusDays = this.getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTotalRemainingDays().v();
			double useDays = this.usedNumber.getUsedDaysBeforeGrant().v();
			useDays += minusDays;
			if (useDays < 0.0) useDays = 0.0;
			this.usedNumber.setUsedDaysBeforeGrant(new ReserveLeaveUsedDayNumber(useDays));
			// 残数付与前．明細．日数　←　0
			this.getRemainingNumberInfo().getRemainingNumberBeforeGrant().setDaysOfAllDetail(0.0);
			// 残数．合計残日数　←　0
			this.getRemainingNumberInfo().getRemainingNumberBeforeGrant().setTotalRemainingDays(new ReserveLeaveRemainingDayNumber(0.0));
		}
		if (this.getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTotalRemainingDays().lessThan(0.0)){
			// 積立年休．使用数（付与前）からマイナス分を引く
			double minusDays = this.getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTotalRemainingDays().v();
			double useDays = this.usedNumber.getUsedDaysBeforeGrant().v();
			useDays += minusDays;
			if (useDays < 0.0) useDays = 0.0;
			this.usedNumber.setUsedDaysBeforeGrant(new ReserveLeaveUsedDayNumber(useDays));
			// 残数付与前．明細．日数　←　0
			this.getRemainingNumberInfo().getRemainingNumberBeforeGrant().setDaysOfAllDetail(0.0);
			// 残数．合計残日数　←　0
			this.getRemainingNumberInfo().getRemainingNumberBeforeGrant().setTotalRemainingDays(new ReserveLeaveRemainingDayNumber(0.0));
		}

		if (!this.getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()) return;
		if (!this.usedNumber.getUsedDaysAfterGrant().isPresent()) return;

		// 残数付与後からマイナスを削除
		val remainingNumberInfoAfterGrantValue = this.getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get();
		val usedDaysAfterGrant = this.usedNumber.getUsedDaysAfterGrant().get();
		if (remainingNumberInfoAfterGrantValue.getTotalRemainingDays().lessThan(0.0)){
			// 積立年休．使用数（付与後）からマイナス分を引く
			double minusDays = remainingNumberInfoAfterGrantValue.getTotalRemainingDays().v();
			double useDays = usedDaysAfterGrant.v();
			useDays += minusDays;
			if (useDays < 0.0) useDays = 0.0;
			this.usedNumber.setUsedDaysAfterGrant(Optional.of(new ReserveLeaveUsedDayNumber(useDays)));
			// 残数付与前．明細．日数　←　0
			remainingNumberInfoAfterGrantValue.setDaysOfAllDetail(0.0);
			// 残数．合計残日数　←　0
			remainingNumberInfoAfterGrantValue.setTotalRemainingDays(new ReserveLeaveRemainingDayNumber(0.0));
		}
	}

	/**
	 * 積立年休付与残数データから積立年休残数を作成
	 * @param remainingDataList 積立年休付与残数データリスト
	 * @param grantPeriodAtr 付与前付与後
	 */
	public void createRemainingNumberFromGrantRemaining(
			List<ReserveLeaveGrantRemainingData> remainingDataList, GrantPeriodAtr grantPeriodAtr){

		// 積立年休付与残数データから残数を作成
//		this.remainingNumberInfo.createRemainingNumberFromGrantRemaining(remainingDataList);
		this.remainingNumberInfo.getRemainingNumber().createRemainingNumberFromGrantRemaining(remainingDataList);

		// 「付与後フラグ」をチェック
		if (grantPeriodAtr.equals(GrantPeriodAtr.AFTER_GRANT)){
			// 残数付与後　←　残数
			//this.remainingNumberInfoAfterGrant = Optional.of(this.remainingNumberInfo.clone());
			saveStateAfterGrant();
		}
		else {
			// 残数付与前　←　残数
			//this.remainingNumberInfoBeforeGrant = this.remainingNumberInfo.clone();
			saveStateBeforeGrant();
		}
	}

	/**
	 * 付与前退避処理
	 */
	public void saveStateBeforeGrant(){
		// 合計残数を付与前に退避する
		this.usedNumber.saveStateBeforeGrant();
		this.remainingNumberInfo.saveStateBeforeGrant();
	}

	/**
	 * 付与後退避処理
	 */
	public void saveStateAfterGrant(){
		// 合計残数を付与後に退避する
		this.usedNumber.saveStateAfterGrant();
		this.remainingNumberInfo.saveStateAfterGrant();
	}

}
