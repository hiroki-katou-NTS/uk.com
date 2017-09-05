package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
/**
 * 01.承認ルート未登録の社員を取得する
 * @author dudt
 *
 */
public interface EmployeeUnregisterApprovalRoot {
	List<EmployeeUnregisterOutput> lstEmployeeUnregister(String companyId, GeneralDate baseDate);
}
