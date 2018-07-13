package nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class SaveSpecialHolidayEventCommand {

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
	private AgeRangeCommand ageRange;

	/* 年齢基準 */
	private int ageStandardYear;

	/* 年齢基準 */
	private GeneralDate ageStandardBaseDate;

	/* メモ */
	private String memo;

	/* 分類一覧 */
	List<ClassificationListCommand> clsList;

	/* 雇用一覧 */
	List<EmploymentListCommand> empList;
	
	private boolean isCreateNew; 

}
