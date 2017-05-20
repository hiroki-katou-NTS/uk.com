package nts.uk.file.pr.app.export.banktransfer.data;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankTransferCRpHeader {
	private String bankName;
	private String branchName;
	private String transferDate;
	private String cNameSJIS;
	private BigDecimal totalMny;
	private int totalObjPerPage;
	private int totalObj;
	private BigDecimal totalMnyPerPage;
}
