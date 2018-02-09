package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 事後の受付制限
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class AfterhandRestriction extends DomainObject {
	
	/**
	 * 未来日許可しない
	 */
	private Boolean allowFutureDay;
	
	public static AfterhandRestriction toDomain(Integer allowFutureDay){
		return new AfterhandRestriction(allowFutureDay == 1 ? true : false);
	}
}
