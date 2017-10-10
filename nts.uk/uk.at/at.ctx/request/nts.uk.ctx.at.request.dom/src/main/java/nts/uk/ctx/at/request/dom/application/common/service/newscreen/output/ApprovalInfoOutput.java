package nts.uk.ctx.at.request.dom.application.common.service.newscreen.output;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;

@NoArgsConstructor
@Setter
@Getter
public class ApprovalInfoOutput {
	List<AppApprovalPhase> appApprovalPhase;
	List<Integer> dispOrderPhase;
}
