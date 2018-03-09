package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.shorttimework.repo.ShortTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistoryRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class ReflectShortWorkingTimeDomainServiceimpl implements ReflectShortWorkingTimeDomainService {
	@Inject
	private ShortTimeOfDailyPerformanceRepository shortTimeOfDailyPerformanceRepo;
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeRepo;
	@Inject
	private WorkInformationRepository workInformationRepo;
	@Inject
	private WorkTypeRepository workTypeRepo;
	@Inject
	private BasicScheduleService basicScheduleService;
	@Inject
	private SWorkTimeHistoryRepository SWorkTimeHistoryRepo;
	@Inject
	private SWorkTimeHistItemRepository SWorkTimeHistItemRepo;

	@Override
	public void reflect(String companyId, GeneralDate date, String employeeId) {
		boolean confirmReflectWorkingTime = confirmReflectWorkingTime(companyId, date, employeeId);
		if (confirmReflectWorkingTime) {
			Optional<ShortWorkTimeHistory> findByBaseDate = this.SWorkTimeHistoryRepo.findByBaseDate(employeeId, date);
			if (findByBaseDate.isPresent()) {
				ShortWorkTimeHistory shortWorkTimeHistory = findByBaseDate.get();
				// this.SWorkTimeHistItemRepo.findByKey(employeeId, ))
			}
		}
	}

	// 短時間勤務時間帯を反映するか確認する
	public boolean confirmReflectWorkingTime(String companyId, GeneralDate date, String employeeId) {
		Optional<ShortTimeOfDailyPerformance> shortTimeOfDailyPerformanceOptional = this.shortTimeOfDailyPerformanceRepo
				.find(employeeId, date);
		if (shortTimeOfDailyPerformanceOptional.isPresent()
				&& shortTimeOfDailyPerformanceOptional.get().getShortWorkingTimeSheets() != null
				&& !shortTimeOfDailyPerformanceOptional.get().getShortWorkingTimeSheets().isEmpty()) {
			return false;
		}
		Optional<TimeLeavingOfDailyPerformance> TimeLeavingOfDailyPerformanceOptional = this.timeRepo
				.findByKey(employeeId, date);
		if (!TimeLeavingOfDailyPerformanceOptional.isPresent()) {
			return false;
		}
		TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance = TimeLeavingOfDailyPerformanceOptional.get();
		List<TimeLeavingWork> timeLeavingWorks = timeLeavingOfDailyPerformance.getTimeLeavingWorks();
		int size = timeLeavingWorks.size();
		Boolean checkReflectWorkInfoOfDailyPerformance = null;
		for (int i = 0; i < size; i++) {
			TimeLeavingWork timeLeavingWork = timeLeavingWorks.get(i);
			if (timeLeavingWork.getAttendanceStamp() != null && timeLeavingWork.getAttendanceStamp().isPresent()
					&& timeLeavingWork.getAttendanceStamp().get().getActualStamp() != null
					&& timeLeavingWork.getAttendanceStamp().get().getActualStamp().isPresent()) {
				checkReflectWorkInfoOfDailyPerformance = this.reflectWorkInfoOfDailyPerformance(companyId, date,
						employeeId);
				break;
			}
			if (timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent()
					&& timeLeavingWork.getLeaveStamp().get().getActualStamp() != null
					&& timeLeavingWork.getLeaveStamp().get().getActualStamp().isPresent()) {
				checkReflectWorkInfoOfDailyPerformance = this.reflectWorkInfoOfDailyPerformance(companyId, date,
						employeeId);
				break;
			}

		}
		if (checkReflectWorkInfoOfDailyPerformance != null) {
			return checkReflectWorkInfoOfDailyPerformance.booleanValue();
		}
		return false;
	}

	// 休出かどうかの判断
	public boolean checkHolidayOrNot(String companyId, String workTypeCd) {
		Optional<WorkType> WorkTypeOptional = this.workTypeRepo.findByPK(companyId, workTypeCd);
		if (!WorkTypeOptional.isPresent()) {
			return false;
		}
		// check null?
		WorkType workType = WorkTypeOptional.get();
		DailyWork dailyWork = workType.getDailyWork();
		WorkTypeClassification oneDay = dailyWork.getOneDay();
		// 休日出勤
		if (oneDay.value == 11) {
			return true;
		}
		return false;
	}

	public boolean reflectWorkInfoOfDailyPerformance(String companyId, GeneralDate date, String employeeId) {
		Optional<WorkInfoOfDailyPerformance> WorkInfoOfDailyPerformanceOptional = this.workInformationRepo
				.find(employeeId, date);
		WorkInfoOfDailyPerformance WorkInfo = WorkInfoOfDailyPerformanceOptional.get();
		// recordWorkInformation or scheduleWorkInformation? fixed
		// recordWorkInformation
		WorkInformation scheduleWorkInformation = WorkInfo.getRecordWorkInformation();
		WorkTypeCode workTypeCode = scheduleWorkInformation.getWorkTypeCode();
		boolean checkHolidayOrNot = this.checkHolidayOrNot(companyId, workTypeCode.v());
		if (checkHolidayOrNot) {
			return false;
		}
		// 1日半日出勤・1日休日系の判定
		WorkStyle checkWorkDay = this.basicScheduleService
				.checkWorkDay(WorkInfo.getRecordWorkInformation().getWorkTypeCode().v());
		// 1日休日系
		if (checkWorkDay.value == 0) {
			return false;
		}
		return true;
	}

}
