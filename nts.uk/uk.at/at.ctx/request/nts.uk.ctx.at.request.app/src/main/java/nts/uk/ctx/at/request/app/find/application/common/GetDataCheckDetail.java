package nts.uk.ctx.at.request.app.find.application.common;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InitMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InputGetDetailCheck;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.User;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetDataCheckDetail {

	@Inject
	private BeforePreBootMode beforePreBootMode;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	@Inject
	private InitMode initMode;
	
	/**
	 * 
	 * @param inputGetDetailCheck
	 * @return
	 */
	public OutputDetailCheckDto getDataCheckDetail(InputGetDetailCheck inputGetDetailCheck) {
		// error EA refactor 4
		/*String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		//14-2
		OutputDetailCheckDto outputDetailCheckDto = OutputDetailCheckDto.fromDomain(beforePreBootMode.judgmentDetailScreenMode(
					companyID,
					employeeID,
					inputGetDetailCheck.getApplicationID(), 
					GeneralDate.fromString(inputGetDetailCheck.getBaseDate(), DATE_FORMAT)));
		//14-3
		outputDetailCheckDto.setInitMode(initMode.getDetailScreenInitMode(
				EnumAdaptor.valueOf(outputDetailCheckDto.getUser(), User.class), 
				outputDetailCheckDto.getReflectPlanState()).getOutputMode().value);
		return outputDetailCheckDto;*/
		return null;
	}
	
	
}
