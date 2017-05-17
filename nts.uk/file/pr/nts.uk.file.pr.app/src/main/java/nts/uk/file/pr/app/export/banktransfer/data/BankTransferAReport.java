package nts.uk.file.pr.app.export.banktransfer.data;

import java.util.List;

import lombok.Data;

@Data
public class BankTransferAReport {
	private BankTransferARpHeader header;
	private List<BankTransferARpData> salaryPreliminaryMonthList;
	private List<BankTransferARpData> salaryPortionList;
	private String sparePayAtr;
	
	public BankTransferAReport() {
	}
}
