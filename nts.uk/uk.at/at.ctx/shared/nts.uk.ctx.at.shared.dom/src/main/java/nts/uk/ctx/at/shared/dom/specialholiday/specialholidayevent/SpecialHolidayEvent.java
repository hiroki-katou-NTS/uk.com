package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent;

import java.util.List;

import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
//import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeRange;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.GenderCls;
import nts.uk.shr.com.primitive.Memo;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// 事象に対する特別休暇
public class SpecialHolidayEvent extends AggregateRoot {

	/* 会社ID */
	private String companyId;

	/* 特別休暇枠NO */
	private int specialHolidayEventNo;

	/* 上限日数の設定方法 */
	private MaxNumberDayType maxNumberDay;

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
	private GenderCls gender;

	/* 年齢範囲 */
	private AgeRange ageRange;

	/* 年齢基準年区分 */
	private AgeStandardType ageStandard;

	/* 年齢基準 */
	private Integer ageStandardBaseDate;

	/* メモ */
	private Memo memo;

	/* 分類一覧 */
	List<ClassificationList> clsList;

	/* 雇用一覧 */
	List<EmploymentList> empList;

	@Override
	public void validate() {

		boolean isfixedDayButGrantNull = this.maxNumberDay.equals(MaxNumberDayType.LIMIT_FIXED_DAY)
				&& this.fixedDayGrant.v() == null;

		if (isfixedDayButGrantNull) {
			throw new BusinessException("Msg_97");
		}

		boolean isUseEmpButNoItem = this.restrictEmployment.equals(UseAtr.USE) && CollectionUtil.isEmpty(this.empList);

		if (isUseEmpButNoItem) {
			throw new BusinessException("Msg_105");
		}

		boolean isUseClsButNoItem = this.restrictClassification.equals(UseAtr.USE)
				&& CollectionUtil.isEmpty(this.clsList);

		if (isUseClsButNoItem) {
			throw new BusinessException("Msg_108");
		}

		boolean isUseAgeLimitButNoAgeRange = this.ageLimit.equals(UseAtr.USE) && this.ageRange == null;

		if (isUseAgeLimitButNoAgeRange) {
			throw new BusinessException("");
		}

		boolean isSetAgeRange = this.ageLimit.equals(UseAtr.USE) && this.ageRange != null;

		if (isSetAgeRange) {

			Integer lower = getAgeLowerLimit();
			Integer higher = getAgeRangeHigherLimit();
			boolean isAgelowerHigherUpper = lower > higher;

			if (isAgelowerHigherUpper) {
				throw new BusinessException("Msg_119");
			}

			boolean isRangeValueNotValid = (0 > lower) || (lower > 99) || (0 > higher) || (higher > 99);

			if (isRangeValueNotValid) {
				throw new BusinessException("Msg_366");
			}

		}

	}

	public Integer getAgeRangeHigherLimit() {
		boolean isHaveAgeRange = this.ageRange != null;
		if (isHaveAgeRange) {
			return this.ageRange.getAgeHigherLimit().v();
		}
		return null;
	}

	public Integer getAgeLowerLimit() {
		boolean isHaveAgeRange = this.ageRange != null;
		if (isHaveAgeRange) {
			return this.ageRange.getAgeLowerLimit().v();
		}
		return null;
	}

}
