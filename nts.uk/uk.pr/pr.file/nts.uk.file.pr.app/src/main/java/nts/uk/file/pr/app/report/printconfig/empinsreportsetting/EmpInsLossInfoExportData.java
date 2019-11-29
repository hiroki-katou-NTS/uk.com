package nts.uk.file.pr.app.report.printconfig.empinsreportsetting;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.EmpInsRptSettingCommand;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.EmpInsRptTxtSettingCommand;

@Data
public class EmpInsLossInfoExportData {
	
	private GeneralDate createDate;
	
	private CompanyInfor companyInfo;
	
	private LaborInsuranceOffice laborInsuranceOffice;
	
	private EmpInsRptSettingCommand empInsReportSetting;
	
	private EmpInsRptTxtSettingCommand empInsReportTxtSetting;
		
	private List<EmpInsLossInfoExportRow> rowsData;
}
