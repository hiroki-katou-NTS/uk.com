package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.masterapproverroot.AppTypeName;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;

public interface EmployeeUnregisterApprovalRoot {
	/**
	 * 01.承認ルート未登録の社員を取得する
	 * @param companyId
	 * @param baseDate
	 * @param sysAtr
	 * @return
	 */
	List<EmployeeUnregisterOutput> lstEmployeeUnregister(String companyId, GeneralDate baseDate, int sysAtr,
			List<Integer> lstNotice, List<String> lstEvent, List<AppTypeName> lstName);
}
