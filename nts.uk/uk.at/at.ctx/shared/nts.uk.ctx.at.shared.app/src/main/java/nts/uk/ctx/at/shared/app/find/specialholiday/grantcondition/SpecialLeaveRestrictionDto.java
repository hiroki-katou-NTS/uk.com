package nts.uk.ctx.at.shared.app.find.specialholiday.grantcondition;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.SpecialLeaveRestriction;

@Value
public class SpecialLeaveRestrictionDto {
	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private int specialHolidayCode;

	/** 分類条件 */
	private int restrictionCls;

	/** 年齢条件 */
	private int ageLimit;

	/** 性別条件 */
	private int genderRest;

	/** 雇用条件 */
	private int restEmp;

	/** 分類一覧 */
	private List<String> listCls;

	/** 年齢基準 */
	private AgeStandardDto ageStandard;

	/** 年齢範囲 */
	private AgeRangeDto ageRange;

	/** 性別 */
	private int gender;

	/** 雇用一覧 */
	private List<String> listEmp;

	public static SpecialLeaveRestrictionDto fromDomain(SpecialLeaveRestriction specialLeaveRestriction) {
		if(specialLeaveRestriction == null) {
			return null;
		}

		AgeStandardDto ageStandardDto = AgeStandardDto.fromDomain(specialLeaveRestriction.getAgeStandard() != null ? specialLeaveRestriction.getAgeStandard() : null);
		
		AgeRangeDto ageRangeDto = AgeRangeDto.fromDomain(specialLeaveRestriction.getAgeRange() != null ? specialLeaveRestriction.getAgeRange() : null);
		
		return new SpecialLeaveRestrictionDto(
				specialLeaveRestriction.getCompanyId(),
				specialLeaveRestriction.getSpecialHolidayCode().v(),
				specialLeaveRestriction.getRestrictionCls().value,
				specialLeaveRestriction.getAgeLimit().value,
				specialLeaveRestriction.getGenderRest().value,
				specialLeaveRestriction.getRestEmp().value,
				specialLeaveRestriction.getListCls(),
				ageStandardDto,
				ageRangeDto,
				specialLeaveRestriction.getGender().value,
				specialLeaveRestriction.getListEmp()
		);
	}
}
