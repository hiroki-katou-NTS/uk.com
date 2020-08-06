package nts.uk.screen.at.app.query.kcp013;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 就業時間帯を全件取得
 * 
 * @author thanhlv
 *
 */
@Stateless
public class GetAllWorkingHours {

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	public List<AcquireWorkingHoursDto> getWorkingHours(AcquireWorkingHoursRequest request) {

		return null;
	}

	public List<AcquireWorkingHoursDto> getAllWorkingHoursDtos() {

		String companyId = AppContexts.user().companyId();

		List<WorkTimeSetting> workTimeSettings = workTimeSettingRepository.findByCompanyId(companyId);

		return null;
	}
}
