package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.adapter.bs.SyJobTitleAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.JobGInfor;
import nts.uk.ctx.workflow.dom.adapter.workplace.WkpDepInfo;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceApproverAdapter;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class ApprovalRootCommonService {
	@Inject
	private WorkplaceApproverAdapter adapterWp;
	@Inject
	private SyJobTitleAdapter adapterJobtitle;
	
	private static final int SHUUGYOU = 0;
	
	/**
	 * get work place/ department info (id, code, name)
	 * @param id
	 * @param sysAtr
	 * @return
	 */
	public WkpDepInfo getWkpDepInfo(String id, int sysAtr){
		String companyId = AppContexts.user().companyId();
		Optional<WkpDepInfo> wkpDepOp = Optional.empty();
		if(sysAtr == SHUUGYOU){
			wkpDepOp = adapterWp.findByWkpIdNEW(companyId, id, GeneralDate.today());
		}else{
			wkpDepOp = adapterWp.findByDepIdNEW(companyId, id, GeneralDate.today());
		}
		if(!wkpDepOp.isPresent()) return new WkpDepInfo(id, "", "コード削除済");
		return wkpDepOp.get();
	}
	
	//get jobG name
	public JobGInfor getJobGInfo(String jobGCD){
		String companyId = AppContexts.user().companyId();
		List<JobGInfor> lstJG = adapterJobtitle.getJobGInfor(companyId, Arrays.asList(jobGCD));
		if(lstJG.isEmpty()) return new JobGInfor(jobGCD, "コード削除済");
		return lstJG.get(0);
	}
	/**
	 * get workplace/deparment login
	 * @param sysAtr
	 * @return
	 */
	public WkpDepInfo getWkpDepInfoLogin(int sysAtr){
		String companyId = AppContexts.user().companyId();
		Optional<WkpDepInfo> wkpDepOp = Optional.empty();
		String employeeID = AppContexts.user().employeeId();
		String id = "";
		if(sysAtr == SHUUGYOU){
			id = adapterWp.getWorkplaceIDByEmpDate(employeeID, GeneralDate.today());
			wkpDepOp = adapterWp.findByWkpIdNEW(companyId, id, GeneralDate.today());
		}else{
			id = adapterWp.getDepartmentIDByEmpDate(employeeID, GeneralDate.today());
			wkpDepOp = adapterWp.findByDepIdNEW(companyId, id, GeneralDate.today());
		}
		if(!wkpDepOp.isPresent()) return new WkpDepInfo(id, "", "コード削除済");
		return wkpDepOp.get();
	}
}
