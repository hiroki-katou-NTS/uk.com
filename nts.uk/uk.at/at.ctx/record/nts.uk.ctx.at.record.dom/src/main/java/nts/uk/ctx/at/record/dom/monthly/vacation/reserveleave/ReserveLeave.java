package nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveGrantRemaining;
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
	private ReserveLeaveRemainingNumberInfo remainingNumberInfo;
//	/** 残数付与前 */
//	private ReserveLeaveRemainingNumber remainingNumberBeforeGrant;
//	/** 残数付与後 */
//	private Optional<ReserveLeaveRemainingNumber> remainingNumberAfterGrant;
//	/** 未消化数 */
//	private ReserveLeaveUndigestedNumber undigestedNumber;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeave(){
		
		this.usedNumber = new ReserveLeaveUsedNumber();
		this.remainingNumberInfo = new ReserveLeaveRemainingNumberInfo();
//		this.remainingNumberBeforeGrant = new ReserveLeaveRemainingNumber();
//		this.remainingNumberAfterGrant = Optional.empty();
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
			ReserveLeaveRemainingNumberInfo remainingNumberInfo){

		ReserveLeave domain = new ReserveLeave();
		domain.usedNumber = usedNumber;
		domain.remainingNumberInfo = remainingNumberInfo;
//		domain.remainingNumberBeforeGrant = remainingNumberBeforeGrant;
//		domain.remainingNumberAfterGrant = remainingNumberAfterGrant;
//		domain.undigestedNumber = undigestedNumber;
		return domain;
	}
	
	@Override
	public ReserveLeave clone() {
		ReserveLeave cloned = new ReserveLeave();
		try {
			cloned.usedNumber = this.usedNumber.clone();
			cloned.remainingNumberInfo = this.remainingNumberInfo.clone();
//			cloned.remainingNumberBeforeGrant = this.remainingNumberBeforeGrant.clone();
//			if (this.remainingNumberAfterGrant.isPresent()){
//				cloned.remainingNumberAfterGrant = Optional.of(this.remainingNumberAfterGrant.get().clone());
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
//		this.remainingNumberBeforeGrant = realReserveLeave.getRemainingNumberBeforeGrant().clone();
//		this.remainingNumberAfterGrant = Optional.empty();
//		if (realReserveLeave.getRemainingNumberAfterGrant().isPresent()){
//			this.remainingNumberAfterGrant = Optional.of(
//					realReserveLeave.getRemainingNumberAfterGrant().get().clone());
//		}
		
		// 残数からマイナスを削除
		if (this.remainingNumberInfo.getTotalRemaining().getTotalRemainingDays().lessThan(0.0)){
			// 積立年休．使用数からマイナス分を引く
			double minusDays = this.remainingNumberInfo.getTotalRemaining().getTotalRemainingDays().v();
			double useDays = this.usedNumber.getUsedDays().v();
			useDays += minusDays;
			if (useDays < 0.0) useDays = 0.0;
			this.usedNumber.setUsedDays(new ReserveLeaveUsedDayNumber(useDays));
			// 残数．明細．日数　←　0
			this.remainingNumberInfo.getTotalRemaining().setDaysOfAllDetail(0.0);
			// 残数．合計残日数　←　0
			this.remainingNumberInfo.getTotalRemaining().setTotalRemainingDays(new ReserveLeaveRemainingDayNumber(0.0));
		}

		// 残数付与前からマイナスを削除
//		if (this.remainingNumberBeforeGrant.getTotalRemainingDays().lessThan(0.0)){
//			// 積立年休．使用数（付与前）からマイナス分を引く
//			double minusDays = this.remainingNumberBeforeGrant.getTotalRemainingDays().v();
//			double useDays = this.usedNumber.getUsedDaysBeforeGrant().v();
//			useDays += minusDays;
//			if (useDays < 0.0) useDays = 0.0;
//			this.usedNumber.setUsedDaysBeforeGrant(new ReserveLeaveUsedDayNumber(useDays));
//			// 残数付与前．明細．日数　←　0
//			this.remainingNumberBeforeGrant.setDaysOfAllDetail(0.0);
//			// 残数．合計残日数　←　0
//			this.remainingNumberBeforeGrant.setTotalRemainingDays(new ReserveLeaveRemainingDayNumber(0.0));
//		}
		if (this.getRemainingNumberInfo().getBeforeGrant().getTotalRemainingDays().lessThan(0.0)){
			// 積立年休．使用数（付与前）からマイナス分を引く
			double minusDays = this.remainingNumberInfo.getBeforeGrant().getTotalRemainingDays().v();
			double useDays = this.usedNumber.getUsedDaysBeforeGrant().v();
			useDays += minusDays;
			if (useDays < 0.0) useDays = 0.0;
			this.usedNumber.setUsedDaysBeforeGrant(new ReserveLeaveUsedDayNumber(useDays));
			// 残数付与前．明細．日数　←　0
			this.remainingNumberInfo.getBeforeGrant().setDaysOfAllDetail(0.0);
			// 残数．合計残日数　←　0
			this.remainingNumberInfo.getBeforeGrant().setTotalRemainingDays(new ReserveLeaveRemainingDayNumber(0.0));
		}
		
//		if (!this.remainingNumberAfterGrant.isPresent()) return;
//		if (!this.usedNumber.getUsedDaysAfterGrant().isPresent()) return;
//		
//		// 残数付与後からマイナスを削除
//		val remainingNumberAfterGrantValue = this.remainingNumberAfterGrant.get();
//		val usedDaysAfterGrant = this.usedNumber.getUsedDaysAfterGrant().get();
//		if (remainingNumberAfterGrantValue.getTotalRemainingDays().lessThan(0.0)){
//			// 積立年休．使用数（付与後）からマイナス分を引く
//			double minusDays = remainingNumberAfterGrantValue.getTotalRemainingDays().v();
//			double useDays = usedDaysAfterGrant.v();
//			useDays += minusDays;
//			if (useDays < 0.0) useDays = 0.0;
//			this.usedNumber.setUsedDaysAfterGrant(Optional.of(new ReserveLeaveUsedDayNumber(useDays)));
//			// 残数付与前．明細．日数　←　0
//			remainingNumberAfterGrantValue.setDaysOfAllDetail(0.0);
//			// 残数．合計残日数　←　0
//			remainingNumberAfterGrantValue.setTotalRemainingDays(new ReserveLeaveRemainingDayNumber(0.0));
//		}
		if (!this.getRemainingNumberInfo().getAfterGrant().isPresent()) return;
		if (!this.usedNumber.getUsedDaysAfterGrant().isPresent()) return;
		
		// 残数付与後からマイナスを削除
		val remainingNumberAfterGrantValue = this.getRemainingNumberInfo().getAfterGrant().get();
		val usedDaysAfterGrant = this.usedNumber.getUsedDaysAfterGrant().get();
		if (remainingNumberAfterGrantValue.getTotalRemainingDays().lessThan(0.0)){
			// 積立年休．使用数（付与後）からマイナス分を引く
			double minusDays = remainingNumberAfterGrantValue.getTotalRemainingDays().v();
			double useDays = usedDaysAfterGrant.v();
			useDays += minusDays;
			if (useDays < 0.0) useDays = 0.0;
			this.usedNumber.setUsedDaysAfterGrant(Optional.of(new ReserveLeaveUsedDayNumber(useDays)));
			// 残数付与前．明細．日数　←　0
			remainingNumberAfterGrantValue.setDaysOfAllDetail(0.0);
			// 残数．合計残日数　←　0
			remainingNumberAfterGrantValue.setTotalRemainingDays(new ReserveLeaveRemainingDayNumber(0.0));
		}
	}
	
	/**
	 * 積立年休付与残数データから積立年休残数を作成
	 * @param remainingDataList 積立年休付与残数データリスト
	 * @param afterGrantAtr 付与後フラグ
	 */
	public void createRemainingNumberFromGrantRemaining(
			List<ReserveLeaveGrantRemaining> remainingDataList, boolean afterGrantAtr){
		
		// 積立年休付与残数データから残数を作成
//		this.remainingNumberInfo.createRemainingNumberFromGrantRemaining(remainingDataList);
		this.remainingNumberInfo.getTotalRemaining().createRemainingNumberFromGrantRemaining(remainingDataList);
		
		// 「付与後フラグ」をチェック
		if (afterGrantAtr){
			// 残数付与後　←　残数
			//this.remainingNumberAfterGrant = Optional.of(this.remainingNumber.clone());
			saveStateAfterGrant();
		}
		else {
			// 残数付与前　←　残数
			//this.remainingNumberBeforeGrant = this.remainingNumber.clone();
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
