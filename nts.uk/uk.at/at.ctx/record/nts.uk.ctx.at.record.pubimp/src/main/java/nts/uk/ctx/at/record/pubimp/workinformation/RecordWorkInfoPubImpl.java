package nts.uk.ctx.at.record.pubimp.workinformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.shorttimework.repo.ShortTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.pub.workinformation.CommonTimeSheet;
import nts.uk.ctx.at.record.pub.workinformation.InfoCheckNotRegisterPubExport;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPub;
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPubExport;
import nts.uk.ctx.at.record.pub.workinformation.WorkInfoOfDailyPerExport;
import nts.uk.ctx.at.record.pub.workinformation.WrScheduleTimeSheetExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrReasonTimeChangeExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrTimeActualStampExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrTimeLeavingWorkExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrWorkStampExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrWorkTimeInformationExport;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.worktime.TotalWorkingTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.GoOutReason;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.holidayworktime.HolidayWorkMidNightTime;

@Stateless
public class RecordWorkInfoPubImpl implements RecordWorkInfoPub {

	@Inject
	private WorkInformationRepository workInformationRepository;

	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;

	@Inject
	private AttendanceTimeRepository attendanceTimeRepo;

	@Inject
	private TemporaryTimeOfDailyPerformanceRepository temporaryTimeOfDailyPerformanceRepo;

	@Inject
	private OutingTimeOfDailyPerformanceRepository outingTimeOfDailyPerformanceRepo;
	
	@Inject
	private ShortTimeOfDailyPerformanceRepository shortTimeOfDailyPerformanceRepo;
	
