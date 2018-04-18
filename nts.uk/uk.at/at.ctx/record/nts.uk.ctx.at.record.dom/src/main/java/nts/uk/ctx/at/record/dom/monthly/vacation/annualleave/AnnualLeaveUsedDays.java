package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;

/**
 * 年休使用日数
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveUsedDays {

	/** 使用日数 */
	private AnnualLeaveUsedDayNumber usedDays;
	/** 使用日数付与後 */
	private Optional<AnnualLeaveUsedDayNumber> usedDaysAfterGrant;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveUsedDays(){
		
		this.usedDays = new AnnualLeaveUsedDayNumber(0.0);
		this.usedDaysAfterGrant = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param usedDays 使用日数
	 * @param usedDaysAfterGrant 使用日数付与後
	 * @return 年休使用日数
	 */
	public static AnnualLeaveUsedDays of(
			AnnualLeaveUsedDayNumber usedDays,
			Optional<AnnualLeaveUsedDayNumber> usedDaysAfterGrant){
		
		AnnualLeaveUsedDays domain = new AnnualLeaveUsedDays();
		domain.usedDays = usedDays;
		domain.usedDaysAfterGrant = usedDaysAfterGrant;
		return domain;
	}
}
