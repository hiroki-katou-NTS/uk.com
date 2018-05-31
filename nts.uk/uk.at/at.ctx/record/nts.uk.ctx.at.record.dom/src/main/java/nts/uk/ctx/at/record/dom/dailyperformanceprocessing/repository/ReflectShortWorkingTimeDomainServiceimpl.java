package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.shorttimework.ShortWorkingTimeSheet;
import nts.uk.ctx.at.record.dom.shorttimework.enums.ChildCareAttribute;
import nts.uk.ctx.at.record.dom.shorttimework.primitivevalue.ShortWorkTimFrameNo;
import nts.uk.ctx.at.record.dom.shorttimework.repo.ShortTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.shortworktime.SChildCareFrame;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistoryRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.history.DateHistoryItem;

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
	public ShortTimeOfDailyPerformance reflect(String companyId, GeneralDate date, String employeeId, WorkInfoOfDailyPerformance WorkInfo, TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {
		boolean confirmReflectWorkingTime = confirmReflectWorkingTime(companyId, date, employeeId, WorkInfo, timeLeavingOfDailyPerformance);
		if (confirmReflectWorkingTime) {
			Optional<ShortWorkTimeHistory> findByBaseDate = this.SWorkTimeHistoryRepo.findByBaseDate(employeeId, date);
			if (findByBaseDate.isPresent()) {
				ShortWorkTimeHistory shortWorkTimeHistory = findByBaseDate.get();
				Optional<ShortWorkTimeHistoryItem> ShortWorkTimeHistoryItemOptional = this.SWorkTimeHistItemRepo.findByKey(employeeId, shortWorkTimeHistory.getHistoryItems().get(0).identifier());
				if(ShortWorkTimeHistoryItemOptional.isPresent()){
					ShortWorkTimeHistoryItem shortWorkTimeHistoryItem = ShortWorkTimeHistoryItemOptional.get();
					List<ShortWorkingTimeSheet> lstShortWorkingTimeSheet = new ArrayList<ShortWorkingTimeSheet>();
					List<SChildCareFrame> lstTimeSlot = shortWorkTimeHistoryItem.getLstTimeSlot();
					for (SChildCareFrame sChildCareFrame : lstTimeSlot) {
						ShortWorkingTimeSheet shortWorkingTimeSheet = new ShortWorkingTimeSheet(new ShortWorkTimFrameNo(sChildCareFrame.getTimeSlot()),EnumAdaptor.valueOf(shortWorkTimeHistoryItem.getChildCareAtr().value, ChildCareAttribute.class) , sChildCareFrame.getStartTime(), sChildCareFrame.getEndTime(), new AttendanceTime(0), new AttendanceTime(0));
						lstShortWorkingTimeSheet.add(shortWorkingTimeSheet);
					}
					return new ShortTimeOfDailyPerformance(employeeId, lstShortWorkingTimeSheet, date);
				}
				return null;
			}
			return null;
		}
		return null;
	}

	// 短時間勤務時間帯を反映するか確認する
	public boolean confirmReflectWorkingTime(String companyId, GeneralDate date, String employeeId,
			WorkInfoOfDailyPerformance WorkInfo, TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {
		Optional<ShortTimeOfDailyPerformance> shortTimeOfDailyPerformanceOptional = this.shortTimeOfDailyPerformanceRepo
				.find(employeeId, date);
		if (shortTimeOfDailyPerformanceOptional.isPresent()
				&& shortTimeOfDailyPerformanceOptional.get().getShortWorkingTimeSheets() != null
				&& !shortTimeOfDailyPerformanceOptional.get().getShortWorkingTimeSheets().isEmpty()) {
			return false;
		}
		List<TimeLeavingWork> timeLeavingWorks = null;
		if (timeLeavingOfDailyPerformance != null && timeLeavingOfDailyPerformance.getTimeLeavingWorks() != null
				&& !timeLeavingOfDailyPerformance.getTimeLeavingWorks().isEmpty()) {
			timeLeavingWorks = timeLeavingOfDailyPerformance.getTimeLeavingWorks();
		}else{
			Optional<TimeLeavingOfDailyPerformance> timeLeavingOfDailyPerformanceOptional = this.timeRepo
					.findByKey(employeeId, date);
			if (!timeLeavingOfDailyPerformanceOptional.isPresent()) {
				return false;
			}
			timeLeavingWorks = timeLeavingOfDailyPerformanceOptional.get().getTimeLeavingWorks();
		}
		 
		int size = timeLeavingWorks.size();
		Boolean checkReflectWorkInfoOfDailyPerformance = null;
		for (int i = 0; i < size; i++) {
			TimeLeavingWork timeLeavingWork = timeLeavingWorks.get(i);
			if (timeLeavingWork.getAttendanceStamp() != null && timeLeavingWork.getAttendanceStamp().isPresent()
					&& timeLeavingWork.getAttendanceStamp().get().getActualStamp() != null
					&& timeLeavingWork.getAttendanceStamp().get().getActualStamp().isPresent()
					&& timeLeavingWork.getAttendanceStamp().get().getActualStamp().get().getTimeWithDay() != null) {
				checkReflectWorkInfoOfDailyPerformance = this.reflectWorkInfoOfDailyPerformance(companyId, date,
						employeeId, WorkInfo);
				break;
			}
			if (timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent()
					&& timeLeavingWork.getLeaveStamp().get().getActualStamp() != null
					&& timeLeavingWork.getLeaveStamp().get().getActualStamp().isPresent()
					&& timeLeavingWork.getLeaveStamp().get().getActualStamp().get().getTimeWithDay() != null) {
				checkReflectWorkInfoOfDailyPerformance = this.reflectWorkInfoOfDailyPerformance(companyId, date,
						employeeId, WorkInfo);
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

	public boolean reflectWorkInfoOfDailyPerformance(String companyId, GeneralDate date, String employeeId, WorkInfoOfDailyPerformance WorkInfo) {
		WorkInformation scheduleWorkInformation = WorkInfo.getRecordInfo();
		WorkTypeCode workTypeCode = scheduleWorkInformation.getWorkTypeCode();
		boolean checkHolidayOrNot = this.checkHolidayOrNot(companyId, workTypeCode.v());
		if (checkHolidayOrNot) {
			return false;
		}
		// 1日半日出勤・1日休日系の判定
		WorkStyle checkWorkDay = this.basicScheduleService
				.checkWorkDay(WorkInfo.getRecordInfo().getWorkTypeCode().v());
		// 1日休日系
		if (checkWorkDay.value == 0) {
			return false;
		}
		return true;
	}

}
