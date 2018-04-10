package nts.uk.ctx.at.record.dom.application.realitystatus.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.record.dom.adapter.request.application.dto.ApprovalStatusMailTempImport;

/**
 * @author dat.lh
 *
 */
@Value
@AllArgsConstructor
public class MailTranmissionContentResultOutput {
	private ApprovalStatusMailTempImport approvalStatusMailTemp;
	private List<MailTransmissionContentOutput> listMail;
}
