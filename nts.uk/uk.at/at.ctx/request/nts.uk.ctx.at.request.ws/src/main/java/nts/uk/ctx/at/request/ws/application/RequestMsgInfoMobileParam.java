package nts.uk.ctx.at.request.ws.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.ReceptionRestrictionSetDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.appuseset.ApplicationUseSetDto;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class RequestMsgInfoMobileParam {
	private String companyID;
	private String employeeID; 
	private String employmentCD;
	private ApplicationUseSetDto applicationUseSetting;
	private ReceptionRestrictionSetDto receptionRestrictionSetting;
	private Integer opOvertimeAppAtr;
}
