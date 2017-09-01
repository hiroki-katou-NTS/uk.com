package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service;

import java.util.List;

import nts.arc.time.GeneralDate;
/**
 * 01.承認ルート未登録の社員を取得する
 * @author dudt
 *
 */
public interface EmployeeUnregisterApprovalRoot {
	List<EmployeeUnregisterDto> lstEmployeeUnregister(String companyId, GeneralDate baseDate);
}
