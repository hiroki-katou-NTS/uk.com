package nts.uk.file.pr.app.report.printconfig.empinsreportsetting;

import java.util.List;
import java.util.Map;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.ForeignerResHistInfo;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.PublicEmploymentSecurityOffice;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CurrentPersonResidence;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.EmpInsRptSettingCommand;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.EmpInsRptTxtSettingCommand;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfo;
import nts.uk.shr.com.history.DateHistoryItem;

@Data
public class EmpInsReportSettingExportData {
	
//	private List<String> employeeIds;

	private GeneralDate createDate;
	
	private CompanyInfor companyInfo;
	
	private LaborInsuranceOffice laborInsuranceOffice;
	
	private EmpInsRptSettingCommand empInsReportSetting;
	
	private EmpInsRptTxtSettingCommand empInsReportTxtSetting;
		
	private List<EmpInsLossInfoExportRow> rowsData;
}
