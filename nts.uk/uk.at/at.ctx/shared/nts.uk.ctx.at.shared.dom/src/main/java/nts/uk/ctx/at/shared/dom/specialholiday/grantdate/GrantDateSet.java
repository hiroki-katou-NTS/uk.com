package nts.uk.ctx.at.shared.dom.specialholiday.grantdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

@AllArgsConstructor
@Getter
public class GrantDateSet {
	
	/*会社ID*/
	private String companyId;

	/* 付与日のID */
	private SpecialHolidayCode specialHolidayCode;

	/*付与日の種類*/
	private GrantDateType grantDateType;
	
	/* 月数 */
	private GrantDateMonth grantDateMonth;

	/* 年数 */
	private GrantDateYear grantDateYear;
	
	public static GrantDateSet createFromJavaType(String companyId, String specialHolidayCode, int grantDateType,
			int grantDateMonth,
			int grantDateYear){
					return new GrantDateSet(companyId,
						   new SpecialHolidayCode(specialHolidayCode),
						   EnumAdaptor.valueOf(grantDateType, GrantDateType.class),
						   new GrantDateMonth(grantDateMonth),
					       new GrantDateYear(grantDateYear));
	}
}
