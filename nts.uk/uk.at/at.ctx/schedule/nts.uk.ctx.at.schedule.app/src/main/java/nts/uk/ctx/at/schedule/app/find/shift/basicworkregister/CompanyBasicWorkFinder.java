/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.basicworkregister;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.custom.IInternationalization;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.BasicWorkSettingFindDto;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.CompanyBasicWorkFindDto;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;

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
	public CompanyBasicWorkFindDto find(Integer workdayDivision) {
		// get login user info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companyId by user login
		String companyId = loginUserContext.companyId();

		CompanyBasicWorkFindDto outputData = new CompanyBasicWorkFindDto();

		// Find CompanyBasicWork By companyId
		Optional<CompanyBasicWork> companyBasicWork = this.repository.find(companyId, workdayDivision);// findAll!!

		// Check null
		if (!companyBasicWork.isPresent()) {
			return null;
		} else {
			// Save to Memento
			companyBasicWork.get().saveToMemento(outputData);
		}

		List<BasicWorkSettingFindDto> basicWorkSettingFindDto = outputData.getBasicWorkSetting();

		basicWorkSettingFindDto.stream().forEach(item -> {
			// Find WorkType by worktypeCode
			Optional<WorkType> worktype = this.worktypeRepo.findByPK(companyId, item.getWorkTypeCode());

			// Find WorkTime by workingCode
			Optional<WorkTime> worktime = this.worktimeRepo.findByCode(companyId, item.getWorkingCode());

			// Set WorkTypeDisplayName
			if (worktype.isPresent()) {
				item.setWorkTypeDisplayName(worktype.get().getName().v());				
			} else {
				item.setWorkTypeDisplayName(internationalization.getItemName("#KSM006_13").get());
			}

			// Set WorkingDisplayName
			if (worktime.isPresent()) {
				if (item.getWorkDayDivision() != WorkdayDivision.WORKINGDAYS.value) {
					// TODO: to be continue. wait for QA #84782 workType (ko phai ngay nghi 1 ngay)
				}
				item.setWorkingDisplayName(worktime.get().getWorkTimeDisplayName().getWorkTimeName().v());
			} else {
				item.setWorkingDisplayName(internationalization.getItemName("#KSM006_13").get());
			}
		});
		
		return outputData;
	}
	
	// Find All
//	
//	public List<CompanyBasicWorkFindDto> findAll() {
//		// get login user info
//		LoginUserContext loginUserContext = AppContexts.user();
//
//		// get companyId by user login
//		String companyId = loginUserContext.companyId();
//	}

}
