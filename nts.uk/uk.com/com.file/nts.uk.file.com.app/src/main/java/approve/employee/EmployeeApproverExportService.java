package approve.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApproverAsApplicationInforOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeApproverAsApplicationOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeApproverOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeOrderApproverAsAppOutput;
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

		// get worplaces: employee info
		Map<String, WpApproverAsAppOutput> wpApprover = registerApprovalRoot.lstEmps(companyId, query.getBaseDate(),
				query.getLstEmpIds(), query.getRootAtr(), query.getLstApps());


		EmployeeApproverAsApplicationOutput employeeApp = null;
		EmployeeApproverOutput employeeInfor = new EmployeeApproverOutput("1", "A");
		List<ApproverAsApplicationInforOutput> appLst = new ArrayList<>();
		List<EmployeeOrderApproverAsAppOutput> employeeOrder = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			employeeOrder.add(new EmployeeOrderApproverAsAppOutput(i, "A"));
		}
		
		for (int i = 0; i < 5; i++) {
			if (i == 0) {
				appLst.add(new ApproverAsApplicationInforOutput(i, "全員承認", employeeOrder));
			}
			else if(i == 1) {
				appLst.add(new ApproverAsApplicationInforOutput(i, "残業申請", employeeOrder));
				
			}else if(i == 2) {
				appLst.add(new ApproverAsApplicationInforOutput(i, "出張申請", employeeOrder));
			}else if(i== 3) {
				appLst.add(new ApproverAsApplicationInforOutput(i, "直行直帰申請", employeeOrder));
			}else if(i== 4) {
				appLst.add(new ApproverAsApplicationInforOutput(i, "時間年休申請", employeeOrder));
			}
		}
		Map<Integer, List<ApproverAsApplicationInforOutput>> mapAppTypeAsApprover = new HashMap<>();
		for(int i = 0 ; i < 5 ; i++) {
			mapAppTypeAsApprover.put(i, appLst);
		}
		EmployeeApproverAsApplicationOutput employee = new EmployeeApproverAsApplicationOutput(employeeInfor, mapAppTypeAsApprover);
		Map<String, EmployeeApproverAsApplicationOutput> mapEmpRootInfo = new HashMap<>();

		for(int i = 0; i < 5; i++) {
			mapEmpRootInfo.put("0", employee);
			
		}
		WorkplaceImport wpInfor = new WorkplaceImport("1","0001","A");
		
		WpApproverAsAppOutput  output = new WpApproverAsAppOutput(wpInfor, mapEmpRootInfo);
		
		for(int i = 0; i < 5 ; i++) {
			wpApprover.put(String.valueOf(i), output);
		}
		
		
	

		if (wpApprover.isEmpty()) {
			throw new BusinessException("ko co' data");
		}

		EmployeeApproverDataSource dataSource = new EmployeeApproverDataSource();
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
