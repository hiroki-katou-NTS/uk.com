package nts.uk.ctx.at.request.infra.repository.application;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.applist.service.content.ComplementLeaveAppLink;
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
	
	public static AsposeAppScreenDto fromDomainPrint(ListOfApplication domain, int dispWplName) {
		return new AsposeAppScreenDto(EnumAdaptor.valueOf(domain.getPrePostAtr(), PrePostAtr.class).name, 
				dispWplName == 1 ? domain.getWorkplaceName() + "\n" + domain.getApplicantName() : domain.getApplicantName(), 
				domain.getAppType().name, 
				domain.getAppContent(), 
				domain.getInputDate().toString("M/d(E) H:mm"), 
				I18NText.getText(domain.getReflectionStatus()), 
				domain.getOpApprovalStatusInquiry().isPresent() ? domain.getOpApprovalStatusInquiry().get().toString() : "", 
				convertStartEndDate(domain.getOpAppStartDate(), domain.getOpAppEndDate(), domain.getAppType(), domain.getAppDate(), domain.getOpComplementLeaveApp()));
	}
	
	public static String convertStartEndDate(Optional<GeneralDate> startDate, Optional<GeneralDate> endDate, ApplicationType appType, 
	        GeneralDate appDate, Optional<ComplementLeaveAppLink> opComplementLeaveApp) {
		String result = "";
		
		if(startDate.isPresent() && endDate.isPresent()) {
			if(startDate.get().toString().equals(endDate.get().toString())) {
				result = startDate.get().toString("M/d(E)");
			} else {
				result = new StringBuilder()
						.append(startDate.get().toString("M/d(E)"))
						.append("ー")
						.append(endDate.get().toString("M/d(E)"))
						.toString();
			}
		}
		if(appType.value == 10) {
		    if (opComplementLeaveApp.isPresent() && opComplementLeaveApp.get().getApplication() != null) {
		        if (opComplementLeaveApp.get().getComplementLeaveFlg() == 1) {
		            result = new StringBuilder(result)
		                    .append("\n")
		                    .append(opComplementLeaveApp.get().getLinkAppDate().toString("M/d(E)"))
		                    .toString();
		        } else 
		            result = new StringBuilder(opComplementLeaveApp.get().getLinkAppDate().toString("M/d(E)"))
		            .append("\n")
		            .append(result)
		            .toString();
		    } else {
    			result = new StringBuilder(result).toString();
		    }
		}
		
		return result;
	}
}
