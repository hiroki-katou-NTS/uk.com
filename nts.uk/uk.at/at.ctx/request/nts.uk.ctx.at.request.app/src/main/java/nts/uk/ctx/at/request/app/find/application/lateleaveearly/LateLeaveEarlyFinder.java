package nts.uk.ctx.at.request.app.find.application.lateleaveearly;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoDto;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.LateLeaveEarlyService;


@Stateless
public class LateLeaveEarlyFinder {
	
	@Inject
	private LateLeaveEarlyService repository;
	
	public ArrivedLateLeaveEarlyInfoDto getLateLeaveEarly(String appId) {
		return ArrivedLateLeaveEarlyInfoDto.convertDto(this.repository.getLateLeaveEarlyInfo(appId));
	}
}
