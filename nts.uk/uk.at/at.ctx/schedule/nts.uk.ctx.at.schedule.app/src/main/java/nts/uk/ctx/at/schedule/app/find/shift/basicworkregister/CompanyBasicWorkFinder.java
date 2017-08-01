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
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.CompanyBasicWorkFindDto;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkRepository;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class CompanyBasicWorkFinder.
 */
@Stateless
public class CompanyBasicWorkFinder {

	/** The repository. */
	@Inject
	private CompanyBasicWorkRepository repository;

	/** The worktype repo. */
	@Inject
	private WorkTypeRepository worktypeRepo;

	/** The worktime repo. */
	@Inject
	private WorkTimeRepository worktimeRepo;

	/** The internationalization. */
	@Inject
	IInternationalization internationalization;

	/**
	 * Find.
	 *
	 * @param workdayDivision
	 *            the worktype code
	 * @return the company basic work find dto
	 */
	public CompanyBasicWorkFindDto findAll() {
		// get login user info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companyId by user login
		String companyId = loginUserContext.companyId();

		CompanyBasicWorkFindDto outputData = new CompanyBasicWorkFindDto();
		
		// Find CompanyBasicWork By companyId
		Optional<CompanyBasicWork> companyBasicWork = this.repository.findAll(companyId);// findAll!!

		// Check null
		if (!companyBasicWork.isPresent()) {
			return null;
		} else {
			// Save to Memento
			companyBasicWork.get().saveToMemento(outputData);
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
			WorkType worktype = worktypeList.stream().filter(a-> {
				return a.getWorkTypeCode().equals(item.getWorkTypeCode());
			}).findFirst().orElse(null);
			// Set WorkTypeDisplayName to Dto
			if (worktype == null) {
//				item.setWorkTypeDisplayName(internationalization.getItemName("#KSM006_13").get());
				item.setWorkTypeDisplayName("something");
			} else {
				item.setWorkTypeDisplayName(worktype.getName().v());
			}
			
			// Get WorkTime
			WorkTime worktime = workingList.stream().filter(wt -> {
				return wt.getSiftCD().equals(item.getWorkingCode());
			}).findFirst().orElse(null);
			
			// Set WorkingDisplayName
			if (worktime == null) {
//				item.setWorkTypeDisplayName(internationalization.getItemName("#KSM006_13").get());
				item.setWorkingDisplayName("something");
			} else {
				item.setWorkingDisplayName(worktime.getWorkTimeDisplayName().getWorkTimeName().v());
			}
		});
		
		return outputData;
	}
	

}
