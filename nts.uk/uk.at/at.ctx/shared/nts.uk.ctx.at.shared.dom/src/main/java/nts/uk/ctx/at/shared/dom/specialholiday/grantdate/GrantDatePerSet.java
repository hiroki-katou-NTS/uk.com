package nts.uk.ctx.at.shared.dom.specialholiday.grantdate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author TanLV
 *
 */
@AllArgsConstructor
@Getter
public class GrantDatePerSet {
	/*会社ID*/
	private String companyId;

	/*付与日のID*/
	private String specialHolidayCode;

	/*特別休暇コード*/
	private PersonalGrantDateCode personalGrantDateCode;

	/* 付与日の数 */
	private int grantDateNo;
	
	/* 月数 */
	private GrantDateMonth grantDateMonth;

	/* 年数 */
	private GrantDateYear grantDateYear;

	/* 
	 * Create from java type
	 */
	public static GrantDatePerSet createSimpleFromJavaType(String companyId, String specialHolidayCode, String personalGrantDateCode, int grantDateNo,
			int grantDateMonth, int grantDateYear) {
		return new GrantDatePerSet(companyId,
				specialHolidayCode,
				new PersonalGrantDateCode(personalGrantDateCode),
				grantDateNo,
				new GrantDateMonth(grantDateMonth),
				new GrantDateYear(grantDateYear));

	}
}
