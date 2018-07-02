package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;

@AllArgsConstructor
@Getter
@Setter
public class MailTransmissionContentResultOutput {
	List<MailTransmissionContentOutput> listMailTransmisContent;
	ApprovalStatusMailTemp mailDomain;
}
