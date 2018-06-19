package nts.uk.ctx.at.request.pubimp.application.approvalstatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailType;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.ApprovalStatusService;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.ApprovalStatusEmployeeOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.MailTransmissionContentOutput;
import nts.uk.ctx.at.request.dom.application.approvalstatus.service.output.SendMailResultOutput;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeEmailImport;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Content;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.Subject;
import nts.uk.ctx.at.request.pub.application.approvalstatus.ApprovalStatusEmployeeExport;
import nts.uk.ctx.at.request.pub.application.approvalstatus.ApprovalStatusMailTempExport;
import nts.uk.ctx.at.request.pub.application.approvalstatus.ApprovalStatusPub;
import nts.uk.ctx.at.request.pub.application.approvalstatus.EmployeeEmailExport;
import nts.uk.ctx.at.request.pub.application.approvalstatus.MailTransmissionContentExport;
import nts.uk.ctx.at.request.pub.application.approvalstatus.SendMailResultExport;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class ApprovalStatusPubImpl implements ApprovalStatusPub {
	@Inject
	ApprovalStatusService approvalStatusService;
	@Inject
	EmployeeRequestAdapter employeeRequestAdapter;

	@Override
	public List<ApprovalStatusEmployeeExport> getApprovalStatusEmployee(String wkpId, GeneralDate closureStart,
			GeneralDate closureEnd, List<String> listEmpCd) {
		List<ApprovalStatusEmployeeExport> result = new ArrayList<>();
		List<ApprovalStatusEmployeeOutput> data = approvalStatusService.getApprovalStatusEmployee(wkpId, closureStart,
				closureEnd, listEmpCd);
		if (data != null) {
			for (ApprovalStatusEmployeeOutput item : data) {
				ApprovalStatusEmployeeExport empExport = new ApprovalStatusEmployeeExport();
				empExport.setSId(item.getSid());
				empExport.setClosureStart(item.getStartDate());
				empExport.setClosureEnd(item.getEndDate());
				result.add(empExport);
			}
			return result;
		}
		return Collections.emptyList();
	}

	@Override
	public ApprovalStatusMailTempExport getApprovalStatusMailTemp(int transmissionAttr) {
		ApprovalStatusMailTemp data = approvalStatusService.getApprovalStatusMailTemp(transmissionAttr);
		if (data == null) {
			return null;
		}
		return new ApprovalStatusMailTempExport(data.getMailType().value,
				Objects.isNull(data.getUrlApprovalEmbed()) ? null : data.getUrlApprovalEmbed().value,
				Objects.isNull(data.getUrlDayEmbed()) ? null : data.getUrlDayEmbed().value,
				Objects.isNull(data.getUrlMonthEmbed()) ? null : data.getUrlMonthEmbed().value,
				data.getMailSubject().v(), data.getMailContent().v());
	}

	@Override
	public List<EmployeeEmailExport> getListEmployeeEmail(List<String> listSId) {
		List<EmployeeEmailExport> result = new ArrayList<>();
		List<EmployeeEmailImport> listEmpEmail = approvalStatusService.findEmpMailAddr(listSId);
		if (listEmpEmail != null) {
			for (EmployeeEmailImport empEmail : listEmpEmail) {
				EmployeeEmailExport newEmp = new EmployeeEmailExport(empEmail.getSId(), empEmail.getSName(),
						empEmail.getEntryDate(), empEmail.getRetiredDate(), empEmail.getMailAddr());
				result.add(newEmp);
			}
			return result;
		}
		return Collections.emptyList();
	}

	@Override
	public String confirmApprovalStatusMailSender() {
		return approvalStatusService.confirmApprovalStatusMailSender();
	}

	@Override
	public SendMailResultExport exeApprovalStatusMailTransmission(List<MailTransmissionContentExport> listMailContent,
			ApprovalStatusMailTempExport domain, int mailType) {
		List<MailTransmissionContentOutput> listMail = new ArrayList<>();

		for (MailTransmissionContentExport item : listMailContent) {
			MailTransmissionContentOutput mail = new MailTransmissionContentOutput(item.getSId(), item.getSName(),
					item.getMailAddr(), item.getSubject(), item.getText());
			listMail.add(mail);
		}
		ApprovalStatusMailTemp domainMail = new ApprovalStatusMailTemp("",
				EnumAdaptor.valueOf(domain.getMailType(), ApprovalStatusMailType.class),
				Objects.isNull(domain.getUrlApprovalEmbed()) ? null
						: EnumAdaptor.valueOf(domain.getUrlApprovalEmbed(), NotUseAtr.class),
				Objects.isNull(domain.getUrlDayEmbed()) ? null
						: EnumAdaptor.valueOf(domain.getUrlDayEmbed(), NotUseAtr.class),
				Objects.isNull(domain.getUrlMonthEmbed()) ? null
						: EnumAdaptor.valueOf(domain.getUrlMonthEmbed(), NotUseAtr.class),
				new Subject(domain.getMailSubject()), new Content(domain.getMailContent()));
		SendMailResultOutput result = approvalStatusService.exeApprovalStatusMailTransmission(listMail, domainMail,
				EnumAdaptor.valueOf(mailType, ApprovalStatusMailType.class));
		return new SendMailResultExport(result.isOK(), result.getListError());
	}
}
