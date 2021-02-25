package nts.uk.ctx.at.request.app.find.application.lateleaveearly;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.lateleaveearly.dto.MessageListDto;
import nts.uk.ctx.at.request.app.find.application.lateleaveearly.dto.PageInitDto;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyService;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.shr.com.context.AppContexts;


/**
 * @author anhnm
 *
 */
@Stateless
public class LateLeaveEarlyGetService {

	@Inject
	private LateLeaveEarlyService service;


	/**
	 * @param appId
	 * @param appDates
	 * @return ArrivedLateLeaveEarlyInfoDto
	 */
	public ArrivedLateLeaveEarlyInfoDto getLateLeaveEarly(int appId, List<String> appDates,
			AppDispInfoStartupDto appDispInfoStartupDto) {
		return ArrivedLateLeaveEarlyInfoDto
				.convertDto(this.service.getLateLeaveEarlyInfo(appId, appDates, appDispInfoStartupDto.toDomain()));
	}

	/**
	 * @param appType
	 *            Integer
	 * @param appDates
	 *            List<String>
	 * @param appDate
	 *            String
	 * @return LateEarlyDateChangeFinderDto
	 */
	public LateEarlyDateChangeFinderDto getChangeAppDate(int appType, List<String> appDates, String appDate,
			AppDispInfoNoDateOutput appDispNoDate, AppDispInfoWithDateOutput appDispWithDate,
			LateEarlyCancelAppSetDto setting) {
		return LateEarlyDateChangeFinderDto.fromDomain(
				this.service.getChangeAppDate(appType, appDates, appDate, appDispNoDate, appDispWithDate,
						setting.toDomain()));
	}

	/**
	 * @param appType
	 * @param agentAtr
	 * @param isNew
	 * @param infoOutput
	 * @param application
	 * @return
	 */
	public List<String> getMessageList(int appType, MessageListDto dto) {
		ApplicationDto app = dto.getApplication();
		app.setInputDate(GeneralDateTime.now().toString("yyyy/MM/dd HH:mm:ss"));
		dto.setApplication(app);

		if (app.getAppID() != null) {
			return this.service.getMessageList(appType, dto.isAgentAtr(), dto.isNew(), dto.getInfoOutput().toDomain(),
					app.toDomain());
		}

		return this.service.getMessageList(appType, dto.isAgentAtr(), dto.isNew(), dto.getInfoOutput().toDomain(),
				Application.createFromNew(EnumAdaptor.valueOf(dto.getApplication().getPrePostAtr(), PrePostAtr.class),
						dto.getApplication().getEmployeeID(),
						EnumAdaptor.valueOf(appType, ApplicationType.class),
						new ApplicationDate(GeneralDate.fromString(dto.getApplication().getAppDate(), "yyyy/MM/dd")),
						AppContexts.user().userId(),
						dto.getApplication().getOpStampRequestMode() == null ? null
								: Optional.of(EnumAdaptor.valueOf(dto.getApplication().getOpStampRequestMode(),
								StampRequestMode.class)),
						Optional.of(new ReasonForReversion(dto.getApplication().getOpReversionReason())),
						dto.getApplication().getOpAppStartDate() == null ? Optional.empty()
								: Optional.of(new ApplicationDate(
								GeneralDate.fromString(dto.getApplication().getOpAppStartDate(), "yyyy/MM/dd"))),
						dto.getApplication().getOpAppEndDate() == null ? Optional.empty()
								: Optional.of(new ApplicationDate(
								GeneralDate.fromString(dto.getApplication().getOpAppEndDate(), "yyyy/MM/dd"))),
						Optional.of(new AppReason(dto.getApplication().getOpAppReason())),
						dto.getApplication().getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(dto.getApplication().getOpAppStandardReasonCD()))));
	}

	/**
	 * @param appId
	 * @return
	 */
	public ArrivedLateLeaveEarlyInfoDto getInitB(PageInitDto input) {
		return ArrivedLateLeaveEarlyInfoDto
				.convertDto(this.service.getInitB(input.getAppId(), input.getInfoStartup().toDomain()));
	}
}