	@Inject
	private BreakTimeOfDailyPerformanceRepository breakTimeOfDailyPerformanceRepo;
	/**
	 * RequestList5
	 */
	@Override
	public RecordWorkInfoPubExport getRecordWorkInfo(String employeeId, GeneralDate ymd) {
		Optional<WorkInfoOfDailyPerformance> workInfo = this.workInformationRepository.find(employeeId, ymd);
		if (!workInfo.isPresent()) {
			return new RecordWorkInfoPubExport("", "");
		}
		String workTimeCode = workInfo.get().getWorkInformation().getRecordInfo().getWorkTimeCode() == null ? null
				: workInfo.get().getWorkInformation().getRecordInfo().getWorkTimeCode().v();
		String workTypeCode = workInfo.get().getWorkInformation().getRecordInfo().getWorkTypeCode().v();
		RecordWorkInfoPubExport record = new RecordWorkInfoPubExport(workTypeCode, workTimeCode);
		record.setEmployeeId(employeeId);
		record.setYmd(ymd);
		// 日別実績の出退勤
		Optional<TimeLeavingOfDailyPerformance> timeLeaving = this.timeLeavingOfDailyPerformanceRepository
				.findByKey(employeeId, ymd);
		Optional<AttendanceTimeOfDailyPerformance> attenTime = this.attendanceTimeRepo.find(employeeId, ymd);

		timeLeaving.ifPresent(tl -> {
			tl.getAttendanceLeavingWork(1).ifPresent(al -> {
				// nampt : check null case
				al.getAttendanceStamp().ifPresent(stamp -> {
					stamp.getStamp().ifPresent(s -> {
						record.setAttendanceStampTimeFirst(!s.getTimeDay().getTimeWithDay().isPresent() ? null
								: s.getTimeDay().getTimeWithDay().get().valueAsMinutes());
					});
				});
				al.getLeaveStamp().ifPresent(stamp -> {
					stamp.getStamp().ifPresent(s -> {
						record.setLeaveStampTimeFirst(!s.getTimeDay().getTimeWithDay().isPresent() ? null
								: s.getTimeDay().getTimeWithDay().get().valueAsMinutes());
					});
				});
			});
			tl.getAttendanceLeavingWork(2).ifPresent(al -> {
				// nampt : check null case
				al.getAttendanceStamp().ifPresent(stamp -> {
					stamp.getStamp().ifPresent(s -> {
						record.setAttendanceStampTimeSecond(!s.getTimeDay().getTimeWithDay().isPresent() ? null
								: s.getTimeDay().getTimeWithDay().get().valueAsMinutes());
					});
				});
				al.getLeaveStamp().ifPresent(stamp -> {
					stamp.getStamp().ifPresent(s -> {
						record.setLeaveStampTimeSecond(!s.getTimeDay().getTimeWithDay().isPresent() ? null
								: s.getTimeDay().getTimeWithDay().get().valueAsMinutes());
					});
				});
			});
		});

		attenTime.ifPresent(at -> {
			TotalWorkingTime totalWT = at.getTime().getActualWorkingTimeOfDaily().getTotalWorkingTime();
			if (totalWT == null) {
				return;
			}
			totalWT.getLateTimeNo(1).ifPresent(lt -> {
				Integer time = lt.getLateTime().getTime() == null ? null : lt.getLateTime().getTime().valueAsMinutes();
				record.setLateTime1(time);
			});
			totalWT.getLateTimeNo(2).ifPresent(lt -> {
				Integer time = lt.getLateTime().getTime() == null ? null : lt.getLateTime().getTime().valueAsMinutes();
				record.setLateTime2(time);
			});
			totalWT.getLeaveEarlyTimeNo(1).ifPresent(lt -> {
				Integer time = lt.getLeaveEarlyTime().getTime() == null ? null
						: lt.getLeaveEarlyTime().getTime().valueAsMinutes();
				record.setLeaveEarlyTime1(time);
			});
			totalWT.getLeaveEarlyTimeNo(2).ifPresent(lt -> {
				Integer time = lt.getLeaveEarlyTime().getTime() == null ? null
						: lt.getLeaveEarlyTime().getTime().valueAsMinutes();
				record.setLeaveEarlyTime2(time);
			});
			if (totalWT.getShotrTimeOfDaily() != null && totalWT.getShotrTimeOfDaily().getTotalTime() != null
					&& totalWT.getShotrTimeOfDaily().getTotalTime().getTotalTime() != null) {
				record.setChildCareTime(
						totalWT.getShotrTimeOfDaily().getTotalTime().getTotalTime().getTime().valueAsMinutes());
			}
			totalWT.getOutingTimeByReason(GoOutReason.OFFICAL).ifPresent(ot -> {
				if (ot.getRecordTotalTime() != null && ot.getRecordTotalTime().getTotalTime() != null) {
					record.setOutingTimePrivate(ot.getRecordTotalTime().getTotalTime().getTime().valueAsMinutes());
				}
			});
			totalWT.getOutingTimeByReason(GoOutReason.SUPPORT).ifPresent(ot -> {
				if (ot.getRecordTotalTime() != null && ot.getRecordTotalTime().getTotalTime() != null) {
					record.setOutingTimePrivate(ot.getRecordTotalTime().getTotalTime().getTime().valueAsMinutes());
				}
			});

			totalWT.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().ifPresent(ovt -> {
				if (ovt.getFlexTime() != null && ovt.getFlexTime().getFlexTime() != null) {
					record.setFlexTime(ovt.getFlexTime().getFlexTime().getCalcTime().valueAsMinutes());
				}
				record.setOvertimes(ovt.getOverTimeWorkFrameTime()
						.stream().map(c -> new CommonTimeSheet(c.getOverWorkFrameNo().v(),
								getCalcTime(c.getOverTimeWork()), getCalcTime(c.getTransferTime())))
						.collect(Collectors.toList()));
			});
			totalWT.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().ifPresent(holW -> {
				record.setHolidayWorks(holW.getHolidayWorkFrameTime()
						.stream().map(c -> new CommonTimeSheet(c.getHolidayFrameNo().v(),
								getCalcTime(c.getHolidayWorkTime()), getCalcTime(c.getTransferTime())))
						.collect(Collectors.toList()));
			});
			record.setMidnightTime(totalWT.getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime()
					.getTime().getCalcTime().v());
			//日別実績の勤怠時間．実績時間．総労働時間．所定外時間．残業時間．所定外深夜時間
			if (at.getTime().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily()
					.getOverTimeWork().isPresent()) {
				record.setExcessOverTimeWorkMidNightTime(Optional.of(at.getTime().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily()
						.getOverTimeWork().get().getExcessOverTimeWorkMidNightTime().get().getTime()));
			}
			if (at.getTime().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily()
					.getWorkHolidayTime().isPresent()) {
				List<HolidayWorkMidNightTime> listHolidayWorkMidNightTime = at.getTime().getActualWorkingTimeOfDaily()
						.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayMidNightWork().get().getHolidayWorkMidNightTime();
				for(HolidayWorkMidNightTime item : listHolidayWorkMidNightTime){
					TimeDivergenceWithCalculation time = item.getTime();
					switch(item.getStatutoryAtr()){
					case WithinPrescribedHolidayWork: //法定内休出
						record.setInsideTheLaw(Optional.of(time));
						break;
					case ExcessOfStatutoryHolidayWork:// 法定外休出
						record.setOutrageous(Optional.of(time));
						break;
					default://PublicHolidayWork -祝日休出
						record.setPublicHoliday(Optional.of(time));
						break;
					}
				}
				
			}
			
		});
		record.setListWrScheduleTimeSheetExport(workInfo.get().getWorkInformation().getScheduleTimeSheets().stream()
				.map(c -> convertToScheduleTimeSheet(c)).collect(Collectors.toList()));
		Optional<TemporaryTimeOfDailyPerformance> temporaryTimeOfDailyPerformance = temporaryTimeOfDailyPerformanceRepo
				.findByKey(employeeId, ymd);
		if (temporaryTimeOfDailyPerformance.isPresent()) {
			record.setListTimeLeavingWorkExport(temporaryTimeOfDailyPerformance.get().getAttendance()
					.getTimeLeavingWorks().stream().map(c -> convertToTimeLeavingWork(c)).collect(Collectors.toList()));
		}
		Optional<OutingTimeOfDailyPerformance> outingTimeOfDailyPer =outingTimeOfDailyPerformanceRepo.findByEmployeeIdAndDate(employeeId, ymd);
		if (outingTimeOfDailyPer.isPresent()) {
			record.setListOutingTimeSheet(outingTimeOfDailyPer.get().getOutingTime().getOutingTimeSheets());
		}
		
		Optional<ShortTimeOfDailyPerformance> shortTimeOfDailyPerformance = shortTimeOfDailyPerformanceRepo.find(employeeId, ymd);
		if (shortTimeOfDailyPerformance.isPresent()) {
			record.setListShortWorkingTimeSheet(shortTimeOfDailyPerformance.get().getTimeZone().getShortWorkingTimeSheets());
			
		}
		List<BreakTimeOfDailyPerformance> listBreakTimeOfDailyPer =  breakTimeOfDailyPerformanceRepo.findByKey(employeeId, ymd);
		List<BreakTimeSheet> listBreakTimeSheet = new ArrayList<>();
		listBreakTimeOfDailyPer.stream().map(c->listBreakTimeSheet.addAll(c.getTimeZone().getBreakTimeSheets())).collect(Collectors.toList());
		record.setListBreakTimeSheet(listBreakTimeSheet);
		
		return record;

	}

