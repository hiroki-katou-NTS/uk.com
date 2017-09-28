package nts.uk.ctx.at.shared.app.command.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDatePerSet;

@Data
@AllArgsConstructor
public class GrantDatePerSetCommand {

	/*付与日のID*/
	private String specialHolidayCode;

	/*特別休暇コード*/
	private String personalGrantDateCode;

	/* 付与日の数 */
	private int grantDateNo;
	
	/* 月数 */
	private int grantDateMonth;

	/* 年数 */
	private int grantDateYear;
	
	/**
	 * Convert to domain object
	 * @return
	 */
	public GrantDatePerSet toDomain(String companyId) {		
		return  GrantDatePerSet.createSimpleFromJavaType(companyId, specialHolidayCode, personalGrantDateCode, grantDateNo, grantDateMonth, grantDateYear);
	}
}
