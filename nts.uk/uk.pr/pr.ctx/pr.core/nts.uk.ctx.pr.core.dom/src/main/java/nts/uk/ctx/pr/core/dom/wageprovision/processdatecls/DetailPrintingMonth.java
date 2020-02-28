package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 明細印字月
 */

@Getter
public class DetailPrintingMonth extends DomainObject {

	/**
	 * 印字月
	 */
	private PreviousMonthClassification printingMonth;

	public DetailPrintingMonth(int printingMonth) {
		this.printingMonth = EnumAdaptor.valueOf(printingMonth, PreviousMonthClassification.class);
	}

}
