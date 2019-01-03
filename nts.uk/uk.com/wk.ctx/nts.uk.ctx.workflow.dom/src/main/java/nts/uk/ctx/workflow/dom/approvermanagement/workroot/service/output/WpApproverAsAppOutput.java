package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;
@Data
@AllArgsConstructor
public class WpApproverAsAppOutput {
	//職場情報
	private WorkplaceImport wpInfor;
	//Thong tin nhan vien
	/** key: sID, values: lstApp */
	private Map<String, EmpApproverAsApp> mapEmpRootInfo;
	//社員の情報一覧
	private List<EmployeeApproverOutput> lstEmployeeInfo;
}
