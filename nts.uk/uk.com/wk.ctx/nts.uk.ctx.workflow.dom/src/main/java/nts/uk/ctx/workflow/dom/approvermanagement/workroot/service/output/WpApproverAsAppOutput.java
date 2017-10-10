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
	WorkplaceImport wpInfor;
	//Thong tin nhan vien
	/**
	 * empployee id
	 */
	Map<String, EmployeeApproverAsApplicationOutput> mapEmpRootInfo;
}
