package nts.uk.ctx.hr.develop.dom.retiredismissalregulation.algorithm;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.shared.dom.enumeration.DateRule;
import nts.uk.ctx.hr.shared.dom.enumeration.DateSelectItem;

/**
 * @author laitv
 * 解雇予告日条件
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DismissalNoticeDateCondition {

	/** 算出条件 */
	private DateRule calculationTerm;
	
	/** 指定数 */
	private Integer dateSettingNum;
	
	/** 指定日 */
	private Optional<DateSelectItem> dateSettingDate;
	
}
