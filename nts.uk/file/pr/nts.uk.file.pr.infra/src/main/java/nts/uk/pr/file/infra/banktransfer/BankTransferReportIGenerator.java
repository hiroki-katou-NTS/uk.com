package nts.uk.pr.file.infra.banktransfer;

import java.io.PrintWriter;

import javax.ejb.Stateless;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.banktransfer.BankTransferRpIGenerator;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferIReport;
import nts.uk.file.pr.app.export.banktransfer.data.BankTransferIRpHeader;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class BankTransferReportIGenerator extends AsposeCellsReportGenerator implements BankTransferRpIGenerator {

	@Override
	public void generate(FileGeneratorContext fileContext, BankTransferIReport report) {
		BankTransferIRpHeader rpHeader = report.getHeader();
		//List<BankTransferBRpData> rpData = report.getData();
		
		try {
			PrintWriter out;
			out = new PrintWriter(this.createNewFile(fileContext, "住民税納付データ.txt"));
			out.print("1210"); // data fix
			out.print(BankTranferReportUtil.createspace(convertIntToString(rpHeader.getConsignorCode()), 10)); // G_SEL_002
			out.print(BankTranferReportUtil.createspace(rpHeader.getPayeeName(), 40)); // G_SEL_001
			out.print(BankTranferReportUtil.createspace(convertIntToString(rpHeader.getTransferDate()), 4)); // G_INP_001
			out.print(BankTranferReportUtil.createspace(convertIntToString(rpHeader.getBankCode()), 4));
			out.print(BankTranferReportUtil.createspace(rpHeader.getBankName(), 15));
			out.print(BankTranferReportUtil.createspace(convertIntToString(rpHeader.getBranchCode()), 3));
			out.print(BankTranferReportUtil.createspace(rpHeader.getBranchName(), 15));
			out.print(rpHeader.getAccountAtr());
			out.print(BankTranferReportUtil.createspace(convertIntToString(rpHeader.getAccountNo()), 7));
			out.print(rpHeader.getDumSpace());
			
			out.println("");
			
//			for (BankTransferBRpData data : rpData) {
//				out.print("2");
//				out.print(createspace(data.getMunicipalCode(),6));
//				out.print(createspace(data.getCityName(),15));
//				out.print(createspace(data.getDesignatedNumber(),15));
//				out.print("0");
//				out.print(createspace(data.getDesignatedNumber(),5));
//				out.print(createspace(format(data.getSalaryAmount()),9));
//				out.print(createspace(data.getNumberRetirees(),5));
//				out.print(createspace(format(data.getRetirementAmount()),9));
//				out.print(createspace(data.getTotalNumberSalaReti(),5));
//				out.print(createspace(format(data.getTotalSalaryRetiAmount()),9));
//				out.print(createspace(data.getPersonnel(),3));
//				out.print(createspace(format(data.getPayment()),10));
//				out.print(createspace(format(data.getCityTownTax()),9));
//				out.print(createspace(format(data.getPrefecturalTax()),9));
//				out.println("");
//			}
//			out.print("8");
//			out.print(createspace(reportData.getCommon().getTotalNumSalaryMi(),7));
//			out.print(createspace(format(reportData.getCommon().getTotalSalaryAmount()),11));
//			out.print(createspace(reportData.getCommon().getTotalNumberRetirees(),7));
//			out.print(createspace(format(reportData.getCommon().getTotalRetirementAmount()),11));
//			out.print(createspace(reportData.getCommon().getTotalNumberSala(),7));
//			out.print(createspace(format(reportData.getCommon().getTotalSalaRetiAmount()),11));
//			out.println("");
//			out.print("9");
			out.close();

		} catch (Exception e) {
			//
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private String convertIntToString(int val) {
		return String.valueOf(val);
	}
	

}
