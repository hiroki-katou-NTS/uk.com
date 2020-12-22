/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.ot.autocalsetting.job;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.job.JobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.job.JobAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JobAutoCalSetFinder.
 */
@Stateless
public class JobAutoCalSetFinder {

	/** The job auto cal setting repository. */
	@Inject
	private JobAutoCalSettingRepository jobAutoCalSettingRepository;

	/**
	 * Gets the job auto cal setting.
	 *
	 * @param jobId the job id
	 * @return the job auto cal setting
	 */
	public JobAutoCalSettingDto getJobAutoCalSetting(String jobId) {
		String companyId = AppContexts.user().companyId();

		Optional<JobAutoCalSetting> opt = this.jobAutoCalSettingRepository.getJobAutoCalSetting(companyId, jobId);

		if (!opt.isPresent()) {
			return null;
		}

		JobAutoCalSettingDto dto = new JobAutoCalSettingDto();

		opt.get().saveToMemento(dto);

		return dto;
	}
	
	public List<JobAutoCalSettingDto> getAllJobAutoCalSetting(){
		String companyId = AppContexts.user().companyId();

		List<JobAutoCalSetting> listAll = this.jobAutoCalSettingRepository.getAllJobAutoCalSetting(companyId);
		
		return listAll.stream().map(e -> {
			JobAutoCalSettingDto dto = new JobAutoCalSettingDto();
			e.saveToMemento(dto);
			
			return dto;
		}).collect(Collectors.toList());
	}
}
