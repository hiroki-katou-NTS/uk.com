package nts.uk.file.pr.app.export.banktransfer.data;

import java.util.List;

import lombok.Data;

@Data
public class BankTransferCReport {
	private BankTransferCRpHeader header;
	private List<BankTransferCRpData> data;
}
