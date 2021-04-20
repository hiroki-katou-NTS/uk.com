package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.AppSendMailByEmp;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppSendMailByEmpDto {
	/**
	 * 承認ルートインスタンス
	 */
	private ApprovalRootDto approvalRoot;
	/**
	 * 申請
	 */
	private ApplicationDto application;
	/**
	 * 申請者名
	 */
	private String applicantName;
	/**
	 * 申請者のメールアドレス
	 */
	private String applicantMail;
	
	public static AppSendMailByEmpDto fromDomain(AppSendMailByEmp appSendMailByEmp) {
		return new AppSendMailByEmpDto(
				ApprovalRootDto.fromDomain(appSendMailByEmp.getApprovalRoot()), 
				ApplicationDto.fromDomain(appSendMailByEmp.getApplication()), 
				appSendMailByEmp.getApplicantName(), 
				appSendMailByEmp.getApplicantMail());
	}
}
