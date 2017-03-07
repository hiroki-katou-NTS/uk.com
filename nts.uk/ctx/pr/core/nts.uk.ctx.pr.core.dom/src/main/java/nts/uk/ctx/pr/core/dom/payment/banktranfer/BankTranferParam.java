package nts.uk.ctx.pr.core.dom.payment.banktranfer;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * Include all information for query bank tranfer
 * @author chinhbv
 *
 */
@Value
public class BankTranferParam {
	String companyCode;
	String personId;
	
	String fromBankCode;
	String fromBranchCode;
	int fromAccountAtr;
	String fromAccountNo;
	
	String toBankCode;
	String toBranchCode;
	int toAccountAtr;
	String toAccountNo;
	
	int processNo;
	int processYearMonth;
	int payBonusAtr;
	int sparePayAtr;
	GeneralDate payDate;
}
