package nts.uk.ctx.at.request.app.find.application.common;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InputGetDetailCheck;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetDataCheckDetail {

	@Inject
	private BeforePreBootMode beforePreBootMode;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	/**
	 * 
	 * @param inputGetDetailCheck
	 * @return
	 */
	public OutputDetailCheckDto getDataCheckDetail(InputGetDetailCheck inputGetDetailCheck) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		return OutputDetailCheckDto.fromDomain(beforePreBootMode.judgmentDetailScreenMode(
					companyID,
					employeeID,
					inputGetDetailCheck.getApplicationID(), 
					GeneralDate.fromString(inputGetDetailCheck.getBaseDate(), DATE_FORMAT)));
	}
	
	
}
