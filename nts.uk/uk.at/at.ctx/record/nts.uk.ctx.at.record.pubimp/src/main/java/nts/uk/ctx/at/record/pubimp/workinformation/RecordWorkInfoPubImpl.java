package nts.uk.ctx.at.record.pubimp.workinformation;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.BooleanUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import nts.arc.enums.EnumAdaptor;
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
import nts.uk.ctx.at.record.pub.workinformation.RecordWorkInfoPubExport_New;
import nts.uk.ctx.at.record.pub.workinformation.WorkInfoOfDailyPerExport;
import nts.uk.ctx.at.record.pub.workinformation.WrScheduleTimeSheetExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrReasonTimeChangeExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrTimeActualStampExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrTimeLeavingWorkExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrWorkStampExport;
import nts.uk.ctx.at.record.pub.workinformation.export.WrWorkTimeInformationExport;
import nts.uk.ctx.at.shared.dom.breakorgoout.primitivevalue.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakouting.OutingFrameNo;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime.ChildCareAttribute;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimFrameNo;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.worktime.TotalWorkingTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.DeductionTotalTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.GoOutReason;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.OverTimeFrameTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.ShortWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.breaktimegoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.holidayworktime.HolidayMidnightWork;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.holidayworktime.HolidayWorkMidNightTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.worktime.overtimedeclaration.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

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
				record.setScheduledAttendence2(value.getAttendance());
				record.setScheduledDeparture2(value.getLeaveWork());
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
						record.setStartTime1(stamp.get().getTimeDay());
					}
				}
//				退勤
				Optional<TimeActualStamp> leaveStamp = value.getLeaveStamp();
				if(leaveStamp.isPresent()) {
					Optional<WorkStamp> stamp = leaveStamp.get().getStamp();
					if (stamp.isPresent()) {
						record.setEndTime1(stamp.get().getTimeDay());
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
						record.setStartTime2(stamp.get().getTimeDay());
					}
				}
//				退勤
				Optional<TimeActualStamp> leaveStamp = value.getLeaveStamp();
				if(leaveStamp.isPresent()) {
					Optional<WorkStamp> stamp = leaveStamp.get().getStamp();
					if (stamp.isPresent()) {
						record.setEndTime2(stamp.get().getTimeDay());
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
	    			record.setLateTime1(workNo1.get().getLateTime().getTime());
	    		}
	    		if (workNo2.isPresent()) {
	    			record.setLateTime2(workNo2.get().getLateTime().getTime());
	    		}
	    		
	    	}
//	    	日別実績の勤怠時間．時間．勤務時間．総労働時間．早退時間
	    	List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily = totalWorkingTime.getLeaveEarlyTimeOfDaily();
	    	{
	    		Optional<LeaveEarlyTimeOfDaily> workNo1 = leaveEarlyTimeOfDaily.stream().filter(x -> x.getWorkNo().v() == 1).findFirst();
	    		if (workNo1.isPresent()) {
	    			record.setEarlyLeaveTime1(workNo1.get().getLeaveEarlyTime().getTime());
	    		}
	    		Optional<LeaveEarlyTimeOfDaily> workNo2 = leaveEarlyTimeOfDaily.stream().filter(x -> x.getWorkNo().v() == 2).findFirst();
	    		if (workNo2.isPresent()) {
	    			record.setEarlyLeaveTime2(workNo2.get().getLeaveEarlyTime().getTime());
	    		}
	    	}
