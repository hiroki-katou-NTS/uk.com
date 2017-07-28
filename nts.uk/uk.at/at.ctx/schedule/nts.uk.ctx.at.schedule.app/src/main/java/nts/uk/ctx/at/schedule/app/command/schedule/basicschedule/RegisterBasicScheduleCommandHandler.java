package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
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

	@Override
	protected List<String> handle(CommandHandlerContext<List<RegisterBasicScheduleCommand>> context) {
//		long tStart = System.currentTimeMillis();

		String companyId = AppContexts.user().companyId();
		List<String> errList = new ArrayList<String>();
		List<RegisterBasicScheduleCommand> bScheduleCommand = context.getCommand();
			 for (RegisterBasicScheduleCommand bSchedule : bScheduleCommand) {
			 // Check WorkType
			 Optional<WorkType> workType = workTypeRepo.findByPK(companyId, bSchedule.getWorkTypeCd());
			 
			 if (!workType.isPresent()) {
				 // set error to list
				 errList.add("WorkTypeCode " + bSchedule.getWorkTypeCd() + " doesn't exist!");
				 continue;
			 }
			
			 if (workType.get().getDisplayAtr() != DisplayAtr.DisplayAtr_Display) {
				 // set error to list
				 errList.add("WorkTypeCode " + bSchedule.getWorkTypeCd() + " doesn't displayed!");
				 continue;
			 }
			
			 // Check WorkTime
			 // WorkTimeCd = "000" : it is day off
			 if (bSchedule.getWorkTimeCd() != "000") {
				 Optional<WorkTime> workTime = workTimeRepo.findByCode(companyId, bSchedule.getWorkTimeCd());
				 
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
			
			 // Insert/Update
			 BasicSchedule basicScheduleObj = BasicSchedule.createFromJavaType(bSchedule.getEmployeeId(), bSchedule.getDate(), bSchedule.getWorkTypeCd(), bSchedule.getWorkTimeCd());
			 // Check exist of basicSchedule
			 Optional<BasicSchedule> basicSchedule = basicScheduleRepo.getByPK(bSchedule.getEmployeeId(), bSchedule.getDate());
			 if (basicSchedule.isPresent()) {
				 basicScheduleRepo.updateBSchedule(basicScheduleObj);
			 } else {
				 basicScheduleRepo.insertBSchedule(basicScheduleObj);
			 }
		 }

		// test insert/update 31000 data
//		List<RegisterBasicScheduleCommand> dtBasicSche = new ArrayList<>();
//		String[] list = { "001", "002", "003", "004", "005", "006", "007", "008", "009", "999" };
//		GeneralDate[] listDate = { GeneralDate.ymd(2017, 1, 1), GeneralDate.ymd(2017, 1, 2),
//				GeneralDate.ymd(2017, 1, 3), GeneralDate.ymd(2017, 1, 4), GeneralDate.ymd(2017, 1, 5),
//				GeneralDate.ymd(2017, 1, 6), GeneralDate.ymd(2017, 1, 7), GeneralDate.ymd(2017, 1, 8),
//				GeneralDate.ymd(2017, 1, 9), GeneralDate.ymd(2017, 1, 10), GeneralDate.ymd(2017, 1, 11),
//				GeneralDate.ymd(2017, 1, 12), GeneralDate.ymd(2017, 1, 13), GeneralDate.ymd(2017, 1, 14),
//				GeneralDate.ymd(2017, 1, 15), GeneralDate.ymd(2017, 1, 16), GeneralDate.ymd(2017, 1, 17),
//				GeneralDate.ymd(2017, 1, 18), GeneralDate.ymd(2017, 1, 19), GeneralDate.ymd(2017, 1, 20),
//				GeneralDate.ymd(2017, 1, 21), GeneralDate.ymd(2017, 1, 22), GeneralDate.ymd(2017, 1, 23),
//				GeneralDate.ymd(2017, 1, 24), GeneralDate.ymd(2017, 1, 25), GeneralDate.ymd(2017, 1, 26),
//				GeneralDate.ymd(2017, 1, 27), GeneralDate.ymd(2017, 1, 28), GeneralDate.ymd(2017, 1, 29),
//				GeneralDate.ymd(2017, 1, 30), GeneralDate.ymd(2017, 1, 31) };
//		Random r = new Random();
//		for (int i = 0; i < 1000; i++) {
//			for (int j = 0; j < 31; j++) {
//				dtBasicSche.add(new RegisterBasicScheduleCommand("00000000-0000-0000-0000-0000000" + (10001 + i),
//						listDate[j], list[r.nextInt(10)], list[r.nextInt(10)]));
//			}
//		}
//
//		for (RegisterBasicScheduleCommand bSchedule : dtBasicSche) {
//
//			// Check WorkType
//			Optional<WorkType> workType = workTypeRepo.findByPK(companyId, bSchedule.getWorkTypeCd());
//			if (!workType.isPresent()) {
//				// set error to list
//				errList.add("WorkTypeCode " + bSchedule.getWorkTypeCd() + " doesn't exist!");
//
//				continue;
//			}
//
//			if (workType.get().getDisplayAtr() != DisplayAtr.DisplayAtr_Display) {
//				// set error to list
//				errList.add("WorkTypeCode " + bSchedule.getWorkTypeCd() + " doesn't displayed!");
//
//				continue;
//			}
//
//			// Check WorkTime
//			// WorkTimeCd = "000" : it is day off
//			if (bSchedule.getWorkTimeCd() != "000") {
//				Optional<WorkTime> workTime = workTimeRepo.findByCode(companyId, bSchedule.getWorkTimeCd());
//				if (!workTime.isPresent()) {
//					// Set error to list
//					errList.add("WorkTimeCode " + bSchedule.getWorkTimeCd() + " doesn't exist!");
//
//					continue;
//				}
//
//				if (workTime.get().getDispAtr().value != DisplayAtr.DisplayAtr_Display.value) {
//					// Set error to list
//					errList.add("WorkTimeCode " + bSchedule.getWorkTimeCd() + " doesn't exist!");
//
//					continue;
//				}
//			}
//
//			// Check workType-workTime
//
//			// Insert/Update
//			BasicSchedule basicScheduleObj = BasicSchedule.createFromJavaType(bSchedule.getEmployeeId(),
//					bSchedule.getDate(), bSchedule.getWorkTypeCd(), bSchedule.getWorkTimeCd());
//			// Check exist of basicSchedule
//			Optional<BasicSchedule> basicSchedule = basicScheduleRepo.getByPK(bSchedule.getEmployeeId(),
//					bSchedule.getDate());
//			if (basicSchedule.isPresent()) {
//				basicScheduleRepo.updateBSchedule(basicScheduleObj);
//			} else {
//				basicScheduleRepo.insertBSchedule(basicScheduleObj);
//			}
//		}
//		
//		long tEnd = System.currentTimeMillis();
//		long tDelta = tEnd - tStart;
//		double elapsedSeconds = tDelta / 1000.0;
//		System.out.println(elapsedSeconds + "s");
		return errList;
	}
}
