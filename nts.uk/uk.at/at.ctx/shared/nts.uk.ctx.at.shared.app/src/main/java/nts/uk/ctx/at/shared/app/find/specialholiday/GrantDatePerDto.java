package nts.uk.ctx.at.shared.app.find.specialholiday;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDatePer;

@Data
@AllArgsConstructor
public class GrantDatePerDto {
	/*会社ID*/
	private String companyId;

	/*付与日のID*/
	private int specialHolidayCode;

	/*特別休暇コード*/
	private String personalGrantDateCode;

	/*特別休暇名称*/
	private String personalGrantDateName;
	
	private int provision;

	/*一律基準日*/
	private GeneralDate grantDate;
 
	/*付与基準日*/
	private int grantDateAtr;
	
	private List<GrantDatePerSetDto> grantDatePerSet;
	
	public static GrantDatePerDto fromDomain(GrantDatePer grantDatePer) {
		List<GrantDatePerSetDto> setDto = grantDatePer.getGrantDatePerSet().stream()
				.map(x-> GrantDatePerSetDto.fromDomain(x))
				.collect(Collectors.toList());
		
		return new GrantDatePerDto(
				grantDatePer.getCompanyId(),
				grantDatePer.getSpecialHolidayCode(),
				grantDatePer.getPersonalGrantDateCode().v(),
				grantDatePer.getPersonalGrantDateName().v(),
				grantDatePer.getProvision(),
				grantDatePer.getGrantDate(),
				grantDatePer.getGrantDateAtr().value,
				setDto
		);
	}
}
