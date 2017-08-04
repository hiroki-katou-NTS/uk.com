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

import nts.arc.i18n.custom.IInternationalization;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.BasicWorkSettingFindDto;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.WorkplaceBasicWorkFindDto;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceId;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

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
	private WorkTimeRepository worktimeRepo;

	/** The internationalization. */
	@Inject
	private IInternationalization internationalization;
	
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
		}).distinct().collect(Collectors.toList());

		// Find WorkType
		List<WorkType> worktypeList = this.worktypeRepo.getPossibleWorkType(companyId, worktypeCodeList);

		// List workingCode
		List<String> workingCodeList = basicWorkSettingFindDto.stream().map(item -> {
			return item.getWorkingCode();
		}).distinct().collect(Collectors.toList());

		// Find WorkTime
		List<WorkTime> workingList = this.worktimeRepo.findByCodeList(companyId, workingCodeList);

		basicWorkSettingFindDto.stream().forEach(item -> {
			// Get WorkType
			WorkType worktype = worktypeList.stream().filter(a -> {
				return a.getWorkTypeCode().equals(item.getWorkTypeCode());
			}).findFirst().orElse(null);
			// Set WorkTypeDisplayName to Dto
			if (worktype == null) {
//				item.setWorkTypeDisplayName(internationalization.getItemName("#KSM006_13").get());
				item.setWorkingDisplayName("Something's not right");
			} else {
				item.setWorkTypeDisplayName(worktype.getName().v());
			}

			// Get WorkTime
			WorkTime worktime = workingList.stream().filter(w -> {
				return w.getSiftCD().equals(item.getWorkingCode());
			}).findFirst().orElse(null);

			// Set WorkingDisplayName
			if (worktime == null) {
				// item.setWorkTypeDisplayName(internationalization.getItemName("#KSM006_13").get());
				item.setWorkingDisplayName("Something's not right");
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
