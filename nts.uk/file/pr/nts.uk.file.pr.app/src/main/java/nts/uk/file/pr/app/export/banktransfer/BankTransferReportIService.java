package nts.uk.file.pr.app.export.banktransfer;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferIReport;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferIRpHeader;
import nts.uk.file.pr.app.export.banktransfer.query.BankTransferIReportQuery;

@Stateless
public class BankTransferReportIService extends ExportService<BankTransferIReportQuery> {
	@Inject
	private BankTransferRpIGenerator generator;
	
	@Override
	protected void handle(ExportServiceContext<BankTransferIReportQuery> context) {
		BankTransferIReport reportData = new BankTransferIReport();
		
		BankTransferIReportQuery query = context.getQuery();
		
		BankTransferIRpHeader header = new BankTransferIRpHeader();
		header.setConsignorCode(Integer.valueOf(query.getConsignorCode()));
		header.setPayeeName(query.getPayeeName());
		header.setTransferDate(query.getTransferDate().yearMonth().v()); 
		header.setBankCode(Integer.valueOf(query.getBankCode()));
		header.setBankName(query.getBankName());
		header.setBranchCode(Integer.valueOf(query.getBranchCode()));
		header.setBranchName(query.getBranchName());
		header.setAccountAtr("普通".equals(query.getAccountAtr()) ? 1 : 2);
		header.setAccountNo(Integer.valueOf(query.getAccountNo()));

		reportData.setHeader(header);
		
		generator.generate(context.getGeneratorContext(), reportData);
	}

}
