package nts.uk.ctx.at.shared.dom.specialholidaynew.grantcondition;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.gul.collection.CollectionUtil;

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
	private int specialHolidayCode;
	
	/** 特別休暇利用条件コード */
	private int specialLeaveCode;
	
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
	public void validateInput() {
		// 雇用条件を使用する場合は、雇用一覧を１件以上登録する事。
		if (this.restEmp == UseAtr.USE && CollectionUtil.isEmpty(this.listEmp)) {
			throw new BusinessException("Msg_105");
		}
		
		// 分類条件を使用する場合は、分類一覧を１件以上登録する事。
		if (this.restrictionCls == UseAtr.USE && CollectionUtil.isEmpty(this.listCls)) {
			throw new BusinessException("Msg_108");
		}
		
		// 年齢条件を使用する場合は、年齢範囲を登録する事。
		if (this.ageLimit == UseAtr.USE) {
			// 年齢下限　<=　年齢上限
			if(this.ageRange.getAgeLowerLimit() >= this.ageRange.getAgeHigherLimit()) {
				throw new BusinessException("Msg_119");
			}
			
			// 0<=年齢上限<=99
			if(this.ageRange.getAgeLowerLimit() >= 99 || this.ageRange.getAgeLowerLimit() <= 0) {
				throw new BusinessException("Msg_366");
			}
			
			// 0<=年齢下限<=99
			if(this.ageRange.getAgeHigherLimit() >= 99 || this.ageRange.getAgeHigherLimit() <= 0) {
				throw new BusinessException("Msg_366");
			}
		}
	}
}
