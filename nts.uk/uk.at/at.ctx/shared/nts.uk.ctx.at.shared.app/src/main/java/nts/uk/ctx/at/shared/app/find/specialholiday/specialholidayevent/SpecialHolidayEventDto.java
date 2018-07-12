package nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEvent;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialHolidayEventDto {

	/* 会社ID */
	private String companyId;

	/* 特別休暇枠NO */
	private int specialHolidayEventNo;

	/* 固定日数を上限とする */
	private int limitFixedDays;

	/* 続柄ごとに上限を設定する */
	private int refRelationShip;

	/* 固定上限日数 */
	private Integer fixedDayGrant;

	/* 忌引とする */
	private int makeInvitation;

	/* 休日を取得日に含める */
	private int includeHolidays;

	private int ageLimit;

	/* 性別条件 */
	private int genderRestrict;

	/* 雇用条件 */
	private int restrictEmployment;

	/* 分類条件 */
	private int restrictClassification;

	/* 性別 */
	private int gender;

	/* 年齢範囲 */
	private AgeRangeDto ageRange;

	/* 年齢基準 */
	private int ageStandardYear;

	/* 年齢基準 */
	private GeneralDate ageStandardBaseDate;

	/* メモ */
	private String memo;

	/* 分類一覧 */
	List<ClassificationListDto> clsList;

	/* 雇用一覧 */
	List<EmploymentListDto> empList;

	public static SpecialHolidayEventDto fromDomain(SpecialHolidayEvent domain) {
		return new SpecialHolidayEventDto(domain.getCompanyId(), domain.getSpecialHolidayEventNo(),
				domain.getLimitFixedDays(), domain.getRefRelationShip(), domain.getFixedDayGrant().v(),
				domain.getMakeInvitation().value, domain.getIncludeHolidays().value, domain.getAgeLimit().value,
				domain.getGenderRestrict().value, domain.getRestrictEmployment().value,
				domain.getRestrictClassification().value, domain.getGender().value,
				AgeRangeDto.fromDomain(domain.getAgeRange()), domain.getAgeStandardYear(),
				domain.getAgeStandardBaseDate(), domain.getMemo().v(),
				ClassificationListDto.fromClsList(domain.getClsList()),
				EmploymentListDto.fromEmpList(domain.getEmpList()));
	}

}
