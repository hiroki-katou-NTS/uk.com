package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 経理締め日
 */
@Getter
public class AccountingClosureDate extends DomainObject {

	/**
	 * 処理月
	 */
	private PreviousMonthClassification processMonth;

	/**
	 * 処理日
	 */
	private DateSelectClassification disposalDay;

	public AccountingClosureDate(int processMonth, int disposalDay) {
		super();
		this.processMonth = EnumAdaptor.valueOf(processMonth, PreviousMonthClassification.class);
		this.disposalDay = EnumAdaptor.valueOf(disposalDay, DateSelectClassification.class);
	}

}
