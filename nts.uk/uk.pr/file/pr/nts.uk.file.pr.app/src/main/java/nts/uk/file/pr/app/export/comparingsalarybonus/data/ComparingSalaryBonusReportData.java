package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSet;
/**
 * The Class ComparingSalaryBonusReportData
 * @author lanlt
 *
 */
@Getter
@Setter
public class ComparingSalaryBonusReportData {
	ComparingSalaryBonusHeaderReportData headerData;
	HeaderTable  headerTable;
	List<DeparmentInf> deparmentInf;
	List<DepartmentHyrachi> lstDepartmentHyrachi;
	List<DataRowComparingSalaryBonusDto> lstDivisionTotal;
	List<DataRowComparingSalaryBonusDto> lstHyrachiTotal;
	List<DataRowComparingSalaryBonusDto> lstTotalA;
	List<DataRowComparingSalaryBonusDto> lstTotalC;
	DataRowComparingSalaryBonusDto grandTotal;
	ComparingPrintSet configPrint;
	PrintMode printMode;
	
}