//	    	日別実績の勤怠時間．時間．勤務時間．総労働時間．外出時間
	    	List<OutingTimeOfDaily> outingTimeOfDailyPerformance = totalWorkingTime.getOutingTimeOfDailyPerformance() ;
	    	{
	    		Optional<OutingTimeOfDaily> support = outingTimeOfDailyPerformance.stream().filter(x -> x.getReason() == GoOutReason.SUPPORT).findFirst();
	    		Optional<OutingTimeOfDaily> offical = outingTimeOfDailyPerformance.stream().filter(x -> x.getReason() == GoOutReason.OFFICAL).findFirst();
	    		if (support.isPresent()) {
	    			record.setOutTime1(support.get().getRecordTotalTime().getTotalTime().getTime());
	    		}
	    		if (offical.isPresent()) {
	    			record.setOutTime2(offical.get().getRecordTotalTime().getTotalTime().getTime());
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
	    		record.setOverTimeMidnight(overTimeWork2.get().getExcessOverTimeWorkMidNightTime().get().getTime());
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
	    				record.setMidnightOnHoliday(WithinPrescribedHolidayWork.get().getTime());
	    			}
//	    			法定区分="法定外休出"
	    			Optional<HolidayWorkMidNightTime> ExcessOfStatutoryHolidayWork =
	    					holidayWorkMidNightTime.stream().filter(x -> x.getStatutoryAtr() == StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork).findFirst();
	    			if (ExcessOfStatutoryHolidayWork.isPresent()) {
	    				record.setOutOfMidnight(ExcessOfStatutoryHolidayWork.get().getTime());
	    			}
//	    			法定区分="祝日休出"
	    			Optional<HolidayWorkMidNightTime> PublicHolidayWork =
	    					holidayWorkMidNightTime.stream().filter(x -> x.getStatutoryAtr() == StaturoryAtrOfHolidayWork.PublicHolidayWork).findFirst();
	    			if (PublicHolidayWork.isPresent()) {
	    				record.setMidnightPublicHoliday(PublicHolidayWork.get().getTime());
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
		return record;
	}

	// create rql5
	@Override
	public RecordWorkInfoPubExport_New getRecordWorkInfo_New(String employeeId, GeneralDate ymd) {
		RecordWorkInfoPubExport_New record = new RecordWorkInfoPubExport_New();
		try {
			record = this.createInDomain(employeeId, ymd);
		} catch (Exception e) {
			record = this.createXML(employeeId, ymd);
		}
		
		
		return record;
	}
	
	
	public RecordWorkInfoPubExport_New createXML(String employeeId, GeneralDate ymd) {
		RecordWorkInfoPubExport_New record = new RecordWorkInfoPubExport_New();
		try {
			String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
			File fXmlFile = new File(currentPath + "\\datatest\\staff1.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			
			String testXml = doc.getDocumentElement().getNodeName();
			Element eElement = (Element) doc.getElementsByTagName("data").item(0);
			
			
			{
				// startTime1
				Element startTime1Element = (Element) eElement.getElementsByTagName("startTime1").item(0);
				// create ReasonTimeChange
				Element reasonTimeChangeElement = (Element) startTime1Element.getElementsByTagName("reasonTimeChange");
				Integer timeChangeMeans = Integer.parseInt(
						reasonTimeChangeElement.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
				TimeChangeMeans timeChangeMeansSet = EnumAdaptor.valueOf(timeChangeMeans, TimeChangeMeans.class);
				// Optional engravingMethod
				Optional<EngravingMethod> engravingMethodSet = Optional.empty();
				Element engravingMethodElement = (Element) reasonTimeChangeElement
						.getElementsByTagName("engravingMethod").item(0);
				if (reasonTimeChangeElement.getElementsByTagName("engravingMethod").item(0) != null) {
					Integer engravingMethod = Integer.parseInt(engravingMethodElement.getTextContent());
					EngravingMethod engravingMethodSetTemp = EnumAdaptor.valueOf(engravingMethod,
							EngravingMethod.class);
					engravingMethodSet = Optional.ofNullable(engravingMethodSetTemp);
				}
				ReasonTimeChange reasonTimeChangeSet = new ReasonTimeChange(timeChangeMeansSet,
						engravingMethodSet.isPresent() ? engravingMethodSet.get() : null);
				// create timeWithDay
				Element timeWithDayElement = (Element) startTime1Element.getElementsByTagName("timeWithDay").item(0);
				Optional<TimeWithDayAttr> timeWithDaySet = Optional.empty();
				if (timeWithDayElement != null) {
					Integer timeWithDay = Integer.parseInt(timeWithDayElement.getTextContent());
					timeWithDaySet = Optional.of(new TimeWithDayAttr(timeWithDay));
				}
				WorkTimeInformation startTime1 = new WorkTimeInformation(reasonTimeChangeSet,
						timeWithDaySet.isPresent() ? timeWithDaySet.get() : null);

				record.setStartTime1(startTime1);
			}

			{
				// endTime1
				Element startTime1Element = (Element) eElement.getElementsByTagName("endTime1").item(0);
				// create ReasonTimeChange
				Element reasonTimeChangeElement = (Element) startTime1Element.getElementsByTagName("reasonTimeChange");
				Integer timeChangeMeans = Integer.parseInt(
						reasonTimeChangeElement.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
				TimeChangeMeans timeChangeMeansSet = EnumAdaptor.valueOf(timeChangeMeans, TimeChangeMeans.class);
				// Optional engravingMethod
				Optional<EngravingMethod> engravingMethodSet = Optional.empty();
				Element engravingMethodElement = (Element) reasonTimeChangeElement
						.getElementsByTagName("engravingMethod").item(0);
				if (reasonTimeChangeElement.getElementsByTagName("engravingMethod").item(0) != null) {
					Integer engravingMethod = Integer.parseInt(engravingMethodElement.getTextContent());
					EngravingMethod engravingMethodSetTemp = EnumAdaptor.valueOf(engravingMethod,
							EngravingMethod.class);
					engravingMethodSet = Optional.ofNullable(engravingMethodSetTemp);
				}
				ReasonTimeChange reasonTimeChangeSet = new ReasonTimeChange(timeChangeMeansSet,
						engravingMethodSet.isPresent() ? engravingMethodSet.get() : null);
				// create timeWithDay
				Element timeWithDayElement = (Element) startTime1Element.getElementsByTagName("timeWithDay").item(0);
				Optional<TimeWithDayAttr> timeWithDaySet = Optional.empty();
				if (timeWithDayElement != null) {
					Integer timeWithDay = Integer.parseInt(timeWithDayElement.getTextContent());
					timeWithDaySet = Optional.of(new TimeWithDayAttr(timeWithDay));
				}
				WorkTimeInformation endTime1 = new WorkTimeInformation(reasonTimeChangeSet,
						timeWithDaySet.isPresent() ? timeWithDaySet.get() : null);

				record.setEndTime1(endTime1);
			}

			{
				// startTime2
				Element startTime1Element = (Element) eElement.getElementsByTagName("startTime2").item(0);
				// create ReasonTimeChange
				Element reasonTimeChangeElement = (Element) startTime1Element.getElementsByTagName("reasonTimeChange");
				Integer timeChangeMeans = Integer.parseInt(
						reasonTimeChangeElement.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
				TimeChangeMeans timeChangeMeansSet = EnumAdaptor.valueOf(timeChangeMeans, TimeChangeMeans.class);
				// Optional engravingMethod
				Optional<EngravingMethod> engravingMethodSet = Optional.empty();
				Element engravingMethodElement = (Element) reasonTimeChangeElement
						.getElementsByTagName("engravingMethod").item(0);
				if (reasonTimeChangeElement.getElementsByTagName("engravingMethod").item(0) != null) {
					Integer engravingMethod = Integer.parseInt(engravingMethodElement.getTextContent());
					EngravingMethod engravingMethodSetTemp = EnumAdaptor.valueOf(engravingMethod,
							EngravingMethod.class);
					engravingMethodSet = Optional.ofNullable(engravingMethodSetTemp);
				}
				ReasonTimeChange reasonTimeChangeSet = new ReasonTimeChange(timeChangeMeansSet,
						engravingMethodSet.isPresent() ? engravingMethodSet.get() : null);
				// create timeWithDay
				Element timeWithDayElement = (Element) startTime1Element.getElementsByTagName("timeWithDay").item(0);
				Optional<TimeWithDayAttr> timeWithDaySet = Optional.empty();
				if (timeWithDayElement != null) {
					Integer timeWithDay = Integer.parseInt(timeWithDayElement.getTextContent());
					timeWithDaySet = Optional.of(new TimeWithDayAttr(timeWithDay));
				}
				WorkTimeInformation startTime2 = new WorkTimeInformation(reasonTimeChangeSet,
						timeWithDaySet.isPresent() ? timeWithDaySet.get() : null);

				record.setStartTime2(startTime2);
			}
			
			{
				// endTime2
				Element startTime1Element = (Element) eElement.getElementsByTagName("endTime2").item(0);
				// create ReasonTimeChange
				Element reasonTimeChangeElement = (Element) startTime1Element.getElementsByTagName("reasonTimeChange");
				Integer timeChangeMeans = Integer.parseInt(
						reasonTimeChangeElement.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
				TimeChangeMeans timeChangeMeansSet = EnumAdaptor.valueOf(timeChangeMeans, TimeChangeMeans.class);
				// Optional engravingMethod
				Optional<EngravingMethod> engravingMethodSet = Optional.empty();
				Element engravingMethodElement = (Element) reasonTimeChangeElement
						.getElementsByTagName("engravingMethod").item(0);
				if (reasonTimeChangeElement.getElementsByTagName("engravingMethod").item(0) != null) {
					Integer engravingMethod = Integer.parseInt(engravingMethodElement.getTextContent());
					EngravingMethod engravingMethodSetTemp = EnumAdaptor.valueOf(engravingMethod,
							EngravingMethod.class);
					engravingMethodSet = Optional.ofNullable(engravingMethodSetTemp);
				}
				ReasonTimeChange reasonTimeChangeSet = new ReasonTimeChange(timeChangeMeansSet,
						engravingMethodSet.isPresent() ? engravingMethodSet.get() : null);
				// create timeWithDay
				Element timeWithDayElement = (Element) startTime1Element.getElementsByTagName("timeWithDay").item(0);
				Optional<TimeWithDayAttr> timeWithDaySet = Optional.empty();
				if (timeWithDayElement != null) {
					Integer timeWithDay = Integer.parseInt(timeWithDayElement.getTextContent());
					timeWithDaySet = Optional.of(new TimeWithDayAttr(timeWithDay));
				}
				WorkTimeInformation endTime2 = new WorkTimeInformation(reasonTimeChangeSet,
						timeWithDaySet.isPresent() ? timeWithDaySet.get() : null);

				record.setEndTime2(endTime2);
			}
			
			{
				// lateTime1
				Element rootElement = (Element) eElement.getElementsByTagName("lateTime1").item(0);
				Integer time = Integer.parseInt(rootElement.getTextContent());
				AttendanceTime timeSet = new AttendanceTime(time);
				record.setLateTime1(timeSet);
			}
			
			{
				// earlyLeaveTime1
				Element rootElement = (Element) eElement.getElementsByTagName("earlyLeaveTime1").item(0);
				Integer time = Integer.parseInt(rootElement.getTextContent());
				AttendanceTime timeSet = new AttendanceTime(time);
				record.setEarlyLeaveTime1(timeSet);
			}
			
			{
				// lateTime2
				Element rootElement = (Element) eElement.getElementsByTagName("lateTime2").item(0);
				Integer time = Integer.parseInt(rootElement.getTextContent());
				AttendanceTime timeSet = new AttendanceTime(time);
				record.setLateTime2(timeSet);
			}
			
			{
				// earlyLeaveTime2
				Element rootElement = (Element) eElement.getElementsByTagName("earlyLeaveTime2").item(0);
				Integer time = Integer.parseInt(rootElement.getTextContent());
				AttendanceTime timeSet = new AttendanceTime(time);
				record.setEarlyLeaveTime2(timeSet);
			}
			
			{
				// outTime1
				Element rootElement = (Element) eElement.getElementsByTagName("outTime1").item(0);
				Integer time = Integer.parseInt(rootElement.getTextContent());
				AttendanceTime timeSet = new AttendanceTime(time);
				record.setOutTime1(timeSet);
			}
			
			{
				// outTime2
				Element rootElement = (Element) eElement.getElementsByTagName("outTime2").item(0);
				Integer time = Integer.parseInt(rootElement.getTextContent());
				AttendanceTime timeSet = new AttendanceTime(time);
				record.setOutTime2(timeSet);
			}
			
			{
				// totalTime
				Element rootElement = (Element) eElement.getElementsByTagName("totalTime").item(0);
				TimeWithCalculation totalTime;
				TimeWithCalculation withinStatutoryTotalTime;
				TimeWithCalculation excessOfStatutoryTotalTime;
				{
					Element totalTimeElement = (Element) rootElement.getElementsByTagName("totalTime");
					Integer time = Integer.parseInt(totalTimeElement.getElementsByTagName("time").item(0).getTextContent());
					AttendanceTime timeSet = new AttendanceTime(time);
					
					Integer calcTime = Integer.parseInt(totalTimeElement.getElementsByTagName("calcTime").item(0).getTextContent());
					AttendanceTime calcTimeSet = new AttendanceTime(calcTime);
					TimeWithCalculation timeWithCalculation = TimeWithCalculation.createTimeWithCalculation(timeSet, calcTimeSet);
					totalTime = timeWithCalculation;
				}
				{
					Element totalTimeElement = (Element) rootElement.getElementsByTagName("withinStatutoryTotalTime");
					Integer time = Integer.parseInt(totalTimeElement.getElementsByTagName("time").item(0).getTextContent());
					AttendanceTime timeSet = new AttendanceTime(time);
					
					Integer calcTime = Integer.parseInt(totalTimeElement.getElementsByTagName("calcTime").item(0).getTextContent());
					AttendanceTime calcTimeSet = new AttendanceTime(calcTime);
					TimeWithCalculation timeWithCalculation = TimeWithCalculation.createTimeWithCalculation(timeSet, calcTimeSet);
					withinStatutoryTotalTime = timeWithCalculation;
				}
				{
					Element totalTimeElement = (Element) rootElement.getElementsByTagName("excessOfStatutoryTotalTime");
					Integer time = Integer.parseInt(totalTimeElement.getElementsByTagName("time").item(0).getTextContent());
					AttendanceTime timeSet = new AttendanceTime(time);
					
					Integer calcTime = Integer.parseInt(totalTimeElement.getElementsByTagName("calcTime").item(0).getTextContent());
					AttendanceTime calcTimeSet = new AttendanceTime(calcTime);
					TimeWithCalculation timeWithCalculation = TimeWithCalculation.createTimeWithCalculation(timeSet, calcTimeSet);
					excessOfStatutoryTotalTime = timeWithCalculation;
				}
				DeductionTotalTime duDeductionTotalTime = DeductionTotalTime.of(totalTime, withinStatutoryTotalTime, excessOfStatutoryTotalTime);
				record.setTotalTime(duDeductionTotalTime);
			}
			
			{
//				calculateFlex
				Element rootElement = (Element)eElement.getElementsByTagName("calculateFlex");
				
				Integer calculateFlex = Integer.parseInt(rootElement.getTextContent());
				AttendanceTimeOfExistMinus calculateFlexSet = new AttendanceTimeOfExistMinus(calculateFlex);
				
				record.setCalculateFlex(calculateFlexSet);
			}
			
			{
				//overTimeLst
				List<AttendanceTime> overTimeLstSet = new ArrayList<>();
				Element rootElement = (Element)eElement.getElementsByTagName("overTimeLst");
				NodeList nodes = rootElement.getElementsByTagName("AttendanceTime");
				for (int itr = 0; itr < nodes.getLength(); itr++) {
					Node node = nodes.item(itr);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) node;
						Integer time = Integer.parseInt(e.getTextContent());
						AttendanceTime timeSet = new AttendanceTime(time);
						overTimeLstSet.add(timeSet);
					}
				}
				record.setOverTimeLst(overTimeLstSet);
				
			}
			
			{
				//calculateTransferOverTimeLst
				List<AttendanceTime> calculateTransferOverTimeLstSet = new ArrayList<>();
				Element rootElement = (Element)eElement.getElementsByTagName("calculateTransferOverTimeLst");
				NodeList nodes = rootElement.getElementsByTagName("AttendanceTime");
				for (int itr = 0; itr < nodes.getLength(); itr++) {
					Node node = nodes.item(itr);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) node;
						Integer time = Integer.parseInt(e.getTextContent());
						AttendanceTime timeSet = new AttendanceTime(time);
						calculateTransferOverTimeLstSet.add(timeSet);
					}
				}
				record.setCalculateTransferOverTimeLst(calculateTransferOverTimeLstSet);
				
			}
			
			{
				//calculateHolidayLst
				List<AttendanceTime> calculateHolidayLstSet = new ArrayList<>();
				Element rootElement = (Element)eElement.getElementsByTagName("calculateHolidayLst");
				NodeList nodes = rootElement.getElementsByTagName("AttendanceTime");
				for (int itr = 0; itr < nodes.getLength(); itr++) {
					Node node = nodes.item(itr);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) node;
						Integer time = Integer.parseInt(e.getTextContent());
						AttendanceTime timeSet = new AttendanceTime(time);
						calculateHolidayLstSet.add(timeSet);
					}
				}
				record.setCalculateHolidayLst(calculateHolidayLstSet);
				
			}
			
			{
				//calculateTransferLst
				List<AttendanceTime> calculateTransferLstSet = new ArrayList<>();
				Element rootElement = (Element)eElement.getElementsByTagName("calculateTransferLst");
				NodeList nodes = rootElement.getElementsByTagName("AttendanceTime");
				for (int itr = 0; itr < nodes.getLength(); itr++) {
					Node node = nodes.item(itr);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) node;
						Integer time = Integer.parseInt(e.getTextContent());
						AttendanceTime timeSet = new AttendanceTime(time);
						calculateTransferLstSet.add(timeSet);
					}
				}
				record.setCalculateTransferLst(calculateTransferLstSet);
				
			}
			
			{
				// scheduledAttendence1
				Element rootElement = (Element)eElement.getElementsByTagName("scheduledAttendence1");
				Integer time = Integer.parseInt(rootElement.getTextContent());
				TimeWithDayAttr timeSet = new TimeWithDayAttr(time);
				record.setScheduledAttendence1(timeSet);
			}
			
			{
				// scheduledDeparture1
				Element rootElement = (Element)eElement.getElementsByTagName("scheduledDeparture1");
				Integer time = Integer.parseInt(rootElement.getTextContent());
				TimeWithDayAttr timeSet = new TimeWithDayAttr(time);
				record.setScheduledDeparture1(timeSet);
			}
			
			{
				// scheduledAttendence2
				Element rootElement = (Element)eElement.getElementsByTagName("scheduledAttendence2");
				Integer time = Integer.parseInt(rootElement.getTextContent());
				TimeWithDayAttr timeSet = new TimeWithDayAttr(time);
				record.setScheduledAttendence2(timeSet);
			}
			
			{
				// scheduledDeparture2
				Element rootElement = (Element)eElement.getElementsByTagName("scheduledDeparture2");
				Integer time = Integer.parseInt(rootElement.getTextContent());
				TimeWithDayAttr timeSet = new TimeWithDayAttr(time);
				record.setScheduledDeparture2(timeSet);
			}
			
			
			{
				// timeLeavingWorks
				List<TimeLeavingWork> timeLeavingWorks = new ArrayList<>();
				Element rootElement = (Element)eElement.getElementsByTagName("timeLeavingWorks");
				NodeList nodes = rootElement.getElementsByTagName("TimeLeavingWork");
				for (int itr = 0; itr < nodes.getLength(); itr++) {
					Node node = nodes.item(itr);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) node;
						Integer workNo = Integer.parseInt(e.getElementsByTagName("workNo").item(0).getTextContent());
						WorkNo workNoSet = new WorkNo(workNo);
						Optional<TimeActualStamp> attendanceStampSet = Optional.empty();
						// Optional 
						{
							// attendanceStamp
							if (e.getElementsByTagName("attendanceStamp").item(0) != null) {
								Element element = (Element) e.getElementsByTagName("attendanceStamp").item(0);
								Optional<WorkStamp> actualStampSet = Optional.empty();
								// Optional 
								{
									// actualStamp
									if (element.getElementsByTagName("actualStamp").item(0) != null) {
										Element actualStampElement = (Element) element.getElementsByTagName("actualStamp").item(0);
										Integer afterRoundingTime = Integer.parseInt(actualStampElement.getElementsByTagName("afterRoundingTime").item(0).getTextContent());
										TimeWithDayAttr afterRoundingTimeSet = new TimeWithDayAttr(afterRoundingTime);
										WorkTimeInformation timeDaySet;
										{
											// timeDay
											Element startTime1Element = (Element) actualStampElement.getElementsByTagName("timeDay").item(0);
											// create ReasonTimeChange
											Element reasonTimeChangeElement = (Element) startTime1Element.getElementsByTagName("reasonTimeChange");
											Integer timeChangeMeans = Integer.parseInt(
													reasonTimeChangeElement.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
											TimeChangeMeans timeChangeMeansSet = EnumAdaptor.valueOf(timeChangeMeans, TimeChangeMeans.class);
											// Optional engravingMethod
											Optional<EngravingMethod> engravingMethodSet = Optional.empty();
											Element engravingMethodElement = (Element) reasonTimeChangeElement
													.getElementsByTagName("engravingMethod").item(0);
											if (reasonTimeChangeElement.getElementsByTagName("engravingMethod").item(0) != null) {
												Integer engravingMethod = Integer.parseInt(engravingMethodElement.getTextContent());
												EngravingMethod engravingMethodSetTemp = EnumAdaptor.valueOf(engravingMethod,
														EngravingMethod.class);
												engravingMethodSet = Optional.ofNullable(engravingMethodSetTemp);
											}
											ReasonTimeChange reasonTimeChangeSet = new ReasonTimeChange(timeChangeMeansSet,
													engravingMethodSet.isPresent() ? engravingMethodSet.get() : null);
											// create timeWithDay
											Element timeWithDayElement = (Element) startTime1Element.getElementsByTagName("timeWithDay").item(0);
											Optional<TimeWithDayAttr> timeWithDaySet = Optional.empty();
											if (timeWithDayElement != null) {
												Integer timeWithDay = Integer.parseInt(timeWithDayElement.getTextContent());
												timeWithDaySet = Optional.of(new TimeWithDayAttr(timeWithDay));
											}
											timeDaySet = new WorkTimeInformation(reasonTimeChangeSet,
													timeWithDaySet.isPresent() ? timeWithDaySet.get() : null);
										}
										
										// Optional
										Optional<WorkLocationCD> locationCodeSet = Optional.empty();
										{
											// locationCode
											if (actualStampElement.getElementsByTagName("locationCode").item(0) != null) {
												Element locationCodeElement = (Element)actualStampElement.getElementsByTagName("locationCode").item(0);
												String locationCode = locationCodeElement.getTextContent();
												WorkLocationCD locationCodeTemp = new WorkLocationCD(locationCode);
												locationCodeSet = Optional.of(locationCodeTemp);
											}
										}
										
										actualStampSet = Optional.of(new WorkStamp(afterRoundingTimeSet, timeDaySet, locationCodeSet));
										
									}
								}
								
								Optional<WorkStamp> stampSet = Optional.empty();
								// Optional 
								{
									// actualStamp
									if (element.getElementsByTagName("stamp").item(0) != null) {
										Element actualStampElement = (Element) element.getElementsByTagName("stamp").item(0);
										Integer afterRoundingTime = Integer.parseInt(actualStampElement.getElementsByTagName("afterRoundingTime").item(0).getTextContent());
										TimeWithDayAttr afterRoundingTimeSet = new TimeWithDayAttr(afterRoundingTime);
										WorkTimeInformation timeDaySet;
										{
											// timeDay
											Element startTime1Element = (Element) actualStampElement.getElementsByTagName("timeDay").item(0);
											// create ReasonTimeChange
											Element reasonTimeChangeElement = (Element) startTime1Element.getElementsByTagName("reasonTimeChange");
											Integer timeChangeMeans = Integer.parseInt(
													reasonTimeChangeElement.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
											TimeChangeMeans timeChangeMeansSet = EnumAdaptor.valueOf(timeChangeMeans, TimeChangeMeans.class);
											// Optional engravingMethod
											Optional<EngravingMethod> engravingMethodSet = Optional.empty();
											Element engravingMethodElement = (Element) reasonTimeChangeElement
													.getElementsByTagName("engravingMethod").item(0);
											if (reasonTimeChangeElement.getElementsByTagName("engravingMethod").item(0) != null) {
												Integer engravingMethod = Integer.parseInt(engravingMethodElement.getTextContent());
												EngravingMethod engravingMethodSetTemp = EnumAdaptor.valueOf(engravingMethod,
														EngravingMethod.class);
												engravingMethodSet = Optional.ofNullable(engravingMethodSetTemp);
											}
											ReasonTimeChange reasonTimeChangeSet = new ReasonTimeChange(timeChangeMeansSet,
													engravingMethodSet.isPresent() ? engravingMethodSet.get() : null);
											// create timeWithDay
											Element timeWithDayElement = (Element) startTime1Element.getElementsByTagName("timeWithDay").item(0);
											Optional<TimeWithDayAttr> timeWithDaySet = Optional.empty();
											if (timeWithDayElement != null) {
												Integer timeWithDay = Integer.parseInt(timeWithDayElement.getTextContent());
												timeWithDaySet = Optional.of(new TimeWithDayAttr(timeWithDay));
											}
											timeDaySet = new WorkTimeInformation(reasonTimeChangeSet,
													timeWithDaySet.isPresent() ? timeWithDaySet.get() : null);
										}
										
										// Optional
										Optional<WorkLocationCD> locationCodeSet = Optional.empty();
										{
											// locationCode
											if (actualStampElement.getElementsByTagName("locationCode").item(0) != null) {
												Element locationCodeElement = (Element)actualStampElement.getElementsByTagName("locationCode").item(0);
												String locationCode = locationCodeElement.getTextContent();
												WorkLocationCD locationCodeTemp = new WorkLocationCD(locationCode);
												locationCodeSet = Optional.of(locationCodeTemp);
											}
										}
										
										stampSet = Optional.of(new WorkStamp(afterRoundingTimeSet, timeDaySet, locationCodeSet));
										
									}
								}
								
								Integer numberOfReflectionStampSet = Integer.parseInt(element.getElementsByTagName("numberOfReflectionStamp").item(0).getTextContent());

								Optional<OvertimeDeclaration> overtimeDeclarationSet = Optional.empty();
								// Optional
								{
									if (element.getElementsByTagName("overtimeDeclaration").item(0) != null) {
										Element overtimeDeclarationElement = (Element) element.getElementsByTagName("overtimeDeclaration").item(0);
										Integer overTime = Integer.parseInt(overtimeDeclarationElement.getElementsByTagName("overTime").item(0).getTextContent());
										AttendanceTime overTimeSet = new AttendanceTime(overTime);
										
										Integer overLateNightTime = Integer.parseInt(overtimeDeclarationElement.getElementsByTagName("overLateNightTime").item(0).getTextContent());
										AttendanceTime overLateNightTimeSet = new AttendanceTime(overLateNightTime);
										
										Optional.of(new OvertimeDeclaration(overTimeSet, overLateNightTimeSet));
									}									
								}
								Optional<TimeZone> timeVacationSet = Optional.empty();
								//Optional 
								{
									if (element.getElementsByTagName("timeVacation").item(0) != null) {
										Element timeVacationElement = (Element) element.getElementsByTagName("timeVacation").item(0);
										Integer start = Integer.parseInt(timeVacationElement.getElementsByTagName("start").item(0).getTextContent());
										Integer end = Integer.parseInt(timeVacationElement.getElementsByTagName("end").item(0).getTextContent());
										TimeZone timeVacationTemp = new TimeZone(new TimeWithDayAttr(start), new TimeWithDayAttr(end));
										timeVacationSet = Optional.of(timeVacationTemp);
									}
								}

								attendanceStampSet = Optional.of(new TimeActualStamp(
										actualStampSet,
										stampSet,
										numberOfReflectionStampSet,
										overtimeDeclarationSet,
										timeVacationSet));
								
								
							}
							
					
						}
						
						
						Optional<TimeActualStamp> leaveStampSet = Optional.empty();
						// Optional 
						{
							// attendanceStamp
							if (e.getElementsByTagName("leaveStamp").item(0) != null) {
								Element element = (Element) e.getElementsByTagName("leaveStamp").item(0);
								Optional<WorkStamp> actualStampSet = Optional.empty();
								// Optional 
								{
									// actualStamp
									if (element.getElementsByTagName("actualStamp").item(0) != null) {
										Element actualStampElement = (Element) element.getElementsByTagName("actualStamp").item(0);
										Integer afterRoundingTime = Integer.parseInt(actualStampElement.getElementsByTagName("afterRoundingTime").item(0).getTextContent());
										TimeWithDayAttr afterRoundingTimeSet = new TimeWithDayAttr(afterRoundingTime);
										WorkTimeInformation timeDaySet;
										{
											// timeDay
											Element startTime1Element = (Element) actualStampElement.getElementsByTagName("timeDay").item(0);
											// create ReasonTimeChange
											Element reasonTimeChangeElement = (Element) startTime1Element.getElementsByTagName("reasonTimeChange");
											Integer timeChangeMeans = Integer.parseInt(
													reasonTimeChangeElement.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
											TimeChangeMeans timeChangeMeansSet = EnumAdaptor.valueOf(timeChangeMeans, TimeChangeMeans.class);
											// Optional engravingMethod
											Optional<EngravingMethod> engravingMethodSet = Optional.empty();
											Element engravingMethodElement = (Element) reasonTimeChangeElement
													.getElementsByTagName("engravingMethod").item(0);
											if (reasonTimeChangeElement.getElementsByTagName("engravingMethod").item(0) != null) {
												Integer engravingMethod = Integer.parseInt(engravingMethodElement.getTextContent());
												EngravingMethod engravingMethodSetTemp = EnumAdaptor.valueOf(engravingMethod,
														EngravingMethod.class);
												engravingMethodSet = Optional.ofNullable(engravingMethodSetTemp);
											}
											ReasonTimeChange reasonTimeChangeSet = new ReasonTimeChange(timeChangeMeansSet,
													engravingMethodSet.isPresent() ? engravingMethodSet.get() : null);
											// create timeWithDay
											Element timeWithDayElement = (Element) startTime1Element.getElementsByTagName("timeWithDay").item(0);
											Optional<TimeWithDayAttr> timeWithDaySet = Optional.empty();
											if (timeWithDayElement != null) {
												Integer timeWithDay = Integer.parseInt(timeWithDayElement.getTextContent());
												timeWithDaySet = Optional.of(new TimeWithDayAttr(timeWithDay));
											}
											timeDaySet = new WorkTimeInformation(reasonTimeChangeSet,
													timeWithDaySet.isPresent() ? timeWithDaySet.get() : null);
										}
										
										// Optional
										Optional<WorkLocationCD> locationCodeSet = Optional.empty();
										{
											// locationCode
											if (actualStampElement.getElementsByTagName("locationCode").item(0) != null) {
												Element locationCodeElement = (Element)actualStampElement.getElementsByTagName("locationCode").item(0);
												String locationCode = locationCodeElement.getTextContent();
												WorkLocationCD locationCodeTemp = new WorkLocationCD(locationCode);
												locationCodeSet = Optional.of(locationCodeTemp);
											}
										}
										
										actualStampSet = Optional.of(new WorkStamp(afterRoundingTimeSet, timeDaySet, locationCodeSet));
										
									}
								}
								
								Optional<WorkStamp> stampSet = Optional.empty();
								// Optional 
								{
									// actualStamp
									if (element.getElementsByTagName("stamp").item(0) != null) {
										Element actualStampElement = (Element) element.getElementsByTagName("stamp").item(0);
										Integer afterRoundingTime = Integer.parseInt(actualStampElement.getElementsByTagName("afterRoundingTime").item(0).getTextContent());
										TimeWithDayAttr afterRoundingTimeSet = new TimeWithDayAttr(afterRoundingTime);
										WorkTimeInformation timeDaySet;
										{
											// timeDay
											Element startTime1Element = (Element) actualStampElement.getElementsByTagName("timeDay").item(0);
											// create ReasonTimeChange
											Element reasonTimeChangeElement = (Element) startTime1Element.getElementsByTagName("reasonTimeChange");
											Integer timeChangeMeans = Integer.parseInt(
													reasonTimeChangeElement.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
											TimeChangeMeans timeChangeMeansSet = EnumAdaptor.valueOf(timeChangeMeans, TimeChangeMeans.class);
											// Optional engravingMethod
											Optional<EngravingMethod> engravingMethodSet = Optional.empty();
											Element engravingMethodElement = (Element) reasonTimeChangeElement
													.getElementsByTagName("engravingMethod").item(0);
											if (reasonTimeChangeElement.getElementsByTagName("engravingMethod").item(0) != null) {
												Integer engravingMethod = Integer.parseInt(engravingMethodElement.getTextContent());
												EngravingMethod engravingMethodSetTemp = EnumAdaptor.valueOf(engravingMethod,
														EngravingMethod.class);
												engravingMethodSet = Optional.ofNullable(engravingMethodSetTemp);
											}
											ReasonTimeChange reasonTimeChangeSet = new ReasonTimeChange(timeChangeMeansSet,
													engravingMethodSet.isPresent() ? engravingMethodSet.get() : null);
											// create timeWithDay
											Element timeWithDayElement = (Element) startTime1Element.getElementsByTagName("timeWithDay").item(0);
											Optional<TimeWithDayAttr> timeWithDaySet = Optional.empty();
											if (timeWithDayElement != null) {
												Integer timeWithDay = Integer.parseInt(timeWithDayElement.getTextContent());
												timeWithDaySet = Optional.of(new TimeWithDayAttr(timeWithDay));
											}
											timeDaySet = new WorkTimeInformation(reasonTimeChangeSet,
													timeWithDaySet.isPresent() ? timeWithDaySet.get() : null);
										}
										
										// Optional
										Optional<WorkLocationCD> locationCodeSet = Optional.empty();
										{
											// locationCode
											if (actualStampElement.getElementsByTagName("locationCode").item(0) != null) {
												Element locationCodeElement = (Element)actualStampElement.getElementsByTagName("locationCode").item(0);
												String locationCode = locationCodeElement.getTextContent();
												WorkLocationCD locationCodeTemp = new WorkLocationCD(locationCode);
												locationCodeSet = Optional.of(locationCodeTemp);
											}
										}
										
										stampSet = Optional.of(new WorkStamp(afterRoundingTimeSet, timeDaySet, locationCodeSet));
										
									}
								}
								
								Integer numberOfReflectionStampSet = Integer.parseInt(element.getElementsByTagName("numberOfReflectionStamp").item(0).getTextContent());

								Optional<OvertimeDeclaration> overtimeDeclarationSet = Optional.empty();
								// Optional
								{
									if (element.getElementsByTagName("overtimeDeclaration").item(0) != null) {
										Element overtimeDeclarationElement = (Element) element.getElementsByTagName("overtimeDeclaration").item(0);
										Integer overTime = Integer.parseInt(overtimeDeclarationElement.getElementsByTagName("overTime").item(0).getTextContent());
										AttendanceTime overTimeSet = new AttendanceTime(overTime);
										
										Integer overLateNightTime = Integer.parseInt(overtimeDeclarationElement.getElementsByTagName("overLateNightTime").item(0).getTextContent());
										AttendanceTime overLateNightTimeSet = new AttendanceTime(overLateNightTime);
										
										Optional.of(new OvertimeDeclaration(overTimeSet, overLateNightTimeSet));
									}									
								}
								Optional<TimeZone> timeVacationSet = Optional.empty();
								//Optional 
								{
									if (element.getElementsByTagName("timeVacation").item(0) != null) {
										Element timeVacationElement = (Element) element.getElementsByTagName("timeVacation").item(0);
										Integer start = Integer.parseInt(timeVacationElement.getElementsByTagName("start").item(0).getTextContent());
										Integer end = Integer.parseInt(timeVacationElement.getElementsByTagName("end").item(0).getTextContent());
										TimeZone timeVacationTemp = new TimeZone(new TimeWithDayAttr(start), new TimeWithDayAttr(end));
										timeVacationSet = Optional.of(timeVacationTemp);
									}
								}

								leaveStampSet = Optional.of(new TimeActualStamp(
										actualStampSet,
										stampSet,
										numberOfReflectionStampSet,
										overtimeDeclarationSet,
										timeVacationSet));
								
								
							}
							
					
						}
						// canceledLate
						Boolean canceledLateSet;
						{
							Element root = (Element)e.getElementsByTagName("canceledLate").item(0);
							canceledLateSet = BooleanUtils.toBoolean(Integer.parseInt(root.getTextContent()));
						}
						
						// CanceledEarlyLeave
						Boolean CanceledEarlyLeaveSet;
						{
							Element root = (Element)e.getElementsByTagName("CanceledEarlyLeave").item(0);
							CanceledEarlyLeaveSet = BooleanUtils.toBoolean(Integer.parseInt(root.getTextContent()));
						}
						
						// timespan
						TimeSpanForCalc timespanSet;
						{
							Element timespanElement = (Element)e.getElementsByTagName("timespan").item(0);
							Integer start = Integer.parseInt(timespanElement.getElementsByTagName("start").item(0).getTextContent());
							Integer end = Integer.parseInt(timespanElement.getElementsByTagName("end").item(0).getTextContent());
							timespanSet = new TimeSpanForCalc(new TimeWithDayAttr(start), new TimeWithDayAttr(end));
							
						}
						
						TimeLeavingWork timeLeavingWork = new TimeLeavingWork(
								workNoSet,
								attendanceStampSet,
								leaveStampSet,
								canceledLateSet,
								CanceledEarlyLeaveSet);
						timeLeavingWorks.add(timeLeavingWork);
						
						
					}
				}
				
				record.setTimeLeavingWorks(timeLeavingWorks);
			}
			
			{
				// outHoursLst
				List<OutingTimeSheet> outHoursLst = new ArrayList<>();
				Element rootElement = (Element)eElement.getElementsByTagName("outHoursLst");
				NodeList nodes = rootElement.getElementsByTagName("OutingTimeSheet");
				
				for (int itr = 0; itr < nodes.getLength(); itr++) {
					Node node = nodes.item(itr);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) node;
						Integer outingFrameNo = Integer.parseInt(e.getElementsByTagName("outingFrameNo").item(0).getTextContent());
						OutingFrameNo outingFrameNoSet = new OutingFrameNo(outingFrameNo);
						
						
						Optional<TimeActualStamp> goOutSet = Optional.empty();
						// Optional 
						{
							// attendanceStamp
							if (e.getElementsByTagName("goOut").item(0) != null) {
								Element element = (Element) e.getElementsByTagName("goOut").item(0);
								Optional<WorkStamp> actualStampSet = Optional.empty();
								// Optional 
								{
									// actualStamp
									if (element.getElementsByTagName("actualStamp").item(0) != null) {
										Element actualStampElement = (Element) element.getElementsByTagName("actualStamp").item(0);
										Integer afterRoundingTime = Integer.parseInt(actualStampElement.getElementsByTagName("afterRoundingTime").item(0).getTextContent());
										TimeWithDayAttr afterRoundingTimeSet = new TimeWithDayAttr(afterRoundingTime);
										WorkTimeInformation timeDaySet;
										{
											// timeDay
											Element startTime1Element = (Element) actualStampElement.getElementsByTagName("timeDay").item(0);
											// create ReasonTimeChange
											Element reasonTimeChangeElement = (Element) startTime1Element.getElementsByTagName("reasonTimeChange");
											Integer timeChangeMeans = Integer.parseInt(
													reasonTimeChangeElement.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
											TimeChangeMeans timeChangeMeansSet = EnumAdaptor.valueOf(timeChangeMeans, TimeChangeMeans.class);
											// Optional engravingMethod
											Optional<EngravingMethod> engravingMethodSet = Optional.empty();
											Element engravingMethodElement = (Element) reasonTimeChangeElement
													.getElementsByTagName("engravingMethod").item(0);
											if (reasonTimeChangeElement.getElementsByTagName("engravingMethod").item(0) != null) {
												Integer engravingMethod = Integer.parseInt(engravingMethodElement.getTextContent());
												EngravingMethod engravingMethodSetTemp = EnumAdaptor.valueOf(engravingMethod,
														EngravingMethod.class);
												engravingMethodSet = Optional.ofNullable(engravingMethodSetTemp);
											}
											ReasonTimeChange reasonTimeChangeSet = new ReasonTimeChange(timeChangeMeansSet,
													engravingMethodSet.isPresent() ? engravingMethodSet.get() : null);
											// create timeWithDay
											Element timeWithDayElement = (Element) startTime1Element.getElementsByTagName("timeWithDay").item(0);
											Optional<TimeWithDayAttr> timeWithDaySet = Optional.empty();
											if (timeWithDayElement != null) {
												Integer timeWithDay = Integer.parseInt(timeWithDayElement.getTextContent());
												timeWithDaySet = Optional.of(new TimeWithDayAttr(timeWithDay));
											}
											timeDaySet = new WorkTimeInformation(reasonTimeChangeSet,
													timeWithDaySet.isPresent() ? timeWithDaySet.get() : null);
										}
										
										// Optional
										Optional<WorkLocationCD> locationCodeSet = Optional.empty();
										{
											// locationCode
											if (actualStampElement.getElementsByTagName("locationCode").item(0) != null) {
												Element locationCodeElement = (Element)actualStampElement.getElementsByTagName("locationCode").item(0);
												String locationCode = locationCodeElement.getTextContent();
												WorkLocationCD locationCodeTemp = new WorkLocationCD(locationCode);
												locationCodeSet = Optional.of(locationCodeTemp);
											}
										}
										
										actualStampSet = Optional.of(new WorkStamp(afterRoundingTimeSet, timeDaySet, locationCodeSet));
										
									}
								}
								
								Optional<WorkStamp> stampSet = Optional.empty();
								// Optional 
								{
									// actualStamp
									if (element.getElementsByTagName("stamp").item(0) != null) {
										Element actualStampElement = (Element) element.getElementsByTagName("stamp").item(0);
										Integer afterRoundingTime = Integer.parseInt(actualStampElement.getElementsByTagName("afterRoundingTime").item(0).getTextContent());
										TimeWithDayAttr afterRoundingTimeSet = new TimeWithDayAttr(afterRoundingTime);
										WorkTimeInformation timeDaySet;
										{
											// timeDay
											Element startTime1Element = (Element) actualStampElement.getElementsByTagName("timeDay").item(0);
											// create ReasonTimeChange
											Element reasonTimeChangeElement = (Element) startTime1Element.getElementsByTagName("reasonTimeChange");
											Integer timeChangeMeans = Integer.parseInt(
													reasonTimeChangeElement.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
											TimeChangeMeans timeChangeMeansSet = EnumAdaptor.valueOf(timeChangeMeans, TimeChangeMeans.class);
											// Optional engravingMethod
											Optional<EngravingMethod> engravingMethodSet = Optional.empty();
											Element engravingMethodElement = (Element) reasonTimeChangeElement
													.getElementsByTagName("engravingMethod").item(0);
											if (reasonTimeChangeElement.getElementsByTagName("engravingMethod").item(0) != null) {
												Integer engravingMethod = Integer.parseInt(engravingMethodElement.getTextContent());
												EngravingMethod engravingMethodSetTemp = EnumAdaptor.valueOf(engravingMethod,
														EngravingMethod.class);
												engravingMethodSet = Optional.ofNullable(engravingMethodSetTemp);
											}
											ReasonTimeChange reasonTimeChangeSet = new ReasonTimeChange(timeChangeMeansSet,
													engravingMethodSet.isPresent() ? engravingMethodSet.get() : null);
											// create timeWithDay
											Element timeWithDayElement = (Element) startTime1Element.getElementsByTagName("timeWithDay").item(0);
											Optional<TimeWithDayAttr> timeWithDaySet = Optional.empty();
											if (timeWithDayElement != null) {
												Integer timeWithDay = Integer.parseInt(timeWithDayElement.getTextContent());
												timeWithDaySet = Optional.of(new TimeWithDayAttr(timeWithDay));
											}
											timeDaySet = new WorkTimeInformation(reasonTimeChangeSet,
													timeWithDaySet.isPresent() ? timeWithDaySet.get() : null);
										}
										
										// Optional
										Optional<WorkLocationCD> locationCodeSet = Optional.empty();
										{
											// locationCode
											if (actualStampElement.getElementsByTagName("locationCode").item(0) != null) {
												Element locationCodeElement = (Element)actualStampElement.getElementsByTagName("locationCode").item(0);
												String locationCode = locationCodeElement.getTextContent();
												WorkLocationCD locationCodeTemp = new WorkLocationCD(locationCode);
												locationCodeSet = Optional.of(locationCodeTemp);
											}
										}
										
										stampSet = Optional.of(new WorkStamp(afterRoundingTimeSet, timeDaySet, locationCodeSet));
										
									}
								}
								
								Integer numberOfReflectionStampSet = Integer.parseInt(element.getElementsByTagName("numberOfReflectionStamp").item(0).getTextContent());

								Optional<OvertimeDeclaration> overtimeDeclarationSet = Optional.empty();
								// Optional
								{
									if (element.getElementsByTagName("overtimeDeclaration").item(0) != null) {
										Element overtimeDeclarationElement = (Element) element.getElementsByTagName("overtimeDeclaration").item(0);
										Integer overTime = Integer.parseInt(overtimeDeclarationElement.getElementsByTagName("overTime").item(0).getTextContent());
										AttendanceTime overTimeSet = new AttendanceTime(overTime);
										
										Integer overLateNightTime = Integer.parseInt(overtimeDeclarationElement.getElementsByTagName("overLateNightTime").item(0).getTextContent());
										AttendanceTime overLateNightTimeSet = new AttendanceTime(overLateNightTime);
										
										Optional.of(new OvertimeDeclaration(overTimeSet, overLateNightTimeSet));
									}									
								}
								Optional<TimeZone> timeVacationSet = Optional.empty();
								//Optional 
								{
									if (element.getElementsByTagName("timeVacation").item(0) != null) {
										Element timeVacationElement = (Element) element.getElementsByTagName("timeVacation").item(0);
										Integer start = Integer.parseInt(timeVacationElement.getElementsByTagName("start").item(0).getTextContent());
										Integer end = Integer.parseInt(timeVacationElement.getElementsByTagName("end").item(0).getTextContent());
										TimeZone timeVacationTemp = new TimeZone(new TimeWithDayAttr(start), new TimeWithDayAttr(end));
										timeVacationSet = Optional.of(timeVacationTemp);
									}
								}

								goOutSet = Optional.of(new TimeActualStamp(
										actualStampSet,
										stampSet,
										numberOfReflectionStampSet,
										overtimeDeclarationSet,
										timeVacationSet));
								
								
							}
							
					
						}
						
						Integer outingTimeCalculation = Integer.parseInt(e.getElementsByTagName("outingTimeCalculation").item(0).getTextContent());
						AttendanceTime outingTimeCalculationSet = new AttendanceTime(outingTimeCalculation);
						
						Integer outingTime = Integer.parseInt(e.getElementsByTagName("outingTime").item(0).getTextContent());
						AttendanceTime outingTimeSet = new AttendanceTime(outingTime);
						
						Integer reasonForGoOut = Integer.parseInt(e.getElementsByTagName("reasonForGoOut").item(0).getTextContent());
						GoingOutReason reasonForGoOutSet = EnumAdaptor.valueOf(reasonForGoOut, GoingOutReason.class);
						
						Optional<TimeActualStamp> comeBackSet = Optional.empty();
						// Optional 
						{
							// attendanceStamp
							if (e.getElementsByTagName("comeBack").item(0) != null) {
								Element element = (Element) e.getElementsByTagName("comeBack").item(0);
								Optional<WorkStamp> actualStampSet = Optional.empty();
								// Optional 
								{
									// actualStamp
									if (element.getElementsByTagName("actualStamp").item(0) != null) {
										Element actualStampElement = (Element) element.getElementsByTagName("actualStamp").item(0);
										Integer afterRoundingTime = Integer.parseInt(actualStampElement.getElementsByTagName("afterRoundingTime").item(0).getTextContent());
										TimeWithDayAttr afterRoundingTimeSet = new TimeWithDayAttr(afterRoundingTime);
										WorkTimeInformation timeDaySet;
										{
											// timeDay
											Element startTime1Element = (Element) actualStampElement.getElementsByTagName("timeDay").item(0);
											// create ReasonTimeChange
											Element reasonTimeChangeElement = (Element) startTime1Element.getElementsByTagName("reasonTimeChange");
											Integer timeChangeMeans = Integer.parseInt(
													reasonTimeChangeElement.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
											TimeChangeMeans timeChangeMeansSet = EnumAdaptor.valueOf(timeChangeMeans, TimeChangeMeans.class);
											// Optional engravingMethod
											Optional<EngravingMethod> engravingMethodSet = Optional.empty();
											Element engravingMethodElement = (Element) reasonTimeChangeElement
													.getElementsByTagName("engravingMethod").item(0);
											if (reasonTimeChangeElement.getElementsByTagName("engravingMethod").item(0) != null) {
												Integer engravingMethod = Integer.parseInt(engravingMethodElement.getTextContent());
												EngravingMethod engravingMethodSetTemp = EnumAdaptor.valueOf(engravingMethod,
														EngravingMethod.class);
												engravingMethodSet = Optional.ofNullable(engravingMethodSetTemp);
											}
											ReasonTimeChange reasonTimeChangeSet = new ReasonTimeChange(timeChangeMeansSet,
													engravingMethodSet.isPresent() ? engravingMethodSet.get() : null);
											// create timeWithDay
											Element timeWithDayElement = (Element) startTime1Element.getElementsByTagName("timeWithDay").item(0);
											Optional<TimeWithDayAttr> timeWithDaySet = Optional.empty();
											if (timeWithDayElement != null) {
												Integer timeWithDay = Integer.parseInt(timeWithDayElement.getTextContent());
												timeWithDaySet = Optional.of(new TimeWithDayAttr(timeWithDay));
											}
											timeDaySet = new WorkTimeInformation(reasonTimeChangeSet,
													timeWithDaySet.isPresent() ? timeWithDaySet.get() : null);
										}
										
										// Optional
										Optional<WorkLocationCD> locationCodeSet = Optional.empty();
										{
											// locationCode
											if (actualStampElement.getElementsByTagName("locationCode").item(0) != null) {
												Element locationCodeElement = (Element)actualStampElement.getElementsByTagName("locationCode").item(0);
												String locationCode = locationCodeElement.getTextContent();
												WorkLocationCD locationCodeTemp = new WorkLocationCD(locationCode);
												locationCodeSet = Optional.of(locationCodeTemp);
											}
										}
										
										actualStampSet = Optional.of(new WorkStamp(afterRoundingTimeSet, timeDaySet, locationCodeSet));
										
									}
								}
								
								Optional<WorkStamp> stampSet = Optional.empty();
								// Optional 
								{
									// actualStamp
									if (element.getElementsByTagName("stamp").item(0) != null) {
										Element actualStampElement = (Element) element.getElementsByTagName("stamp").item(0);
										Integer afterRoundingTime = Integer.parseInt(actualStampElement.getElementsByTagName("afterRoundingTime").item(0).getTextContent());
										TimeWithDayAttr afterRoundingTimeSet = new TimeWithDayAttr(afterRoundingTime);
										WorkTimeInformation timeDaySet;
										{
											// timeDay
											Element startTime1Element = (Element) actualStampElement.getElementsByTagName("timeDay").item(0);
											// create ReasonTimeChange
											Element reasonTimeChangeElement = (Element) startTime1Element.getElementsByTagName("reasonTimeChange");
											Integer timeChangeMeans = Integer.parseInt(
													reasonTimeChangeElement.getElementsByTagName("timeChangeMeans").item(0).getTextContent());
											TimeChangeMeans timeChangeMeansSet = EnumAdaptor.valueOf(timeChangeMeans, TimeChangeMeans.class);
											// Optional engravingMethod
											Optional<EngravingMethod> engravingMethodSet = Optional.empty();
											Element engravingMethodElement = (Element) reasonTimeChangeElement
													.getElementsByTagName("engravingMethod").item(0);
											if (reasonTimeChangeElement.getElementsByTagName("engravingMethod").item(0) != null) {
												Integer engravingMethod = Integer.parseInt(engravingMethodElement.getTextContent());
												EngravingMethod engravingMethodSetTemp = EnumAdaptor.valueOf(engravingMethod,
														EngravingMethod.class);
												engravingMethodSet = Optional.ofNullable(engravingMethodSetTemp);
											}
											ReasonTimeChange reasonTimeChangeSet = new ReasonTimeChange(timeChangeMeansSet,
													engravingMethodSet.isPresent() ? engravingMethodSet.get() : null);
											// create timeWithDay
											Element timeWithDayElement = (Element) startTime1Element.getElementsByTagName("timeWithDay").item(0);
											Optional<TimeWithDayAttr> timeWithDaySet = Optional.empty();
											if (timeWithDayElement != null) {
												Integer timeWithDay = Integer.parseInt(timeWithDayElement.getTextContent());
												timeWithDaySet = Optional.of(new TimeWithDayAttr(timeWithDay));
											}
											timeDaySet = new WorkTimeInformation(reasonTimeChangeSet,
													timeWithDaySet.isPresent() ? timeWithDaySet.get() : null);
										}
										
										// Optional
										Optional<WorkLocationCD> locationCodeSet = Optional.empty();
										{
											// locationCode
											if (actualStampElement.getElementsByTagName("locationCode").item(0) != null) {
												Element locationCodeElement = (Element)actualStampElement.getElementsByTagName("locationCode").item(0);
												String locationCode = locationCodeElement.getTextContent();
												WorkLocationCD locationCodeTemp = new WorkLocationCD(locationCode);
												locationCodeSet = Optional.of(locationCodeTemp);
											}
										}
										
										stampSet = Optional.of(new WorkStamp(afterRoundingTimeSet, timeDaySet, locationCodeSet));
										
									}
								}
								
								Integer numberOfReflectionStampSet = Integer.parseInt(element.getElementsByTagName("numberOfReflectionStamp").item(0).getTextContent());

								Optional<OvertimeDeclaration> overtimeDeclarationSet = Optional.empty();
								// Optional
								{
									if (element.getElementsByTagName("overtimeDeclaration").item(0) != null) {
										Element overtimeDeclarationElement = (Element) element.getElementsByTagName("overtimeDeclaration").item(0);
										Integer overTime = Integer.parseInt(overtimeDeclarationElement.getElementsByTagName("overTime").item(0).getTextContent());
										AttendanceTime overTimeSet = new AttendanceTime(overTime);
										
										Integer overLateNightTime = Integer.parseInt(overtimeDeclarationElement.getElementsByTagName("overLateNightTime").item(0).getTextContent());
										AttendanceTime overLateNightTimeSet = new AttendanceTime(overLateNightTime);
										
										Optional.of(new OvertimeDeclaration(overTimeSet, overLateNightTimeSet));
									}									
								}
								Optional<TimeZone> timeVacationSet = Optional.empty();
								//Optional 
								{
									if (element.getElementsByTagName("timeVacation").item(0) != null) {
										Element timeVacationElement = (Element) element.getElementsByTagName("timeVacation").item(0);
										Integer start = Integer.parseInt(timeVacationElement.getElementsByTagName("start").item(0).getTextContent());
										Integer end = Integer.parseInt(timeVacationElement.getElementsByTagName("end").item(0).getTextContent());
										TimeZone timeVacationTemp = new TimeZone(new TimeWithDayAttr(start), new TimeWithDayAttr(end));
										timeVacationSet = Optional.of(timeVacationTemp);
									}
								}

								comeBackSet = Optional.of(new TimeActualStamp(
										actualStampSet,
										stampSet,
										numberOfReflectionStampSet,
										overtimeDeclarationSet,
										timeVacationSet));
								
								
							}
							
					
						}
						OutingTimeSheet outingTimeSheet = new OutingTimeSheet(
								outingFrameNoSet,
								goOutSet,
								outingTimeCalculationSet,
								outingTimeSet,
								reasonForGoOutSet,
								comeBackSet);	
						
						outHoursLst.add(outingTimeSheet);
					}
				}
				record.setOutHoursLst(outHoursLst);
			}
			
			
			{
				// shortWorkingTimeSheets
				List<ShortWorkingTimeSheet> shortWorkingTimeSheets = new ArrayList<>();
				Element rootElement = (Element)eElement.getElementsByTagName("shortWorkingTimeSheets");
				NodeList nodes = rootElement.getElementsByTagName("ShortWorkingTimeSheet");
				for (int itr = 0; itr < nodes.getLength(); itr++) {
					Node node = nodes.item(itr);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) node;
						Integer shortWorkTimeFrameNo = Integer.parseInt(e.getElementsByTagName("shortWorkTimeFrameNo").item(0).getTextContent());
						ShortWorkTimFrameNo shortWorkTimeFrameNoSet = new ShortWorkTimFrameNo(shortWorkTimeFrameNo);
						Integer childCareAttr = Integer.parseInt(e.getElementsByTagName("childCareAttr").item(0).getTextContent());
						ChildCareAttribute childCareAttrSet = EnumAdaptor.valueOf(childCareAttr, ChildCareAttribute.class);
						Integer startTime = Integer.parseInt(e.getElementsByTagName("startTime").item(0).getTextContent());
						TimeWithDayAttr startTimeSet = new TimeWithDayAttr(startTime);
						Integer endTime = Integer.parseInt(e.getElementsByTagName("endTime").item(0).getTextContent());
						TimeWithDayAttr endTimeSet = new TimeWithDayAttr(endTime);
						Integer deductionTime = Integer.parseInt(e.getElementsByTagName("deductionTime").item(0).getTextContent());
						TimeWithDayAttr deductionTimeSet = new TimeWithDayAttr(deductionTime);
						Integer shortTime = Integer.parseInt(e.getElementsByTagName("shortTime").item(0).getTextContent());
						TimeWithDayAttr shortTimeSet = new TimeWithDayAttr(shortTime);
						
						ShortWorkingTimeSheet sheet = new ShortWorkingTimeSheet(
								shortWorkTimeFrameNoSet,
								childCareAttrSet,
								startTimeSet,
								endTimeSet);
						shortWorkingTimeSheets.add(sheet);
						
					}
				}
				record.setShortWorkingTimeSheets(shortWorkingTimeSheets);
				
			}
			
			
			{
				// breakTimeSheets
				List<BreakTimeSheet> breakTimeSheets = new ArrayList<>();
				Element rootElement = (Element)eElement.getElementsByTagName("breakTimeSheets");
				NodeList nodes = rootElement.getElementsByTagName("BreakTimeSheet");
				for (int itr = 0; itr < nodes.getLength(); itr++) {
					Node node = nodes.item(itr);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element e = (Element) node;
						Integer breakFrameNo = Integer.parseInt(e.getElementsByTagName("breakFrameNo").item(0).getTextContent());
						BreakFrameNo breakFrameNoSet = new BreakFrameNo(breakFrameNo);
						
						Integer startTime = Integer.parseInt(e.getElementsByTagName("startTime").item(0).getTextContent());
						TimeWithDayAttr startTimeSet = new TimeWithDayAttr(startTime);
						
						Integer endTime = Integer.parseInt(e.getElementsByTagName("endTime").item(0).getTextContent());
						TimeWithDayAttr endTimeSet = new TimeWithDayAttr(endTime);
						
						Integer breakTime = Integer.parseInt(e.getElementsByTagName("breakTime").item(0).getTextContent());
						AttendanceTime breakTimeSet = new AttendanceTime(breakTime);
						
						BreakTimeSheet breakTimeSheet = new BreakTimeSheet(
								breakFrameNoSet,
								startTimeSet,
								endTimeSet,
								breakTimeSet);
						breakTimeSheets.add(breakTimeSheet);
					}
				}
				record.setBreakTimeSheets(breakTimeSheets);
			}
			
			{
				// overTimeMidnight
				Element rootElement = (Element)eElement.getElementsByTagName("overTimeMidnight");
				AttendanceTime timeSet;
				AttendanceTime calcTimeSet;
				AttendanceTime divergenceTimeset;
				{
					Integer time = Integer.parseInt(rootElement.getElementsByTagName("time").item(0).getTextContent());
					timeSet = new AttendanceTime(time);
				}
				{
					Integer time = Integer.parseInt(rootElement.getElementsByTagName("calcTime").item(0).getTextContent());
					calcTimeSet = new AttendanceTime(time);
				}
				{
					Integer time = Integer.parseInt(rootElement.getElementsByTagName("divergenceTime").item(0).getTextContent());
					divergenceTimeset = new AttendanceTime(time);
				}
				TimeDivergenceWithCalculation timeDivergenceWithCalculation = new TimeDivergenceWithCalculation(
						timeSet,
						calcTimeSet,
						divergenceTimeset);
				record.setOverTimeMidnight(timeDivergenceWithCalculation);
			}
			
			{
				// midnightOnHoliday
				Element rootElement = (Element)eElement.getElementsByTagName("midnightOnHoliday");
				AttendanceTime timeSet;
				AttendanceTime calcTimeSet;
				AttendanceTime divergenceTimeset;
				{
					Integer time = Integer.parseInt(rootElement.getElementsByTagName("time").item(0).getTextContent());
					timeSet = new AttendanceTime(time);
				}
				{
					Integer time = Integer.parseInt(rootElement.getElementsByTagName("calcTime").item(0).getTextContent());
					calcTimeSet = new AttendanceTime(time);
				}
				{
					Integer time = Integer.parseInt(rootElement.getElementsByTagName("divergenceTime").item(0).getTextContent());
					divergenceTimeset = new AttendanceTime(time);
				}
				TimeDivergenceWithCalculation timeDivergenceWithCalculation = new TimeDivergenceWithCalculation(
						timeSet,
						calcTimeSet,
						divergenceTimeset);
				record.setMidnightOnHoliday(timeDivergenceWithCalculation);
			}
			
			{
				// outOfMidnight
				Element rootElement = (Element)eElement.getElementsByTagName("outOfMidnight");
				AttendanceTime timeSet;
				AttendanceTime calcTimeSet;
				AttendanceTime divergenceTimeset;
				{
					Integer time = Integer.parseInt(rootElement.getElementsByTagName("time").item(0).getTextContent());
					timeSet = new AttendanceTime(time);
				}
				{
					Integer time = Integer.parseInt(rootElement.getElementsByTagName("calcTime").item(0).getTextContent());
					calcTimeSet = new AttendanceTime(time);
				}
				{
					Integer time = Integer.parseInt(rootElement.getElementsByTagName("divergenceTime").item(0).getTextContent());
					divergenceTimeset = new AttendanceTime(time);
				}
				TimeDivergenceWithCalculation timeDivergenceWithCalculation = new TimeDivergenceWithCalculation(
						timeSet,
						calcTimeSet,
						divergenceTimeset);
				record.setOutOfMidnight(timeDivergenceWithCalculation);
			}
			
			
			{
				// midnightPublicHoliday
				Element rootElement = (Element)eElement.getElementsByTagName("midnightPublicHoliday");
				AttendanceTime timeSet;
				AttendanceTime calcTimeSet;
				AttendanceTime divergenceTimeset;
				{
					Integer time = Integer.parseInt(rootElement.getElementsByTagName("time").item(0).getTextContent());
					timeSet = new AttendanceTime(time);
				}
				{
					Integer time = Integer.parseInt(rootElement.getElementsByTagName("calcTime").item(0).getTextContent());
					calcTimeSet = new AttendanceTime(time);
				}
				{
					Integer time = Integer.parseInt(rootElement.getElementsByTagName("divergenceTime").item(0).getTextContent());
					divergenceTimeset = new AttendanceTime(time);
				}
				TimeDivergenceWithCalculation timeDivergenceWithCalculation = new TimeDivergenceWithCalculation(
						timeSet,
						calcTimeSet,
						divergenceTimeset);
				record.setMidnightPublicHoliday(timeDivergenceWithCalculation);
			}
			
			
			
			
			
		} catch (Exception e) {
			return null;
		}
		return record;
	}

}
