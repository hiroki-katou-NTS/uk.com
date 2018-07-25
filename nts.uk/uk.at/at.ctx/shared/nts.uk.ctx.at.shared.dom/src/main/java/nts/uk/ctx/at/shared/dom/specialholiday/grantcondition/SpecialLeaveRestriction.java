package nts.uk.ctx.at.shared.dom.specialholiday.grantcondition;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;

/**
 * 特別休暇利用条件
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpecialLeaveRestriction extends DomainObject {
	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;

	/** 分類条件 */
	private UseAtr restrictionCls;

	/** 年齢条件 */
	private UseAtr ageLimit;

	/** 性別条件 */
	private UseAtr genderRest;

	/** 雇用条件 */
	private UseAtr restEmp;

	/** 分類一覧 */
	private List<String> listCls;

	/** 年齢基準 */
	private AgeStandard ageStandard;

	/** 年齢範囲 */
	private AgeRange ageRange;

	/** 性別 */
	private GenderCls gender;

	/** 雇用一覧 */
	private List<String> listEmp;

	@Override
	public void validate() {
		super.validate();
	}

	/**
	 * Validate input data
	 */
	public List<String> validateInput() {
		List<String> errors = new ArrayList<>();
		
		// 雇用条件を使用する場合は、雇用一覧を１件以上登録する事。
		if (this.restEmp == UseAtr.USE && CollectionUtil.isEmpty(this.listEmp)) {
			errors.add("Msg_105");
		}

		// 分類条件を使用する場合は、分類一覧を１件以上登録する事。
		if (this.restrictionCls == UseAtr.USE && CollectionUtil.isEmpty(this.listCls)) {
			errors.add("Msg_108");
		}

		// 年齢条件を使用する場合は、年齢範囲を登録する事。
		if (this.ageLimit == UseAtr.USE) {
			Integer lower = this.ageRange.getAgeLowerLimit().v();
			Integer higer = this.ageRange.getAgeHigherLimit().v();
			
			// 年齢下限 <= 年齢上限
			if (lower >= higer) {
				errors.add("Msg_119");
			}

			// 0<=年齢上限<=99
			// 0<=年齢下限<=99
			if ((lower >= 99 || lower <= 0) || (higer >= 99 || higer <= 0)) {
				errors.add("Msg_366");
			}
		}
		
		return errors;
	}

	public static SpecialLeaveRestriction createFromJavaType(String companyId, int specialHolidayCode,
			int restrictionCls, int ageLimit, int genderRest, int restEmp, List<String> listCls,
			AgeStandard ageStandard, AgeRange ageRange, int gender, List<String> listEmp) {
		return new SpecialLeaveRestriction(companyId, new SpecialHolidayCode(specialHolidayCode),
				EnumAdaptor.valueOf(restrictionCls, UseAtr.class), EnumAdaptor.valueOf(ageLimit, UseAtr.class),
				EnumAdaptor.valueOf(genderRest, UseAtr.class), EnumAdaptor.valueOf(restEmp, UseAtr.class), listCls,
				ageStandard, ageRange, EnumAdaptor.valueOf(gender, GenderCls.class), listEmp);
	}

	public static SpecialLeaveRestriction createFromJavaType(String companyId, int specialHolidayCode,
			int restrictionCls, int ageLimit, int genderRest, int restEmp,
			AgeStandard ageStandard, AgeRange ageRange, int gender) {
		return new SpecialLeaveRestriction(companyId, new SpecialHolidayCode(specialHolidayCode),
				EnumAdaptor.valueOf(restrictionCls, UseAtr.class), EnumAdaptor.valueOf(ageLimit, UseAtr.class),
				EnumAdaptor.valueOf(genderRest, UseAtr.class), EnumAdaptor.valueOf(restEmp, UseAtr.class), ageStandard,
				ageRange, EnumAdaptor.valueOf(gender, GenderCls.class));
	}

	public SpecialLeaveRestriction(String companyId, SpecialHolidayCode specialHolidayCode,
			UseAtr restrictionCls, UseAtr ageLimit, UseAtr genderRest, UseAtr restEmp, AgeStandard ageStandard,
			AgeRange ageRange, GenderCls gender) {
		super();
		this.companyId = companyId;
		this.specialHolidayCode = specialHolidayCode;
		this.restrictionCls = restrictionCls;
		this.ageLimit = ageLimit;
		this.genderRest = genderRest;
		this.restEmp = restEmp;
		this.ageStandard = ageStandard;
		this.ageRange = ageRange;
		this.gender = gender;
	}
}
