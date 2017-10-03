package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EnumType;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.adapter.bs.SyJobTitleAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.JobTitleImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.WpApproverAsAppOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval.EmployeeRegisterApprovalRoot;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class DataServiceAdapterFinder {
	@Inject
	private SyJobTitleAdapter jobTitle;
	@Inject
	private EmployeeRegisterApprovalRoot registerApprovalRoot;
	
	public List<JobTitleImport> findAllJobTitle(GeneralDate baseDate){
		String companyId = AppContexts.user().companyId();
		return jobTitle.findAll(companyId, baseDate);
	}
	
	public Map<String, WpApproverAsAppOutput> lstEmps(EmployeeRegisterApprovalRootDto data){
		String companyId = AppContexts.user().companyId();
		return registerApprovalRoot.lstEmps(companyId, data.getBaseDate(), data.getLstEmpIds(),data.getRootAtr(), data.getLstApps());
	}
	
}
