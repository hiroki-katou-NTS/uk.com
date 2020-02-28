package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.workflow.dom.adapter.workplace.WkpDepInfo;

@Getter
@AllArgsConstructor
public class DataSourceApproverList {
	//key: wpkID, values: wpApprover
	private Map<String, WpApproverAsAppOutput> wpApprover;
	//職場・部門情報 List
	private List<WkpDepInfo> lstWpInfor;
}
