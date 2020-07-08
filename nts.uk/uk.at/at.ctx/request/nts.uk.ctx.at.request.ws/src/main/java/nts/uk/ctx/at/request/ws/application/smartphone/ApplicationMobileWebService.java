package nts.uk.ctx.at.request.ws.application.smartphone;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.application.common.service.smartphone.output.RequestMsgInfoDto;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.CommonAlgorithmMobile;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.RequestMsgInfoOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Path("at/request/app/smartphone")
@Produces("application/json")
public class ApplicationMobileWebService {
	
	@Inject
	private CommonAlgorithmMobile commonAlgorithmMobile;
	
	@POST
	@Path("requestmsg")
	public RequestMsgInfoDto getRequestMsgInfoOutputMobile(RequestMsgInfoMobileParam requestMsgInfoMobileParam) {
		RequestMsgInfoOutput requestMsgInfoOutput = commonAlgorithmMobile.getRequestMsgInfoOutputMobile(
				requestMsgInfoMobileParam.getCompanyID(), 
				requestMsgInfoMobileParam.getEmployeeID(), 
				requestMsgInfoMobileParam.getEmploymentCD(), 
				requestMsgInfoMobileParam.getApplicationUseSetting().toDomain(), 
				requestMsgInfoMobileParam.getReceptionRestrictionSetting().toDomain(), 
				requestMsgInfoMobileParam.getOpOvertimeAppAtr() == null 
					? Optional.empty() 
					: Optional.of(EnumAdaptor.valueOf(requestMsgInfoMobileParam.getOpOvertimeAppAtr(), OvertimeAppAtr.class)));
		return RequestMsgInfoDto.fromDomain(requestMsgInfoOutput);
	} 
	
}
