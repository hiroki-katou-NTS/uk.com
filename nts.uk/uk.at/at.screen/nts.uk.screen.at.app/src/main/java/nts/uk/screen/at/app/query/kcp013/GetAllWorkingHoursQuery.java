package nts.uk.screen.at.app.query.kcp013;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * ScreenQuery職場で使える就業時間帯取得.
 * UKDesign.UniversalK.共通.KCP_共通部品.KCP013_就業時間帯選択.メニュー別OCD
 * 
 * ScreenQuery就業時間帯を全件取得.
 * UKDesign.UniversalK.共通.KCP_共通部品.KCP013_就業時間帯選択.メニュー別OCD
 *
 * @author thanhlv
 *
 */
@Stateless
public class GetAllWorkingHoursQuery {

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private WorkTimeWorkplaceRepository workTimeWorkplaceRepo;

	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;

	@Inject
	private WorkManagementMultipleRepository workMultipleRepo;

	/**
	 * 複数回勤務を取得
	 * 
	 * @return
	 */
	public Optional<WorkManagementMultiple> getUseDistinction() {
		String companyId = AppContexts.user().companyId();
		return workMultipleRepo.findByCode(companyId);
	}

	/**
	 * 職場が選択できる就業時間帯を取得する
	 * 
	 * @param request
	 * @return
	 */
	public AcquireWorkingHoursDto getWorkingHours(AcquireWorkingHoursRequest request) {

		AcquireWorkingHoursDto dto = new AcquireWorkingHoursDto();

		String companyId = AppContexts.user().companyId();

		// <<Public>> 職場が選択できる就業時間帯を取得する
		List<WorkTimeSetting> listWorkTime = workTimeWorkplaceRepo.getWorkTimeWorkplaceById(companyId,
				request.getWorkPlaceId());
		if (listWorkTime.size() > 0) {
			//Collections.sort(listWorkTime, Comparator.comparing(x -> x.getWorktimeCode().v()));
		}
		List<String> worktimeCodes = listWorkTime.stream().map(x -> {
			return x.getWorktimeCode().v();
		}).collect(Collectors.toList());

		// Query.該当所定時間設定を取得する
		List<PredetemineTimeSetting> predetemineTimeSettings = predetemineTimeSettingRepository
				.findByCodeList(companyId, worktimeCodes);

		dto.setListWorkTime(listWorkTime);
		dto.setPredetemineTimeSettings(predetemineTimeSettings);

		return dto;
	}

	/**
	 * 就業時間帯を全件取得
	 * 
	 * @return
	 */
	public AcquireWorkingHoursDto getAllWorkingHoursDtos() {

		String companyId = AppContexts.user().companyId();

		AcquireWorkingHoursDto dto = new AcquireWorkingHoursDto();

		// 会社で使用できる就業時間帯を全件を取得する
		List<WorkTimeSetting> listWorkTime = workTimeSettingRepository.findByCompanyId(companyId).stream()
				.filter(x -> x.getAbolishAtr() == AbolishAtr.NOT_ABOLISH)
				.sorted(Comparator.comparing(x -> x.getWorktimeCode().v())).collect(Collectors.toList());
		List<String> worktimeCodes = listWorkTime.stream().map(x -> {
			return x.getWorktimeCode().v();
		}).collect(Collectors.toList());

		// 該当所定時間設定を取得する
		List<PredetemineTimeSetting> predetemineTimeSettings = predetemineTimeSettingRepository
				.findByCodeList(companyId, worktimeCodes);

		dto.setListWorkTime(listWorkTime);
		dto.setPredetemineTimeSettings(predetemineTimeSettings);

		return dto;
	}
}