	private WrScheduleTimeSheetExport convertToScheduleTimeSheet(ScheduleTimeSheet st) {
		return new WrScheduleTimeSheetExport(st.getWorkNo().v(), st.getAttendance(), st.getLeaveWork());
	}

	private WrTimeLeavingWorkExport convertToTimeLeavingWork(TimeLeavingWork domain) {
		return new WrTimeLeavingWorkExport(domain.getWorkNo().v(),
				!domain.getAttendanceStamp().isPresent() ? null
						: convertToTimeActualStamp(domain.getAttendanceStamp().get()),
				!domain.getLeaveStamp().isPresent() ? null : convertToTimeActualStamp(domain.getLeaveStamp().get()));
	}

	private WrTimeActualStampExport convertToTimeActualStamp(TimeActualStamp domain) {
		return new WrTimeActualStampExport(
				domain.getActualStamp().isPresent() ? convertToWorkStamp(domain.getActualStamp().get()) : null,
				domain.getStamp().isPresent() ? convertToWorkStamp(domain.getStamp().get()) : null,
				domain.getNumberOfReflectionStamp());
	}

	private WrWorkStampExport convertToWorkStamp(WorkStamp domain) {
		return new WrWorkStampExport(domain.getAfterRoundingTime().v(), new WrWorkTimeInformationExport(
				new WrReasonTimeChangeExport(domain.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value,
						domain.getTimeDay().getReasonTimeChange().getEngravingMethod().isPresent()
								? domain.getTimeDay().getReasonTimeChange().getEngravingMethod().get().value : null),
				domain.getTimeDay().getTimeWithDay().isPresent() ? domain.getTimeDay().getTimeWithDay().get().v()
						: null),
				domain.getLocationCode().isPresent() ? domain.getLocationCode().get().v() : null);
	}

