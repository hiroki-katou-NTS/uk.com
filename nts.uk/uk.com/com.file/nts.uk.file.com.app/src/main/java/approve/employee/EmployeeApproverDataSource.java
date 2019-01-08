package approve.employee;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.WpApproverAsAppOutput;
import nts.uk.file.com.app.HeaderEmployeeUnregisterOutput;

@AllArgsConstructor
@Getter
public class EmployeeApproverDataSource {
	//header
	private HeaderEmployeeUnregisterOutput headerEmployee;
	//key: wpkID, values: WpApprover
	private Map<String, WpApproverAsAppOutput> wpApprover;
	//職場情報 List
	private List<WorkplaceImport> lstWpInfor;
}
