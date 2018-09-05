package nts.uk.ctx.at.shared.app.find.worktype.specialholidayframe;

import lombok.Value;
import nts.uk.ctx.at.shared.app.find.worktype.absenceframe.AbsenceFrameDto;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;

/**
 * 
 * @author TanLV
 *
 */
@Value
public class SpecialHolidayFrameDto {
	/* 会社ID */
	private String companyId;

	/* 特別休暇枠ID */
	private int specialHdFrameNo;

	/* 枠名称 */
	private String specialHdFrameName;

	/* 特別休暇枠の廃止区分 */
	private int deprecateSpecialHd;

	private String itemType;

	/**
	 * From domain method
	 * 
	 */
	public static SpecialHolidayFrameDto fromDomain(SpecialHolidayFrame domain) {
		return new SpecialHolidayFrameDto(domain.getCompanyId(), domain.getSpecialHdFrameNo(),
				domain.getSpecialHdFrameName().v(), domain.getDeprecateSpecialHd().value, "b");
	}

	public static SpecialHolidayFrameDto fromAbsenDto(AbsenceFrameDto domain) {
		return new SpecialHolidayFrameDto(domain.getCompanyId(), domain.getAbsenceFrameNo(),
				domain.getAbsenceFrameName(), domain.getDeprecateAbsence(), "a");
	}
}
