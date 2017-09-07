package nts.uk.ctx.at.shared.dom.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

@AllArgsConstructor
@Getter
public class SphdLimit {
	
	/*会社ID*/
	private String companyId;

	/*特別休暇コード*/
	private SpecialHolidayCode specialHolidayCode;
	
	/*月数*/
	private SpecialVacationMonths specialVacationMonths;
	
	/*年数*/
	private SpecialVacationYears specialVacationYears;
	
	/*付与日数を繰り越す*/
	private GrantCarryForward grantCarryForward;
	
	/*繰越上限日数*/
	private LimitCarryoverDays limitCarryoverDays;
	
	/*特別休暇の期限方法*/
	private SpecialVacationMethod specialVacationMethod; 

	public static SphdLimit createFromJavaType(
			String companyId,
			String specialHolidayCode,
			int specialVacationMonths,
			int specialVacationYears,
			int grantCarryForward,
			int limitCarryoverDays,
			int specialVacationMethod){
					return new SphdLimit(companyId, 
					new SpecialHolidayCode(specialHolidayCode),
					new SpecialVacationMonths(specialVacationMonths),
					new SpecialVacationYears(specialVacationYears),
					EnumAdaptor.valueOf(grantCarryForward, GrantCarryForward.class),
					new LimitCarryoverDays(limitCarryoverDays),
					EnumAdaptor.valueOf(specialVacationMethod, SpecialVacationMethod.class));
}

	public SphdLimit() {
		// TODO Auto-generated constructor stub
	}
}
