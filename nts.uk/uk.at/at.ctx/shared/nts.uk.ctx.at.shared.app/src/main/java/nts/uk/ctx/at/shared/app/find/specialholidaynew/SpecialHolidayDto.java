package nts.uk.ctx.at.shared.app.find.specialholidaynew;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHoliday;

@Value
public class SpecialHolidayDto {
	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private int specialHolidayCode;

	/** 特別休暇名称 */
	private String specialHolidayName;
	
	/** メモ */
	private String memo;

	public static SpecialHolidayDto fromDomain(SpecialHoliday specialHoliday) {
//		List<GrantDateSetDto> setDto = grantDateCom.getGrantDateSets().stream()
//				.map(x-> GrantDateSetDto.fromDomain(x))
//				.collect(Collectors.toList());
		
		return new SpecialHolidayDto(
				specialHoliday.getCompanyId(),
				specialHoliday.getSpecialHolidayCode().v(),
				specialHoliday.getSpecialHolidayName().v(),
				specialHoliday.getMemo().v()
		);
	}
}
