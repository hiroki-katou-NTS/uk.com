package nts.uk.ctx.at.request.infra.repository.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ListOfApplication;

@Getter
@Setter
@AllArgsConstructor
public class AsposeAppScreenDto {

	/**
	 * 事前事後区分
	 */
	private String prePostAtr;

	/**
	 * 申請者名
	 */
	private String applicantName;

	/**
	 * 申請種類
	 */
	private String appType;

	/**
	 * 申請内容
	 */
	private String appContent;

	/**
	 * 入力日
	 */
	private String inputDate;

	/**
	 * 反映状態
	 */
	private String reflectionStatus;

	/**
	 * 承認状況照会
	 */
	private String approvalStatusInquiry;

	/**
	 * 申請開始日
	 */
	private String appStartDate;

	public static AsposeAppScreenDto fromDomain(ListOfApplication domain) {
		return new AsposeAppScreenDto(EnumAdaptor.valueOf(domain.getPrePostAtr(), PrePostAtr.class).name, 
				domain.getApplicantName(), 
				domain.getAppType().name, 
				domain.getAppContent(), 
				domain.getInputDate().toString(), 
				domain.getReflectionStatus(), 
				domain.getOpApprovalStatusInquiry().isPresent() ? domain.getOpApprovalStatusInquiry().get().toString() : "", 
				domain.getOpAppStartDate().isPresent() ? domain.getOpAppStartDate().get().toString() : "");
	}
}
