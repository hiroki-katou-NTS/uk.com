package nts.uk.ctx.at.request.ws.application;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.common.service.smartphone.output.RequestMsgInfoDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.CommonAlgorithmMobile;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.RequestMsgInfoOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.shr.com.context.AppContexts;

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
	
	@POST
	@Path("getStartNewMob")
	public AppDispInfoStartupDto getStartNewMob(StartNewMobParam param) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		List<GeneralDate> dateLst = CollectionUtil.isEmpty(param.getDateLst()) 
				? Collections.emptyList() 
				: param.getDateLst().stream().map(x -> {
					if(x== null) {
						return null;
					} else {
						return GeneralDate.fromString(x, "yyyy/MM/dd");
					}
				}).collect(Collectors.toList());
		AppDispInfoStartupOutput appDispInfoStartupOutput = commonAlgorithmMobile.appCommonStartProcess(
				true, 
				companyID, 
				Strings.isBlank(param.getEmployeeID()) ? employeeID : param.getEmployeeID(), 
				EnumAdaptor.valueOf(param.getAppType(), ApplicationType.class), 
				param.getHolidayAppType() == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(param.getHolidayAppType(), HolidayAppType.class)), 
				dateLst, 
				param.getOvertimeAppAtr() == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(param.getOvertimeAppAtr(), OvertimeAppAtr.class)));
		return AppDispInfoStartupDto.fromDomain(appDispInfoStartupOutput);
	}
	
	@POST
	@Path("getDetailMob")
	public AppDispInfoStartupDto getDetailMob(String appID) {
		String companyID = AppContexts.user().companyId();
		AppDispInfoStartupOutput appDispInfoStartupOutput = commonAlgorithmMobile.getDetailMob(companyID, appID);
		return AppDispInfoStartupDto.fromDomain(appDispInfoStartupOutput);
	}
	
}
