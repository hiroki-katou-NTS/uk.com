package nts.uk.ctx.at.request.app.find.application.lateleaveearly;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.application.lateleaveearly.dto.MessageListDto;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoDto;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyService;


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
	public ArrivedLateLeaveEarlyInfoDto getLateLeaveEarly(int appId, List<String> appDates) {
		return ArrivedLateLeaveEarlyInfoDto.convertDto(this.service.getLateLeaveEarlyInfo(appId, appDates));
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
		return this.service.getMessageList(appType, dto.isAgentAtr(), dto.isNew(), dto.getInfoOutput().toDomain(),
				dto.getApplication().toDomain());
	}

	/**
	 * @param appId
	 * @return
	 */
	public ArrivedLateLeaveEarlyInfoDto getInitB(String appId) {
		return ArrivedLateLeaveEarlyInfoDto.convertDto(this.service.getInitB(appId));
	}
}
