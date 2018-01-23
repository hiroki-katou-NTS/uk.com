package nts.uk.ctx.at.shared.app.find.specialholiday;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDateCom;

@Data
@AllArgsConstructor
public class GrantDateComDto {
	/*会社ID*/
	private String companyId;

	/*付与日のID*/
	private String specialHolidayCode;

	/*付与基準日*/
	private int grantDateAtr;

	/*一律基準日*/
	private GeneralDate grantDate;
	
	private List<GrantDateSetDto> grantDateSets;

	public static GrantDateComDto fromDomain(GrantDateCom grantDateCom) {
		List<GrantDateSetDto> setDto = grantDateCom.getGrantDateSets().stream()
				.map(x-> GrantDateSetDto.fromDomain(x))
				.collect(Collectors.toList());
		
		return new GrantDateComDto(
				grantDateCom.getCompanyId(),
				grantDateCom.getSpecialHolidayCode().v(),
				grantDateCom.getGrantDateAtr().value,
				grantDateCom.getGrantDate(),
				setDto
		);
	}
}
