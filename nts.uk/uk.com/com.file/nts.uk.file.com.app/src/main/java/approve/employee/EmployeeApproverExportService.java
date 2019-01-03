package approve.employee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.DataSourceApproverList;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval.EmployeeRegisterApprovalRoot;
import nts.uk.file.com.app.HeaderEmployeeUnregisterOutput;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeApproverExportService extends ExportService<EmployeeApproverRootQuery> {
	@Inject
	private EmployeeApproverRootOutputGenerator employeeGenerator;
	@Inject
	private EmployeeRegisterApprovalRoot registerApprovalRoot;

	@Inject
	private CompanyAdapter company;

	/**
	 * print CMM018 - N
	 */
	@Override
	protected void handle(ExportServiceContext<EmployeeApproverRootQuery> context) {
		String companyId = AppContexts.user().companyId();
		// get query parameters
		EmployeeApproverRootQuery query = context.getQuery();
		List<EmployeeQuery> employee = query.getLstEmpIds();
		List<String> employeeIdLst = employee.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		// get worplaces: employee info
		//01.申請者としての承認ルートを取得する
		DataSourceApproverList wpApprover = registerApprovalRoot.lstEmps(companyId, query.getBaseDate(),
				employeeIdLst, query.getLstApps());
		//check data
		if (wpApprover.getWpApprover().isEmpty()) {
			throw new BusinessException("Msg_7");
		}
		EmployeeApproverDataSource dataSource = new EmployeeApproverDataSource(this.setHeader(), wpApprover.getWpApprover(), wpApprover.getLstWpInfor());
		//IN
		this.employeeGenerator.generate(context.getGeneratorContext(), dataSource);

	}

	private HeaderEmployeeUnregisterOutput setHeader() {
		HeaderEmployeeUnregisterOutput header = new HeaderEmployeeUnregisterOutput();
		Optional<CompanyInfor> companyInfo = this.company.getCurrentCompany();
		if (companyInfo.isPresent()) {
			header.setNameCompany(companyInfo.get().getCompanyName());
		}
		return header;
	}

}
