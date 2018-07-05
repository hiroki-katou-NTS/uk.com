package nts.uk.ctx.at.shared.dom.specialholidaynew.periodinformation;

import java.time.Period;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantcondition.UseAtr;

/**
 * 期限情報
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GrantPeriodic extends DomainObject {
	/** 会社ID */
	private String companyId;
	
	/** 特別休暇コード */
	private int specialHolidayCode;
	
	/** 期限指定方法 */
	private TimeLimitSpecification timeSpecifyMethod;
	
	/** 使用可能期間 */
	private Period availabilityPeriod;
	
	/** 有効期限 */
	private SpecialVacationDeadline expirationDate;
	
	/** 繰越上限日数 */
	private LimitCarryoverDays limitCarryoverDays;
	
	@Override
	public void validate() {
		super.validate();
	}
	
	/**
	 * Validate input data
	 */
	public void validateInput() {
		// 0年0ヶ月は登録不可
		if (this.expirationDate.getMonths().v() == 0 && this.expirationDate.getYears().v() == 0) {
			throw new BusinessException("Msg_95");
		}
	}
}
