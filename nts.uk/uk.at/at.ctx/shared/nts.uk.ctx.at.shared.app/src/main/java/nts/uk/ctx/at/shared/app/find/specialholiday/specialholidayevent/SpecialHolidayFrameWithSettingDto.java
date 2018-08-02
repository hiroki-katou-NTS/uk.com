package nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialHolidayFrameWithSettingDto {

	/* 会社ID */
	private String companyId;

	/* 特別休暇枠ID */
	private int specialHdFrameNo;

	/* 枠名称 */
	private String specialHdFrameName;

	/* 特別休暇枠の廃止区分 */
	private int deprecateSpecialHd;

	private boolean isSetting;

	/**
	 * From domain method
	 * 
	 */
	public static SpecialHolidayFrameWithSettingDto fromDomain(SpecialHolidayFrame domain, boolean isSetting) {
		return new SpecialHolidayFrameWithSettingDto(domain.getCompanyId(), domain.getSpecialHdFrameNo(),
				domain.getSpecialHdFrameName().v(), domain.getDeprecateSpecialHd().value, isSetting);
	}

}
