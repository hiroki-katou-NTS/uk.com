package nts.uk.ctx.at.aggregation.app.find.scheduletable.outputsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.aggregation.dom.scheduletable.OutputSettingCode;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSetting;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author quytb
 *
 */
@Stateless
public class ScheduleTableOutputSettingFinder {
	@Inject
	private ScheduleTableOutputSettingRepository repository;
	
	public ScheduleTableOutputSettingDto findByCidAndCode(String code) {
		Optional<ScheduleTableOutputSetting> optional = repository.get(AppContexts.user().companyId(), new OutputSettingCode(code));
		return optional.isPresent() ? ScheduleTableOutputSettingDto.setData(optional.get()): new ScheduleTableOutputSettingDto();
	}

	public List<ScheduleTableOutputSettingDto> findByCid() {
		List<ScheduleTableOutputSetting> scheduleTableOutputSettings = repository
				.getList(AppContexts.user().companyId());
		if (scheduleTableOutputSettings.isEmpty()) {
			List<ScheduleTableOutputSettingDto> results = new ArrayList<ScheduleTableOutputSettingDto>();
			results.add(ScheduleTableOutputSettingDto.builder().isAttendance(AppContexts.user().roles().isInChargeAttendance()).build());
			return results;			
		} else {
			return scheduleTableOutputSettings.stream().map(item -> ScheduleTableOutputSettingDto.setData(item))
					.collect(Collectors.toList());
		}
	}
}
