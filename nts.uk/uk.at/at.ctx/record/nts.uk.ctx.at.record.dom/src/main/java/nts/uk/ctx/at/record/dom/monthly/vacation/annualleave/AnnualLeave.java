package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;

/**
 * 年休
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AnnualLeave {

	/** 使用数 */
	private AnnualLeaveUsedNumber usedNumber;
	/** 残数 */
	private AnnualLeaveRemainingNumber remainingNumber;
	/** 残数付与前 */
	private AnnualLeaveRemainingNumber remainingNumberBeforeGrant;
	/** 残数付与後 */
	private Optional<AnnualLeaveRemainingNumber> remainingNumberAfterGrant;
	/** 未消化数 */
	private AnnualLeaveUndigestedNumber undigestedNumber;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeave(){
		
		this.usedNumber = new AnnualLeaveUsedNumber();
		this.remainingNumber = new AnnualLeaveRemainingNumber();
		this.remainingNumberBeforeGrant = new AnnualLeaveRemainingNumber();
		this.remainingNumberAfterGrant = Optional.empty();
		this.undigestedNumber = new AnnualLeaveUndigestedNumber();
	}
	
	/**
	 * ファクトリー
	 * @param usedNumber 使用数
	 * @param remainingNumber 残数
	 * @param remainingNumberBeforeGrant 残数付与前
	 * @param remainingNumberAfterGrant 残数付与後
	 * @param undigestedNumber 未消化数
	 * @return 年休
	 */
	public static AnnualLeave of(
			AnnualLeaveUsedNumber usedNumber,
			AnnualLeaveRemainingNumber remainingNumber,
			AnnualLeaveRemainingNumber remainingNumberBeforeGrant,
			Optional<AnnualLeaveRemainingNumber> remainingNumberAfterGrant,
			AnnualLeaveUndigestedNumber undigestedNumber){

		AnnualLeave domain = new AnnualLeave();
		domain.usedNumber = usedNumber;
		domain.remainingNumber = remainingNumber;
		domain.remainingNumberBeforeGrant = remainingNumberBeforeGrant;
		domain.remainingNumberAfterGrant = remainingNumberAfterGrant;
		domain.undigestedNumber = undigestedNumber;
		return domain;
	}
	
	/**
	 * 実年休から値をセット　（年休（マイナスなし）を年休（マイナスあり）で上書き　＆　年休からマイナスを削除）
	 * @param realAnnualLeave 実年休
	 */
	public void setValueFromRealAnnualLeave(RealAnnualLeave realAnnualLeave){
		
		// 実年休から上書き
		this.usedNumber = realAnnualLeave.getUsedNumber();
		this.remainingNumber = realAnnualLeave.getRemainingNumber();
		this.remainingNumberBeforeGrant = realAnnualLeave.getRemainingNumberBeforeGrant();
		this.remainingNumberAfterGrant = realAnnualLeave.getRemainingNumberAfterGrant();
		
		// 残数からマイナスを削除
		if (this.remainingNumber.getTotalRemainingDays().lessThan(0.0)){
			// 年休．使用数からマイナス分を引く
			double minusDays = this.remainingNumber.getTotalRemainingDays().v();
			double useDays = this.usedNumber.getUsedDays().getUsedDays().v();
			useDays += minusDays;
			if (useDays < 0.0) useDays = 0.0;
			this.usedNumber.getUsedDays().setUsedDays(new AnnualLeaveUsedDayNumber(useDays));
			// 残数．明細．日数　←　0
			this.remainingNumber.setDaysOfAllDetail(0.0);
			// 残数．合計残日数　←　0
			this.remainingNumber.setTotalRemainingDays(new AnnualLeaveRemainingDayNumber(0.0));
		}

		// 残数付与前からマイナスを削除
		if (this.remainingNumberBeforeGrant.getTotalRemainingDays().lessThan(0.0)){
			// 年休．使用数からマイナス分を引く
			double minusDays = this.remainingNumberBeforeGrant.getTotalRemainingDays().v();
			double useDays = this.usedNumber.getUsedDays().getUsedDays().v();
			useDays += minusDays;
			if (useDays < 0.0) useDays = 0.0;
			this.usedNumber.getUsedDays().setUsedDays(new AnnualLeaveUsedDayNumber(useDays));
			// 残数付与前．明細．日数　←　0
			this.remainingNumberBeforeGrant.setDaysOfAllDetail(0.0);
			// 残数．合計残日数　←　0
			this.remainingNumber.setTotalRemainingDays(new AnnualLeaveRemainingDayNumber(0.0));
		}
		
		if (!this.remainingNumberAfterGrant.isPresent()) return;
		
		// 残数付与後からマイナスを削除
		val remainingNumberAfterGrantValue = this.remainingNumberAfterGrant.get();
		if (remainingNumberAfterGrantValue.getTotalRemainingDays().lessThan(0.0)){
			// 年休．使用数からマイナス分を引く
			double minusDays = remainingNumberAfterGrantValue.getTotalRemainingDays().v();
			double useDays = this.usedNumber.getUsedDays().getUsedDays().v();
			useDays += minusDays;
			if (useDays < 0.0) useDays = 0.0;
			this.usedNumber.getUsedDays().setUsedDays(new AnnualLeaveUsedDayNumber(useDays));
			// 残数付与前．明細．日数　←　0
			remainingNumberAfterGrantValue.setDaysOfAllDetail(0.0);
			// 残数．合計残日数　←　0
			this.remainingNumber.setTotalRemainingDays(new AnnualLeaveRemainingDayNumber(0.0));
		}
	}
}
