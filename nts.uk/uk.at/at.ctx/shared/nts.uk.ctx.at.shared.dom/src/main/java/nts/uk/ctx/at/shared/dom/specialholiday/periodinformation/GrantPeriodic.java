package nts.uk.ctx.at.shared.dom.specialholiday.periodinformation;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

/**
 * 期限情報
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GrantPeriodic extends DomainObject {
	/** 会社ID */
	private String companyId;
	
	/** 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;
	
	/** 期限指定方法 */
	private TimeLimitSpecification timeSpecifyMethod;
	
	/** 使用可能期間 */
	private AvailabilityPeriod availabilityPeriod;
	
	/** 有効期限 */
	private SpecialVacationDeadline expirationDate;
	
	/** 繰越上限日数 */
	private LimitCarryoverDays limitCarryoverDays;
	
	@Override
	public void validate() {
		super.validate();
	}
	
	/**
	 * Validate input data
	 */
	public List<String> validateInput() {
		List<String> errors = new ArrayList<>();
		
		// 0年0ヶ月は登録不可
		if(this.getTimeSpecifyMethod() == TimeLimitSpecification.AVAILABLE_GRANT_DATE_DESIGNATE) {
			if (this.expirationDate.getMonths().v() == 0 && this.expirationDate.getYears().v() == 0) {
				errors.add("Msg_95");
			}
		}
		
		return errors;
	}
	
	/**
	 * Create from Java Type
	 * 
	 * @param companyId
	 * @param specialHolidayCode
	 * @param timeSpecifyMethod
	 * @param availabilityPeriod
	 * @param expirationDate
	 * @param limitCarryoverDays
	 * @return
	 */
	public static GrantPeriodic createFromJavaType(String companyId, int specialHolidayCode, int timeSpecifyMethod, AvailabilityPeriod availabilityPeriod, 
			SpecialVacationDeadline expirationDate, int limitCarryoverDays) {
		return new GrantPeriodic(companyId, 
				new SpecialHolidayCode(specialHolidayCode),
				EnumAdaptor.valueOf(timeSpecifyMethod, TimeLimitSpecification.class),
				availabilityPeriod,
				expirationDate,
				new LimitCarryoverDays(limitCarryoverDays));
	}
}
