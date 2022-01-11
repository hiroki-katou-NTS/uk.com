package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;

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

	/**
	 * コンストラクタ
	 */
	public ReserveLeave(){

		this.usedNumber = new ReserveLeaveUsedNumber();
		this.remainingNumberInfo = new ReserveLeaveRemainingInfo();
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
		return domain;
	}

	@Override
	public ReserveLeave clone() {
		ReserveLeave cloned = new ReserveLeave();
		try {
			cloned.usedNumber = this.usedNumber.clone();
			cloned.remainingNumberInfo = this.remainingNumberInfo.clone();
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

		if (!this.getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
				|| !this.usedNumber.getUsedDaysAfterGrant().isPresent()) {
			this.usedNumber
					.setUsedDays(new ReserveLeaveUsedDayNumber(this.getUsedNumber().getUsedDaysBeforeGrant().v()));
			return;
		}

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
		
		//合計使用数を求める
		this.usedNumber.setUsedDays(new ReserveLeaveUsedDayNumber(this.getUsedNumber().getUsedDaysBeforeGrant().v()
				+ this.getUsedNumber().getUsedDaysAfterGrant().map(x -> x.v()).orElse(0.0)));
	}
	
	public void createRemainingNumberFromGrantRemaining(
			List<ReserveLeaveGrantRemainingData> remainingDataList, GrantBeforeAfterAtr grantPeriodAtr){

		remainingNumberInfo.createRemainingNumberFromGrantRemaining(remainingDataList, grantPeriodAtr);

	}

}
