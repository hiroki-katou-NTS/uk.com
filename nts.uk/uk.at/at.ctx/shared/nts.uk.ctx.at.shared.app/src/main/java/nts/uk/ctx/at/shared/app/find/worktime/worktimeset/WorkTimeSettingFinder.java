/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.worktimeset;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.SimpleWorkTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkTimeSettingFinder.
 */
@Stateless
public class WorkTimeSettingFinder {

	/** The work time setting repository. */
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	/**
	 * Find all simple.
	 *
	 * @return the list
	 */
	public List<SimpleWorkTimeSettingDto> findAllSimple() {
		String companyId = AppContexts.user().companyId();
		List<WorkTimeSetting> lstWorktimeSetting = workTimeSettingRepository.findAll(companyId);
		return lstWorktimeSetting.stream().map(item -> {
			return SimpleWorkTimeSettingDto.builder().companyId(item.getCompanyId())
					.worktimeCode(item.getWorktimeCode().v())
					.workTimeName(item.getWorkTimeDisplayName().getWorkTimeName().v()).build();
		}).collect(Collectors.toList());
	}

	/**
	 * Find all.
	 *
	 * @return the work time setting dto
	 */
	public WorkTimeSettingDto findByCode(String worktimeCode) {
		String companyId = AppContexts.user().companyId();
		WorkTimeSetting worktimeSetting = workTimeSettingRepository.findByCode(companyId, worktimeCode).get();
		WorkTimeSettingDto dto = new WorkTimeSettingDto();
		worktimeSetting.saveToMemento(dto);
		return dto;
	}

}
