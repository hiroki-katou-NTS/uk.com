package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
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
	private ReserveLeaveRemainingNumber remainingNumber;
	/** 残数付与前 */
	private ReserveLeaveRemainingNumber remainingNumberBeforeGrant;
	/** 残数付与後 */
	private Optional<ReserveLeaveRemainingNumber> remainingNumberAfterGrant;
	/** 未消化数 */
	private ReserveLeaveUndigestedNumber undigestedNumber;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeave(){
		
		this.usedNumber = new ReserveLeaveUsedNumber();
		this.remainingNumber = new ReserveLeaveRemainingNumber();
		this.remainingNumberBeforeGrant = new ReserveLeaveRemainingNumber();
		this.remainingNumberAfterGrant = Optional.empty();
		this.undigestedNumber = new ReserveLeaveUndigestedNumber();
	}
	
	/**
	 * ファクトリー
	 * @param usedNumber 使用数
	 * @param remainingNumber 残数
	 * @param remainingNumberBeforeGrant 残数付与前
	 * @param remainingNumberAfterGrant 残数付与後
	 * @param undigestedNumber 未消化数
	 * @return 積立年休
	 */
	public static ReserveLeave of(
			ReserveLeaveUsedNumber usedNumber,
			ReserveLeaveRemainingNumber remainingNumber,
			ReserveLeaveRemainingNumber remainingNumberBeforeGrant,
			Optional<ReserveLeaveRemainingNumber> remainingNumberAfterGrant,
			ReserveLeaveUndigestedNumber undigestedNumber){

		ReserveLeave domain = new ReserveLeave();
		domain.usedNumber = usedNumber;
		domain.remainingNumber = remainingNumber;
		domain.remainingNumberBeforeGrant = remainingNumberBeforeGrant;
		domain.remainingNumberAfterGrant = remainingNumberAfterGrant;
		domain.undigestedNumber = undigestedNumber;
		return domain;
	}
	
	@Override
	public ReserveLeave clone() {
		ReserveLeave cloned = new ReserveLeave();
		try {
			cloned.usedNumber = this.usedNumber.clone();
			cloned.remainingNumber = this.remainingNumber.clone();
			cloned.remainingNumberBeforeGrant = this.remainingNumberBeforeGrant.clone();
			if (this.remainingNumberAfterGrant.isPresent()){
				cloned.remainingNumberAfterGrant = Optional.of(this.remainingNumberAfterGrant.get().clone());
			}
			cloned.undigestedNumber = this.undigestedNumber.clone();
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
	public void setValueFromRealReserveLeave(RealReserveLeave realReserveLeave){
		
		// 実年休から上書き
		this.usedNumber = realReserveLeave.getUsedNumber().clone();
		this.remainingNumber = realReserveLeave.getRemainingNumber().clone();
		this.remainingNumberBeforeGrant = realReserveLeave.getRemainingNumberBeforeGrant().clone();
		this.remainingNumberAfterGrant = Optional.empty();
		if (realReserveLeave.getRemainingNumberAfterGrant().isPresent()){
			this.remainingNumberAfterGrant = Optional.of(
					realReserveLeave.getRemainingNumberAfterGrant().get().clone());
		}
		
		// 残数からマイナスを削除
		if (this.remainingNumber.getTotalRemainingDays().lessThan(0.0)){
			// 積立年休．使用数からマイナス分を引く
			double minusDays = this.remainingNumber.getTotalRemainingDays().v();
			double useDays = this.usedNumber.getUsedDays().v();
			useDays += minusDays;
			if (useDays < 0.0) useDays = 0.0;
			this.usedNumber.setUsedDays(new ReserveLeaveUsedDayNumber(useDays));
			// 残数．明細．日数　←　0
			this.remainingNumber.setDaysOfAllDetail(0.0);
			// 残数．合計残日数　←　0
			this.remainingNumber.setTotalRemainingDays(new ReserveLeaveRemainingDayNumber(0.0));
		}

		// 残数付与前からマイナスを削除
		if (this.remainingNumberBeforeGrant.getTotalRemainingDays().lessThan(0.0)){
			// 積立年休．使用数（付与前）からマイナス分を引く
			double minusDays = this.remainingNumberBeforeGrant.getTotalRemainingDays().v();
			double useDays = this.usedNumber.getUsedDaysBeforeGrant().v();
			useDays += minusDays;
			if (useDays < 0.0) useDays = 0.0;
			this.usedNumber.setUsedDaysBeforeGrant(new ReserveLeaveUsedDayNumber(useDays));
			// 残数付与前．明細．日数　←　0
			this.remainingNumberBeforeGrant.setDaysOfAllDetail(0.0);
			// 残数．合計残日数　←　0
			this.remainingNumberBeforeGrant.setTotalRemainingDays(new ReserveLeaveRemainingDayNumber(0.0));
		}
		
		if (!this.remainingNumberAfterGrant.isPresent()) return;
		if (!this.usedNumber.getUsedDaysAfterGrant().isPresent()) return;
		
		// 残数付与後からマイナスを削除
		val remainingNumberAfterGrantValue = this.remainingNumberAfterGrant.get();
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
}
