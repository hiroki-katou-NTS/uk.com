package nts.uk.ctx.at.record.ac.request.application;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.request.application.ApprovalStatusRequestAdapter;
import nts.uk.ctx.at.record.dom.adapter.request.application.dto.ApprovalStatusMailTempImport;
import nts.uk.ctx.at.record.dom.adapter.request.application.dto.EmployeeUnconfirmImport;
import nts.uk.ctx.at.record.dom.adapter.request.application.dto.RealityStatusEmployeeImport;
import nts.uk.ctx.at.record.dom.adapter.request.application.dto.SendMailResultImport;
import nts.uk.ctx.at.record.dom.application.realitystatus.output.MailTransmissionContentOutput;
import nts.uk.ctx.at.request.pub.application.approvalstatus.ApprovalStatusMailTempExport;
import nts.uk.ctx.at.request.pub.application.approvalstatus.ApprovalStatusPub;
import nts.uk.ctx.at.request.pub.application.approvalstatus.MailTransmissionContentExport;
import nts.uk.ctx.at.request.pub.application.approvalstatus.SendMailResultExport;

@Stateless
public class ApprovalStatusRequestImpl implements ApprovalStatusRequestAdapter {
	@Inject
	ApprovalStatusPub approvalStatusPub;

	@Override
	public List<RealityStatusEmployeeImport> getApprovalStatusEmployee(String wkpId, GeneralDate closureStart,
			GeneralDate closureEnd, List<String> listEmpCd) {
		return approvalStatusPub.getApprovalStatusEmployee(wkpId, closureStart, closureEnd, listEmpCd).stream()
				.map(x -> new RealityStatusEmployeeImport(x.getSId(), x.getClosureStart(), x.getClosureEnd()))
				.collect(Collectors.toList());
	}

	@Override
	public ApprovalStatusMailTempImport getApprovalStatusMailTemp(int transmissionAttr) {
		ApprovalStatusMailTempExport data = approvalStatusPub.getApprovalStatusMailTemp(transmissionAttr);
		if (data == null) {
			return null;
		}
		return new ApprovalStatusMailTempImport(data.getMailType(), data.getUrlApprovalEmbed(), data.getUrlDayEmbed(),
				data.getUrlMonthEmbed(), data.getMailSubject(), data.getMailContent());
	}

	@Override
	public List<EmployeeUnconfirmImport> getListEmployeeEmail(List<String> listSId) {
		return approvalStatusPub.getListEmployeeEmail(listSId).stream().map(x -> new EmployeeUnconfirmImport(x.getSId(),
				x.getSName(), x.getEntryDate(), x.getRetiredDate(), x.getMailAddr())).collect(Collectors.toList());
	}

	@Override
	public String confirmApprovalStatusMailSender() {
		return approvalStatusPub.confirmApprovalStatusMailSender();
	}

	@Override
	public SendMailResultImport exeApprovalStatusMailTransmission(List<MailTransmissionContentOutput> listMailContent,
			ApprovalStatusMailTempImport domain, int mailType) {
		List<MailTransmissionContentExport> listData = listMailContent.stream()
				.map(x -> new MailTransmissionContentExport(x.getSId(), x.getSName(), x.getMailAddr(), x.getSubject(),
						x.getText()))
				.collect(Collectors.toList());
		ApprovalStatusMailTempExport domainMail = new ApprovalStatusMailTempExport(domain.getMailType(),
				domain.getUrlApprovalEmbed(), domain.getUrlDayEmbed(), domain.getUrlMonthEmbed(),
				domain.getMailSubject(), domain.getMailContent());
		SendMailResultExport result = approvalStatusPub.exeApprovalStatusMailTransmission(listData, domainMail, mailType);

		return new SendMailResultImport(result.isOK(), result.getListError());
	}
}
