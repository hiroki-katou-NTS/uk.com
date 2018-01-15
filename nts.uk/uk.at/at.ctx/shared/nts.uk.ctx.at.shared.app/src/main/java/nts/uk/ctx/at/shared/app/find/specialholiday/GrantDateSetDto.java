package nts.uk.ctx.at.shared.app.find.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDateSet;

@Data
@AllArgsConstructor
public class GrantDateSetDto {
	/*会社ID*/
	private String companyId;

	/* 付与日のID */
	private String specialHolidayCode;
	
	/* 付与日の数 */
	private int grantDateNo;
	
	/* 月数 */
	private int grantDateMonth;

	/* 年数 */
	private int grantDateYear;

	public static GrantDateSetDto fromDomain(GrantDateSet grantDateSet) {
		return new GrantDateSetDto(
				grantDateSet.getCompanyId(),
				grantDateSet.getSpecialHolidayCode().v(),
				grantDateSet.getGrantDateNo(),
				grantDateSet.getGrantDateMonth().v(),
				grantDateSet.getGrantDateYear().v()				
		);
	}
}
