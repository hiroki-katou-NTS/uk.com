package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 社員抽出基準日
 */

@Getter
public class EmployeeExtractionReferenceDate extends DomainObject {

	/**
	 * 参照月
	 */
	private PreviousMonthClassification refeMonth;

	/**
	 * 参照日
	 */
	private DateSelectClassification refeDate;

	public EmployeeExtractionReferenceDate(int refeMonth, int refeDate) {
		super();
		this.refeMonth = EnumAdaptor.valueOf(refeMonth, PreviousMonthClassification.class);
		this.refeDate = EnumAdaptor.valueOf(refeDate, DateSelectClassification.class);
	}

}
