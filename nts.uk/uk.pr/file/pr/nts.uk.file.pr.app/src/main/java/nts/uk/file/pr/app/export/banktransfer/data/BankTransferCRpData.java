package nts.uk.file.pr.app.export.banktransfer.data;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankTransferCRpData {
	private String bankName;
	private String branchName;
	private String fromAccountAtr;
	private String fromAccountNo;
	private String accHolderName;
	private BigDecimal paymentMny;
}
