package nts.uk.ctx.pr.core.app.find.payment.banktransfer;

import lombok.Value;

@Value
public class BankTransferDto {
	String scd;
	String nameB;
	int paymentMethod1;
	int paymentMethod2;
	int paymentMethod3;
	int paymentMethod4;
	int paymentMethod5;
}
