package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
* 毎月の支払日
*/
@Getter
public class MonthlyPaymentDate extends DomainObject
{
    
    /**
    * 支払日
    */
    private DateSelectClassification datePayMent;

	public MonthlyPaymentDate(int datePayMent) {
		super();
		this.datePayMent = EnumAdaptor.valueOf(datePayMent, DateSelectClassification.class);
	}
        
}
