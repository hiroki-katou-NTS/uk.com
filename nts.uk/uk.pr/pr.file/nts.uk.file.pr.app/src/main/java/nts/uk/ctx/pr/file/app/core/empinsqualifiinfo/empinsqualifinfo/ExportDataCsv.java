package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.PublicEmploymentSecurityOffice;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportSetting;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpInsReportTxtSetting;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpInsOffice;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfo;

import java.util.List;
import java.util.Map;

@Builder
@Getter
public class ExportDataCsv {

    private List<String> lstHeader;

    private Map<String, EmpInsHist> empInsHists;

    private EmpInsReportSetting reportSetting;

    private EmpInsReportTxtSetting reportTxtSetting;

    private Map<String, EmpInsNumInfo> empInsNumInfos;

    private Map<String, EmpInsOffice> empInsOffices;

    private Map<String, LaborInsuranceOffice> laborInsuranceOffices;

    private CompanyInfor companyInfo;

    private Map<String, PublicEmploymentSecurityOffice> pubEmpSecOffices;

    private GeneralDate baseDate;

    private GeneralDate  startDate;

    private GeneralDate endDate;
}
