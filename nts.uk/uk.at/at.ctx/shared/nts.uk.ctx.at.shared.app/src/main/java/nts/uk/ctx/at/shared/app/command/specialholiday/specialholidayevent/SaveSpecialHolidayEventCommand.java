package nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class SaveSpecialHolidayEventCommand {

	/* 会社ID */
	private String companyId;

	/* 特別休暇枠NO */
	private int specialHolidayEventNo;

	/* 上限日数の設定方法 */
	private int maxNumberDay;

	/* 固定上限日数 */
	private Integer fixedDayGrant;

	/* 忌引とする */
	private boolean makeInvitation;

	/* 休日を取得日に含める */
	private boolean includeHolidays;

	private boolean ageLimit;

	/* 性別条件 */
	private boolean genderRestrict;

	/* 雇用条件 */
	private boolean restrictEmployment;

	/* 分類条件 */
	private boolean restrictClassification;

	/* 性別 */
	private int gender;

	/* 年齢範囲 */
	private AgeRangeCommand ageRange;

	/* 年齢基準 */
	private int ageStandard;

	/* 年齢基準 */
	private String ageStandardBaseDate;

	/* メモ */
	private String memo;

	/* 分類一覧 */
	List<ClassificationListCommand> clsList;

	/* 雇用一覧 */
	List<EmploymentListCommand> empList;

	private boolean createNew;

	public int getMakeInvitation() {
		return this.makeInvitation == true ? 1 : 0;
	}

	public int getIncludeHolidays() {
		return this.includeHolidays == true ? 1 : 0;
	}

	public int getAgeLimit() {
		return this.ageLimit == true ? 1 : 0;
	}

	public int getGenderRestrict() {
		return this.genderRestrict == true ? 1 : 0;
	}

	public int getRestrictEmployment() {
		return this.restrictEmployment == true ? 1 : 0;
	}

	public int getRestrictClassification() {
		return this.restrictClassification == true ? 1 : 0;
	}

	public GeneralDate getAgeStandardBaseDate() {
		return !StringUtils.isEmpty(this.ageStandardBaseDate)
				? GeneralDate.fromString(this.ageStandardBaseDate, "yyyy/MM/dd") : null;
	}

}
