package nts.uk.ctx.at.shared.app.find.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDatePerSet;

@Data
@AllArgsConstructor
public class GrantDatePerSetDto {
	/*会社ID*/
	private String companyId;

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
	
	public static GrantDatePerSetDto fromDomain(GrantDatePerSet grantDatePerSet) {
		return new GrantDatePerSetDto(
				grantDatePerSet.getCompanyId(),
				grantDatePerSet.getSpecialHolidayCode(),
				grantDatePerSet.getPersonalGrantDateCode().v(),
				grantDatePerSet.getGrantDateNo(),
				grantDatePerSet.getGrantDateMonth().v(),
				grantDatePerSet.getGrantDateYear().v()				
		);
	}
}
