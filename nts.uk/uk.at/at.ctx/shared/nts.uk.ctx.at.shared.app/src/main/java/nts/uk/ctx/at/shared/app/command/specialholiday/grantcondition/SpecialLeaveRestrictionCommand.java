package nts.uk.ctx.at.shared.app.command.specialholiday.grantcondition;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent.AgeRangeCommand;

@Value
public class SpecialLeaveRestrictionCommand {
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
	private AgeStandardCommand ageStandard;

	/** 年齢範囲 */
	private AgeRangeCommand ageRange;

	/** 性別 */
	private int gender;

	/** 雇用一覧 */
	private List<String> listEmp;
}
