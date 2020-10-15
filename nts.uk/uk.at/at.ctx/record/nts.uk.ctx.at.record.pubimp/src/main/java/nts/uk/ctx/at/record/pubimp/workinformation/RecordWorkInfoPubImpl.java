package nts.uk.ctx.at.record.pubimp.workinformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
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
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPubExport_New;
import nts.uk.ctx.at.record.pub.workinformation.WorkInfoOfDailyPerExport;
import nts.uk.ctx.at.record.pub.workinformation.WrScheduleTimeSheetExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrReasonTimeChangeExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrTimeActualStampExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrTimeLeavingWorkExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrWorkStampExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrWorkTimeInformationExport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayMidnightWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.OverTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ChildCareAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.TotalWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;

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
			totalWT.getOutingTimeByReason(GoingOutReason.UNION).ifPresent(ot -> {
				if (ot.getRecordTotalTime() != null && ot.getRecordTotalTime().getTotalTime() != null) {
					record.setOutingTimePrivate(ot.getRecordTotalTime().getTotalTime().getTime().valueAsMinutes());
				}
			});
			totalWT.getOutingTimeByReason(GoingOutReason.PRIVATE).ifPresent(ot -> {
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

	@Override
	public List<InfoCheckNotRegisterPubExport> findByEmpAndPeriod(String employeeId, DatePeriod datePeriod) {
		List<WorkInfoOfDailyPerformance> result = workInformationRepository.findByPeriodOrderByYmd(employeeId, datePeriod);
		if (result.isEmpty()) {
			return Collections.emptyList();
		}
		return result.stream()
					.map(item -> convertToExport(item))
					.collect(Collectors.toList());
	}
	
	public RecordWorkInfoPubExport_New createInDomain(String employeeId, GeneralDate ymd) {
		RecordWorkInfoPubExport_New record = new RecordWorkInfoPubExport_New();
//		get 日別実績の勤務情報
		Optional<WorkInfoOfDailyPerformance> workInfoOp = this.workInformationRepository.find(employeeId, ymd);
		if (workInfoOp.isPresent()) {
			WorkInfoOfDailyPerformance workInfo = workInfoOp.get();
//			日別実績の勤務情報.社員ID
			record.setEmployeeId(workInfo.getEmployeeId());
//			日別実績の勤務情報.年月日
			record.setDate(workInfo.getYmd());
//			日別実績の勤務情報．勤務情報．勤務実績の勤務情報.勤務種類コード
			record.setWorkTypeCode(workInfo.getWorkInformation().getRecordInfo().getWorkTypeCode());
//			日別実績の勤務情報．勤務情報．勤務実績の勤務情報.就業時間帯コード
			record.setWorkTimeCode(workInfo.getWorkInformation().getRecordInfo().getWorkTimeCode());
			
//			日別実績の勤務情報．勤務情報．勤務予定時間帯
			List<ScheduleTimeSheet> scheduleTimeSheets = workInfo.getWorkInformation().getScheduleTimeSheets();
			Optional<ScheduleTimeSheet> workNo1 =  scheduleTimeSheets.stream().filter(x -> x.getWorkNo().v() == 1).findFirst();
			if (workNo1.isPresent()) {
				ScheduleTimeSheet value = workNo1.get();
				record.setScheduledAttendence1(value.getAttendance());
				record.setScheduledDeparture1(value.getLeaveWork());
			}
			Optional<ScheduleTimeSheet> workNo2 =  scheduleTimeSheets.stream().filter(x -> x.getWorkNo().v() == 2).findFirst();
			if (workNo2.isPresent()) {
				ScheduleTimeSheet value = workNo2.get();
				record.setScheduledAttendence2(Optional.of(value.getAttendance()));
				record.setScheduledDeparture2(Optional.of(value.getLeaveWork()));
			}
		}
		
//		get 日別実績の出退勤
		Optional<TimeLeavingOfDailyPerformance> timeLeavingOp = this.timeLeavingOfDailyPerformanceRepository
				.findByKey(employeeId, ymd);
		if (timeLeavingOp.isPresent()) {
			TimeLeavingOfDailyPerformance timeLeaving = timeLeavingOp.get();
//			日別実績の出退勤．出退勤．出退勤
			List<TimeLeavingWork> timeLeavingWorks = timeLeaving.getAttendance().getTimeLeavingWorks();
			Optional<TimeLeavingWork> workNo1 = timeLeavingWorks.stream().filter(x -> x.getWorkNo().v() == 1).findFirst();
			Optional<TimeLeavingWork> workNo2 = timeLeavingWorks.stream().filter(x -> x.getWorkNo().v() == 2).findFirst();
			if (workNo1.isPresent()) {
				TimeLeavingWork value = workNo1.get();
//				出勤
				Optional<TimeActualStamp> attendanceStamp = value.getAttendanceStamp();
				if(attendanceStamp.isPresent()) {
					Optional<WorkStamp> stamp = attendanceStamp.get().getStamp();
					if (stamp.isPresent()) {
						record.setStartTime1(Optional.of(stamp.get().getTimeDay()));
					}
				}
//				退勤
				Optional<TimeActualStamp> leaveStamp = value.getLeaveStamp();
				if(leaveStamp.isPresent()) {
					Optional<WorkStamp> stamp = leaveStamp.get().getStamp();
					if (stamp.isPresent()) {
						record.setEndTime1(Optional.of(stamp.get().getTimeDay()));
					}
				}
				
				
			}
			
			if (workNo2.isPresent()) {
				TimeLeavingWork value = workNo2.get();
//				出勤
				Optional<TimeActualStamp> attendanceStamp = value.getAttendanceStamp();
				if(attendanceStamp.isPresent()) {
					Optional<WorkStamp> stamp = attendanceStamp.get().getStamp();
					if (stamp.isPresent()) {
						record.setStartTime2(Optional.of(stamp.get().getTimeDay()));
					}
				}
//				退勤
				Optional<TimeActualStamp> leaveStamp = value.getLeaveStamp();
				if(leaveStamp.isPresent()) {
					Optional<WorkStamp> stamp = leaveStamp.get().getStamp();
					if (stamp.isPresent()) {
						record.setEndTime2(Optional.of(stamp.get().getTimeDay()));
					}
				}
				
				
			}
		}
		
//		get 日別実績の勤怠時間
		Optional<AttendanceTimeOfDailyPerformance> attenTime = this.attendanceTimeRepo.find(employeeId, ymd);
	    if (attenTime.isPresent()) {
//	    	日別実績の勤怠時間．時間．勤務時間．総労働時間
	    	TotalWorkingTime totalWorkingTime = attenTime.get().getTime().getActualWorkingTimeOfDaily().getTotalWorkingTime();	
//	    	日別実績の勤怠時間．時間．勤務時間．総労働時間.遅刻時間
	    	List<LateTimeOfDaily> lateTimeOfDaily = totalWorkingTime.getLateTimeOfDaily();
	    	{
	    		Optional<LateTimeOfDaily> workNo1 = lateTimeOfDaily.stream().filter(x -> x.getWorkNo().v() == 1).findFirst();
	    		Optional<LateTimeOfDaily> workNo2 = lateTimeOfDaily.stream().filter(x -> x.getWorkNo().v() == 2).findFirst();
	    		if (workNo1.isPresent()) {
	    			record.setLateTime1(Optional.of(workNo1.get().getLateTime().getTime()));
	    		}
	    		if (workNo2.isPresent()) {
	    			record.setLateTime2(Optional.of(workNo2.get().getLateTime().getTime()));
	    		}
	    		
	    	}
//	    	日別実績の勤怠時間．時間．勤務時間．総労働時間．早退時間
	    	List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily = totalWorkingTime.getLeaveEarlyTimeOfDaily();
	    	{
	    		Optional<LeaveEarlyTimeOfDaily> workNo1 = leaveEarlyTimeOfDaily.stream().filter(x -> x.getWorkNo().v() == 1).findFirst();
	    		if (workNo1.isPresent()) {
	    			record.setEarlyLeaveTime1(Optional.of(workNo1.get().getLeaveEarlyTime().getTime()));
	    		}
	    		Optional<LeaveEarlyTimeOfDaily> workNo2 = leaveEarlyTimeOfDaily.stream().filter(x -> x.getWorkNo().v() == 2).findFirst();
	    		if (workNo2.isPresent()) {
	    			record.setEarlyLeaveTime2(Optional.of(workNo2.get().getLeaveEarlyTime().getTime()));
	    		}
	    	}
//	    	日別実績の勤怠時間．時間．勤務時間．総労働時間．外出時間
	    	List<OutingTimeOfDaily> outingTimeOfDailyPerformance = totalWorkingTime.getOutingTimeOfDailyPerformance() ;
	    	{
	    		Optional<OutingTimeOfDaily> support = outingTimeOfDailyPerformance.stream().filter(x -> x.getReason() == GoingOutReason.PRIVATE).findFirst();
	    		Optional<OutingTimeOfDaily> offical = outingTimeOfDailyPerformance.stream().filter(x -> x.getReason() == GoingOutReason.UNION).findFirst();
	    		if (support.isPresent()) {
	    			record.setOutTime1(Optional.of(support.get().getRecordTotalTime().getTotalTime().getTime()));
	    		}
	    		if (offical.isPresent()) {
	    			record.setOutTime2(Optional.of(offical.get().getRecordTotalTime().getTotalTime().getTime()));
	    		}

	    	}
//	    	日別実績の勤怠時間．時間．勤務時間．総労働時間．短時間勤務時間
	    	ShortWorkTimeOfDaily shotrTimeOfDaily = totalWorkingTime.getShotrTimeOfDaily();
	    	record.setTotalTime(shotrTimeOfDaily.getTotalDeductionTime());
	    	
//	    	日別実績の勤怠時間．時間．勤務時間．総労働時間．所定外時間．残業時間
	    	Optional<OverTimeOfDaily> overTimeWork = totalWorkingTime.getExcessOfStatutoryTimeOfDaily().getOverTimeWork();
	    	if (overTimeWork.isPresent()) {
	    		record.setCalculateFlex(overTimeWork.get().getFlexTime().getFlexTime().getTime());
//	    		日別実績の勤怠時間．時間．勤務時間．総労働時間．所定外時間．残業時間．残業枠時間
	    		List<OverTimeFrameTime> overTimeWorkFrameTime = overTimeWork.get().getOverTimeWorkFrameTime();
	    		List<AttendanceTime> overTimeLstSet = overTimeWorkFrameTime.stream().map(x -> x.getOverTimeWork().getCalcTime()).collect(Collectors.toList());
	    		record.setOverTimeLst(overTimeLstSet);
	    		List<AttendanceTime> calculateTransferOverTimeLstSet = 
	    				overTimeWorkFrameTime.stream().map(x -> x.getTransferTime().getCalcTime()).collect(Collectors.toList());
	    		record.setCalculateTransferOverTimeLst(calculateTransferOverTimeLstSet);
//	    		日別実績の勤怠時間．時間．勤務時間．総労働時間．所定外時間．休出時間
	    		Optional<HolidayWorkTimeOfDaily> workHolidayTime = totalWorkingTime.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime();
	    		if (workHolidayTime.isPresent()) {
	    			List<HolidayWorkFrameTime> holidayWorkFrameTime = workHolidayTime.get().getHolidayWorkFrameTime();
	    			List<AttendanceTime> calculateHolidayLst = 
	    					holidayWorkFrameTime.stream().map(x -> x.getHolidayWorkTime().get().getCalcTime()).collect(Collectors.toList());
	    			record.setCalculateHolidayLst(calculateHolidayLst);
	    			
	    			List<AttendanceTime> calculateTransferLstSet = 
	    					holidayWorkFrameTime.stream().map(x -> x.getTransferTime().get().getCalcTime()).collect(Collectors.toList());
	    			record.setCalculateTransferLst(calculateTransferLstSet);
	    			
	    		}
	    		
	    	}
//	    	日別実績の勤怠時間．時間．勤務時間．総労働時間．所定外時間
	    	ExcessOfStatutoryTimeOfDaily excessOfStatutoryTimeOfDaily = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
//	    	日別実績の勤怠時間．時間．勤務時間．総労働時間．所定外時間．残業時間
	    	Optional<OverTimeOfDaily> overTimeWork2 = excessOfStatutoryTimeOfDaily.getOverTimeWork();
	    	if (overTimeWork2.isPresent()) {
	    		if (overTimeWork2.get().getExcessOverTimeWorkMidNightTime().isPresent()) {
	    			record.setOverTimeMidnight(Optional.of(overTimeWork2.get().getExcessOverTimeWorkMidNightTime().get().getTime()));	    			
	    		}
	    	}
//	    	日別実績の勤怠時間．時間．勤務時間．総労働時間．所定外時間．休出時間
	    	Optional<HolidayWorkTimeOfDaily> workHolidayTime = excessOfStatutoryTimeOfDaily.getWorkHolidayTime();
	    	if (workHolidayTime.isPresent()) {
	    		Finally<HolidayMidnightWork> holidayMidNightWork = workHolidayTime.get().getHolidayMidNightWork();
	    		if (holidayMidNightWork.isPresent()) {
	    			List<HolidayWorkMidNightTime> holidayWorkMidNightTime = holidayMidNightWork.get().getHolidayWorkMidNightTime();
//	    			法定区分="法定内休出"
	    			Optional<HolidayWorkMidNightTime> WithinPrescribedHolidayWork =
	    					holidayWorkMidNightTime.stream().filter(x -> x.getStatutoryAtr() == StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork).findFirst();
	    			if (WithinPrescribedHolidayWork.isPresent()) {
	    				record.setMidnightOnHoliday(Optional.of(WithinPrescribedHolidayWork.get().getTime()));
	    			}
//	    			法定区分="法定外休出"
	    			Optional<HolidayWorkMidNightTime> ExcessOfStatutoryHolidayWork =
	    					holidayWorkMidNightTime.stream().filter(x -> x.getStatutoryAtr() == StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork).findFirst();
	    			if (ExcessOfStatutoryHolidayWork.isPresent()) {
	    				record.setOutOfMidnight(Optional.of(ExcessOfStatutoryHolidayWork.get().getTime()));
	    			}
//	    			法定区分="祝日休出"
	    			Optional<HolidayWorkMidNightTime> PublicHolidayWork =
	    					holidayWorkMidNightTime.stream().filter(x -> x.getStatutoryAtr() == StaturoryAtrOfHolidayWork.PublicHolidayWork).findFirst();
	    			if (PublicHolidayWork.isPresent()) {
	    				record.setMidnightPublicHoliday(Optional.of(PublicHolidayWork.get().getTime()));
	    			}
	    		}
	    	}
	    }
		
	    
//	    get 日別実績の臨時出退勤
	    Optional<TemporaryTimeOfDailyPerformance> temporaryTimeOfDailyPerformance = temporaryTimeOfDailyPerformanceRepo
				.findByKey(employeeId, ymd);
//	    日別実績の臨時出退勤．出退勤.出退勤
	    if (temporaryTimeOfDailyPerformance.isPresent()) {
	    	List<TimeLeavingWork> timeLeavingWorks = temporaryTimeOfDailyPerformance.get().getAttendance().getTimeLeavingWorks();
	    	record.setTimeLeavingWorks(timeLeavingWorks);
	    }
	    
//		get 日別実績の外出時間帯
		Optional<OutingTimeOfDailyPerformance> outingTimeOfDailyPer =outingTimeOfDailyPerformanceRepo.findByEmployeeIdAndDate(employeeId, ymd);
		if (outingTimeOfDailyPer.isPresent()) {
			List<OutingTimeSheet> outingTimeSheets = outingTimeOfDailyPer.get().getOutingTime().getOutingTimeSheets();
			record.setOutHoursLst(outingTimeSheets);
		}
//		get 日別勤怠の短時間勤務時間帯
		Optional<ShortTimeOfDailyPerformance> shortTimeOfDailyPerformance = shortTimeOfDailyPerformanceRepo.find(employeeId, ymd);
		Optional<ShortTimeOfDailyAttd> shOptional = Optional.empty();
		if (shortTimeOfDailyPerformance.isPresent()) {
			shOptional = Optional.ofNullable(shortTimeOfDailyPerformance.get().getTimeZone());
		}
		if (shOptional.isPresent()) {
			record.setShortWorkingTimeSheets(shOptional.get().getShortWorkingTimeSheets());
		}
//		breakTimeSheets do not repo
		List<BreakTimeOfDailyPerformance> breakTimeOfDailyPerformanceLst =  breakTimeOfDailyPerformanceRepo.findByKey(employeeId, ymd);
		if (!CollectionUtil.isEmpty(breakTimeOfDailyPerformanceLst)) {
			List<BreakTimeSheet> breakTimeSheetsSet = 
					breakTimeOfDailyPerformanceLst.get(0).getTimeZone().getBreakTimeSheets();
			record.setBreakTimeSheets(breakTimeSheetsSet);
		}
		
		// get 日別実績の短時間勤務時間帯
		Optional<ShortTimeOfDailyPerformance> opShortTimeOfDailyPerformance = shortTimeOfDailyPerformanceRepo.find(employeeId, ymd);
		if(opShortTimeOfDailyPerformance.isPresent()) {
			record.setChildCareShortWorkingTimeList(opShortTimeOfDailyPerformance.get().getTimeZone().getShortWorkingTimeSheets().stream()
					.filter(x -> x.getChildCareAttr()==ChildCareAttribute.CHILD_CARE).collect(Collectors.toList()));
			record.setCareShortWorkingTimeList(opShortTimeOfDailyPerformance.get().getTimeZone().getShortWorkingTimeSheets().stream()
					.filter(x -> x.getChildCareAttr()==ChildCareAttribute.CARE).collect(Collectors.toList()));
		}
		
		return record;
	}

	// create rql5
	@Override
	public RecordWorkInfoPubExport_New getRecordWorkInfo_New(String employeeId, GeneralDate ymd) {
		return createInDomain(employeeId, ymd);

	}
	

}
