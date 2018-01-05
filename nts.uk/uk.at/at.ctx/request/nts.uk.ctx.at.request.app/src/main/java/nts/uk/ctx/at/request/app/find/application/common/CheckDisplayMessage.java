package nts.uk.ctx.at.request.app.find.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckDisplayMessage {
	@Inject
	private BeforePreBootMode beforePreBootModeRepo;
	
	/**
	 * A3-1 check hiển thị hay không hiển thị
	 * @param applicationData
	 * @param baseDate
	 * @return
	 */
	public boolean checkDisplayReasonApp(Application applicationData,
			GeneralDate baseDate) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		boolean check = false;
		//neu reason = null return false
		if(applicationData.getApplicationReason().v().isEmpty()) {
			return false;
		}
		DetailedScreenPreBootModeOutput detailedScreenPreBootModeOutput = 
				beforePreBootModeRepo.judgmentDetailScreenMode(companyID, employeeID, applicationData.getApplicationID(), baseDate);
		if(detailedScreenPreBootModeOutput.getReflectPlanState().value ==0 || 
				detailedScreenPreBootModeOutput.getReflectPlanState().value ==1) {
			check = true;
			if(detailedScreenPreBootModeOutput.isAuthorizableFlags() == true && 
					detailedScreenPreBootModeOutput.isAlternateExpiration() ==true) {
				check = false;
			}
		}
		return check;
	}
	/**
	 * B4-2 check hiển thị hay không hiển thị
	 * @param applicationData
	 * @param baseDate
	 * @return
	 */
	public boolean checkDisplayAuthorizationComment(Application applicationData,
			GeneralDate baseDate) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		boolean check = false;
		
		DetailedScreenPreBootModeOutput detailedScreenPreBootModeOutput = 
				beforePreBootModeRepo.judgmentDetailScreenMode(companyID, employeeID, applicationData.getApplicationID(), baseDate);
		if(detailedScreenPreBootModeOutput.getUser().value ==0 || 
				detailedScreenPreBootModeOutput.getUser().value ==1) {
			check = true;
			if(detailedScreenPreBootModeOutput.isAuthorizableFlags()==true && 
					detailedScreenPreBootModeOutput.isAlternateExpiration() ==true) {
				check = false;
			}
		}
		return check;
	}
	

}
