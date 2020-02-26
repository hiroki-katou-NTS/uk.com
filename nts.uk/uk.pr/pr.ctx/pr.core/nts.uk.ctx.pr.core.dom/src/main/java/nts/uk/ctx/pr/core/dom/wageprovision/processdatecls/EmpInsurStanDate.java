package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 雇用保険基準日
 */
@Getter
public class EmpInsurStanDate extends DomainObject {

	/**
	 * 基準日
	 */
	private DateSelectClassification empInsurRefeDate;

	/**
	 * 基準月
	 */
	private MonthSelectionSegment empInsurBaseMonth;

	public EmpInsurStanDate(int empInsurRefeDate, int empInsurBaseMonth) {
		this.empInsurRefeDate = EnumAdaptor.valueOf(empInsurRefeDate, DateSelectClassification.class);
		this.empInsurBaseMonth = EnumAdaptor.valueOf(empInsurBaseMonth, MonthSelectionSegment.class);
	}

}
