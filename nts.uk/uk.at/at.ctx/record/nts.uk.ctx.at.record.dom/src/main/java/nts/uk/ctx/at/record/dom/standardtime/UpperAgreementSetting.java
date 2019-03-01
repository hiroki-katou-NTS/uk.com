package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AgreementOneMonthTime;

/**
 * ３６協定上限規制
 * 
 * @author sonnh1
 *
 */
@Getter
public class UpperAgreementSetting extends DomainObject {
	// 月間
	private AgreementOneMonthTime upperMonth;
	// 複数月平均
	private AgreementOneMonthTime upperMonthAverage;
	
	public UpperAgreementSetting(AgreementOneMonthTime upperMonth, AgreementOneMonthTime upperMonthAverage) {
		super();
		this.upperMonth = upperMonth;
		this.upperMonthAverage = upperMonthAverage;
	}
}
