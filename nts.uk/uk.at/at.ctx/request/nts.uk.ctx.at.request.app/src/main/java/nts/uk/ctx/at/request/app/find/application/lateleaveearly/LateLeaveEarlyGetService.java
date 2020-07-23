package nts.uk.ctx.at.request.app.find.application.lateleaveearly;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyService;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoOutput;


/**
 * @author anhnm
 *
 */
@Stateless
public class LateLeaveEarlyGetService {

	@Inject
	private LateLeaveEarlyService repository;

	/**
	 * @param appId
	 * @param appDates
	 * @return ArrivedLateLeaveEarlyInfoDto
	 */
	public ArrivedLateLeaveEarlyInfoDto getLateLeaveEarly(int appId, List<String> appDates) {
		return ArrivedLateLeaveEarlyInfoDto.convertDto(this.repository.getLateLeaveEarlyInfo(appId, appDates));
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
			AppDispInfoNoDateOutput appDispNoDate, AppDispInfoWithDateOutput appDispWithDate) {
		return LateEarlyDateChangeFinderDto.fromDomain(
				this.repository.getChangeAppDate(appType, appDates, appDate, appDispNoDate, appDispWithDate));
	}

	/**
	 * @param appType
	 * @param agentAtr
	 * @param isNew
	 * @param infoOutput
	 * @param application
	 * @return
	 */
	public List<String> getMessageList(int appType, boolean agentAtr, boolean isNew,
			ArrivedLateLeaveEarlyInfoOutput infoOutput, Application application) {
		return this.repository.getMessageList(appType, agentAtr, isNew, infoOutput, application);
	}
}
