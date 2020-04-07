package nts.uk.ctx.hr.shared.dom.notice.report.registration.person;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
@Getter
@AllArgsConstructor
public class ApprStateHrImport {
	/**インスタンスID*/
	private String rootStateID;
	/**対象日*/
	private GeneralDate appDate;
	/**対象者ID*/
	private String employeeID;
	/**反映前flg*/
	private boolean reflectFlag;
	/**承認フェーズ*/
	private List<PhaseSttHrImport> lstPhaseState = new ArrayList<>();
}
