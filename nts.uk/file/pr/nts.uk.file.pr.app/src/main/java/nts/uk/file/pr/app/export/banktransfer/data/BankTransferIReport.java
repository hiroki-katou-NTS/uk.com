package nts.uk.file.pr.app.export.banktransfer.data;

import java.util.List;

import lombok.Data;

@Data
public class BankTransferIReport {
	private BankTransferIRpHeader header;
	private List<BankReportData> data;
}
