package nts.uk.ctx.at.shared.dom.specialholiday;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SubCondition extends DomainObject {

	/* 会社ID */
	private String companyId;

	/* 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;

	/* 性別制限 */
	private UseGender useGender;

	/* 雇用制限 */
	private UseEmployee useEmployee;

	/* 分類制限 */
	private UseCls useCls;

	/* 年齢制限 */
	private UseAge useAge;

	/* 性別区分 */
	private GenderAtr genderAtr;

	/* 年齢上限 */
	private LimitAgeFrom limitAgeFrom;

	/* 年齢下限 */
	private LimitAgeTo limitAgeTo;

	/* 年齢基準区分 */
	private AgeCriteriaAtr ageCriteriaAtr;

	/* 年齢基準年区分 */
	private AgeBaseYearAtr ageBaseYearAtr;

	/* 年齢基準日 */
	private AgeBaseDates ageBaseDates;
	
	private List<String> employmentList;
	
	private List<String> classificationList;

	@Override
	
	public void validate() {
		super.validate();
	}

	/**
	 * Check Age and Date of Sub Condition
	 */
	public void checkAge() {
		if (this.useEmployee == UseEmployee.Use) {
		}
		if (this.useCls == UseCls.Use) {
		}
		if (this.useAge == UseAge.Allow) {
			if (this.limitAgeFrom == null || this.limitAgeTo == null) {
				throw new BusinessException("Msg_118");
			}
			if (this.limitAgeFrom.v() > this.limitAgeTo.v()) {
				throw new BusinessException("Msg_119");
			}
			if (99 < this.limitAgeFrom.v() && this.limitAgeFrom.v() < 0) {
				throw new BusinessException("Msg_336");
			}
			if (99 < this.limitAgeTo.v() && this.limitAgeTo.v() < 0) {
				throw new BusinessException("Msg_336");
			}
		}
		if (this.ageCriteriaAtr == AgeCriteriaAtr.GrantDate) {
			if (this.ageBaseDates == null) {
				throw new BusinessException("Msg_120");
			}
		}
	}

	/**
	 * Create from Java Type of Sub Condition
	 * 
	 * @param companyId
	 * @param specialHolidayCode
	 * @param useGender
	 * @param useEmployee
	 * @param useCls
	 * @param useAge
	 * @param genderAtr
	 * @param limitAgeFrom
	 * @param limitAgeTo
	 * @param ageCriteriaAtr
	 * @param ageBaseYearAtr
	 * @param ageBaseDates
	 * @return
	 */
	public static SubCondition createFromJavaType(String companyId, String specialHolidayCode, int useGender,
			int useEmployee, int useCls, int useAge, int genderAtr, Integer limitAgeFrom, Integer limitAgeTo,
			int ageCriteriaAtr, int ageBaseYearAtr, int ageBaseDates, List<String> employmentList, List<String> classificationList) {
		return new SubCondition(companyId, new SpecialHolidayCode(specialHolidayCode),
				EnumAdaptor.valueOf(useGender, UseGender.class), EnumAdaptor.valueOf(useEmployee, UseEmployee.class),
				EnumAdaptor.valueOf(useCls, UseCls.class), EnumAdaptor.valueOf(useAge, UseAge.class),
				EnumAdaptor.valueOf(genderAtr, GenderAtr.class),
				limitAgeFrom != null ? new LimitAgeFrom(limitAgeFrom) : null,
				limitAgeTo != null ? new LimitAgeTo(limitAgeTo) : null,
				EnumAdaptor.valueOf(ageCriteriaAtr, AgeCriteriaAtr.class),
				EnumAdaptor.valueOf(ageBaseYearAtr, AgeBaseYearAtr.class), new AgeBaseDates(ageBaseDates) ,employmentList, classificationList);
	}
}
