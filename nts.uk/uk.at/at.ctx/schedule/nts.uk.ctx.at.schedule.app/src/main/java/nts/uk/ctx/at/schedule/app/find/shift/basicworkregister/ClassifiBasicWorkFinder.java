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

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.BasicWorkSettingFindDto;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.ClassifiBasicWorkFindDto;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.i18n.TextResource;

/**
 * The Class ClassifiBasicWorkFinder.
 */
@Stateless
public class ClassifiBasicWorkFinder {

	/** The repository. */
	@Inject
	private ClassifiBasicWorkRepository repository;

	/** The worktype repo. */
	@Inject
	private WorkTypeRepository worktypeRepo;

	/** The worktime repo. */
	@Inject
	private WorkTimeSettingRepository worktimeRepo;
	

	/**
	 * Find.
	 *
	 * @param classificationCode
	 *            the classification code
	 * @param workdayDivision
	 *            the worktype code
	 * @return the classifi basic work find dto
	 */
	public ClassifiBasicWorkFindDto findAll(String classificationCode) {
		// get login user info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companyId by user login
		String companyId = loginUserContext.companyId();

		ClassifiBasicWorkFindDto outputData = new ClassifiBasicWorkFindDto();

		Optional<ClassificationBasicWork> classifyBasicWork = this.repository.findAll(companyId, classificationCode);

		if (!classifyBasicWork.isPresent()) {
			return null;
		} else {
			classifyBasicWork.get().saveToMemento(outputData);
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

		basicWorkSettingFindDto.stream().forEach(item -> {
			// Get WorkType
			WorkType worktype = worktypeList.stream().filter(a -> {
				return a.getWorkTypeCode().equals(item.getWorkTypeCode());
			}).findFirst().orElse(null);
			
			// Set WorkTypeDisplayName to Dto
			if (worktype == null && !StringUtil.isNullOrEmpty(item.getWorkTypeCode(), true)) {
				item.setWorkTypeDisplayName(TextResource.localize("KSM006_13"));
			} else if (StringUtil.isNullOrEmpty(item.getWorkTypeCode(), true)) {
				item.setWorkTypeDisplayName("");
			} else {
				item.setWorkTypeDisplayName(worktype.getName().v());
			}

			// Get WorkTime
			WorkTimeSetting worktime = workingList.stream().filter(wt -> {
				return wt.getWorktimeCode().v().equals(item.getWorkingCode());
			}).findFirst().orElse(null);

			// Set WorkingDisplayName
			if (worktime == null && !StringUtil.isNullOrEmpty(item.getWorkingCode(), true)) {
				item.setWorkingDisplayName(TextResource.localize("KSM006_13"));
			} else if (StringUtil.isNullOrEmpty(item.getWorkingCode(), true)) {
				item.setWorkingDisplayName("");
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
		// get login user info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companyId by user login
		String companyId = loginUserContext.companyId();
		
		// Find Classification Code List
		List<ClassificationCode> classifyCodeList = this.repository.findSetting(companyId);
		return classifyCodeList.stream().map(c -> {
			return c.v(); 
		}).collect(Collectors.toList());
	}
}
