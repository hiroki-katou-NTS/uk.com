package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;

@AllArgsConstructor
@Value
public class MailTransmissionContentResultOutput {
	List<MailTransmissionContentOutput> listMailTransmisContent;
	ApprovalStatusMailTemp mailDomain;
}
