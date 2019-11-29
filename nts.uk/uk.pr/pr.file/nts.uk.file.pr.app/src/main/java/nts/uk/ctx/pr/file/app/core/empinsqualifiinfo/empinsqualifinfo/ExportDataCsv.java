package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.ForeignerResHistInfo;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.LaborContractHist;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.PublicEmploymentSecurityOffice;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSetting;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpInsOffice;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfo;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class ExportDataCsv {

    private List<String> lstHeader;

    private List<String> empIds;

    private Map<String, EmpInsHist> empInsHists;

    private EmpInsReportSetting reportSetting;

    private EmpInsReportTxtSetting reportTxtSetting;

    private EmpInsReportSettingExport reportSettingExport;

    private EmpInsRptTxtSettingExport reportTxtSettingExport;

    private Map<String, EmpInsNumInfo> empInsNumInfos;

    private Map<String, EmpInsGetInfo> empInsGetInfos;

    private Map<String, EmpInsOffice> empInsOffices;

    private Map<String, EmployeeInfoEx> employeeInfos;

    private Map<String, PersonExport> personExports;

    private Map<String, LaborInsuranceOffice> laborInsuranceOffices;

    private CompanyInfor companyInfo;

    private Map<String, PublicEmploymentSecurityOffice> pubEmpSecOffices;

    private LaborContractHist dummyLaborContractHist;

    private ForeignerResHistInfo dummyForResHistInfo;

    private GeneralDate fillingDate;

    private GeneralDate  startDate;

    private GeneralDate endDate;

    private List<SortObject> sortObjects;
}
