package nts.uk.ctx.at.shared.dom.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SphdLimit extends DomainObject {

	/* 会社ID */
	private String companyId;

	/* 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;

	/* 月数 */
	private SpecialVacationMonths specialVacationMonths;

	/* 年数 */
	private SpecialVacationYears specialVacationYears;

	/* 付与日数を繰り越す */
	private GrantCarryForward grantCarryForward;

	/* 繰越上限日数 */
	private LimitCarryoverDays limitCarryoverDays;

	/*特別休暇の期限方法 */
	private SpecialVacationMethod specialVacationMethod;

	@Override
	public void validate() {
		super.validate();
	}

	/**
	 * Check Months and Years of Sphd Limit
	 */
	public void checkTime() {
		if (this.specialVacationMethod == SpecialVacationMethod.AvailableGrantDateDesignate) {
			if (this.specialVacationMonths == null || this.specialVacationYears == null) {
				throw new BusinessException("Msg_98");
			}
		}
//		if (this.limitCarryoverDays == null || this.limitCarryoverDays.v() < 0) {
//			throw new BusinessException("Msg_104");
//		}
	}

	/**
	 * Create from Java Type of Sphd Limit
	 * 
	 * @param companyId
	 * @param specialHolidayCode
	 * @param specialVacationMonths
	 * @param specialVacationYears
	 * @param grantCarryForward
	 * @param limitCarryoverDays
	 * @param specialVacationMethod
	 * @return
	 */
	public static SphdLimit createFromJavaType(String companyId, String specialHolidayCode, Integer specialVacationMonths,
			Integer specialVacationYears, int grantCarryForward, int limitCarryoverDays, int specialVacationMethod) {
		return new SphdLimit(companyId, new SpecialHolidayCode(specialHolidayCode),
				specialVacationMonths != null ? new SpecialVacationMonths(specialVacationMonths) : null, 
				specialVacationYears != null ?  new SpecialVacationYears(specialVacationYears) : null,
				EnumAdaptor.valueOf(grantCarryForward, GrantCarryForward.class),
				new LimitCarryoverDays(limitCarryoverDays),
				EnumAdaptor.valueOf(specialVacationMethod, SpecialVacationMethod.class));
	}

}
