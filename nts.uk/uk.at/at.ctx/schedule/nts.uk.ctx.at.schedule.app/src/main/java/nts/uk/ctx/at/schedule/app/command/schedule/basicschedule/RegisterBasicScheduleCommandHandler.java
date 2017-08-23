package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.DisplayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class RegisterBasicScheduleCommandHandler
		extends CommandHandlerWithResult<List<RegisterBasicScheduleCommand>, List<String>> {

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeRepository workTimeRepo;

	@Inject
	private BasicScheduleRepository basicScheduleRepo;

	@Inject
	private BasicScheduleService basicScheduleService;

	@Override
	protected List<String> handle(CommandHandlerContext<List<RegisterBasicScheduleCommand>> context) {
		// long tStart = System.currentTimeMillis();
		Optional<WorkType> workType = null;
		Optional<WorkTime> workTime = null;

		String companyId = AppContexts.user().companyId();
		List<String> errList = new ArrayList<String>();
		List<RegisterBasicScheduleCommand> bScheduleCommand = context.getCommand();
		for (RegisterBasicScheduleCommand bSchedule : bScheduleCommand) {
			// Check WorkType
			workType = workTypeRepo.findByPK(companyId, bSchedule.getWorkTypeCd());

			if (!workType.isPresent()) {
				// set error to list
				errList.add("WorkTypeCode " + bSchedule.getWorkTypeCd() + " doesn't exist!");
				continue;
			}

			if (workType.get().getDeprecate() != DeprecateClassification.Deprecated) {
				// set error to list
				errList.add("WorkTypeCode " + bSchedule.getWorkTypeCd() + " doesn't displayed!");
				continue;
			}

			// Check WorkTime
			// WorkTimeCd = "000" : it is day off
			if (bSchedule.getWorkTimeCd() != "000") {
				workTime = workTimeRepo.findByCode(companyId, bSchedule.getWorkTimeCd());

				if (!workTime.isPresent()) {
					// Set error to list
					errList.add("WorkTimeCode " + bSchedule.getWorkTimeCd() + " doesn't exist!");
					continue;
				}

				if (workTime.get().getDispAtr().value != DisplayAtr.DisplayAtr_Display.value) {
					// Set error to list
					errList.add("WorkTimeCode " + bSchedule.getWorkTimeCd() + " doesn't exist!");
					continue;
				}
			}

			// Check workType-workTime
			try {
				if (workTime.isPresent() && workType.isPresent()) {
					basicScheduleService.checkPairWorkTypeWorkTime(workType.get().getWorkTypeCode().v(),
							workTime.get().getSiftCD().v());
				}
			} catch (BusinessException ex) {
				errList.add(ex.getMessage());
			}

			// Insert/Update
			BasicSchedule basicScheduleObj = BasicSchedule.createFromJavaType(bSchedule.getEmployeeId(),
					bSchedule.getDate(), bSchedule.getWorkTypeCd(), bSchedule.getWorkTimeCd());
			// Check exist of basicSchedule
			Optional<BasicSchedule> basicSchedule = basicScheduleRepo.getByPK(bSchedule.getEmployeeId(),
					bSchedule.getDate());
			if (basicSchedule.isPresent()) {
				basicScheduleRepo.updateBSchedule(basicScheduleObj);
			} else {
				basicScheduleRepo.insertBSchedule(basicScheduleObj);
			}
		}

		return errList;
	}
}
