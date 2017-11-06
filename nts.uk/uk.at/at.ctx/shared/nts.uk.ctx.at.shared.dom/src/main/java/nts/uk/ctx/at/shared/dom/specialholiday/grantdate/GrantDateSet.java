package nts.uk.ctx.at.shared.dom.specialholiday.grantdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

/**
 * 
 * @author TanLV
 *
 */
@AllArgsConstructor
@Getter
public class GrantDateSet {
	
	/*会社ID*/
	private String companyId;

	/* 付与日のID */
	private SpecialHolidayCode specialHolidayCode;
	
	/* 付与日の数 */
	private int grantDateNo;
	
	/* 月数 */
	private GrantDateMonth grantDateMonth;

	/* 年数 */
	private GrantDateYear grantDateYear;
	
	/* 
	 * Create from java type
	 */
	public static GrantDateSet createFromJavaType(String companyId, String specialHolidayCode, int grantDateNo,
			int grantDateMonth,
			int grantDateYear){
					return new GrantDateSet(companyId,
						   new SpecialHolidayCode(specialHolidayCode),
						   grantDateNo,
						   new GrantDateMonth(grantDateMonth),
					       new GrantDateYear(grantDateYear));
	}
}
