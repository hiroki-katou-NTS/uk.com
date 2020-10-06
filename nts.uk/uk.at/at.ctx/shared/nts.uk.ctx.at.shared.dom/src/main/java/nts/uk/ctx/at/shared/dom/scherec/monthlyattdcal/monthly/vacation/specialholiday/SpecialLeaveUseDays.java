package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUsedDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;

/**
 * 特別休暇使用日数
 * @author do_dt
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class SpecialLeaveUseDays extends DomainObject {
	
	/**
	 * 使用日数
	 */
	private SpecialLeaveRemainDay useDays;
//	/**
//	 * 使用日数付与前
//	 */
//	private SpecialLeaveRemainDay beforeUseGrantDays;
//	/**
//	 * 使用日数付与後
//	 */
//	private Optional<SpecialLeaveRemainDay> afterUseGrantDays;
	
	/**
	 * メンバ変数をクリア
	 */
	public void clear(){
		useDays = new SpecialLeaveRemainDay(0.0);
//		beforeUseGrantDays = new SpecialLeaveRemainDay(0.0);
//		afterUseGrantDays = Optional.empty();
	}

	/**
	 * コンストラクタ
	 */
	public SpecialLeaveUseDays(){
		
		this.useDays = new SpecialLeaveRemainDay(0.0);
//		this.usedDaysBeforeGrant = new AnnualLeaveUsedDayNumber(0.0);
//		this.usedDaysAfterGrant = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param usedDays 使用日数
	 * @return 特休使用日数
	 */
	public static SpecialLeaveUseDays of(
			SpecialLeaveRemainDay usedDays){
		
		SpecialLeaveUseDays domain = new SpecialLeaveUseDays();
		domain.useDays = usedDays;
		return domain;
	}
	
	@Override
	protected SpecialLeaveUseDays clone() {
		SpecialLeaveUseDays cloned = new SpecialLeaveUseDays();
		try {
			cloned.useDays = new SpecialLeaveRemainDay(this.useDays.v());
		}
		catch (Exception e){
			throw new RuntimeException("SpecialLeaveUseDays clone error.");
		}
		return cloned;
	}
	
	/**
	 * 日数を使用日数に加算する
	 * @param days 日数
	 */
	public void addUseDays(SpecialLeaveUseDays days){
		this.useDays = new SpecialLeaveRemainDay(this.useDays.v() + days.getUseDays().v());
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