	@Override
	public Optional<InfoCheckNotRegisterPubExport> getInfoCheckNotRegister(String employeeId, GeneralDate ymd) {
		Optional<InfoCheckNotRegisterPubExport> data = workInformationRepository.find(employeeId, ymd)
				.map(c -> convertToExport(c));
		if (data.isPresent()) {
			return data;
		}
		return Optional.empty();
	}

	private InfoCheckNotRegisterPubExport convertToExport(WorkInfoOfDailyPerformance domain) {
		return new InfoCheckNotRegisterPubExport(domain.getEmployeeId(),
				domain.getWorkInformation().getRecordInfo().getWorkTimeCode() == null ? ""
						: domain.getWorkInformation().getRecordInfo().getWorkTimeCode().v(),
				domain.getWorkInformation().getRecordInfo().getWorkTypeCode().v(), domain.getYmd());
	}

	private Integer getCalcTime(TimeDivergenceWithCalculation calc) {
		return calc == null || calc.getCalcTime() == null ? null : calc.getCalcTime().valueAsMinutes();
	}

	private Integer getCalcTime(Finally<TimeDivergenceWithCalculation> calc) {
		return !calc.isPresent() || calc == null || calc.get().getCalcTime() == null ? null
				: calc.get().getCalcTime().valueAsMinutes();
	}

	@Override
	public Optional<String> getWorkTypeCode(String employeeId, GeneralDate ymd) {
		Optional<WorkInfoOfDailyPerformance> optWorkInfo = workInformationRepository.find(employeeId, ymd);
		if (!optWorkInfo.isPresent())
			return Optional.ofNullable(null);
		return Optional.of(optWorkInfo.get().getWorkInformation().getRecordInfo().getWorkTypeCode().v());
	}

	@Override
	public List<InfoCheckNotRegisterPubExport> findByPeriodOrderByYmdAndEmps(List<String> employeeIds,
			DatePeriod datePeriod) {
		List<WorkInfoOfDailyPerformance> data = workInformationRepository.findByPeriodOrderByYmdAndEmps(employeeIds,
				datePeriod);
		if (data.isEmpty())
			return Collections.emptyList();
		return data.stream().map(c -> convertToExportInfor(c)).collect(Collectors.toList());
	}

	private InfoCheckNotRegisterPubExport convertToExportInfor(WorkInfoOfDailyPerformance domain) {
		return new InfoCheckNotRegisterPubExport(domain.getEmployeeId(),
				domain.getWorkInformation().getRecordInfo() == null ? null
						: (domain.getWorkInformation().getRecordInfo().getWorkTimeCode() == null ? null
								: domain.getWorkInformation().getRecordInfo().getWorkTimeCode().v()),
				domain.getWorkInformation().getRecordInfo() == null ? null
						: (domain.getWorkInformation().getRecordInfo().getWorkTypeCode() == null ? null
								: domain.getWorkInformation().getRecordInfo().getWorkTypeCode().v()),
				domain.getYmd());
	}

	@Override
	public List<WorkInfoOfDailyPerExport> findByEmpId(String employeeId) {
		List<WorkInfoOfDailyPerformance> workInfo = this.workInformationRepository.findByEmployeeId(employeeId);
		if (workInfo.isEmpty())
			return Collections.emptyList();
		return workInfo.stream().map(c -> convertToWorkInfoOfDailyPerformance(c)).collect(Collectors.toList());
	}

	private WorkInfoOfDailyPerExport convertToWorkInfoOfDailyPerformance(WorkInfoOfDailyPerformance domain) {
		return new WorkInfoOfDailyPerExport(domain.getEmployeeId(), domain.getYmd());
	}

}
