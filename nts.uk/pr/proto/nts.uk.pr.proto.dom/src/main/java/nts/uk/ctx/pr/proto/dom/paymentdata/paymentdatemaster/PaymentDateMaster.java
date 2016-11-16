package nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.proto.dom.paymentdata.PayBonusAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.ProcessingNo;
import nts.uk.ctx.pr.proto.dom.paymentdata.SparePayAtr;

/**
 * Payroll date master;
 *
 */
public class PaymentDateMaster extends AggregateRoot {
	@Getter
	private int neededWorkDay;
	
	@Getter
	private ProcessingNo processingNo;
	
	@Getter
	private YearMonth processingYearMonth;
	
	@Getter
	private SparePayAtr sparePayAttribute;
	
	@Getter
	private PayBonusAtr payBonusAttribute;
}
