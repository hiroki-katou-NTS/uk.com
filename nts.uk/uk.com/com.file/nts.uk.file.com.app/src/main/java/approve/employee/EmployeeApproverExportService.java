package approve.employee;

import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.WpApproverAsAppOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval.EmployeeRegisterApprovalRoot;
import nts.uk.file.com.app.HeaderEmployeeUnregisterOutput;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeApproverExportService extends ExportService<EmployeeApproverRootQuery> {
	@Inject
	private EmployeeApproverRootOutputGenerator employeeGenerator;
	@Inject
	private EmployeeRegisterApprovalRoot registerApprovalRoot;

	@Override
	protected void handle(ExportServiceContext<EmployeeApproverRootQuery> context) {
		String companyId = AppContexts.user().companyId();

		// get query parameters
		EmployeeApproverRootQuery query = context.getQuery();
		
		//get worplaces: employee info
		Map<String, WpApproverAsAppOutput> wpApprover = registerApprovalRoot.lstEmps(companyId, query.getBaseDate(),
														query.getLstEmpIds(),query.getRootAtr(),
														query.getLstApps());
		if(wpApprover.isEmpty()) {
			throw new BusinessException("ko co' data");
		}
		
		EmployeeApproverDataSource  dataSource = new EmployeeApproverDataSource();
		dataSource.setWpApprover(wpApprover);
		dataSource.setHeaderEmployee(this.setHeader());

		this.employeeGenerator.generate(context.getGeneratorContext(), dataSource);

	}
	
	private HeaderEmployeeUnregisterOutput setHeader() {
		HeaderEmployeeUnregisterOutput header = new HeaderEmployeeUnregisterOutput();
		header.setNameCompany("A");
		header.setTitle("承認ルート未登録社員一覧");
		header.setEmployee("対象者");
		header.setWorkplaceCode("所属職場コード");
		header.setWorkplaceName("所属職場名");
		header.setAppName("申請名");
		return header;
	}

}
