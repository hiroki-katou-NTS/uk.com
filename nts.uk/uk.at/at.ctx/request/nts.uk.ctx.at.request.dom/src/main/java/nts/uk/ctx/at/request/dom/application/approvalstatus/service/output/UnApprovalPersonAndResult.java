package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class UnApprovalPersonAndResult {
	List<UnApprovalPerson> listUnAppPerson;
	boolean result;
}
