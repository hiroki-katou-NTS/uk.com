package nts.uk.ctx.hr.shared.dom.notice.report.registration.person;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class PhaseSttHrImport {
	/**承認フェーズNo*/
	private Integer phaseOrder;
	/**承認区分*/
	private int approvalAtr;
	/**承認形態*/
	private int approvalForm;
	/**承認枠*/
	private List<FrameHumanImport> lstApprovalFrame;
}
