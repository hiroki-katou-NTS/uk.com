package nts.uk.ctx.at.schedule.app.find.shift.basicworkregister;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.custom.IInternationalization;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.BasicWorkSettingFindDto;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.WorkplaceBasicWorkFindDto;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkplaceBasicWorkFinder {

	@Inject
	private WorkplaceBasicWorkRepository repository;
	
	@Inject
	private WorkTypeRepository worktypeRepo;

	/** The worktime repo. */
	@Inject
	private WorkTimeRepository worktimeRepo;

	/** The internationalization. */
	@Inject
	IInternationalization internationalization;
	
	public WorkplaceBasicWorkFindDto find(String workplaceId, Integer workdayDivision) {
		// get companyId by user login
		String companyId = AppContexts.user().companyId();
		
		WorkplaceBasicWorkFindDto outputData = new WorkplaceBasicWorkFindDto();
		
		Optional<WorkplaceBasicWork> workplaceBasicWork = this.repository.find(workplaceId, workdayDivision);
		
		if (!workplaceBasicWork.isPresent()) {
			return null;
		} else {
			// Save To Memento
			workplaceBasicWork.get().saveToMemento(outputData);
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
}
