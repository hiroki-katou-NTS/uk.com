package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;

/**
 * 年休使用日数
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AnnualLeaveUsedDays implements Cloneable, Serializable {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/** 使用日数 */
	private AnnualLeaveUsedDayNumber usedDayNumber;
//	/** 使用日数付与前 */
//	private AnnualLeaveUsedDayNumber usedDaysBeforeGrant;
//	/** 使用日数付与後 */
//	private Optional<AnnualLeaveUsedDayNumber> usedDaysAfterGrant;

	/**
	 * コンストラクタ
	 */
	public AnnualLeaveUsedDays(){
		
		this.usedDayNumber = new AnnualLeaveUsedDayNumber(0.0);
//		this.usedDaysBeforeGrant = new AnnualLeaveUsedDayNumber(0.0);
//		this.usedDaysAfterGrant = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param usedDays 使用日数
	 * @return 年休使用日数
	 */
	public static AnnualLeaveUsedDays of(
			AnnualLeaveUsedDayNumber usedDays){
		
		AnnualLeaveUsedDays domain = new AnnualLeaveUsedDays();
		domain.usedDayNumber = usedDays;
		return domain;
	}
	
	@Override
	protected AnnualLeaveUsedDays clone() {
		AnnualLeaveUsedDays cloned = new AnnualLeaveUsedDays();
		try {
			cloned.usedDayNumber = new AnnualLeaveUsedDayNumber(this.usedDayNumber.v());
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveUsedDays clone error.");
		}
		return cloned;
	}
	
	/**
	 * 日数を使用日数に加算する
	 * @param days 日数
	 */
	public void addUsedDays(double days){
		this.usedDayNumber = new AnnualLeaveUsedDayNumber(this.usedDayNumber.v() + days);
	}
	
//	/**
//	 * 日数を使用日数付与前に加算する
//	 * @param days 日数
//	 */
//	public void addUsedDaysBeforeGrant(double days){
//		this.usedDaysBeforeGrant = new AnnualLeaveUsedDayNumber(this.usedDaysBeforeGrant.v() + days);
//	}
//	
//	/**
//	 * 日数を使用日数付与後に加算する
//	 * @param days 日数
//	 */
//	public void addUsedDaysAfterGrant(double days){
//		if (!this.usedDaysAfterGrant.isPresent()){
//			this.usedDaysAfterGrant = Optional.of(new AnnualLeaveUsedDayNumber(0.0));
//		}
//		this.usedDaysAfterGrant = Optional.of(new AnnualLeaveUsedDayNumber(
//				this.usedDaysAfterGrant.get().v() + days));
//	}
}
