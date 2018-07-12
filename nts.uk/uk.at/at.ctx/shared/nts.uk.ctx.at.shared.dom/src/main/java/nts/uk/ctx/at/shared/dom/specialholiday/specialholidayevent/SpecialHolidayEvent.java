package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.specialholiday.GenderAtr;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantcondition.AgeRange;
import nts.uk.shr.com.primitive.Memo;

@NoArgsConstructor
@AllArgsConstructor
@Data
// 事象に対する特別休暇
public class SpecialHolidayEvent extends AggregateRoot {

	/* 会社ID */
	private String companyId;

	/* 特別休暇枠NO */
	private int specialHolidayEventNo;

	/* 固定日数を上限とする */
	private int limitFixedDays;

	/* 続柄ごとに上限を設定する */
	private int refRelationShip;

	/* 固定上限日数 */
	private FixedDayGrant fixedDayGrant;

	/* 忌引とする */
	private UseAtr makeInvitation;

	/* 休日を取得日に含める */
	private UseAtr includeHolidays;

	private UseAtr ageLimit;

	/* 性別条件 */
	private UseAtr genderRestrict;

	/* 雇用条件 */
	private UseAtr restrictEmployment;

	/* 分類条件 */
	private UseAtr restrictClassification;

	/* 性別 */
	private GenderAtr gender;

	/* 年齢範囲 */
	private AgeRange ageRange;

	/* 年齢基準 */
	private int ageStandardYear;
	
	/* 年齢基準 */
	private GeneralDate ageStandardBaseDate;

	/* メモ */
	private Memo memo;
	
	/* 分類一覧 */
	List<ClassificationList> clsList;

	/* 雇用一覧 */
	List<EmploymentList> empList;
}
