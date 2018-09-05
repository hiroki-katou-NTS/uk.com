package nts.uk.ctx.sys.assist.dom.salary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.sys.assist.dom.datarestoration.RetentionPeriodIndicator;

/**
 * 明細印字月
 */
@AllArgsConstructor
@Getter
public class DetailPrintingMon extends DomainObject {

	/**
	 * 処理区分NO
	 */
	private int processCateNo;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 印字月
	 */
	private PreviousMonthClassification printingMonth;

	public DetailPrintingMon(int processCateNo, String cid, int printingMonth) {
		this.cid = cid;
		this.processCateNo = processCateNo;
		this.printingMonth = EnumAdaptor.valueOf(printingMonth, PreviousMonthClassification.class);
	}

}
