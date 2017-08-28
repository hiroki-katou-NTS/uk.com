package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.StringUtil;
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
 *         Insert or Update data to DB BASIC_SCHEDULE. If error exist, return
 *         error
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
		Optional<WorkType> workType = null;
		Optional<WorkTime> workTime = null;

		String companyId = AppContexts.user().companyId();
		List<String> errList = new ArrayList<String>();
		List<RegisterBasicScheduleCommand> bScheduleCommand = context.getCommand();
		for (RegisterBasicScheduleCommand bSchedule : bScheduleCommand) {
			BasicSchedule basicScheduleObj = BasicSchedule.createFromJavaType(bSchedule.getEmployeeId(),
					bSchedule.getDate(), bSchedule.getWorkTypeCode(), bSchedule.getWorkTimeCode());

			// Check WorkType
			workType = workTypeRepo.findByPK(companyId, bSchedule.getWorkTypeCode());

			if (!workType.isPresent()) {
				// set error to list
				errList.add("Msg_436");
				continue;
			}

			if (workType.get().getDeprecate() != DeprecateClassification.Deprecated) {
				// set error to list
				errList.add("Msg_468");
				continue;
			}

			// Check WorkTime
			if (StringUtil.isNullOrEmpty(bSchedule.getWorkTimeCode(), true)) {
				continue;
			}
			
			workTime = workTimeRepo.findByCode(companyId, bSchedule.getWorkTimeCode());

			if (!workTime.isPresent()) {
				// Set error to list
				errList.add("Msg_437");
				continue;
			}

			if (workTime.get().getDispAtr().value != DisplayAtr.DisplayAtr_Display.value) {
				// Set error to list
				errList.add("Msg_469");
				continue;
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

			// Check exist of basicSchedule
			Optional<BasicSchedule> basicSchedule = basicScheduleRepo.find(bSchedule.getEmployeeId(),
					bSchedule.getDate());
			// Insert/Update
			if (basicSchedule.isPresent()) {
				basicScheduleRepo.update(basicScheduleObj);
			} else {
				basicScheduleRepo.insert(basicScheduleObj);
			}
		}

		return errList;
	}
}
