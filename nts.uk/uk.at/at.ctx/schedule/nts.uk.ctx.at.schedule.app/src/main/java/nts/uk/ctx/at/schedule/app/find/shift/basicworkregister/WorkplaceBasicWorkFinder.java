/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.basicworkregister;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.BasicWorkSettingFindDto;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.WorkplaceBasicWorkFindDto;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceId;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

/**
 * The Class WorkplaceBasicWorkFinder.
 */
@Stateless
public class WorkplaceBasicWorkFinder {

	/** The repository. */
	@Inject
	private WorkplaceBasicWorkRepository repository;
	
	/** The worktype repo. */
	@Inject
	private WorkTypeRepository worktypeRepo;

	/** The worktime repo. */
	@Inject
	private WorkTimeSettingRepository worktimeRepo;
	
	/**
	 * Find.
	 *
	 * @param workplaceId the workplace id
	 * @return the workplace basic work find dto
	 */
	public WorkplaceBasicWorkFindDto find(String workplaceId) {
		// get companyId by user login
		String companyId = AppContexts.user().companyId();
		
		WorkplaceBasicWorkFindDto outputData = new WorkplaceBasicWorkFindDto();
		
		Optional<WorkplaceBasicWork> workplaceBasicWork = this.repository.findById(workplaceId);
		
		if (!workplaceBasicWork.isPresent()) {
			return null;
		} else {
			// Save To Memento
			workplaceBasicWork.get().saveToMemento(outputData);
		}
		// List BasicWorkSetting
		List<BasicWorkSettingFindDto> basicWorkSettingFindDto = outputData.getBasicWorkSetting();

		// List worktypeCode
		List<String> worktypeCodeList = basicWorkSettingFindDto.stream().map(item -> {
			return item.getWorkTypeCode();
		}).distinct().filter(a -> {
			return a.length() > 0;
		}).collect(Collectors.toList());
		
		// If WorktypeCodeList is null
		if (worktypeCodeList.isEmpty()) {
			return null;
		}

		// Find WorkType
		List<WorkType> worktypeList = this.worktypeRepo.getPossibleWorkType(companyId, worktypeCodeList);

		// List workingCode
		List<String> workingCodeList = basicWorkSettingFindDto.stream().map(item -> {
			return item.getWorkingCode();
		}).distinct().filter(a -> {
			return a.length() > 0;
		}).collect(Collectors.toList());

		// Find WorkTime
		List<WorkTimeSetting> workingList = this.worktimeRepo.findByCodes(companyId, workingCodeList);

		basicWorkSettingFindDto.stream().filter(a -> {
			return a.getWorkTypeCode().length() > 0;
		}).forEach(item -> {
			// Get WorkType
			WorkType worktype = worktypeList.stream().filter(a -> {
				return a.getWorkTypeCode().equals(item.getWorkTypeCode());
			}).findFirst().orElse(null);
			// Set WorkTypeDisplayName to Dto
			if (worktype == null) {
				item.setWorkTypeDisplayName(TextResource.localize("KSM006_13"));
			} else {
				item.setWorkTypeDisplayName(worktype.getName().v());
			}

			// Get WorkTime
			WorkTimeSetting worktime = workingList.stream().filter(wt -> {
				return wt.getWorktimeCode().v().equals(item.getWorkingCode());
			}).findFirst().orElse(null);

			// Set WorkingDisplayName
			if (worktime == null) {
				item.setWorkTypeDisplayName(TextResource.localize("KSM006_13"));
			} else {
				item.setWorkingDisplayName(worktime.getWorkTimeDisplayName().getWorkTimeName().v());
			}
		});
		
		return outputData;
	}
	
	/**
	 * Find setting.
	 *
	 * @return the list
	 */
	public List<String> findSetting() {
		List<WorkplaceId> workplaceBWList = this.repository.findSetting();
		return workplaceBWList.stream().map(workplaceBW -> {
			return workplaceBW.v();
		}).collect(Collectors.toList());
	}
}
