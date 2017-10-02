package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class LateOrLeaveEarlyFinder {
	
	@Inject
	private LateOrLeaveEarlyRepository lateOrLeaveEarlyRepository;
	
	@Inject
	private ApplicationReasonRepository applicationReasonRepository;

	public ScreenLateOrLeaveEarlyDto getLateOrLeaveEarly(String appID) {
		String companyID = AppContexts.user().companyId();

		List<ApplicationReason> applicationReasons = applicationReasonRepository.getReasonByAppType(companyID, 9);
		List<ApplicationReasonDto> listApplicationReasonDto = applicationReasons.stream()
																.map(r -> new ApplicationReasonDto(r.getReasonID(), r.getReasonTemp()))
																.collect(Collectors.toList());
		Optional<LateOrLeaveEarly> lateOrLeaveEarly = Optional.empty();
		if (!StringUtil.isNullOrEmpty(appID, true)) {
			lateOrLeaveEarly = this.lateOrLeaveEarlyRepository.findByCode(companyID, appID);
		}
		
		if (!lateOrLeaveEarly.isPresent()) {
			return new ScreenLateOrLeaveEarlyDto(null, listApplicationReasonDto);
		}
		else {
			LateOrLeaveEarly result = lateOrLeaveEarly.get();
			LateOrLeaveEarlyDto lateOrLeaveEarlyDto = LateOrLeaveEarlyDto.fromDomain(result);
			return new ScreenLateOrLeaveEarlyDto(lateOrLeaveEarlyDto, listApplicationReasonDto);
		}
	}
}