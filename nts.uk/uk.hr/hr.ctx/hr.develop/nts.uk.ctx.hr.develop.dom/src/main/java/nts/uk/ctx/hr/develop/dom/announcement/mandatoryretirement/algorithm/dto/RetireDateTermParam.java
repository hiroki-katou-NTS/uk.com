package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.RetireDateRule;
import nts.uk.ctx.hr.shared.dom.enumeration.DateSelectItem;

@AllArgsConstructor
@Getter
public class RetireDateTermParam {

	/** 退職日条件 */
	private RetireDateRule retireDateTerm;
	
	/** 退職日指定日 */
	private DateSelectItem retireDateSettingDate;
	
}
