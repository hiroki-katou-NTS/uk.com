package nts.uk.file.pr.app.export.banktransfer.data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankTransferARpData {
	private BankTransferARpHeader header;
	private List<BankReportData> dataSalaryList;
}
