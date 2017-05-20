package nts.uk.pr.file.infra.banktransfer;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.banktransfer.BankTransferRpIGenerator;
import nts.uk.file.pr.app.export.banktransfer.data.BankReportData;
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
			out = new PrintWriter(this.createNewFile(fileContext, "振込元銀行の情報.txt"));
			
			//（１）ヘッダー・レコード
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
			
			//（２）データ・レコード
			List<BankReportData> reportData = report.getData();
			List<BankReportData> reportDataNotTotal = reportData.stream().filter(x -> !x.isRowTotal()).collect(Collectors.toList());
			List<BankReportData> reportDataTotal = reportData.stream().filter(x -> x.isRowTotal()).collect(Collectors.toList());
			for (BankReportData item : reportDataNotTotal) {
				if (item.isRowTotal()) {
					continue;
				}
				
				out.print(1);
				out.print(BankTranferReportUtil.createspace(item.getBankCode(), 4));
				out.print(BankTranferReportUtil.createspace(item.getBankName(), 15));
				out.print(BankTranferReportUtil.createspace(item.getBranchCode(), 3));
				out.print(BankTranferReportUtil.createspace(item.getBranchName(), 15));
				out.print("0000");
				out.print(item.getToAccountAtr());
				out.print(BankTranferReportUtil.createspace(item.getToAccountNo(), 7));
				out.print(BankTranferReportUtil.createspace(item.getName(), 30));
				out.print(BankTranferReportUtil.createspace(format(item.getPaymentMyn()), 10));
				out.print(0);
				out.print("          "); // fix space
				out.print("          "); // fix space
				out.print("        "); // fix space
				
				out.println("");
			}
			
			//（３）トレーラ・レコード
			BankReportData rowTotal = reportDataTotal.get(0);
			out.print(8);
			out.print(checkLength(convertIntToString(reportDataNotTotal.size()), "999999", 6));
			out.print(checkLength(format(rowTotal.getPaymentMyn()), "999999999999", 12));
			out.print("                                                                                                     "); // fix 101 space
			out.println("");
			
			//（４）エンド・レコード
			out.print(9);
			out.print("                                                                                                                       "); // fix 119 space
			out.println("");
			
			out.close();

		} catch (Exception e) {
			//
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * Create default max output
	 * @param inputStr
	 * @param maxOutput
	 * @param maxLength
	 * @return
	 */
	private String checkLength(String inputStr, String maxOutput, int maxLength) {
		if (inputStr.length() < maxLength) {
			String result = "";
			for (int i = 0; i < maxLength - inputStr.length(); i++) {
				result += "0";
			}
			return result.concat(inputStr);
		} else if (inputStr.length() > maxLength) {
			return maxOutput;
		}
		
		return inputStr;
	}
	
	/**
	 * Convert int to string
	 * @param val
	 * @return
	 */
	private String convertIntToString(int val) {
		return String.valueOf(val);
	}
	
	/**
	 * Convert type BigDecimal to String
	 * @param input BigDecimal
	 * @return
	 */
	private String format(BigDecimal input) {
		DecimalFormat formatter = new DecimalFormat("#0");
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		formatter.setDecimalFormatSymbols(symbols);
		
		return formatter.format(input);
	}

}
