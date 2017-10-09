/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.ot.autocalsetting.job;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.ot.autocalsetting.job.JobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.job.JobAutoCalSettingRepository;
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

		Optional<JobAutoCalSetting> opt = this.jobAutoCalSettingRepository.getAllJobAutoCalSetting(companyId, jobId);

		if (!opt.isPresent()) {
			return null;
		}

		JobAutoCalSettingDto dto = new JobAutoCalSettingDto();

		opt.get().saveToMemento(dto);

		return dto;
	}

	/**
	 * Delete by code.
	 *
	 * @param jobId the job id
	 */
	public void deleteByCode(String jobId) {
		String companyId = AppContexts.user().companyId();
		jobAutoCalSettingRepository.delete(companyId, jobId);
	}
}
