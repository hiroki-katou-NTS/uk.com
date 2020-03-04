package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.masterapproverroot.AppTypeName;
@Getter
@AllArgsConstructor
public class EmpUnregisterInput {
	private int sysAtr;
	private GeneralDate baseDate;
	private List<AppTypeName> lstAppName;
}
