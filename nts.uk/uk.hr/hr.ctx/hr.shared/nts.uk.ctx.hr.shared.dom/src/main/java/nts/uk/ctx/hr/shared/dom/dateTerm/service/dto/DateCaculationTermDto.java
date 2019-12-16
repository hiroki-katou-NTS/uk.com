package nts.uk.ctx.hr.shared.dom.dateTerm.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.hr.shared.dom.enumeration.DateRule;
import nts.uk.ctx.hr.shared.dom.enumeration.DateSelectItem;

@AllArgsConstructor
@Getter
public class DateCaculationTermDto {

	/** 算出条件 */
	private DateRule calculationTerm;
	
	/** 指定数 */
	private int dateSettingNum;
	
	/** 指定日 */
	private DateSelectItem dateSettingDate;
}
