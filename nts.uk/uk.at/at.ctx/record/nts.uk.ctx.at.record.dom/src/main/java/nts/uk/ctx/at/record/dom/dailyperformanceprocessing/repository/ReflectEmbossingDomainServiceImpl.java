package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.AttendanceLeavingGateOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.PCLogOnInfoOfDailyRepo;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.PCLogonLogoffReflectOuput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ProcessTimeOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ReflectEntryGateOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ReflectStampOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.TimePrintDestinationOutput;
import nts.uk.ctx.at.record.dom.goout.OutingManagement;
import nts.uk.ctx.at.record.dom.goout.repository.OutingManagementRepository;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporary;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryRepository;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.shared.dom.calculationsetting.repository.StampReflectionManagementRepository;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
//import nts.uk.ctx.at.shared.dom.employmentrules.temporarywork.repository.TempWorkUseManageRepository;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.AttendanceLeavingGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.LogOnInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.entranceandexit.PCLogOnNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectTimezoneOutput;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.TimeZoneOutput;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FontRearSection;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.MultiStampTimePiorityAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.PrioritySetting;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.common.RoundingTimeUnit;
import nts.uk.ctx.at.shared.dom.worktime.common.StampPiorityAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.Superiority;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneStampSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class ReflectEmbossingDomainServiceImpl implements ReflectEmbossingDomainService {
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeRepo;
	@Inject
	private StampReflectionManagementRepository stampRepo;
	@Inject
	private WorkInformationRepository workInforRepo;
	@Inject 
	private RecordDomRequireService requireService;
//	@Inject
//	private WorkTypeRepository WorkRepo;
	@Inject
	private BasicScheduleService basicScheduleService;
	@Inject
	private ReflectWorkInformationDomainService reflectWorkInformationDomainService;
	@Inject
	private OutingTimeOfDailyPerformanceRepository OutRepo;
	@Inject
	private TemporaryTimeOfDailyPerformanceRepository temporaryTimeRepo;
	@Inject
	private AttendanceLeavingGateOfDailyRepo attendanceLeavingGateOfDailyRepo;
	@Inject
	private PCLogOnInfoOfDailyRepo PCLogOnInfoOfDailyRepo;
	@Inject
	private OutingManagementRepository outingManagementRepo;
//	@Inject
//	private TempWorkUseManageRepository tempWorkUseManageRepo;
	@Inject
	private ManageWorkTemporaryRepository temporaryWorkManageRepo;

	@Override
	public ReflectStampOutput reflectStamp(WorkInfoOfDailyPerformance WorkInfo,
			TimeLeavingOfDailyPerformance timeDailyPer, List<Stamp> lstStamp, StampReflectRangeOutput s,
			GeneralDate date, String employeeId, String companyId) {
		List<Stamp> stamps = new ArrayList<Stamp>();

		OutingTimeOfDailyPerformance outingDailyPerformance = null;
		TemporaryTimeOfDailyPerformance temporaryPerformance = null;
		TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance = null;
		AttendanceLeavingGateOfDaily attendanceLeavingGateOfDaily = null;
		PCLogOnInfoOfDaily pcLogOnInfoOfDaily = null;
		WorkInfoOfDailyPerformance dailyPerformance = new WorkInfoOfDailyPerformance(employeeId, date, WorkInfo.getWorkInformation());
		if (lstStamp == null) {
			return null;
		}
		int size = lstStamp.size();
		for (int i = 0; i < size; i++) {

			Stamp x = lstStamp.get(i);
			//Con 12 13 mới có chưa làm. 
			switch (x.getType().getChangeClockArt().value) {
			case 0: // 出勤 //出勤を反映する
				String confirmReflectRange = this.confirmReflectRange(x, s);
				if ("range1".equals(confirmReflectRange)) {
					// 出退勤区分 = 出勤
					String attendanceClass = "出勤";
					// 実打刻区分 = 実打刻
					String actualStampClass = "実打刻";
					int worktNo = 1;
					TimeLeavingOfDailyPerformance timeLeaving1 = null;
					TimeLeavingOfDailyPerformance timeDailyPer1 = null;
					if (timeLeavingOfDailyPerformance == null) {
						Optional<TimeLeavingOfDailyPerformance> timeOptional = this.timeRepo.findByKey(employeeId,
								date);
						if (timeOptional.isPresent()) {
							TimeLeavingOfDailyPerformance timeLeaving = timeOptional.get();
							timeLeaving1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeLeaving, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						} else {
							timeDailyPer1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeDailyPer, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						}

					} else {
						timeLeavingOfDailyPerformance = this.reflectActualTimeOrAttendence(stamps, WorkInfo,
								timeLeavingOfDailyPerformance, date, employeeId, x, attendanceClass, actualStampClass,
								worktNo, companyId);
					}

					// 出退勤区分 = 出勤
					attendanceClass = "出勤";
					// 実打刻区分 = 実打刻
					actualStampClass = "打刻";
					worktNo = 1;

					if (timeLeavingOfDailyPerformance == null) {
						if (timeLeaving1 != null) {

							timeLeaving1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeLeaving1, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						} else {
							timeDailyPer1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeDailyPer1, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						}
					} else {
						timeLeavingOfDailyPerformance = this.reflectActualTimeOrAttendence(stamps, WorkInfo,
								timeLeavingOfDailyPerformance, date, employeeId, x, attendanceClass, actualStampClass,
								worktNo, companyId);
					}

					if (timeLeavingOfDailyPerformance == null) {
						timeLeavingOfDailyPerformance = timeLeaving1 != null ? timeLeaving1 : timeDailyPer1;
					}

				} else if ("range2".equals(confirmReflectRange)) {
					// 出退勤区分 = 出勤
					String attendanceClass = "出勤";
					// 実打刻区分 = 実打刻
					String actualStampClass = "実打刻";
					int worktNo = 2;
					Optional<TimeLeavingOfDailyPerformance> timeOptional = this.timeRepo.findByKey(employeeId, date);
					TimeLeavingOfDailyPerformance timeLeaving1 = null;
					TimeLeavingOfDailyPerformance timeDailyPer1 = null;

					if (timeLeavingOfDailyPerformance == null) {
						if (timeOptional.isPresent()) {
							TimeLeavingOfDailyPerformance timeLeaving = timeOptional.get();
							timeLeaving1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeLeaving, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						} else {
							timeDailyPer1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeDailyPer, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						}
					} else {
						timeLeavingOfDailyPerformance = this.reflectActualTimeOrAttendence(stamps, WorkInfo,
								timeLeavingOfDailyPerformance, date, employeeId, x, attendanceClass, actualStampClass,
								worktNo, companyId);
					}

					// 出退勤区分 = 出勤
					attendanceClass = "出勤";
					// 実打刻区分 = 実打刻
					actualStampClass = "打刻";
					worktNo = 2;

					if (timeLeavingOfDailyPerformance == null) {
						if (timeLeaving1 != null) {
							timeLeaving1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeLeaving1, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						} else {
							timeDailyPer1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeDailyPer, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						}
					} else {
						timeLeavingOfDailyPerformance = this.reflectActualTimeOrAttendence(stamps, WorkInfo,
								timeLeavingOfDailyPerformance, date, employeeId, x, attendanceClass, actualStampClass,
								worktNo, companyId);
					}

					if (timeLeavingOfDailyPerformance == null) {
						timeLeavingOfDailyPerformance = timeLeaving1 != null ? timeLeaving1 : timeDailyPer1;
					}

				} else {

				}
				break;
			case 1: // 退勤 //退勤を反映する
				// in or outrange
				String confirmReflectRangeLeavingTime = this.confirmReflectRangeLeavingTime(x, s);
				if ("range1".equals(confirmReflectRangeLeavingTime)) {
					// 出退勤区分 = 退勤
					String attendanceClass = "退勤";
					// 実打刻区分 = 実打刻
					String actualStampClass = "実打刻";
					int worktNo = 1;
					Optional<TimeLeavingOfDailyPerformance> timeOptional = this.timeRepo.findByKey(employeeId, date);
					TimeLeavingOfDailyPerformance timeLeaving1 = null;
					TimeLeavingOfDailyPerformance timeDailyPer1 = null;

					if (timeLeavingOfDailyPerformance == null) {
						if (timeOptional.isPresent()) {
							TimeLeavingOfDailyPerformance timeLeaving = timeOptional.get();
							timeLeaving1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeLeaving, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						} else {
							timeDailyPer1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeDailyPer, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						}
					} else {
						timeLeavingOfDailyPerformance = this.reflectActualTimeOrAttendence(stamps, WorkInfo,
								timeLeavingOfDailyPerformance, date, employeeId, x, attendanceClass, actualStampClass,
								worktNo, companyId);
					}

					// 出退勤区分 = 退勤
					attendanceClass = "退勤";
					// 実打刻区分 = 打刻
					actualStampClass = "打刻";
					worktNo = 1;

					if (timeLeavingOfDailyPerformance == null) {
						if (timeLeaving1 != null) {
							timeLeaving1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeLeaving1, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						} else {
							timeDailyPer1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeDailyPer, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						}
					} else {
						timeLeavingOfDailyPerformance = this.reflectActualTimeOrAttendence(stamps, WorkInfo,
								timeLeavingOfDailyPerformance, date, employeeId, x, attendanceClass, actualStampClass,
								worktNo, companyId);
					}
					if (timeLeavingOfDailyPerformance == null) {
						timeLeavingOfDailyPerformance = timeLeaving1 != null ? timeLeaving1 : timeDailyPer1;
					}

				} else if ("range2".equals(confirmReflectRangeLeavingTime)) {
					// 出退勤区分 = 退勤
					String attendanceClass = "退勤";
					// 実打刻区分 = 実打刻
					String actualStampClass = "実打刻";
					int worktNo = 2;
					Optional<TimeLeavingOfDailyPerformance> timeOptional = this.timeRepo.findByKey(employeeId, date);
					TimeLeavingOfDailyPerformance timeLeaving1 = null;
					TimeLeavingOfDailyPerformance timeDailyPer1 = null;
					if (timeLeavingOfDailyPerformance == null) {
						if (timeOptional.isPresent()) {
							TimeLeavingOfDailyPerformance timeLeaving = timeOptional.get();
							timeLeaving1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeLeaving, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						} else {
							timeDailyPer1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeDailyPer, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						}
					} else {
						timeLeavingOfDailyPerformance = this.reflectActualTimeOrAttendence(stamps, WorkInfo,
								timeLeavingOfDailyPerformance, date, employeeId, x, attendanceClass, actualStampClass,
								worktNo, companyId);
					}

					// 出退勤区分 = 退勤
					attendanceClass = "退勤";
					// 実打刻区分 = 打刻
					actualStampClass = "打刻";
					worktNo = 2;
					if (timeLeavingOfDailyPerformance == null) {
						if (timeLeaving1 != null) {
							timeLeaving1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeLeaving1, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						} else {
							timeDailyPer1 = this.reflectActualTimeOrAttendence(stamps, WorkInfo, timeDailyPer, date,
									employeeId, x, attendanceClass, actualStampClass, worktNo, companyId);
						}
					} else {
						timeLeavingOfDailyPerformance = this.reflectActualTimeOrAttendence(stamps, WorkInfo,
								timeLeavingOfDailyPerformance, date, employeeId, x, attendanceClass, actualStampClass,
								worktNo, companyId);
					}

					if (timeLeavingOfDailyPerformance == null) {
						timeLeavingOfDailyPerformance = timeLeaving1 != null ? timeLeaving1 : timeDailyPer1;
					}

				} else {

				}

				break;
			case 4:// 外出・戻りを反映する
			case 5: // 外出,戻り
				// Thay đổi 打刻の時刻 của ngày đang xử lý thành 時刻 tương ứng với
				// ngày đang xử lý
				// 打刻を反映するか確認する (Xác nhận )
				ProcessTimeOutput processTimeOutput = new ProcessTimeOutput();
				boolean confirmReflectStamp = confirmReflectStamp(s, x, processTimeOutput);
				if (confirmReflectStamp) {
					// stampAtr
					ChangeClockArt stampAtr = x.getType().getChangeClockArt();
					// 外出
					if (stampAtr.value == 4) {
						// *7 外出打刻を反映する (Phản ánh 外出打刻 (thời diểm check ra
						// ngoài))

						String attendanceClass = "外出";
						outingDailyPerformance = reflectTimeGoOutCheck(dailyPerformance, attendanceClass, stamps, date,
								employeeId, x, processTimeOutput, companyId, outingDailyPerformance);

						// *7
					}
					// 戻り
					else if (stampAtr.value == 5) {
						// 8* 戻り打刻を反映する (Phản ánh 戻り打刻 (THời điểm check quay
						// về))
						String attendanceClass = "戻り";
						outingDailyPerformance = reflectTimeComeBackCheck(dailyPerformance, attendanceClass, stamps, date,
								employeeId, x, processTimeOutput, companyId, outingDailyPerformance);
						// 8*
					}
				} else {

				}

				break;
			case 7:
			case 9:// 開始 , 終了
					// 9* ドメインモデル「臨時勤務管理」を取得する (Lấy về domain model "臨時勤務管理")
					// 9*
				boolean isUse = true;
				// Optional<TemporaryWorkUseManage>
				// temporaryWorkUseManageOptional = this.tempWorkUseManageRepo
				// .findByKey(companyId);
				// if (temporaryWorkUseManageOptional.isPresent()) {
				// NotUseAtr useClassification =
				// temporaryWorkUseManageOptional.get().getUseClassification();
				// isUse = (useClassification.value == 1) ? true : false;
				// }
				if (isUse) {
					// 10* Chuyển thời gian check tay đang xử lý sang thời gian
					// tương ứng với ngày tháng năm đang xử lý
					ProcessTimeOutput processTimeOutput1 = new ProcessTimeOutput();
					AttendanceTime attendanceTime = x.getAttendanceTime().isPresent()?
							x.getAttendanceTime().get():new AttendanceTime(x.getStampDateTime().clockHourMinute().v());

					processTimeOutput1.setTimeOfDay(new TimeWithDayAttr(attendanceTime.v()));
					// 10*
					// 11* Xác nhận xem có phản ảnh check tay chưa)
					if (s.getTemporary().getStart().v().intValue() <= processTimeOutput1.getTimeOfDay().v().intValue()
							&& s.getTemporary().getEnd().v().intValue() >= processTimeOutput1.getTimeOfDay().v()
									.intValue()) {
						Optional<ManageWorkTemporary> temporaryWorkManageOptional = this.temporaryWorkManageRepo
								.findByCID(companyId);
						if (temporaryWorkManageOptional.isPresent()) {
							// reflect
							if (x.getType().getChangeClockArt().value == 7) {
								// 開始
								String attendanceClass = "出勤";
								temporaryPerformance = reflectTimeTemporaryStart(companyId, dailyPerformance, attendanceClass,
										stamps, date, employeeId, x, processTimeOutput1, temporaryPerformance);

							} else if (x.getType().getChangeClockArt().value == 9) {
								String attendanceClass = "退勤";
								// 終了
								temporaryPerformance = reflectTimeTemporaryEnd(companyId, dailyPerformance, attendanceClass,
										stamps, date, employeeId, x, processTimeOutput1, temporaryPerformance);
							}
						}
					}
					// 11*

				}

				break;
			case 2: // 入門 //入門を反映する
				String confirmReflectRanges = this.confirmReflectRange(x, s);
				if ("range1".equals(confirmReflectRanges)) {
					// 入門退門区分 = 入門
					String inOrOutClass = "入門";
					// 勤務回数 = 1
					int worktNo = 1;

					// 入退門を反映する

					// 反映先を取得する
					
					// lay tu a nam tren chuyen xuong
					AttendanceLeavingGateOfDaily attendanceLeavingGateOfDailyTemp = null;
					if (attendanceLeavingGateOfDaily == null) {
						attendanceLeavingGateOfDaily = reflectInOutCompany(companyId, dailyPerformance, date, employeeId,
								attendanceLeavingGateOfDailyTemp, worktNo, inOrOutClass, x, stamps);
					} else {
						attendanceLeavingGateOfDaily = reflectInOutCompany(companyId, dailyPerformance, date, employeeId,
								attendanceLeavingGateOfDaily, worktNo, inOrOutClass, x, stamps);
					}

				} else if ("range2".equals(confirmReflectRanges)) {
					// 入門退門区分 = 入門
					String inOrOutClass = "入門";
					// 勤務回数 = 2
					int worktNo = 2;
					// lay tu a nam tren chuyen xuong
					AttendanceLeavingGateOfDaily attendanceLeavingGateOfDailyTemp = null;
					if (attendanceLeavingGateOfDaily == null) {
						attendanceLeavingGateOfDaily = reflectInOutCompany(companyId, dailyPerformance, date, employeeId,
								attendanceLeavingGateOfDailyTemp, worktNo, inOrOutClass, x, stamps);
					} else {
						attendanceLeavingGateOfDaily = reflectInOutCompany(companyId, dailyPerformance, date, employeeId,
								attendanceLeavingGateOfDaily, worktNo, inOrOutClass, x, stamps);
					}
				}

				break;
			case 3: // 退門
				String confirmReflect = this.confirmReflectRangeLeavingTime(x, s);
				if ("range1".equals(confirmReflect)) {
					// 入門退門区分 = 退門
					String inOrOutClass = "	";
					// 勤務回数 = 1
					int worktNo = 1;

					// lay tu a nam tren chuyen xuong
					AttendanceLeavingGateOfDaily attendanceLeavingGateOfDailyTemp = null;
					if (attendanceLeavingGateOfDaily == null) {
						attendanceLeavingGateOfDaily = reflectInOutCompany(companyId, dailyPerformance, date, employeeId,
								attendanceLeavingGateOfDailyTemp, worktNo, inOrOutClass, x, stamps);
					} else {
						attendanceLeavingGateOfDaily = reflectInOutCompany(companyId, dailyPerformance, date, employeeId,
								attendanceLeavingGateOfDaily, worktNo, inOrOutClass, x, stamps);
					}

				} else if ("range2".equals(confirmReflect)) {
					// 入門退門区分 = 退門
					String inOrOutClass = "退門";
					// 勤務回数 = 2
					int worktNo = 2;
					// fixed lay tu a nam tren chuyen xuong
					AttendanceLeavingGateOfDaily attendanceLeavingGateOfDailyTemp = null;
					if (attendanceLeavingGateOfDaily == null) {
						attendanceLeavingGateOfDaily = reflectInOutCompany(companyId, dailyPerformance, date, employeeId,
								attendanceLeavingGateOfDailyTemp, worktNo, inOrOutClass, x, stamps);
					} else {
						attendanceLeavingGateOfDaily = reflectInOutCompany(companyId, dailyPerformance, date, employeeId,
								attendanceLeavingGateOfDaily, worktNo, inOrOutClass, x, stamps);
					}
				}
				break;
			case 10: // PCログオン //PCログオンを反映する
				String confirmReflec = this.confirmReflectRange(x, s);
				if ("range1".equals(confirmReflec)) {
					// 入門退門区分 = PCログオン
					String inOrOutClass = "PCログオン";
					// 勤務回数 = 1
					int worktNo = 1;

					// 入退門を反映する

					// 反映先を取得する

					// PCログオンログオフを反映する

					// 反映先を取得する
					// fixed lay tu a nam tren chuyen xuong
					PCLogOnInfoOfDaily pcLogOnInfoOfDailyTemp = null;
					if (pcLogOnInfoOfDaily == null) {
						pcLogOnInfoOfDaily = reflectInOutPC(companyId, date, employeeId, dailyPerformance, pcLogOnInfoOfDailyTemp, worktNo,
								inOrOutClass, x, stamps);
					} else {
						pcLogOnInfoOfDaily = reflectInOutPC(companyId, date, employeeId, dailyPerformance, pcLogOnInfoOfDaily, worktNo, inOrOutClass,
								x, stamps);
					}

				} else if ("range2".equals(confirmReflec)) {
					// 入門退門区分 = PCログオン
					String inOrOutClass = "PCログオン";
					// 勤務回数 = 2
					int worktNo = 2;
					// fixed lay tu a nam tren chuyen xuong
					PCLogOnInfoOfDaily pcLogOnInfoOfDailyTemp = null;
					if (pcLogOnInfoOfDaily == null) {
						pcLogOnInfoOfDaily = reflectInOutPC(companyId, date, employeeId, dailyPerformance, pcLogOnInfoOfDailyTemp, worktNo,
								inOrOutClass, x, stamps);
					} else {
						pcLogOnInfoOfDaily = reflectInOutPC(companyId, date, employeeId, dailyPerformance, pcLogOnInfoOfDaily, worktNo, inOrOutClass,
								x, stamps);
					}
				}
				break;
			case 11:// PCログオフ
				String confirmRangePcLogoff = this.confirmReflectRangeLeavingTime(x, s);
				if ("range1".equals(confirmRangePcLogoff)) {
					// 入門退門区分 = PCログオフ
					String inOrOutClass = "PCログオフ";
					// 勤務回数 = 1
					int worktNo = 1;

					// 入退門を反映する

					// 反映先を取得する

					// PCログオンログオフを反映する

					// 反映先を取得する
					// fixed lay tu a nam tren chuyen xuong
					PCLogOnInfoOfDaily pcLogOnInfoOfDailyTemp = null;
					if (pcLogOnInfoOfDaily == null) {
						pcLogOnInfoOfDaily = reflectInOutPC(companyId, date, employeeId, dailyPerformance, pcLogOnInfoOfDailyTemp, worktNo,
								inOrOutClass, x, stamps);
					} else {
						pcLogOnInfoOfDaily = reflectInOutPC(companyId, date, employeeId, dailyPerformance, pcLogOnInfoOfDaily, worktNo, inOrOutClass,
								x, stamps);
					}

				} else if ("range2".equals(confirmRangePcLogoff)) {
					// 入門退門区分 = PCログオフ
					String inOrOutClass = "PCログオフ";
					// 勤務回数 = 2
					int worktNo = 2;
					// fixed lay tu a nam tren chuyen xuong
					PCLogOnInfoOfDaily pcLogOnInfoOfDailyTemp = null;
					if (pcLogOnInfoOfDaily == null) {
						pcLogOnInfoOfDaily = reflectInOutPC(companyId, date, employeeId, dailyPerformance, pcLogOnInfoOfDailyTemp, worktNo,
								inOrOutClass, x, stamps);
					} else {
						pcLogOnInfoOfDaily = reflectInOutPC(companyId, date, employeeId, dailyPerformance, pcLogOnInfoOfDaily, worktNo, inOrOutClass,
								x, stamps);
					}
				}
				break;
			default:
				break;
			}
		}
		ReflectStampOutput reflectStampOutput = new ReflectStampOutput();
		reflectStampOutput.setAttendanceLeavingGateOfDaily(attendanceLeavingGateOfDaily);
		reflectStampOutput.setPcLogOnInfoOfDaily(pcLogOnInfoOfDaily);
		reflectStampOutput.setOutingTimeOfDailyPerformance(outingDailyPerformance);
		reflectStampOutput.setLstStamp(stamps);
		reflectStampOutput.setTemporaryTimeOfDailyPerformance(temporaryPerformance);

		boolean isPassed = false;
		for (int i = 0; i < size; i++) {
			Stamp x = lstStamp.get(i);
			if (x.getType().getChangeClockArt().value == 0 || x.getType().getChangeClockArt().value == 1) {
				isPassed = true;
				break;
			}
		}
		TimeLeavingOfDailyPerformance performance = new TimeLeavingOfDailyPerformance(employeeId, date, timeDailyPer.getAttendance());
		if (isPassed) {
			reflectStampOutput.setTimeLeavingOfDailyPerformance(timeLeavingOfDailyPerformance);
		} else {
			reflectStampOutput.setTimeLeavingOfDailyPerformance(performance);
		}

		return reflectStampOutput;

	}

	// PCログオンログオフを反映する
	PCLogOnInfoOfDaily reflectInOutPC(String companyId, GeneralDate date, String employeeId, WorkInfoOfDailyPerformance workInfo, PCLogOnInfoOfDaily pcLogOnInfoOfDailyTemp,
			int worktNo, String inOrOutClass, Stamp x, List<Stamp> stamps) {
		PCLogOnInfoOfDaily pcLogOnInfoOfDaily = null;
		int indexPCLogOnInfo = -1;
		PCLogonLogoffReflectOuput pcLogonLogoffReflectOuput = null;
		// 反映先を取得する
		if (pcLogOnInfoOfDailyTemp == null || pcLogOnInfoOfDailyTemp.getTimeZone().getLogOnInfo() == null
				|| pcLogOnInfoOfDailyTemp.getTimeZone().getLogOnInfo().isEmpty()) {
			// lay tu db
			Optional<PCLogOnInfoOfDaily> PCLogOnInfoOfDailyOptional = PCLogOnInfoOfDailyRepo.find(employeeId, date);
			if (PCLogOnInfoOfDailyOptional.isPresent() && PCLogOnInfoOfDailyOptional.get().getTimeZone().getLogOnInfo() != null
					&& !PCLogOnInfoOfDailyOptional.get().getTimeZone().getLogOnInfo().isEmpty()) {
				pcLogonLogoffReflectOuput = this.getPCLogonLogoffReflectOuput(PCLogOnInfoOfDailyOptional.get(), worktNo,
						inOrOutClass);
				indexPCLogOnInfo = this.getIndexPCLogOnInfo(PCLogOnInfoOfDailyOptional.get(), worktNo, inOrOutClass);
				pcLogOnInfoOfDaily = PCLogOnInfoOfDailyOptional.get();

			} else {
				ArrayList<LogOnInfo> lstLogOnInfo = new ArrayList<LogOnInfo>();
				// fixed LogOnInfo thuoc tinh dang khong dung can sua lai
				lstLogOnInfo.add(new LogOnInfo(new PCLogOnNo(worktNo), null, null));
				pcLogOnInfoOfDaily = new PCLogOnInfoOfDaily(employeeId, date, lstLogOnInfo);
				indexPCLogOnInfo = 0;
			}
		} else {
			pcLogonLogoffReflectOuput = this.getPCLogonLogoffReflectOuput(pcLogOnInfoOfDailyTemp, worktNo,
					inOrOutClass);
			indexPCLogOnInfo = this.getIndexPCLogOnInfo(pcLogOnInfoOfDailyTemp, worktNo, inOrOutClass);
			pcLogOnInfoOfDaily = pcLogOnInfoOfDailyTemp;

		}

		// 反映するか判断する
		boolean determineReflect = this.determineReflect(companyId, workInfo, inOrOutClass, x, pcLogonLogoffReflectOuput, date, employeeId);

		if (determineReflect) {
			// 反映する
			pcLogOnInfoOfDaily = this.refect(pcLogOnInfoOfDaily, indexPCLogOnInfo, x, stamps, worktNo, inOrOutClass);
		}

		return pcLogOnInfoOfDaily;
	}

	// 反映する inoutPc
	private PCLogOnInfoOfDaily refect(PCLogOnInfoOfDaily pcLogOnInfoOfDaily, int indexPCLogOnInfo, Stamp x,
			List<Stamp> stamps, int worktNo, String inOrOutClass) {
		List<LogOnInfo> lstLogOnInfo = pcLogOnInfoOfDaily.getTimeZone().getLogOnInfo();
		// TimeWithDayAttr logonOrLogoff = new
		// TimeWithDayAttr(x.getAttendanceTime().v());
		// logonOrLogoff dang nhe TimeWithDayAttr nhung hien tai workStamp
		// fixed
		// WorkStamp logonOrLogoff = new WorkStamp(new
		// TimeWithDayAttr(x.getAttendanceTime().v()),
		// new TimeWithDayAttr(x.getAttendanceTime().v()), null, null);
		TimeWithDayAttr logonOrLogoff = new TimeWithDayAttr((x.getAttendanceTime().isPresent()?
				x.getAttendanceTime().get():new AttendanceTime(x.getStampDateTime().clockHourMinute().v())).v());
		// 反映済み区分 ← true stamp
//		StampItem stampItem = new StampItem(x.getCardNumber(), x.getAttendanceTime(), x.getStampCombinationAtr(),
//				x.getSiftCd(), x.getStampMethod(), x.getStampAtr(), x.getWorkLocationCd(), x.getWorkLocationName(),
//				x.getGoOutReason(), x.getDate(), x.getEmployeeId(), ReflectedAtr.REFLECTED);
		Stamp stamp = new Stamp(x.getContractCode(), x.getCardNumber(), x.getStampDateTime(), x.getRelieve(),
				x.getType(), x.getRefActualResults(),
				Optional.ofNullable(x.getLocationInfor().isPresent() ? x.getLocationInfor().get() : null));
		stamp.setAttendanceTime(x.getAttendanceTime().isPresent()?x.getAttendanceTime().get():null);
		stamps.add(stamp);
		if (indexPCLogOnInfo == -1) {
			if ("PCログオン".equals(inOrOutClass)) {
				lstLogOnInfo.add(new LogOnInfo(new PCLogOnNo(worktNo), null, logonOrLogoff));
			} else {
				lstLogOnInfo.add(new LogOnInfo(new PCLogOnNo(worktNo), logonOrLogoff, null));
			}
			return pcLogOnInfoOfDaily;
		}

		LogOnInfo logOnInfo = lstLogOnInfo.get(indexPCLogOnInfo);
		if ("PCログオン".equals(inOrOutClass)) {
			lstLogOnInfo.set(indexPCLogOnInfo,
					new LogOnInfo(new PCLogOnNo(logOnInfo.getWorkNo().v()),
							(logOnInfo.getLogOff() != null && logOnInfo.getLogOff().isPresent())
									? logOnInfo.getLogOff().get() : null,
							logonOrLogoff));
			return pcLogOnInfoOfDaily;
		}
		lstLogOnInfo.set(indexPCLogOnInfo,
				new LogOnInfo(new PCLogOnNo(logOnInfo.getWorkNo().v()), logonOrLogoff,
						(logOnInfo.getLogOn() != null && logOnInfo.getLogOn().isPresent()) ? logOnInfo.getLogOn().get()
								: null));
		return pcLogOnInfoOfDaily;
	}

	// 反映するか判断する
	boolean determineReflect(String companyId, WorkInfoOfDailyPerformance workInfo, String inOrOutClass, Stamp x, PCLogonLogoffReflectOuput pcLogonLogoffReflectOuput, GeneralDate date, String employeeId) {
		if (pcLogonLogoffReflectOuput == null) {
			return true;
		}
		Optional<WorkInfoOfDailyPerformance> WorkInfoOptional = workInfo != null ? Optional.of(workInfo) : this.workInforRepo.find(employeeId, date);
		if (WorkInfoOptional.isPresent()) {
			WorkInformation recordWorkInformation = WorkInfoOptional.get().getWorkInformation().getRecordInfo();
			WorkTimeCode workTimeCode = recordWorkInformation.getWorkTimeCode();

			PrioritySetting prioritySetting = this.getPrioritySetting(companyId, workTimeCode.v(),
					"PCログオン".equals(inOrOutClass) ? StampPiorityAtr.PCLOGIN : StampPiorityAtr.PC_LOGOUT);
			MultiStampTimePiorityAtr priorityAtr = null;
			if (prioritySetting == null) {
				priorityAtr = MultiStampTimePiorityAtr.valueOf(0);
			} else {
				priorityAtr = prioritySetting.getPriorityAtr();
			}

			AttendanceTime attendanceTime = x.getAttendanceTime().isPresent()?
					x.getAttendanceTime().get():new AttendanceTime(x.getStampDateTime().clockHourMinute().v());
			TimeWithDayAttr timeDestination = pcLogonLogoffReflectOuput.getTimeOfDay();
			if (priorityAtr.value == 0) {
				if (timeDestination != null && (attendanceTime.v().intValue() >= timeDestination.v().intValue())) {
					return false;
				} else {
					return true;
				}

			} else {
				if (timeDestination == null || (timeDestination != null && (attendanceTime.v().intValue() > timeDestination.v().intValue()))) {
					return true;
				} else {
					return false;
				}
			}

		}
		return false;
	}

	int getIndexPCLogOnInfo(PCLogOnInfoOfDaily pcLogOnInfoOfDaily, int worktNo, String inOrOutClass) {
		List<LogOnInfo> lstLogOnInfo = pcLogOnInfoOfDaily.getTimeZone().getLogOnInfo();
		int logOnInfoSize = lstLogOnInfo.size();
		if ("PCログオン".equals(inOrOutClass)) {
			for (int i = 0; i < logOnInfoSize; i++) {
				LogOnInfo logOnInfo = lstLogOnInfo.get(i);
				/*
				 * if (logOnInfo.getWorkNo().v().intValue() == worktNo &&
				 * logOnInfo.getLogOn() != null &&
				 * logOnInfo.getLogOn().isPresent()) { return i; }
				 */
				if (logOnInfo.getWorkNo().v().intValue() == worktNo) {
					return i;
				}

			}
		} else {
			for (int i = 0; i < logOnInfoSize; i++) {
				LogOnInfo logOnInfo = lstLogOnInfo.get(i);
				/*
				 * if (logOnInfo.getWorkNo().v().intValue() == worktNo &&
				 * logOnInfo.getLogOff() != null &&
				 * logOnInfo.getLogOff().isPresent()) { return i; }
				 */
				if (logOnInfo.getWorkNo().v().intValue() == worktNo) {
					return i;
				}
			}
		}
		return -1;
	}

	PCLogonLogoffReflectOuput getPCLogonLogoffReflectOuput(PCLogOnInfoOfDaily pcLogOnInfoOfDaily, int worktNo,
			String inOrOutClass) {
		TimeWithDayAttr inOrOutWork;
		List<LogOnInfo> lstLogOnInfo = pcLogOnInfoOfDaily.getTimeZone().getLogOnInfo();
		int logOnInfoSize = lstLogOnInfo.size();
		if ("PCログオン".equals(inOrOutClass)) {
			for (int i = 0; i < logOnInfoSize; i++) {
				LogOnInfo logOnInfo = lstLogOnInfo.get(i);
				if (logOnInfo.getWorkNo().v().intValue() == worktNo && logOnInfo.getLogOn() != null
						&& logOnInfo.getLogOn().isPresent()) {
					inOrOutWork = logOnInfo.getLogOn().get(); // fixed
																// logOnInfo.getLogOn();
					PCLogonLogoffReflectOuput pcLogonLogoffReflectOuput = new PCLogonLogoffReflectOuput();
					pcLogonLogoffReflectOuput.setTimeOfDay(inOrOutWork);
					return pcLogonLogoffReflectOuput;
				}
			}
		} else {
			for (int i = 0; i < logOnInfoSize; i++) {
				LogOnInfo logOnInfo = lstLogOnInfo.get(i);
				if (logOnInfo.getWorkNo().v().intValue() == worktNo && logOnInfo.getLogOff() != null
						&& logOnInfo.getLogOff().isPresent()) {
					inOrOutWork = logOnInfo.getLogOff().get(); // fixed
																// logOnInfo.getLogOff();
					PCLogonLogoffReflectOuput pcLogonLogoffReflectOuput = new PCLogonLogoffReflectOuput();
					pcLogonLogoffReflectOuput.setTimeOfDay(inOrOutWork);
					return pcLogonLogoffReflectOuput;
				}
			}
		}
		return null;
	}

	// 入退門を反映する
	AttendanceLeavingGateOfDaily reflectInOutCompany(String companyId, WorkInfoOfDailyPerformance workInfo, GeneralDate date, String employeeId,
			AttendanceLeavingGateOfDaily attendanceLeavingGateOfDailyTemp, int worktNo, String inOrOutClass,
			Stamp x, List<Stamp> lstStamp) {

		AttendanceLeavingGateOfDaily attendanceLeavingGateOfDaily = null;
		int indexAttendanceLeavingGate = -1;
		ReflectEntryGateOutput reflectEntryGateOutput = null;

		// 反映先を取得する
		if (attendanceLeavingGateOfDailyTemp == null
				|| attendanceLeavingGateOfDailyTemp.getTimeZone().getAttendanceLeavingGates() == null
				|| attendanceLeavingGateOfDailyTemp.getTimeZone().getAttendanceLeavingGates().isEmpty()) {
			// lay tu db
			Optional<AttendanceLeavingGateOfDaily> attendanceLeavingGateOfDailyOptional = attendanceLeavingGateOfDailyRepo
					.find(employeeId, date);
			if (attendanceLeavingGateOfDailyOptional.isPresent()
					&& attendanceLeavingGateOfDailyOptional.get().getTimeZone().getAttendanceLeavingGates() != null
					&& !attendanceLeavingGateOfDailyOptional.get().getTimeZone().getAttendanceLeavingGates().isEmpty()) {
				reflectEntryGateOutput = this.getReflectEntryGateOutput(attendanceLeavingGateOfDailyOptional.get(),
						worktNo, inOrOutClass);
				indexAttendanceLeavingGate = this.getIndexAttendanceLeavingGate(
						attendanceLeavingGateOfDailyOptional.get(), worktNo, inOrOutClass);
				attendanceLeavingGateOfDaily = attendanceLeavingGateOfDailyOptional.get();
			} else {
				ArrayList<AttendanceLeavingGate> attendanceLeavingGates = new ArrayList<AttendanceLeavingGate>();
				attendanceLeavingGates.add(new AttendanceLeavingGate(
						new WorkNo(worktNo), null, null));
				attendanceLeavingGateOfDaily = new AttendanceLeavingGateOfDaily(employeeId, date,
						attendanceLeavingGates);
				indexAttendanceLeavingGate = 0;
			}
		} else {
			reflectEntryGateOutput = this.getReflectEntryGateOutput(attendanceLeavingGateOfDailyTemp, worktNo,
					inOrOutClass);
			indexAttendanceLeavingGate = this.getIndexAttendanceLeavingGate(attendanceLeavingGateOfDailyTemp, worktNo,
					inOrOutClass);
			attendanceLeavingGateOfDaily = attendanceLeavingGateOfDailyTemp;
		}

		// 反映するか判断する
		boolean determineReflect = this.determineReflect(companyId, workInfo, x, date, employeeId, inOrOutClass,
				reflectEntryGateOutput);
		if (determineReflect) {
			// 反映する
			this.refect(attendanceLeavingGateOfDaily, indexAttendanceLeavingGate, x, lstStamp, worktNo, inOrOutClass);
		}
		return attendanceLeavingGateOfDaily;
	}

	// 反映する
	private AttendanceLeavingGateOfDaily refect(AttendanceLeavingGateOfDaily attendanceLeavingGateOfDaily,
			int indexAttendanceLeavingGate, Stamp x, List<Stamp> stamps, int worktNo, String inOrOutClass) {

		WorkStamp inorOutStamp = null;
		// AfterRoundingTime chua xac dinh fixed, workstamp nay 3 thuoc tinh
		// khong co lam tron
		
		switch (x.getRelieve().getStampMeans().value) {
		// タイムレコーダー → タイムレコーダー
		case 0:case 1:case 2:case 3: case 4:case 7:case 8:
			inorOutStamp = new WorkStamp(
					new TimeWithDayAttr(x.getAttendanceTime().isPresent() ? x.getAttendanceTime().get().v()
							: new AttendanceTime(x.getStampDateTime().clockHourMinute().v()).v()),
					(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.WEB_STAMP_INPUT);
			break;
		// Web → Web打刻入力
		case 5:
			inorOutStamp = new WorkStamp(
					new TimeWithDayAttr(x.getAttendanceTime().isPresent() ? x.getAttendanceTime().get().v()
							: new AttendanceTime(x.getStampDateTime().clockHourMinute().v()).v()),
					(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.MOBILE_ENGRAVING);
			break;
		// その他 no cover
		default:
			inorOutStamp = new WorkStamp(
					new TimeWithDayAttr(x.getAttendanceTime().isPresent() ? x.getAttendanceTime().get().v()
							: new AttendanceTime(x.getStampDateTime().clockHourMinute().v()).v()),
					(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, null);
			break;
		}
		// 反映済み区分 ← true stamp
		Stamp stamp = new Stamp(x.getContractCode(),x.getCardNumber(), x.getStampDateTime(), x.getRelieve(), x.getType(), 
				x.getRefActualResults(), Optional.ofNullable(x.getLocationInfor().isPresent() ? x.getLocationInfor().get() : null));
		stamp.setAttendanceTime(x.getAttendanceTime().isPresent()?x.getAttendanceTime().get():null);
//		StampItem stampItem = new StampItem(x.getCardNumber(), x.getAttendanceTime(), x.getStampCombinationAtr(),
//				x.getSiftCd(), x.getStampMethod(), x.getStampAtr(), x.getWorkLocationCd(), x.getWorkLocationName(),
//				x.getGoOutReason(), x.getDate(), x.getEmployeeId(), ReflectedAtr.REFLECTED);
		stamps.add(stamp);
		List<AttendanceLeavingGate> attendanceLeavingGates = attendanceLeavingGateOfDaily.getTimeZone().getAttendanceLeavingGates();
		AttendanceLeavingGate attendanceLeavingGate = attendanceLeavingGates.get(indexAttendanceLeavingGate);
		if (indexAttendanceLeavingGate == -1) {
			if ("入門".equals(inOrOutClass)) {
				attendanceLeavingGates.add(new AttendanceLeavingGate(
						new WorkNo(worktNo), inorOutStamp, null));
			} else {
				attendanceLeavingGates.add(new AttendanceLeavingGate(
						new WorkNo(worktNo), null, inorOutStamp));
			}
			return attendanceLeavingGateOfDaily;
		}
		if ("入門".equals(inOrOutClass)) {
			attendanceLeavingGates.set(indexAttendanceLeavingGate, new AttendanceLeavingGate(
					attendanceLeavingGate.getWorkNo(), inorOutStamp,
					(attendanceLeavingGate.getLeaving() != null && attendanceLeavingGate.getLeaving().isPresent())
							? attendanceLeavingGate.getLeaving().get() : null));
		} else {
			attendanceLeavingGates.set(indexAttendanceLeavingGate, new AttendanceLeavingGate(
					attendanceLeavingGate.getWorkNo(),
					(attendanceLeavingGate.getAttendance() != null && attendanceLeavingGate.getAttendance().isPresent())
							? attendanceLeavingGate.getAttendance().get() : null,
					inorOutStamp));
		}
		return attendanceLeavingGateOfDaily;
	}

	// 反映するか判断する
	private boolean determineReflect(String companyId, WorkInfoOfDailyPerformance workInfo, Stamp stamp, GeneralDate date, String employeeId,
			String attendanceClass, ReflectEntryGateOutput reflectEntryGateOutput) {
		if (reflectEntryGateOutput != null) {
			TimeChangeMeans stampSourceInfo = reflectEntryGateOutput.getStampSourceInfo();
			if (stampSourceInfo != null && (stampSourceInfo.value == 6 || stampSourceInfo.value == 7)) {
				return false;
			} else {
				// 前優先後優先を見て反映するか確認する
				return this.confirmReflectFirstOrAfterPriority(companyId, workInfo, attendanceClass, stamp,
						reflectEntryGateOutput, date, employeeId);
			}
		} else {
			return true;
		}
	}

	private ReflectEntryGateOutput getReflectEntryGateOutput(AttendanceLeavingGateOfDaily attendanceLeavingGateOfDaily,
			int worktNo, String inOrOutClass) {
		WorkStamp inOrOutWorkStamp;
		List<AttendanceLeavingGate> attendanceLeavingGates = attendanceLeavingGateOfDaily.getTimeZone().getAttendanceLeavingGates();
		int attendanceLeavingGateSize = attendanceLeavingGates.size();
		if ("入門".equals(inOrOutClass)) {
			for (int i = 0; i < attendanceLeavingGateSize; i++) {
				AttendanceLeavingGate attendanceLeavingGate = attendanceLeavingGates.get(i);
				if (attendanceLeavingGate.getWorkNo().v().intValue() == worktNo
						&& attendanceLeavingGate.getAttendance() != null
						&& attendanceLeavingGate.getAttendance().isPresent()) {
					inOrOutWorkStamp = attendanceLeavingGate.getAttendance().get();
					ReflectEntryGateOutput reflectEntryGateOutput = new ReflectEntryGateOutput();
					reflectEntryGateOutput.setLocationCode((inOrOutWorkStamp.getLocationCode() != null
							&& inOrOutWorkStamp.getLocationCode().isPresent())
									? inOrOutWorkStamp.getLocationCode().get() : null);
					reflectEntryGateOutput.setStampSourceInfo(inOrOutWorkStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans());
					reflectEntryGateOutput.setTimeOfDay(inOrOutWorkStamp.getTimeDay().getTimeWithDay().get());
					return reflectEntryGateOutput;
				}
			}
		} else {
			for (int i = 0; i < attendanceLeavingGateSize; i++) {
				AttendanceLeavingGate attendanceLeavingGate = attendanceLeavingGates.get(i);
				if (attendanceLeavingGate.getWorkNo().v().intValue() == worktNo
						&& attendanceLeavingGate.getLeaving() != null
						&& attendanceLeavingGate.getLeaving().isPresent()) {
					inOrOutWorkStamp = attendanceLeavingGate.getLeaving().get();
					ReflectEntryGateOutput reflectEntryGateOutput = new ReflectEntryGateOutput();
					reflectEntryGateOutput.setLocationCode((inOrOutWorkStamp.getLocationCode() != null
							&& inOrOutWorkStamp.getLocationCode().isPresent())
									? inOrOutWorkStamp.getLocationCode().get() : null);
					reflectEntryGateOutput.setStampSourceInfo(inOrOutWorkStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans());
					reflectEntryGateOutput.setTimeOfDay(inOrOutWorkStamp.getTimeDay().getTimeWithDay().get());
					return reflectEntryGateOutput;
				}
			}
		}
		return null;
	}

	private int getIndexAttendanceLeavingGate(AttendanceLeavingGateOfDaily attendanceLeavingGateOfDaily, int worktNo,
			String inOrOutClass) {
		List<AttendanceLeavingGate> attendanceLeavingGates = attendanceLeavingGateOfDaily.getTimeZone().getAttendanceLeavingGates();
		int attendanceLeavingGateSize = attendanceLeavingGates.size();
		if ("入門".equals(inOrOutClass)) {
			for (int i = 0; i < attendanceLeavingGateSize; i++) {
				AttendanceLeavingGate attendanceLeavingGate = attendanceLeavingGates.get(i);
				/*
				 * if (attendanceLeavingGate.getWorkNo().v().intValue() ==
				 * worktNo && attendanceLeavingGate.getAttendance() != null &&
				 * attendanceLeavingGate.getAttendance().isPresent()) { return
				 * i; }
				 */
				if (attendanceLeavingGate.getWorkNo().v().intValue() == worktNo) {
					return i;
				}
			}
		} else {
			for (int i = 0; i < attendanceLeavingGateSize; i++) {
				AttendanceLeavingGate attendanceLeavingGate = attendanceLeavingGates.get(i);
				/*
				 * if (attendanceLeavingGate.getWorkNo().v().intValue() ==
				 * worktNo && attendanceLeavingGate.getLeaving() != null &&
				 * attendanceLeavingGate.getLeaving().isPresent()) { return i; }
				 */
				if (attendanceLeavingGate.getWorkNo().v().intValue() == worktNo) {
					return i;
				}
			}
		}
		return -1;
	}

	// *7 臨時終了打刻を反映する (Phản ánh 打刻 kết thúc tạm thời)
	// 臨時終了打刻を反映する
	private TemporaryTimeOfDailyPerformance reflectTimeTemporaryEnd(String companyId,
			WorkInfoOfDailyPerformance WorkInfo, String attendanceClass, List<Stamp> stamps, GeneralDate date,
			String employeeId, Stamp x, ProcessTimeOutput processTimeOutput,
			TemporaryTimeOfDailyPerformance temporaryPerformance) {
		Optional<TemporaryTimeOfDailyPerformance> temporaryTimeOptional = this.temporaryTimeRepo.findByKey(employeeId,
				date);
		TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance;
		List<TimeLeavingWork> timeLeavingWorks = new ArrayList<>();
		if (temporaryPerformance == null) {
			if (temporaryTimeOptional.isPresent()) {
				temporaryTimeOfDailyPerformance = temporaryTimeOptional.get();
				timeLeavingWorks = temporaryTimeOfDailyPerformance.getAttendance().getTimeLeavingWorks();
				Collections.sort(timeLeavingWorks, new Comparator<TimeLeavingWork>() {
					public int compare(TimeLeavingWork o1, TimeLeavingWork o2) {
						if (o2 == null || o2.getAttendanceStamp() == null || !o2.getAttendanceStamp().isPresent()
								|| o2.getAttendanceStamp().get().getStamp() == null
								|| !o2.getAttendanceStamp().get().getStamp().isPresent()) {
							return 1;
						}
						if (o1 == null || o1.getAttendanceStamp() == null || !o1.getAttendanceStamp().isPresent()
								|| o1.getAttendanceStamp().get().getStamp() == null
								|| !o1.getAttendanceStamp().get().getStamp().isPresent()) {
							return -1;
						}
						int t1 = o1.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v().intValue();
						int t2 = o2.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v().intValue();
						if (t1 == t2)
							return 0;
						return t1 < t2 ? -1 : 1;
					}
				});
			} else {
				temporaryTimeOfDailyPerformance = new TemporaryTimeOfDailyPerformance(employeeId, new WorkTimes(0),
						new ArrayList<>(), date);
			}
		} else {
			temporaryTimeOfDailyPerformance = temporaryPerformance;
			timeLeavingWorks = temporaryTimeOfDailyPerformance.getAttendance().getTimeLeavingWorks();
			Collections.sort(timeLeavingWorks, new Comparator<TimeLeavingWork>() {
				public int compare(TimeLeavingWork o1, TimeLeavingWork o2) {
					if (o2 == null || o2.getAttendanceStamp() == null || !o2.getAttendanceStamp().isPresent()
							|| o2.getAttendanceStamp().get().getStamp() == null
							|| !o2.getAttendanceStamp().get().getStamp().isPresent()) {
						return 1;
					}
					if (o1 == null || o1.getAttendanceStamp() == null || !o1.getAttendanceStamp().isPresent()
							|| o1.getAttendanceStamp().get().getStamp() == null
							|| !o1.getAttendanceStamp().get().getStamp().isPresent()) {
						return -1;
					}
					int t1 = o1.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v().intValue();
					int t2 = o2.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v().intValue();
					if (t1 == t2)
						return 0;
					return t1 < t2 ? -1 : 1;
				}
			});

		}
		// 出退勤Listに最大枠数分の枠を用意する
		// *7.1 出退勤Listに最大枠数分の枠を用意する (Trong 出退勤List, chuẩn bị phần tử có số
		// lượng phần tử lớn nhất)
		// 臨時勤務管理 chưa có (fixed)
		// 最大使用回数 = 11;
		int Maxcount = 3;

		Optional<ManageWorkTemporary> TemporaryWorkManageOptional = this.temporaryWorkManageRepo.findByCID(companyId);
		if (TemporaryWorkManageOptional.isPresent()) {
			Maxcount = TemporaryWorkManageOptional.get().getMaxUsage().v();
		}

		int timeLeavingSize = timeLeavingWorks.size();
		if (timeLeavingSize < Maxcount) {
			for (int i = 0; i < Maxcount - timeLeavingSize; i++) {
				timeLeavingWorks.add(new TimeLeavingWork(new WorkNo(i), null, null));
			}
		} else if (timeLeavingSize > Maxcount) {
			for (int i = 0; i < timeLeavingSize - Maxcount; i++) {
				timeLeavingWorks.remove(timeLeavingWorks.get(timeLeavingSize - i - 1));
			}
		}
		// *7.1
		int timeLeavingWorkSize = timeLeavingWorks.size();
		boolean isBreak = false;
		List<TimeLeavingWork> newTimeLeavingWorks = null;
		for (int i = 0; i < timeLeavingWorkSize; i++) {
			TimeLeavingWork timeLeavingWork = timeLeavingWorks.get(i);
			if (timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent()
					&& timeLeavingWork.getLeaveStamp().get().getStamp() != null
					&& timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()) {
				// 臨時終了打刻を反映する
				// 8* 打刻時刻と臨時時刻が同一か判定する (Đánh giá xem 打刻時刻 và 臨時時刻 có giống
				// nhau không)
				// 臨時勤務管理 chưa có (fixed) true (đồng nhất thời gian) false
				// (k đồng nhất)
				boolean equal = true;

				TimeWithDayAttr tempTime = timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get();
				TimeWithDayAttr stamp = processTimeOutput.getTimeOfDay();
				int timeTreatTempSame = 0;
				timeTreatTempSame = TemporaryWorkManageOptional.get().getTimeTreatTemporarySame().valueAsMinutes();

				equal = this.confirmStampAndTempTimeSame(stamp, tempTime, timeTreatTempSame);

				// 8*
				if (equal) {
					// 打刻を出退勤．退勤．実打刻に入れる (Set 打刻 vào 退勤．退勤．実打刻)
					TimeLeavingWork newTimeLeavingWork = putInActualStampOfLeaveWork(companyId, WorkInfo,
							attendanceClass, stamps, x, processTimeOutput, timeLeavingWork);
					timeLeavingWorks.get(i).setTimeLeavingWork(newTimeLeavingWork.getWorkNo(),
							newTimeLeavingWork.getAttendanceStamp(), newTimeLeavingWork.getLeaveStamp());
					newTimeLeavingWorks = revomeEmptyTimeLeaves(timeLeavingWorks);
					isBreak = true;
					break;
				}

			} else {

				if (timeLeavingWork.getAttendanceStamp() == null || !timeLeavingWork.getAttendanceStamp().isPresent()
						|| timeLeavingWork.getAttendanceStamp().get().getStamp() == null
						|| !timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()
						|| (timeLeavingWork.getAttendanceStamp() != null
								&& timeLeavingWork.getAttendanceStamp().isPresent()
								&& timeLeavingWork.getAttendanceStamp().get().getStamp() != null
								&& timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()
								&& timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v()
										.intValue() <= processTimeOutput.getTimeOfDay().v().intValue())) {
					if (i + 1 == timeLeavingWorkSize || timeLeavingWorks.get(i + 1) == null
							|| timeLeavingWorks.get(i + 1).getAttendanceStamp() == null
							|| !timeLeavingWorks.get(i + 1).getAttendanceStamp().isPresent()
							|| timeLeavingWorks.get(i + 1).getAttendanceStamp().get().getStamp() == null
							|| !timeLeavingWorks.get(i + 1).getAttendanceStamp().get().getStamp().isPresent()
							|| (i + 1 < timeLeavingWorkSize && timeLeavingWorks.get(i + 1) != null
									&& timeLeavingWorks.get(i + 1).getAttendanceStamp() != null
									&& timeLeavingWorks.get(i + 1).getAttendanceStamp().isPresent()
									&& timeLeavingWorks.get(i + 1).getAttendanceStamp().get().getStamp() != null
									&& timeLeavingWorks.get(i + 1).getAttendanceStamp().get().getStamp().isPresent()
									&& processTimeOutput.getTimeOfDay().v().intValue() < timeLeavingWorks.get(i + 1)
											.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v()
											.intValue())) {
						TimeLeavingWork newTimeLeavingWork = putTimeLeaveForActualAndStamp(companyId, WorkInfo,
								attendanceClass, stamps, x, processTimeOutput, timeLeavingWork);
						timeLeavingWorks.get(i).setTimeLeavingWork(newTimeLeavingWork.getWorkNo(),
								newTimeLeavingWork.getAttendanceStamp(), newTimeLeavingWork.getLeaveStamp());
						newTimeLeavingWorks = revomeEmptyTimeLeaves(timeLeavingWorks);
						isBreak = true;
						break;
					}
				}

			}
		}
		if (!isBreak) {
			newTimeLeavingWorks = revomeEmptyTimeLeaves(timeLeavingWorks);
		}
		if (newTimeLeavingWorks == null || newTimeLeavingWorks.size() == 0) {
			return new TemporaryTimeOfDailyPerformance(employeeId, temporaryTimeOfDailyPerformance.getAttendance().getWorkTimes(),
					timeLeavingWorks, date);
		} else {
			return new TemporaryTimeOfDailyPerformance(employeeId, temporaryTimeOfDailyPerformance.getAttendance().getWorkTimes(),
					newTimeLeavingWorks, date);
		}

	}

	// 打刻を出退勤．退勤（実打刻と打刻）に入れる (Set 打刻 vào 出退勤．退勤（実打刻と打刻))
	// 打刻を出退勤．退勤に入れる
	private TimeLeavingWork putTimeLeaveForActualAndStamp(String companyId, WorkInfoOfDailyPerformance WorkInfo,
			String attendanceClass, List<Stamp> stamps, Stamp x, ProcessTimeOutput processTimeOutput,
			TimeLeavingWork timeLeavingWork) {
		
		// 臨時時刻を丸める
		RoundingSet roudingTime = WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode() != null
				? this.getRoudingTime(companyId, WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode().v(),
						"退勤".equals(attendanceClass) ? Superiority.OFFICE_WORK : Superiority.ATTENDANCE)
				: null;
		InstantRounding instantRounding = null;
		if (roudingTime != null) {
			instantRounding = new InstantRounding(roudingTime.getRoundingSet().getFontRearSection(),
					roudingTime.getRoundingSet().getRoundingTimeUnit());
		}

		TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();
		int numberMinuteTimeOfDayRounding = instantRounding != null ? roudingTimeWithDay(timeOfDay, instantRounding.getFontRearSection(),
				instantRounding.getRoundingTimeUnit()) : timeOfDay.valueAsMinutes() ;
		processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));
		// 7.2.1
		WorkStamp newActualStamp = null;
		switch (x.getRelieve().getStampMeans().value) {
		// タイムレコーダー → タイムレコーダー
		case 0:case 1:case 2:case 3:case 4: case 7: case 8: 
			newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
					(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.WEB_STAMP_INPUT);
			break;
		// Web → Web打刻入力
		case 5:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
					(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.MOBILE_ENGRAVING);
			break;
		// その他 no cover
		default:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
					(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, null);
			break;
		}

		// 反映済み区分 ← true stamp
		Stamp stamp = new Stamp(x.getContractCode(),x.getCardNumber(), x.getStampDateTime(), x.getRelieve(), x.getType(), 
				x.getRefActualResults(), Optional.ofNullable(x.getLocationInfor().isPresent() ? x.getLocationInfor().get() : null));
		stamp.setAttendanceTime(x.getAttendanceTime().isPresent()?x.getAttendanceTime().get():null);
		stamps.add(stamp);

		int numberOfReflectionStamp = 0;
		if (timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent()) {
			numberOfReflectionStamp = timeLeavingWork.getLeaveStamp().get().getNumberOfReflectionStamp() == null ? 0
					: timeLeavingWork.getLeaveStamp().get().getNumberOfReflectionStamp();
		}

		return new TimeLeavingWork(timeLeavingWork.getWorkNo(),
				(timeLeavingWork.getAttendanceStamp() != null && timeLeavingWork.getAttendanceStamp().isPresent())
						? timeLeavingWork.getAttendanceStamp().get() : null,
				new TimeActualStamp(newActualStamp, newActualStamp, numberOfReflectionStamp + 1));

	}

	// 打刻を出退勤．退勤．実打刻に入れる (Set 打刻 vào 退勤．退勤．実打刻)
	// 打刻を出退勤．退勤．実打刻に入れる
	private TimeLeavingWork putInActualStampOfLeaveWork(String companyId, WorkInfoOfDailyPerformance WorkInfo,
			String attendanceClass, List<Stamp> stamps, Stamp x, ProcessTimeOutput processTimeOutput,
			TimeLeavingWork timeLeavingWork) {
		WorkStamp leaveStamp = (timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent()
				&& timeLeavingWork.getLeaveStamp().get().getActualStamp() != null
				&& timeLeavingWork.getLeaveStamp().get().getActualStamp().isPresent())
						? timeLeavingWork.getLeaveStamp().get().getActualStamp().get() : null;
		if (leaveStamp == null) {
			// 臨時時刻を丸める
			RoundingSet roudingTime = WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode() != null
					? this.getRoudingTime(companyId, WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode().v(),
							"退勤".equals(attendanceClass) ? Superiority.OFFICE_WORK : Superiority.ATTENDANCE)
					: null;
			InstantRounding instantRounding = null;
			if (roudingTime != null) {
				instantRounding = new InstantRounding(roudingTime.getRoundingSet().getFontRearSection(),
						roudingTime.getRoundingSet().getRoundingTimeUnit());
			}
			TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();

			int numberMinuteTimeOfDayRounding = instantRounding != null ? roudingTimeWithDay(timeOfDay, instantRounding.getFontRearSection(),
					instantRounding.getRoundingTimeUnit()) : timeOfDay.valueAsMinutes();
			processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));

			WorkStamp newActualStamp = null;

			switch (x.getRelieve().getStampMeans().value) {
			// タイムレコーダー → タイムレコーダー
			case 0:case 1:case 2:case 3:case 4: case 7: case 8: 
				newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
						(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.WEB_STAMP_INPUT);
				break;
			// Web → Web打刻入力
			case 5:
				newActualStamp = new WorkStamp( processTimeOutput.getTimeOfDay(),
						(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.MOBILE_ENGRAVING);
				break;
			// その他 no cover
			default:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
						(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, null);
				break;
			}
			int numberOfReflectionStamp = 0;
			if (timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent()) {
				numberOfReflectionStamp = timeLeavingWork.getLeaveStamp().get().getNumberOfReflectionStamp() == null ? 0
						: timeLeavingWork.getLeaveStamp().get().getNumberOfReflectionStamp();
			}

			return new TimeLeavingWork(timeLeavingWork.getWorkNo(),
					(timeLeavingWork.getAttendanceStamp() != null && timeLeavingWork.getAttendanceStamp().isPresent())
							? timeLeavingWork.getAttendanceStamp().get() : null,
					new TimeActualStamp(newActualStamp,
							(timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent()
									&& timeLeavingWork.getLeaveStamp().get().getStamp() != null
									&& timeLeavingWork.getLeaveStamp().get().getStamp().isPresent())
											? timeLeavingWork.getLeaveStamp().get().getStamp().get() : null,
							numberOfReflectionStamp));

		}

		// 反映済み区分 = true
		Stamp stamp = new Stamp(x.getContractCode(),x.getCardNumber(), x.getStampDateTime(), x.getRelieve(), x.getType(), 
				x.getRefActualResults(), Optional.ofNullable(x.getLocationInfor().isPresent() ? x.getLocationInfor().get() : null));
		stamp.setAttendanceTime(x.getAttendanceTime().isPresent()?x.getAttendanceTime().get():null);
		stamps.add(stamp);

		int numberOfReflectionStamp = 0;
		if (timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent()) {
			numberOfReflectionStamp = timeLeavingWork.getLeaveStamp().get().getNumberOfReflectionStamp() == null ? 0
					: timeLeavingWork.getLeaveStamp().get().getNumberOfReflectionStamp();
		}

		return new TimeLeavingWork(
				timeLeavingWork
						.getWorkNo(),
				(timeLeavingWork.getAttendanceStamp() != null && timeLeavingWork.getAttendanceStamp().isPresent())
						? timeLeavingWork.getAttendanceStamp().get() : null,
				new TimeActualStamp(
						(timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent()
								&& timeLeavingWork.getLeaveStamp().get().getActualStamp() != null
								&& timeLeavingWork.getLeaveStamp().get().getActualStamp().isPresent())
										? timeLeavingWork.getLeaveStamp().get().getActualStamp().get() : null,
						(timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent()
								&& timeLeavingWork.getLeaveStamp().get().getStamp() != null
								&& timeLeavingWork.getLeaveStamp().get().getStamp().isPresent())
										? timeLeavingWork.getLeaveStamp().get().getStamp().get() : null,
						numberOfReflectionStamp + 1));

	}

	// *7 臨時開始打刻を反映する (Phản ánh 打刻 bắt đầu tạm thời)
	// 臨時開始打刻を反映する
	private TemporaryTimeOfDailyPerformance reflectTimeTemporaryStart(String companyId,
			WorkInfoOfDailyPerformance WorkInfo, String attendanceClass, List<Stamp> stamps, GeneralDate date,
			String employeeId, Stamp x, ProcessTimeOutput processTimeOutput,
			TemporaryTimeOfDailyPerformance temporaryPerformance) {
		Optional<TemporaryTimeOfDailyPerformance> temporaryTimeOptional = this.temporaryTimeRepo.findByKey(employeeId,
				date);
		TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance;
		List<TimeLeavingWork> timeLeavingWorks = new ArrayList<>();
		if (temporaryPerformance == null) {
			if (temporaryTimeOptional.isPresent()) {
				temporaryTimeOfDailyPerformance = temporaryTimeOptional.get();
				timeLeavingWorks = temporaryTimeOfDailyPerformance.getAttendance().getTimeLeavingWorks();
				Collections.sort(timeLeavingWorks, new Comparator<TimeLeavingWork>() {
					public int compare(TimeLeavingWork o1, TimeLeavingWork o2) {
						if (o2 == null || o2.getAttendanceStamp() == null || !o2.getAttendanceStamp().isPresent()
								|| o2.getAttendanceStamp().get().getStamp() == null
								|| !o2.getAttendanceStamp().get().getStamp().isPresent()) {
							return 1;
						}
						if (o1 == null || o1.getAttendanceStamp() == null || !o1.getAttendanceStamp().isPresent()
								|| o1.getAttendanceStamp().get().getStamp() == null
								|| !o1.getAttendanceStamp().get().getStamp().isPresent()) {
							return -1;
						}
						int t1 = o1.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v().intValue();
						int t2 = o2.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v().intValue();
						if (t1 == t2)
							return 0;
						return t1 < t2 ? -1 : 1;
					}
				});
			} else {
				temporaryTimeOfDailyPerformance = new TemporaryTimeOfDailyPerformance(employeeId, new WorkTimes(0),
						new ArrayList<>(), date);
			}
		} else {
			temporaryTimeOfDailyPerformance = temporaryPerformance;
			timeLeavingWorks = temporaryTimeOfDailyPerformance.getAttendance().getTimeLeavingWorks();
			Collections.sort(timeLeavingWorks, new Comparator<TimeLeavingWork>() {
				public int compare(TimeLeavingWork o1, TimeLeavingWork o2) {
					if (o2 == null || o2.getAttendanceStamp() == null || !o2.getAttendanceStamp().isPresent()
							|| o2.getAttendanceStamp().get().getStamp() == null
							|| !o2.getAttendanceStamp().get().getStamp().isPresent()) {
						return 1;
					}
					if (o1 == null || o1.getAttendanceStamp() == null || !o1.getAttendanceStamp().isPresent()
							|| o1.getAttendanceStamp().get().getStamp() == null
							|| !o1.getAttendanceStamp().get().getStamp().isPresent()) {
						return -1;
					}
					int t1 = o1.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v().intValue();
					int t2 = o2.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v().intValue();
					if (t1 == t2)
						return 0;
					return t1 < t2 ? -1 : 1;
				}
			});
		}

		// *7.1 出退勤Listに最大枠数分の枠を用意する (Trong 出退勤List, chuẩn bị phần tử có số
		// lượng phần tử lớn nhất)
		// 臨時勤務管理 chưa có (fixed)
		// 最大使用回数 = 11;
		int Maxcount = 3;

		Optional<ManageWorkTemporary> TemporaryWorkManageOptional = this.temporaryWorkManageRepo.findByCID(companyId);
		if (TemporaryWorkManageOptional.isPresent()) {
			Maxcount = TemporaryWorkManageOptional.get().getMaxUsage().v();
		}

		int timeLeavingSize = timeLeavingWorks.size();
		if (timeLeavingSize < Maxcount) {
			for (int i = 0; i < Maxcount - timeLeavingSize; i++) {
				timeLeavingWorks.add(new TimeLeavingWork(new WorkNo(i), null, null));
			}
		} else if (timeLeavingSize > Maxcount) {
			for (int i = 0; i < timeLeavingSize - Maxcount; i++) {
				timeLeavingWorks.remove(timeLeavingWorks.get(timeLeavingSize - i - 1));
			}
		}
		// *7.1
		int timeLeavingWorkSize = timeLeavingWorks.size();
		List<TimeLeavingWork> newTimeLeavingWorks = null;
		boolean isBreak = false;
		for (int i = 0; i < timeLeavingWorkSize; i++) {
			TimeLeavingWork timeLeavingWork = timeLeavingWorks.get(i);
			if (timeLeavingWork.getAttendanceStamp() != null && timeLeavingWork.getAttendanceStamp().isPresent()
					&& timeLeavingWork.getAttendanceStamp().get().getStamp() != null
					&& timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()) {
				// 8* 打刻時刻と臨時時刻が同一か判定する (Đánh giá xem 打刻時刻 và 臨時時刻 có giống
				// nhau không)
				// 打刻時刻と臨時時刻が同一か判定する
				boolean equal = true;

				TimeWithDayAttr tempTime = timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get();
				TimeWithDayAttr stamp = processTimeOutput.getTimeOfDay();
				int timeTreatTempSame = 0;
				if (TemporaryWorkManageOptional.isPresent()) {
					timeTreatTempSame = TemporaryWorkManageOptional.get().getTimeTreatTemporarySame().valueAsMinutes();
					equal = this.confirmStampAndTempTimeSame(stamp, tempTime, timeTreatTempSame);
				}

				// 8*
				if (equal) {
					// tiếp
					// 打刻を出退勤．出勤．実打刻に入れる (Set 打刻 vào 出退勤．出勤．実打刻)
					TimeLeavingWork newTimeLeavingWork = setStampInActualStampOfTimeLeave(companyId, WorkInfo,
							attendanceClass, stamps, x, processTimeOutput, timeLeavingWork);
					timeLeavingWorks.get(i).setTimeLeavingWork(newTimeLeavingWork.getWorkNo(),
							newTimeLeavingWork.getAttendanceStamp(), newTimeLeavingWork.getLeaveStamp());
					newTimeLeavingWorks = revomeEmptyTimeLeaves(timeLeavingWorks);
					isBreak = true;
					break;
				}

			} else {
				if (timeLeavingWork.getLeaveStamp() == null || !timeLeavingWork.getLeaveStamp().isPresent()
						|| timeLeavingWork.getLeaveStamp().get().getStamp() == null
						|| !timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()
						|| (timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent()
								&& timeLeavingWork.getLeaveStamp().get().getStamp() != null
								&& timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()
								&& processTimeOutput.getTimeOfDay().v().intValue() <= timeLeavingWork.getLeaveStamp()
										.get().getStamp().get().getTimeDay().getTimeWithDay().get().v().intValue())) {
					// 打刻を出退勤．出勤（実打刻と打刻）に入れる (Set 打刻 vào 出退勤．出勤（実打刻と打刻))
					TimeLeavingWork newTimeLeavingWork = putDataTimeLeaveForActualAndStamp(companyId, WorkInfo,
							attendanceClass, stamps, x, processTimeOutput, timeLeavingWork);
					timeLeavingWorks.get(i).setTimeLeavingWork(newTimeLeavingWork.getWorkNo(),
							newTimeLeavingWork.getAttendanceStamp(), newTimeLeavingWork.getLeaveStamp());
					newTimeLeavingWorks = revomeEmptyTimeLeaves(timeLeavingWorks);
					isBreak = true;
					break;
				}
			}
		}
		if (!isBreak) {
			newTimeLeavingWorks = revomeEmptyTimeLeaves(timeLeavingWorks);
		}
		if (newTimeLeavingWorks == null || newTimeLeavingWorks.isEmpty()) {
			return new TemporaryTimeOfDailyPerformance(employeeId, temporaryTimeOfDailyPerformance.getAttendance().getWorkTimes(),
					timeLeavingWorks, date);
		} else {
			return new TemporaryTimeOfDailyPerformance(employeeId, temporaryTimeOfDailyPerformance.getAttendance().getWorkTimes(),
					newTimeLeavingWorks, date);
		}

	}

	// 打刻を出退勤．出勤（実打刻と打刻）に入れる (Set 打刻 vào 出退勤．出勤（実打刻と打刻))
	// 打刻を出退勤．出勤に入れる
	private TimeLeavingWork putDataTimeLeaveForActualAndStamp(String companyId, WorkInfoOfDailyPerformance WorkInfo,
			String attendanceClass, List<Stamp> stamps, Stamp x, ProcessTimeOutput processTimeOutput,
			TimeLeavingWork timeLeavingWork) {

		// 臨時時刻を丸める
		TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();
		RoundingSet roudingTime = WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode() != null
				? this.getRoudingTime(companyId, WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode().v(),
						"退勤".equals(attendanceClass) ? Superiority.OFFICE_WORK : Superiority.ATTENDANCE)
				: null;
		InstantRounding instantRounding = null;
		if (roudingTime != null) {
			instantRounding = new InstantRounding(roudingTime.getRoundingSet().getFontRearSection(),
					roudingTime.getRoundingSet().getRoundingTimeUnit());
		}
		int numberMinuteTimeOfDayRounding = instantRounding != null ? roudingTimeWithDay(timeOfDay, instantRounding.getFontRearSection(),
				instantRounding.getRoundingTimeUnit()) : timeOfDay.valueAsMinutes();
		processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));
		// 7.2.1
		WorkStamp newActualStamp = null;

		switch (x.getRelieve().getStampMeans().value) {
		// タイムレコーダー → タイムレコーダー
		case 0:case 1:case 2:case 3:case 4: case 7: case 8: 
			newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
					(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.WEB_STAMP_INPUT);
			break;
		// Web → Web打刻入力
		case 5:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
					(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.MOBILE_ENGRAVING);
			break;
		// その他 no cover
		default:
			newActualStamp = new WorkStamp( processTimeOutput.getTimeOfDay(),
					(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, null);
			break;
		}

		// 反映済み区分 ← true stamp
		Stamp stamp = new Stamp(x.getContractCode(),x.getCardNumber(), x.getStampDateTime(), x.getRelieve(), x.getType(), 
				x.getRefActualResults(), Optional.ofNullable(x.getLocationInfor().isPresent() ? x.getLocationInfor().get() : null));
		stamp.setAttendanceTime(x.getAttendanceTime().isPresent()?x.getAttendanceTime().get():null);
		stamps.add(stamp);
		int numberOfReflectionStamp = 0;
		if (timeLeavingWork.getAttendanceStamp() != null && timeLeavingWork.getAttendanceStamp().isPresent()) {
			numberOfReflectionStamp = timeLeavingWork.getAttendanceStamp().get().getNumberOfReflectionStamp() == null
					? 0 : timeLeavingWork.getAttendanceStamp().get().getNumberOfReflectionStamp();
		}

		return new TimeLeavingWork(timeLeavingWork.getWorkNo(),
				new TimeActualStamp(newActualStamp, newActualStamp, numberOfReflectionStamp + 1),
				(timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent())
						? timeLeavingWork.getLeaveStamp().get() : null);
	}

	// 打刻を出退勤．出勤．実打刻に入れる (Set 打刻 vào 出退勤．出勤．実打刻)
	// 打刻を出退勤．出勤．実打刻に入れる
	private TimeLeavingWork setStampInActualStampOfTimeLeave(String companyId, WorkInfoOfDailyPerformance WorkInfo,
			String attendanceClass, List<Stamp> stamps, Stamp x, ProcessTimeOutput processTimeOutput,
			TimeLeavingWork timeLeavingWork) {
		WorkStamp actualStamp = (timeLeavingWork.getAttendanceStamp() != null
				&& timeLeavingWork.getAttendanceStamp().isPresent()
				&& timeLeavingWork.getAttendanceStamp().get().getActualStamp() != null
				&& timeLeavingWork.getAttendanceStamp().get().getActualStamp().isPresent())
						? timeLeavingWork.getAttendanceStamp().get().getActualStamp().get() : null;
		if (actualStamp == null) {
			// 臨時時刻を丸める
			TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();
			RoundingSet roudingTime = WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode() != null
					? this.getRoudingTime(companyId, WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode().v(),
							"退勤".equals(attendanceClass) ? Superiority.OFFICE_WORK : Superiority.ATTENDANCE)
					: null;
			InstantRounding instantRounding = null;
			if (roudingTime != null) {
				instantRounding = new InstantRounding(roudingTime.getRoundingSet().getFontRearSection(),
						roudingTime.getRoundingSet().getRoundingTimeUnit());
			}

			int numberMinuteTimeOfDayRounding = instantRounding != null ? roudingTimeWithDay(timeOfDay, instantRounding.getFontRearSection(),
					instantRounding.getRoundingTimeUnit()) : timeOfDay.valueAsMinutes();
			processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));
			WorkStamp newActualStamp = null;

			switch (x.getRelieve().getStampMeans().value) {
			// タイムレコーダー → タイムレコーダー
			case 0:case 1:case 2:case 3:case 4: case 7: case 8: 
				newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
						(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.WEB_STAMP_INPUT);
				break;
			// Web → Web打刻入力
			case 5:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
						(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.MOBILE_ENGRAVING);
				break;
			// その他 no cover
			default:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
						(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, null);
				break;
			}
			int numberOfReflectionStamp = 0;
			if (timeLeavingWork.getAttendanceStamp() != null && timeLeavingWork.getAttendanceStamp().isPresent()) {
				numberOfReflectionStamp = timeLeavingWork.getAttendanceStamp().get()
						.getNumberOfReflectionStamp() == null ? 0
								: timeLeavingWork.getAttendanceStamp().get().getNumberOfReflectionStamp();
			}
			return new TimeLeavingWork(timeLeavingWork.getWorkNo(), new TimeActualStamp(newActualStamp,
					(timeLeavingWork.getAttendanceStamp() != null && timeLeavingWork.getAttendanceStamp().isPresent()
							&& timeLeavingWork.getAttendanceStamp().get().getStamp() != null
							&& timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent())
									? timeLeavingWork.getAttendanceStamp().get().getStamp().get() : null,
					numberOfReflectionStamp),
					(timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent())
							? timeLeavingWork.getLeaveStamp().get() : null);
		}
		// 反映済み区分 = true
		Stamp stamp = new Stamp(x.getContractCode(),x.getCardNumber(), x.getStampDateTime(), x.getRelieve(), x.getType(), 
				x.getRefActualResults(), Optional.ofNullable(x.getLocationInfor().isPresent() ? x.getLocationInfor().get() : null));
		stamp.setAttendanceTime(x.getAttendanceTime().isPresent()?x.getAttendanceTime().get():null);
		stamps.add(stamp);

		int numberOfReflectionStamp = 0;
		if (timeLeavingWork.getAttendanceStamp() != null && timeLeavingWork.getAttendanceStamp().isPresent()) {
			numberOfReflectionStamp = timeLeavingWork.getAttendanceStamp().get().getNumberOfReflectionStamp() == null
					? 0 : timeLeavingWork.getAttendanceStamp().get().getNumberOfReflectionStamp();
		}

		return new TimeLeavingWork(timeLeavingWork.getWorkNo(), new TimeActualStamp(actualStamp,
				(timeLeavingWork.getAttendanceStamp() != null && timeLeavingWork.getAttendanceStamp().isPresent()
						&& timeLeavingWork.getAttendanceStamp().get().getStamp() != null
						&& timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent())
								? timeLeavingWork.getAttendanceStamp().get().getStamp().get() : null,
				numberOfReflectionStamp + 1),
				(timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent())
						? timeLeavingWork.getLeaveStamp().get() : null);

	}

	// *8 THời điểm check quay về
	// 戻り打刻を反映する
	private OutingTimeOfDailyPerformance reflectTimeComeBackCheck(WorkInfoOfDailyPerformance WorkInfo,
			String attendanceClass, List<Stamp> stamps, GeneralDate date, String employeeId, Stamp x,
			ProcessTimeOutput processTimeOutput, String companyId,
			OutingTimeOfDailyPerformance outingDailyPerformance) {
		Optional<OutingTimeOfDailyPerformance> outDailyOptional = this.OutRepo.findByEmployeeIdAndDate(employeeId,
				date);
		OutingTimeOfDailyPerformance outDailyPer;
		List<OutingTimeSheet> lstOutingTimeSheet;
		if (outingDailyPerformance == null) {
			if (outDailyOptional.isPresent()) {
				outDailyPer = outDailyOptional.get();
				lstOutingTimeSheet = outDailyPer.getOutingTime().getOutingTimeSheets();
				Collections.sort(lstOutingTimeSheet, new Comparator<OutingTimeSheet>() {
					public int compare(OutingTimeSheet o1, OutingTimeSheet o2) {
						if (o2 == null || o2.getGoOut() == null || !o2.getGoOut().isPresent()) {
							return 1;
						}
						if (o1 == null || o1.getGoOut() == null || !o1.getGoOut().isPresent()) {
							return -1;
						}
						int t1 = o1.getGoOut().get().getTimeDay().getTimeWithDay().get().v().intValue();
						int t2 = o2.getGoOut().get().getTimeDay().getTimeWithDay().get().v().intValue();
						if (t1 == t2)
							return 0;
						return t1 < t2 ? -1 : 1;
					}
				});
			} else {
				lstOutingTimeSheet = new ArrayList<OutingTimeSheet>();
				lstOutingTimeSheet.add(new OutingTimeSheet(null, Optional.empty(), null, Optional.empty()));

				outDailyPer = new OutingTimeOfDailyPerformance(employeeId, date, lstOutingTimeSheet);
			}
		} else {
			outDailyPer = outingDailyPerformance;
			lstOutingTimeSheet = outDailyPer.getOutingTime().getOutingTimeSheets();
			Collections.sort(lstOutingTimeSheet, new Comparator<OutingTimeSheet>() {
				public int compare(OutingTimeSheet o1, OutingTimeSheet o2) {
					if (o2 == null || o2.getGoOut() == null || !o2.getGoOut().isPresent()) {
						return 1;
					}
					if (o1 == null || o1.getGoOut() == null || !o1.getGoOut().isPresent()) {
						return -1;
					}
					int t1 = o1.getGoOut().get().getTimeDay().getTimeWithDay().get().v().intValue();
					int t2 = o2.getGoOut().get().getTimeDay().getTimeWithDay().get().v().intValue();
					if (t1 == t2)
						return 0;
					return t1 < t2 ? -1 : 1;
				}
			});
		}
		// 外出時間帯Listに最大枠数分の枠を用意する
		// *7.1 外出時間帯Listに最大枠数分の枠を用意する (Chuẩn bị )
		// Xác nhận 最大使用回数 (最大使用回数 lấy từ 打刻反映管理 .外出管理 )
		Optional<StampReflectionManagement> stampOptional = this.stampRepo.findByCid(companyId);
		if (stampOptional.isPresent()) {
//			StampReflectionManagement stampReflectionManagement = stampOptional.get();
			// stampReflectionManagement sẽ gọi .外出管理.最大使用回数
			// (outingManager)
			Optional<OutingManagement> OutingManagementOptional = this.outingManagementRepo.findByKey(companyId);
			int outingManager = 3;
			if (OutingManagementOptional.isPresent()) {
				outingManager = OutingManagementOptional.get().getMaximumUsageCount();
			}
			int outingTimeSize = lstOutingTimeSheet.size();
			if (outingTimeSize < outingManager) {
				for (int i = 0; i < outingManager - outingTimeSize; i++) {
					lstOutingTimeSheet
							.add(new OutingTimeSheet(null, Optional.empty(), null, Optional.empty()));
				}
			} else if (outingTimeSize > outingManager) {
				for (int i = 0; i < outingTimeSize - outingManager; i++) {
					lstOutingTimeSheet.remove(lstOutingTimeSheet.get(outingTimeSize - i - 1));
				}
			}
			// *7.1
			int lstOutingTimeSheetSize = lstOutingTimeSheet.size();
			List<OutingTimeSheet> newOutingTimeSheets = null;
			boolean isBreak = false;
			for (int i = 0; i < lstOutingTimeSheetSize; i++) {
				OutingTimeSheet o = lstOutingTimeSheet.get(i);
				if (o.getComeBack() != null && o.getComeBack().isPresent()
						&& o.getComeBack().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans().value != 6 
						&& !o.getComeBack().get().getTimeDay().getReasonTimeChange().getEngravingMethod().isPresent()) {
					if (o.getComeBack().get().getTimeDay().getTimeWithDay().get().v().intValue() == processTimeOutput
							.getTimeOfDay().v().intValue()) {
						OutingTimeSheet newOutingTimeSheet = putInDataComeBack(companyId, WorkInfo, attendanceClass,
								stamps, x, processTimeOutput, o); // ok
						lstOutingTimeSheet.get(i).setProperty(newOutingTimeSheet.getOutingFrameNo(),
								newOutingTimeSheet.getGoOut(), newOutingTimeSheet.getReasonForGoOut(),
								newOutingTimeSheet.getComeBack());
						newOutingTimeSheets = revomeEmptyOutingTimeSheets(lstOutingTimeSheet);
						isBreak = true;
						break;
					}
				} else {
					// 次の枠の時間帯．外出．打刻を確認する
					if ((o.getGoOut() == null || !o.getGoOut().isPresent()
							|| (o.getGoOut() != null && o.getGoOut().isPresent()
									&& o.getGoOut().get().getTimeDay().getTimeWithDay().get().v()
											.intValue() <= processTimeOutput.getTimeOfDay().v().intValue()))) {

						if (i + 1 == lstOutingTimeSheetSize || lstOutingTimeSheet.get(i + 1) == null
								|| lstOutingTimeSheet.get(i + 1).getGoOut() == null
								|| !lstOutingTimeSheet.get(i + 1).getGoOut().isPresent()
								|| (i + 1 < lstOutingTimeSheetSize && lstOutingTimeSheet.get(i + 1) != null
										&& lstOutingTimeSheet.get(i + 1).getGoOut() != null
										&& lstOutingTimeSheet.get(i + 1).getGoOut().isPresent()
										&& processTimeOutput.getTimeOfDay().v().intValue() < lstOutingTimeSheet
												.get(i + 1).getGoOut().get().getTimeDay().getTimeWithDay().get().v()
												.intValue())) {
							OutingTimeSheet newOutingTimeSheet = putDataComeBackForActualAndStamp(companyId, WorkInfo,
									attendanceClass, stamps, x, processTimeOutput, o);
							lstOutingTimeSheet.get(i).setProperty(newOutingTimeSheet.getOutingFrameNo(),
									newOutingTimeSheet.getGoOut(), newOutingTimeSheet.getReasonForGoOut(),
									newOutingTimeSheet.getComeBack());
							newOutingTimeSheets = revomeEmptyOutingTimeSheets(lstOutingTimeSheet);
							isBreak = true;
							break;

						}
					}
				}
			}
			if (!isBreak) {
				newOutingTimeSheets = revomeEmptyOutingTimeSheets(lstOutingTimeSheet);
			}
			if (newOutingTimeSheets == null || newOutingTimeSheets.isEmpty()) {
				return new OutingTimeOfDailyPerformance(employeeId, date, lstOutingTimeSheet);
			} else {
				return new OutingTimeOfDailyPerformance(employeeId, date, newOutingTimeSheets);
			}

		}
		return outDailyPer;

	}

	// *7 外出打刻を反映する (Phản ánh 外出打刻 (thời diểm check ra ngoài))
	// 外出打刻を反映する
	private OutingTimeOfDailyPerformance reflectTimeGoOutCheck(WorkInfoOfDailyPerformance WorkInfo,
			String attendanceClass, List<Stamp> stamps, GeneralDate date, String employeeId, Stamp x,
			ProcessTimeOutput processTimeOutput, String companyId,
			OutingTimeOfDailyPerformance outingDailyPerformance) {
		Optional<OutingTimeOfDailyPerformance> outDailyOptional = this.OutRepo.findByEmployeeIdAndDate(employeeId,
				date);
		OutingTimeOfDailyPerformance outDailyPer;
		List<OutingTimeSheet> lstOutingTimeSheet;
		if (outingDailyPerformance == null) {
			if (outDailyOptional.isPresent()) {
				outDailyPer = outDailyOptional.get();
				lstOutingTimeSheet = outDailyPer.getOutingTime().getOutingTimeSheets();
				Collections.sort(lstOutingTimeSheet, new Comparator<OutingTimeSheet>() {
					public int compare(OutingTimeSheet o1, OutingTimeSheet o2) {
						if (o2 == null || o2.getGoOut() == null || !o2.getGoOut().isPresent()) {
							return 1;
						}
						if (o1 == null || o1.getGoOut() == null || !o1.getGoOut().isPresent()) {
							return -1;
						}
						int t1 = o1.getGoOut().get().getTimeDay().getTimeWithDay().get().v().intValue();
						int t2 = o2.getGoOut().get().getTimeDay().getTimeWithDay().get().v().intValue();
						if (t1 == t2)
							return 0;
						return t1 < t2 ? -1 : 1;
					}
				});
			} else {
				lstOutingTimeSheet = new ArrayList<OutingTimeSheet>();
				lstOutingTimeSheet.add(new OutingTimeSheet(null, Optional.empty(),
						null, Optional.empty()));
				outDailyPer = new OutingTimeOfDailyPerformance(employeeId, date, lstOutingTimeSheet);
			}
		} else {
			outDailyPer = outingDailyPerformance;
			lstOutingTimeSheet = outDailyPer.getOutingTime().getOutingTimeSheets();
			Collections.sort(lstOutingTimeSheet, new Comparator<OutingTimeSheet>() {
				public int compare(OutingTimeSheet o1, OutingTimeSheet o2) {
					if (o2 == null || o2.getGoOut() == null || !o2.getGoOut().isPresent()) {
						return 1;
					}
					if (o1 == null || o1.getGoOut() == null || !o1.getGoOut().isPresent()) {
						return -1;
					}
					int t1 = o1.getGoOut().get().getTimeDay().getTimeWithDay().get().v().intValue();
					int t2 = o2.getGoOut().get().getTimeDay().getTimeWithDay().get().v().intValue();
					if (t1 == t2)
						return 0;
					return t1 < t2 ? -1 : 1;
				}
			});
		}

		// *7.1 外出時間帯Listに最大枠数分の枠を用意する (Chuẩn bị )
		// Xác nhận 最大使用回数 (最大使用回数 lấy từ 打刻反映管理 .外出管理 )
		// 外出時間帯Listに最大枠数分の枠を用意する
		Optional<StampReflectionManagement> stampOptional = this.stampRepo.findByCid(companyId);
		if (stampOptional.isPresent()) {
//			StampReflectionManagement stampReflectionManagement = stampOptional.get();
			// stampReflectionManagement sẽ gọi .外出管理.最大使用回数
			// (outingManager)
			Optional<OutingManagement> OutingManagementOptional = this.outingManagementRepo.findByKey(companyId);
			int outingManager = 3;
			if (OutingManagementOptional.isPresent()) {
				outingManager = OutingManagementOptional.get().getMaximumUsageCount();
			}
			// thiếu điều kiện giữa outingManager và
			// lstOutingTimeSheet.size();
			int outingTimeSize = lstOutingTimeSheet.size();
			if (outingTimeSize < outingManager) {
				for (int i = 0; i < outingManager - outingTimeSize; i++) {
					lstOutingTimeSheet
							.add(new OutingTimeSheet(null, Optional.empty(), null, Optional.empty()));
				}
			} else if (outingTimeSize > outingManager) {
				for (int i = 0; i < outingTimeSize - outingManager; i++) {
					lstOutingTimeSheet.remove(lstOutingTimeSheet.get(outingTimeSize - i - 1));
				}
			}
			// *7.1
			int lstOutingTimeSheetSize = lstOutingTimeSheet.size();
			List<OutingTimeSheet> newOutingTimeSheets = null;
			boolean isBreak = false;
			for (int i = 0; i < lstOutingTimeSheetSize; i++) {
				OutingTimeSheet o = lstOutingTimeSheet.get(i);
				if (o.getGoOut() != null && o.getGoOut().isPresent()
						&& o.getGoOut().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans().value != 6 ) {
					if (o.getGoOut().get().getTimeDay().getTimeWithDay().get().v().intValue() == processTimeOutput
							.getTimeOfDay().v().intValue()) {
						// 打刻を時間帯．外出．実打刻に入れる (put vào 打刻を時間帯．外出．実打刻)
						OutingTimeSheet newOutingTimeSheet = putInDataActualStamp(companyId, WorkInfo, attendanceClass,
								stamps, x, processTimeOutput, o);
						lstOutingTimeSheet.get(i).setProperty(newOutingTimeSheet.getOutingFrameNo(),
								newOutingTimeSheet.getGoOut(), newOutingTimeSheet.getReasonForGoOut(),
								newOutingTimeSheet.getComeBack());
						// 7.1.2 Xóa những cái trống trong list
						newOutingTimeSheets = revomeEmptyOutingTimeSheets(lstOutingTimeSheet);
						isBreak = true;
						break;
						// 7.1.2
					}
				} else {
					if (o.getComeBack() == null || !o.getComeBack().isPresent()) {
						// 7.2* 打刻を時間帯．外出（実打刻と打刻）に入れる
						// (put
						// 打刻を時間帯．外出)
						OutingTimeSheet newOutingTimeSheet = putDataInGoOut(companyId, WorkInfo, attendanceClass,
								stamps, x, processTimeOutput, o);
						lstOutingTimeSheet.get(i).setProperty(newOutingTimeSheet.getOutingFrameNo(),
								newOutingTimeSheet.getGoOut(), newOutingTimeSheet.getReasonForGoOut(),
								newOutingTimeSheet.getComeBack());
						newOutingTimeSheets = revomeEmptyOutingTimeSheets(lstOutingTimeSheet);
						isBreak = true;
						break;
						// 7.2*
					} else {
						if (o.getComeBack().get().getTimeDay().getTimeWithDay().get().v().intValue() <= processTimeOutput
								.getTimeOfDay().v().intValue()) {
							OutingTimeSheet newOutingTimeSheet = putDataInGoOut(companyId, WorkInfo, attendanceClass,
									stamps, x, processTimeOutput, o);
							lstOutingTimeSheet.get(i).setProperty(newOutingTimeSheet.getOutingFrameNo(),
									newOutingTimeSheet.getGoOut(), newOutingTimeSheet.getReasonForGoOut(),
									newOutingTimeSheet.getComeBack());
							newOutingTimeSheets = revomeEmptyOutingTimeSheets(lstOutingTimeSheet);
							isBreak = true;
							break;
						}
					}
				}
			}
			if (!isBreak) {
				newOutingTimeSheets = revomeEmptyOutingTimeSheets(lstOutingTimeSheet);
			}
			if (newOutingTimeSheets == null || newOutingTimeSheets.isEmpty()) {
				return new OutingTimeOfDailyPerformance(employeeId, date, lstOutingTimeSheet);
			} else {
				return new OutingTimeOfDailyPerformance(employeeId, date, newOutingTimeSheets);

			}

		}
		return outDailyPer;

	}

	// 出退勤Listの空枠を削除する
	private List<TimeLeavingWork> revomeEmptyTimeLeaves(List<TimeLeavingWork> timeLeavingWorks) {
		List<TimeLeavingWork> newTimeLeavingWorks = new ArrayList<TimeLeavingWork>();
		List<TimeLeavingWork> newTimeLeavingWorksHasData = new ArrayList<TimeLeavingWork>();
		int lstOutingTimeSize = timeLeavingWorks.size();
		for (int j = 0; j < lstOutingTimeSize; j++) {
			TimeLeavingWork timeLeavingWork = timeLeavingWorks.get(j);

			if ((timeLeavingWork.getAttendanceStamp() != null && timeLeavingWork.getAttendanceStamp().isPresent())
					|| (timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent())) {
				newTimeLeavingWorksHasData.add(timeLeavingWork);
			}

		}
		timeLeavingWorks = newTimeLeavingWorksHasData;
		Collections.sort(timeLeavingWorks, new Comparator<TimeLeavingWork>() {
			public int compare(TimeLeavingWork o1, TimeLeavingWork o2) {
				if (o2 == null || o2.getAttendanceStamp() == null || !o2.getAttendanceStamp().isPresent()
						|| o2.getAttendanceStamp().get().getStamp() == null
						|| !o2.getAttendanceStamp().get().getStamp().isPresent()) {
					return 1;
				}
				if (o1 == null || o1.getAttendanceStamp() == null || !o1.getAttendanceStamp().isPresent()
						|| o1.getAttendanceStamp().get().getStamp() == null
						|| !o1.getAttendanceStamp().get().getStamp().isPresent()) {
					return -1;
				}

				int t1 = o1.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v().intValue();
				int t2 = o2.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get().v().intValue();
				if (t1 == t2)
					return 0;
				return t1 < t2 ? -1 : 1;
			}
		});
		int timeLeavingWorksSize = timeLeavingWorks.size();
		for (int j = 0; j < timeLeavingWorksSize; j++) {
			TimeLeavingWork timeLeavingWork = timeLeavingWorks.get(j);
			if (j < 3) {
				newTimeLeavingWorks.add(new TimeLeavingWork(new WorkNo(j + 1),
						(timeLeavingWork.getAttendanceStamp() != null
								&& timeLeavingWork.getAttendanceStamp().isPresent())
										? timeLeavingWork.getAttendanceStamp().get() : null,
						(timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent())
								? timeLeavingWork.getLeaveStamp().get() : null));

			} else {
				newTimeLeavingWorks.add(new TimeLeavingWork(new WorkNo(j % 3 + 1),
						(timeLeavingWork.getAttendanceStamp() != null
								&& timeLeavingWork.getAttendanceStamp().isPresent())
										? timeLeavingWork.getAttendanceStamp().get() : null,
						(timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent())
								? timeLeavingWork.getLeaveStamp().get() : null));
			}
		}
		return newTimeLeavingWorks;
	}

	// 外出時間帯Listの空枠を削除する
	private List<OutingTimeSheet> revomeEmptyOutingTimeSheets(List<OutingTimeSheet> lstOutingTimeSheet) {
		int lstOutingTimeSize = lstOutingTimeSheet.size();
		List<OutingTimeSheet> newOutingTimeSheets = new ArrayList<OutingTimeSheet>();
		List<OutingTimeSheet> newOutingTimeSheetsRemoved = new ArrayList<OutingTimeSheet>();
		for (int j = 0; j < lstOutingTimeSize; j++) {
			OutingTimeSheet outingTimeSheet = lstOutingTimeSheet.get(j);

			if ((outingTimeSheet.getGoOut() != null && outingTimeSheet.getGoOut().isPresent())
					|| (outingTimeSheet.getComeBack() != null && outingTimeSheet.getComeBack().isPresent())) {
				newOutingTimeSheetsRemoved.add(outingTimeSheet);
			}

		}
		lstOutingTimeSheet = newOutingTimeSheetsRemoved;

		Collections.sort(lstOutingTimeSheet, new Comparator<OutingTimeSheet>() {
			public int compare(OutingTimeSheet o1, OutingTimeSheet o2) {
				if (o2 == null || o2.getGoOut() == null || !o2.getGoOut().isPresent()) {
					return 1;
				}
				if (o1 == null || o1.getGoOut() == null || !o1.getGoOut().isPresent()) {
					return -1;
				}
				int t1 = o1.getGoOut().get().getTimeDay().getTimeWithDay().get().v().intValue();
				int t2 = o2.getGoOut().get().getTimeDay().getTimeWithDay().get().v().intValue();
				if (t1 == t2)
					return 0;
				return t1 < t2 ? -1 : 1;
			}
		});
		int lstOutingTimeNewSize = lstOutingTimeSheet.size();
		for (int j = 0; j < lstOutingTimeNewSize; j++) {
			OutingTimeSheet outingTimeSheet = lstOutingTimeSheet.get(j);
			if (j < 10) {
				OutingFrameNo outingFrameNo = new OutingFrameNo(j + 1);
				newOutingTimeSheets.add(new OutingTimeSheet(outingFrameNo, outingTimeSheet.getGoOut(),
						outingTimeSheet.getReasonForGoOut(), outingTimeSheet.getComeBack()));
			} else {
				OutingFrameNo outingFrameNo = new OutingFrameNo(j % 10 + 1);
				newOutingTimeSheets.add(new OutingTimeSheet(outingFrameNo, outingTimeSheet.getGoOut(),
						outingTimeSheet.getReasonForGoOut(), outingTimeSheet.getComeBack()));
			}
		}
		return newOutingTimeSheets;
	}

	private OutingTimeSheet putInDataActualStamp(String companyId, WorkInfoOfDailyPerformance WorkInfo,
			String attendanceClass, List<Stamp> stamps, Stamp x, ProcessTimeOutput processTimeOutput,
			OutingTimeSheet o) {
		// 外出・休憩時刻を丸める fixed lam tron
		WorkStamp actualStamp = (o.getGoOut() != null && o.getGoOut().isPresent()) 
				? o.getGoOut().get() : null;
		if (actualStamp == null) {
			TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();
			RoundingSet roudingTime = WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode() != null
					? this.getRoudingTime(companyId, WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode().v(),
							"外出".equals(attendanceClass) ? Superiority.GO_OUT : Superiority.TURN_BACK)
					: null;
			InstantRounding instantRounding = null;
			if (roudingTime == null) {
				instantRounding = new InstantRounding(FontRearSection.AFTER, RoundingTimeUnit.ONE);
			} else {
				instantRounding = new InstantRounding(roudingTime.getRoundingSet().getFontRearSection(),
						roudingTime.getRoundingSet().getRoundingTimeUnit());
			}

			int numberMinuteTimeOfDayRounding = roudingTimeWithDay(timeOfDay, instantRounding.getFontRearSection(),
					instantRounding.getRoundingTimeUnit());
			processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));

			WorkStamp newActualStamp = null;

			switch (x.getRelieve().getStampMeans().value) {
			// タイムレコーダー → タイムレコーダー
			case 0:case 1:case 2:case 3:case 4: case 7: case 8: 
				newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
						(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.WEB_STAMP_INPUT);
				break;
			// Web → Web打刻入力
			case 5:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
						(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.MOBILE_ENGRAVING);
				break;
//			// ID入力 → タイムレコーダ(ID入力)
//			case 2:
//				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//						x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_ID_INPUT);
//				break;
//			// 磁気カード → タイムレコーダ(磁気カード)
//			case 3:
//				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//						x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_MAGNET_CARD);
//				break;
//			// ICカード → タイムレコーダ(ICカード)
//			case 4:
//				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//						x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_Ic_CARD);
//				break;
//			// 指紋 → タイムレコーダ(指紋打刻)
//			case 5:
//				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//						x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_FINGER_STAMP);
//				break;
//			// その他 no cover
			default:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
						(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, null);
				break;
			}
			return new OutingTimeSheet(o.getOutingFrameNo(), Optional.ofNullable(newActualStamp),
					x.getType().getGoOutArt().isPresent() ? EnumAdaptor.valueOf(x.getType().getGoOutArt().get().value, GoingOutReason.class) : null, o.getComeBack());

		}
		// 反映済み区分 = true
		Stamp stamp = new Stamp(x.getContractCode(),x.getCardNumber(), x.getStampDateTime(), x.getRelieve(), x.getType(), 
				x.getRefActualResults(), Optional.ofNullable(x.getLocationInfor().map(loc-> loc).orElse(null)));
		stamp.setAttendanceTime(x.getAttendanceTime().isPresent()?x.getAttendanceTime().get():null);
		stamps.add(stamp);
		return new OutingTimeSheet(o.getOutingFrameNo(), Optional.ofNullable(
				(o.getGoOut() != null && o.getGoOut().isPresent()) ? o.getGoOut().get() : null),
				x.getType().getGoOutArt().isPresent() ? EnumAdaptor.valueOf(x.getType().getGoOutArt().get().value, GoingOutReason.class) : null, o.getComeBack());

	}

	// 打刻を時間帯．戻り．実打刻に入れる
	private OutingTimeSheet putInDataComeBack(String companyId, WorkInfoOfDailyPerformance WorkInfo,
			String attendanceClass, List<Stamp> stamps, Stamp x, ProcessTimeOutput processTimeOutput,
			OutingTimeSheet o) {
		WorkStamp actualStamp = (o.getComeBack() != null && o.getComeBack().isPresent())
						? o.getComeBack().get() : null;
		if (actualStamp == null) {
			// 外出・休憩時刻を丸める
			TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();
			RoundingSet roudingTime = WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode() != null
					? this.getRoudingTime(companyId, WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode().v(),
							"外出".equals(attendanceClass) ? Superiority.GO_OUT : Superiority.TURN_BACK)
					: null;
			InstantRounding instantRounding = null;
			if (roudingTime == null) {
				instantRounding = new InstantRounding(FontRearSection.AFTER, RoundingTimeUnit.ONE);
			} else {
				instantRounding = new InstantRounding(roudingTime.getRoundingSet().getFontRearSection(),
						roudingTime.getRoundingSet().getRoundingTimeUnit());
			}
			int numberMinuteTimeOfDayRounding = roudingTimeWithDay(timeOfDay, instantRounding.getFontRearSection(),
					instantRounding.getRoundingTimeUnit());
			processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));

			WorkStamp newActualStamp = null;

			switch (x.getRelieve().getStampMeans().value) {
			// タイムレコーダー → タイムレコーダー
			case 0:case 1:case 2:case 3:case 4: case 7: case 8: 
				newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
						(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.WEB_STAMP_INPUT);
				break;
			// Web → Web打刻入力
			case 5:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
						(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.MOBILE_ENGRAVING);
				break;
//			// ID入力 → タイムレコーダ(ID入力)
//			case 2:
//				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//						x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_ID_INPUT);
//				break;
//			// 磁気カード → タイムレコーダ(磁気カード)
//			case 3:
//				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//						x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_MAGNET_CARD);
//				break;
//			// ICカード → タイムレコーダ(ICカード)
//			case 4:
//				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//						x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_Ic_CARD);
//				break;
//			// 指紋 → タイムレコーダ(指紋打刻)
//			case 5:
//				newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//						x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_FINGER_STAMP);
//				break;
//			// その他 no cover
			default:
				newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
						(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, null);
				break;
			}


			return new OutingTimeSheet(o.getOutingFrameNo(), o.getGoOut(),
					x.getType().getGoOutArt().isPresent() ? EnumAdaptor.valueOf(x.getType().getGoOutArt().get().value, GoingOutReason.class) : null,
					Optional.ofNullable(newActualStamp));

		}

		// 反映済み区分 = true
		Stamp stamp = new Stamp(x.getContractCode(),x.getCardNumber(), x.getStampDateTime(), x.getRelieve(), x.getType(), 
				x.getRefActualResults(), Optional.ofNullable(x.getLocationInfor().map(loc -> loc).orElse(null)));
		stamp.setAttendanceTime(x.getAttendanceTime().isPresent()?x.getAttendanceTime().get():null);
//		StampItem stampItem = new StampItem(x.getCardNumber(), x.getAttendanceTime(), x.getStampCombinationAtr(),
//				x.getSiftCd(), x.getStampMethod(), x.getStampAtr(), x.getWorkLocationCd(), x.getWorkLocationName(),
//				x.getGoOutReason(), x.getDate(), x.getEmployeeId(), ReflectedAtr.REFLECTED);
		stamps.add(stamp);



		return new OutingTimeSheet(
				o.getOutingFrameNo(), o
						.getGoOut(),
						x.getType().getGoOutArt().isPresent() ? EnumAdaptor.valueOf(x.getType().getGoOutArt().get().value, GoingOutReason.class) : null,
				Optional.ofNullable((o.getComeBack() != null && o.getComeBack().isPresent())
										? o.getComeBack().get() : null));
	}

	// 打刻を時間帯．戻り（実打刻と打刻）に入れる
	// 打刻を時間帯．戻りに入れる
	private OutingTimeSheet putDataComeBackForActualAndStamp(String companyId, WorkInfoOfDailyPerformance WorkInfo,
			String attendanceClass, List<Stamp> stamps, Stamp x, ProcessTimeOutput processTimeOutput,
			OutingTimeSheet o) {
		// 外出・休憩時刻を丸める
		// 7.2.1* Làm tròn 打刻時刻 đang xử
		// lý (chưa xử
		// lý)

		TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();
		RoundingSet roudingTime = WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode() != null
				? this.getRoudingTime(companyId, WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode().v(),
						"外出".equals(attendanceClass) ? Superiority.GO_OUT : Superiority.TURN_BACK)
				: null;
		InstantRounding instantRounding = null;
		if (roudingTime == null) {
			instantRounding = new InstantRounding(FontRearSection.AFTER, RoundingTimeUnit.ONE);
		} else {
			instantRounding = new InstantRounding(roudingTime.getRoundingSet().getFontRearSection(),
					roudingTime.getRoundingSet().getRoundingTimeUnit());
		}
		int numberMinuteTimeOfDayRounding = roudingTimeWithDay(timeOfDay, instantRounding.getFontRearSection(),
				instantRounding.getRoundingTimeUnit());
		processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));
		// 7.2.1
		WorkStamp newActualStamp = null;

		switch (x.getRelieve().getStampMeans().value) {
		// タイムレコーダー → タイムレコーダー
		case 0:case 1:case 2:case 3:case 4: case 7: case 8: 
			newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
					(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.WEB_STAMP_INPUT);
			break;
		// Web → Web打刻入力
		case 5:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
					(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.MOBILE_ENGRAVING);
			break;
//		// ID入力 → タイムレコーダ(ID入力)
//		case 2:
//			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//					x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_ID_INPUT);
//			break;
//		// 磁気カード → タイムレコーダ(磁気カード)
//		case 3:
//			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//					x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_MAGNET_CARD);
//			break;
//		// ICカード → タイムレコーダ(ICカード)
//		case 4:
//			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//					x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_Ic_CARD);
//			break;
//		// 指紋 → タイムレコーダ(指紋打刻)
//		case 5:
//			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//					x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_FINGER_STAMP);
//			break;
//		// その他 no cover
		default:
			newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
					(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, null);
			break;
		}

		// 反映済み区分 ← true stamp
		Stamp stamp = new Stamp(x.getContractCode(),x.getCardNumber(), x.getStampDateTime(), x.getRelieve(), x.getType(), 
				x.getRefActualResults(), Optional.ofNullable(x.getLocationInfor().isPresent() ? x.getLocationInfor().get() : null));
		stamp.setAttendanceTime(x.getAttendanceTime().isPresent()?x.getAttendanceTime().get():null);
//		StampItem stampItem = new StampItem(x.getCardNumber(), x.getAttendanceTime(), x.getStampCombinationAtr(),
//				x.getSiftCd(), x.getStampMethod(), x.getStampAtr(), x.getWorkLocationCd(), x.getWorkLocationName(),
//				x.getGoOutReason(), x.getDate(), x.getEmployeeId(), ReflectedAtr.REFLECTED);
		stamps.add(stamp);


		return new OutingTimeSheet(o.getOutingFrameNo(), o.getGoOut(),
				x.getType().getGoOutArt().isPresent() ? EnumAdaptor.valueOf(x.getType().getGoOutArt().get().value, GoingOutReason.class) : null,
				Optional.ofNullable(newActualStamp));
	}

	// 打刻を時間帯．外出に入れる
	private OutingTimeSheet putDataInGoOut(String companyId, WorkInfoOfDailyPerformance WorkInfo,
			String attendanceClass, List<Stamp> stamps, Stamp x, ProcessTimeOutput processTimeOutput,
			OutingTimeSheet o) {
		// 外出・休憩時刻を丸める
		// 7.2.1* Làm tròn 打刻時刻 đang xử ly
		TimeWithDayAttr timeOfDay = processTimeOutput.getTimeOfDay();
		RoundingSet roudingTime = WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode() != null
				? this.getRoudingTime(companyId, WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode().v(),
						"外出".equals(attendanceClass) ? Superiority.GO_OUT : Superiority.TURN_BACK)
				: null;
		InstantRounding instantRounding = null;
		if (roudingTime == null) {
			instantRounding = new InstantRounding(FontRearSection.AFTER, RoundingTimeUnit.ONE);
		} else {
			instantRounding = new InstantRounding(roudingTime.getRoundingSet().getFontRearSection(),
					roudingTime.getRoundingSet().getRoundingTimeUnit());
		}
		int numberMinuteTimeOfDayRounding = roudingTimeWithDay(timeOfDay, instantRounding.getFontRearSection(),
				instantRounding.getRoundingTimeUnit());
		processTimeOutput.setTimeAfter(new TimeWithDayAttr(numberMinuteTimeOfDayRounding));
		// 7.2.1
		WorkStamp newActualStamp = null;

		switch (x.getRelieve().getStampMeans().value) {
		// タイムレコーダー → タイムレコーダー
		case 0:case 1:case 2:case 3:case 4: case 7: case 8: 
			newActualStamp = new WorkStamp(processTimeOutput.getTimeOfDay(),
					(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.WEB_STAMP_INPUT);
			break;
		// Web → Web打刻入力
		case 5:
			newActualStamp = new WorkStamp( processTimeOutput.getTimeOfDay(),
					(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, EngravingMethod.MOBILE_ENGRAVING);
			break;
//		// ID入力 → タイムレコーダ(ID入力)
//		case 2:
//			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//					x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_ID_INPUT);
//			break;
//		// 磁気カード → タイムレコーダ(磁気カード)
//		case 3:
//			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//					x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_MAGNET_CARD);
//			break;
//		// ICカード → タイムレコーダ(ICカード)
//		case 4:
//			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//					x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_Ic_CARD);
//			break;
//		// 指紋 → タイムレコーダ(指紋打刻)
//		case 5:
//			newActualStamp = new WorkStamp(processTimeOutput.getTimeAfter(), processTimeOutput.getTimeOfDay(),
//					x.getWorkLocationCd(), StampSourceInfo.TIME_RECORDER_FINGER_STAMP);
//			break;
//		// その他 no cover
		default:
			newActualStamp = new WorkStamp( processTimeOutput.getTimeOfDay(),
					(x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent())?x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get():null, TimeChangeMeans.REAL_STAMP, null);
			break;
		}

		// 反映済み区分 ← true stamp
		Stamp stamp = new Stamp(x.getContractCode(),x.getCardNumber(), x.getStampDateTime(), x.getRelieve(), x.getType(), 
				x.getRefActualResults(), Optional.ofNullable(x.getLocationInfor().isPresent() ? x.getLocationInfor().get() : null));
		stamp.setAttendanceTime(x.getAttendanceTime().isPresent()?x.getAttendanceTime().get():null);
//		StampItem stampItem = new StampItem(x.getCardNumber(), x.getAttendanceTime(), x.getStampCombinationAtr(),
//				x.getSiftCd(), x.getStampMethod(), x.getStampAtr(), x.getWorkLocationCd(), x.getWorkLocationName(),
//				x.getGoOutReason(), x.getDate(), x.getEmployeeId(), ReflectedAtr.REFLECTED);
		stamps.add(stamp);



		return new OutingTimeSheet(o.getOutingFrameNo(),
				Optional.ofNullable( newActualStamp),
				x.getType().getGoOutArt().isPresent() ? EnumAdaptor.valueOf(x.getType().getGoOutArt().get().value, GoingOutReason.class) : null, o.getComeBack());

	}

	private int roudingTimeWithDay(TimeWithDayAttr timeOfDay, FontRearSection fontRearSection,
			RoundingTimeUnit roundTimeUnit) {
		InstantRounding instantRounding = new InstantRounding(fontRearSection, roundTimeUnit);
		int numberMinuteTimeOfDay = timeOfDay.v().intValue();
		int timeOfDayMinute = timeOfDay.minute();
		int roundingTimeUnit = new Integer(instantRounding.getRoundingTimeUnit().description).intValue();
		// FontRearSection.BEFOR
		int numberMinuteTimeOfDayRounding = 0;
		int modTimeOfDay = timeOfDayMinute % roundingTimeUnit;
		if (instantRounding.getFontRearSection().value == 0) {

			numberMinuteTimeOfDayRounding = modTimeOfDay == 0 ? numberMinuteTimeOfDay
					: numberMinuteTimeOfDay - modTimeOfDay;
		} else {
			numberMinuteTimeOfDayRounding = modTimeOfDay == 0 ? numberMinuteTimeOfDay
					: numberMinuteTimeOfDay - modTimeOfDay + roundingTimeUnit;
		}
		return numberMinuteTimeOfDayRounding;
	}

	// 打刻を反映するか確認する (Xác nhận ) true reflect false no reflect
	// 打刻を処理中年月日の時刻に変換する and 打刻を反映するか確認する
	private boolean confirmReflectStamp(StampReflectRangeOutput s, Stamp x, ProcessTimeOutput processTimeOutput) {
		AttendanceTime attendanceTime = x.getAttendanceTime().isPresent()?
				x.getAttendanceTime().get():new AttendanceTime(x.getStampDateTime().clockHourMinute().v());
		processTimeOutput.setTimeOfDay(new TimeWithDayAttr(attendanceTime.v()));
		// 外出
		TimeZoneOutput goOut = s.getGoOut();
		if (goOut.getStart().v().intValue() <= processTimeOutput.getTimeOfDay().v().intValue()
				&& goOut.getEnd().v().intValue() >= processTimeOutput.getTimeOfDay().v().intValue()) {
			return true;
		}
		return false;
	}

	// 退勤打刻の反映範囲か確認する
	private String confirmReflectRangeLeavingTime(Stamp stamp, StampReflectRangeOutput s) {
		AttendanceTime attendanceTime = stamp.getAttendanceTime().isPresent()?
				stamp.getAttendanceTime().get():new AttendanceTime(stamp.getStampDateTime().clockHourMinute().v());
		List<StampReflectTimezoneOutput> lstStampReflectTimezone = s.getLstStampReflectTimezone();
		int n = lstStampReflectTimezone.size();
		for (int i = 0; i < n; i++) {
			StampReflectTimezoneOutput stampReflectTimezone = lstStampReflectTimezone.get(i);
			if (stampReflectTimezone.getStartTime().v().intValue() <= attendanceTime.v().intValue()
					&& stampReflectTimezone.getEndTime().v().intValue() >= attendanceTime.v().intValue()
					&& stampReflectTimezone.getClassification().value == 1) {
				if (stampReflectTimezone.getWorkNo().v().intValue() == 1) {
					return "range1";
				}
				if (stampReflectTimezone.getWorkNo().v().intValue() == 2) {
					return "range2";
				}
			}
		}
		return "outrange";
	}

	private TimeLeavingOfDailyPerformance reflectActualTimeOrAttendence(List<Stamp> stamps,
			WorkInfoOfDailyPerformance WorkInfo, TimeLeavingOfDailyPerformance timeDailyPer, GeneralDate date,
			String employeeId, Stamp x, String attendanceClass, String actualStampClass, int worktNo,
			String companyId) {
		TimePrintDestinationOutput timePrintDestinationOutput = new TimePrintDestinationOutput();
		// getReflecDestination Lấy dữ liệu 反映先

		boolean checkTimeLeavingWorkExist = false;
		// khởi tạo đối tượng mới
		WorkStamp stampOrActualStamp = null;
		TimeActualStamp timeActualStamp = null;
		if (timeDailyPer != null && timeDailyPer.getAttendance().getTimeLeavingWorks() != null
				&& !timeDailyPer.getAttendance().getTimeLeavingWorks().isEmpty()) {
			timeActualStamp = this.getTimeActualStamp(timeDailyPer, worktNo, attendanceClass);
			
			TimeLeavingOfDailyPerformance dailyPerformance = new TimeLeavingOfDailyPerformance(employeeId, date, timeDailyPer.getAttendance());
			checkTimeLeavingWorkExist = this.checkTimeLeavingWorkExist(dailyPerformance, worktNo, attendanceClass);
			if (timeActualStamp != null && "実打刻".equals(actualStampClass)) {
				stampOrActualStamp = (timeActualStamp.getActualStamp() != null
						&& timeActualStamp.getActualStamp().isPresent()) ? timeActualStamp.getActualStamp().get()
								: null;
			} else if (timeActualStamp != null && !"実打刻".equals(actualStampClass)) {
				stampOrActualStamp = (timeActualStamp.getStamp() != null && timeActualStamp.getStamp().isPresent())
						? timeActualStamp.getStamp().get() : null;
			}

			timePrintDestinationOutput.setLocationCode((stampOrActualStamp != null
					&& stampOrActualStamp.getLocationCode() != null && stampOrActualStamp.getLocationCode().isPresent())
							? stampOrActualStamp.getLocationCode().get() : null);
			timePrintDestinationOutput
					.setStampSourceInfo(stampOrActualStamp != null ? stampOrActualStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() : null);
			timePrintDestinationOutput
					.setTimeOfDay(stampOrActualStamp != null ? stampOrActualStamp.getTimeDay().getTimeWithDay().get() : null);
		}
		boolean isNullTimeActualStamp = true;
		boolean isNullStampOrActualStamp = true;
		if (timeActualStamp != null) {
			isNullTimeActualStamp = false;
		} else {
			timeActualStamp = new TimeActualStamp();
		}
		if (stampOrActualStamp != null) {
			isNullStampOrActualStamp = false;
		} else {
			stampOrActualStamp = new WorkStamp();
		}
		// 組み合わせ区分 != 直行 or != 直帰
		if (x.getType().getChangeCalArt().value != 0 && x.getType().isChangeHalfDay()
				&& x.getType().getSetPreClockArt().value != 1 && x.getType().getSetPreClockArt().value != 2) {

			WorkInfoOfDailyPerformance infoOfDailyPerformance = new WorkInfoOfDailyPerformance(employeeId, date, WorkInfo.getWorkInformation());
			// 1* // Phán đoán điều kiện phản ảnh 出退勤 của 通常打刻
			boolean checkReflectNormal = checkReflectNormal(infoOfDailyPerformance, attendanceClass, x, timePrintDestinationOutput, date,
					employeeId, companyId);
			// 1*
			if (checkReflectNormal) {
				// 2* check tay ngày nghỉ) worktype thay đổi
				boolean checkHolidayChange = checkHolidayChange(infoOfDailyPerformance, companyId);
				// 2*
				if (checkHolidayChange) {
					// Phản ánh 時刻
					// 打刻を反映する
					AttendanceTime attendanceTime = x.getAttendanceTime().isPresent()?
							x.getAttendanceTime().get():new AttendanceTime(x.getStampDateTime().clockHourMinute().v());
					WorkLocationCD workLocationCd = (x.getRefActualResults().getWorkInforStamp().isPresent() && x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().isPresent()) ? x.getRefActualResults().getWorkInforStamp().get().getWorkLocationCD().get() : null;
					StampMeans stampMethod = x.getRelieve().getStampMeans();

					TimePrintDestinationOutput timePrintDestinationCopy = new TimePrintDestinationOutput();
					timePrintDestinationCopy.setTimeOfDay(new TimeWithDayAttr(attendanceTime.v()));
					timePrintDestinationCopy.setLocationCode(workLocationCd);
					switch (stampMethod.value) {
					// タイムレコーダー → タイムレコーダー
					case 0:case 1:case 2:case 3: case 4:case 7:case 8:
						timePrintDestinationCopy.setStampSourceInfo(TimeChangeMeans.REAL_STAMP);
						break;
					case 5:
						timePrintDestinationCopy.setStampSourceInfo(TimeChangeMeans.REAL_STAMP);
						break;
					// その他 no cover
					default:
						timePrintDestinationCopy.setStampSourceInfo(TimeChangeMeans.REAL_STAMP);
						break;
					}

					// Copy tới 勤怠打刻 từ 打刻反映先

					stampOrActualStamp.setPropertyWorkStamp(
							timePrintDestinationCopy.getTimeOfDay(), timePrintDestinationCopy.getLocationCode(),
							timePrintDestinationCopy.getStampSourceInfo());

					// 5* làm tròn 打刻
					// 打刻を反映する
					// timePrintDestinationCopy
					if ("打刻".equals(actualStampClass)) {

						RoundingSet roudingTime = WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode() != null
								? this.getRoudingTime(companyId, WorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode().v(),
										"退勤".equals(attendanceClass) ? Superiority.OFFICE_WORK : Superiority.ATTENDANCE)
								: null;
						InstantRounding instantRounding = null;
						if (roudingTime == null) {
							instantRounding = new InstantRounding(FontRearSection.AFTER, RoundingTimeUnit.ONE);
						} else {
							instantRounding = new InstantRounding(roudingTime.getRoundingSet().getFontRearSection(),
									roudingTime.getRoundingSet().getRoundingTimeUnit());
						}

						TimeWithDayAttr timeOfDay = stampOrActualStamp.getTimeDay().getTimeWithDay().get();
						int numberMinuteTimeOfDay = stampOrActualStamp.getTimeDay().getTimeWithDay().get().v().intValue();
						int timeOfDayMinute = timeOfDay.minute();
						int roundingTimeUnit = new Integer(instantRounding.getRoundingTimeUnit().description)
								.intValue();
						// FontRearSection.BEFOR
						int numberMinuteTimeOfDayRounding = 0;
						int modTimeOfDay = timeOfDayMinute % roundingTimeUnit;
						if (instantRounding.getFontRearSection().value == 0) {

							numberMinuteTimeOfDayRounding = modTimeOfDay == 0 ? numberMinuteTimeOfDay
									: numberMinuteTimeOfDay - modTimeOfDay;
						} else {
							numberMinuteTimeOfDayRounding = modTimeOfDay == 0 ? numberMinuteTimeOfDay
									: numberMinuteTimeOfDay - modTimeOfDay + roundingTimeUnit;
						}

						stampOrActualStamp.setPropertyWorkStamp(
								timePrintDestinationCopy.getTimeOfDay(), timePrintDestinationCopy.getLocationCode(),
								timePrintDestinationCopy.getStampSourceInfo());

					} else {
						stampOrActualStamp.setPropertyWorkStamp(
								timePrintDestinationCopy.getTimeOfDay(), timePrintDestinationCopy.getLocationCode(),
								timePrintDestinationCopy.getStampSourceInfo());

					}

					// 5*
					// gan 反映済み区分 = true, trường này chưa có trong StampItem
					Stamp stamp = new Stamp(x.getContractCode(),x.getCardNumber(), x.getStampDateTime(), x.getRelieve(), x.getType(), 
							x.getRefActualResults(), Optional.ofNullable(x.getLocationInfor().isPresent() ? x.getLocationInfor().get() : null));
					stamp.setAttendanceTime(x.getAttendanceTime().isPresent()?x.getAttendanceTime().get():null);
				}

			}

		}
		// 6* Update số lần phản ánh 打刻
		// 打刻反映回数を更新
		if ("実打刻".equals(actualStampClass)) {
			timeActualStamp.setPropertyTimeActualStamp(timeActualStamp.getActualStamp(), timeActualStamp.getStamp(),
					timeActualStamp.getNumberOfReflectionStamp() == null ? 1
							: timeActualStamp.getNumberOfReflectionStamp() + 1);
		}
		// 6*

		// update data TimeLeavingOfDailyPerformance

		if (isNullStampOrActualStamp && isNullTimeActualStamp && !checkTimeLeavingWorkExist) {
			List<TimeLeavingWork> lstTimeLeave = (timeDailyPer != null && timeDailyPer.getAttendance().getTimeLeavingWorks() != null)
					? timeDailyPer.getAttendance().getTimeLeavingWorks() : new ArrayList<TimeLeavingWork>();

			if ("出勤".equals(attendanceClass) && "実打刻".equals(actualStampClass)) {
				lstTimeLeave.add(new TimeLeavingWork(new WorkNo(worktNo),
						new TimeActualStamp(stampOrActualStamp,
								(timeActualStamp.getStamp() != null && timeActualStamp.getStamp().isPresent())
										? timeActualStamp.getStamp().get() : null,
								timeActualStamp.getNumberOfReflectionStamp() == null ? 0
										: timeActualStamp.getNumberOfReflectionStamp()),
						null));

			} else if (" ".equals(attendanceClass) && !"実打刻".equals(actualStampClass)) {
				lstTimeLeave.add(new TimeLeavingWork(new WorkNo(worktNo), new TimeActualStamp(
						(timeActualStamp.getActualStamp() != null && timeActualStamp.getActualStamp().isPresent())
								? timeActualStamp.getActualStamp().get() : null,
						stampOrActualStamp, timeActualStamp.getNumberOfReflectionStamp() == null ? 0
								: timeActualStamp.getNumberOfReflectionStamp()),
						null));
			} else if (!"出勤".equals(attendanceClass) && "実打刻".equals(actualStampClass)) {
				lstTimeLeave.add(new TimeLeavingWork(new WorkNo(worktNo), null,
						new TimeActualStamp(stampOrActualStamp,
								(timeActualStamp.getStamp() != null && timeActualStamp.getStamp().isPresent())
										? timeActualStamp.getStamp().get() : null,
								timeActualStamp.getNumberOfReflectionStamp() == null ? 0
										: timeActualStamp.getNumberOfReflectionStamp())));
			} else {
				lstTimeLeave.add(new TimeLeavingWork(new WorkNo(worktNo), null,
						new TimeActualStamp(
								(timeActualStamp.getStamp() != null && timeActualStamp.getStamp().isPresent())
										? timeActualStamp.getStamp().get() : null,
								stampOrActualStamp, timeActualStamp.getNumberOfReflectionStamp() == null ? 0
										: timeActualStamp.getNumberOfReflectionStamp())));
			}

			return new TimeLeavingOfDailyPerformance(employeeId, new WorkTimes(worktNo), lstTimeLeave, date);
		} else {
			List<TimeLeavingWork> timeLeavingWorks = timeDailyPer.getAttendance().getTimeLeavingWorks();
			int size = timeLeavingWorks.size();
			for (int i = 0; i < size; i++) {
				TimeLeavingWork timeLeavingWork = timeLeavingWorks.get(i);
				if (timeLeavingWork.getWorkNo().v().intValue() == worktNo) {
					if ("出勤".equals(attendanceClass) && "実打刻".equals(actualStampClass)) {

						if (timeLeavingWorks.get(i).getAttendanceStamp() != null
								&& timeLeavingWorks.get(i).getAttendanceStamp().isPresent()) {
							timeLeavingWorks.get(i).getAttendanceStamp().get().setPropertyTimeActualStamp(
									Optional.ofNullable(stampOrActualStamp), timeActualStamp.getStamp(),
									timeActualStamp.getNumberOfReflectionStamp() == null ? 0
											: timeActualStamp.getNumberOfReflectionStamp());
						} else {
							TimeActualStamp timeActualStamp2 = new TimeActualStamp(stampOrActualStamp,
									(timeActualStamp.getStamp() != null && timeActualStamp.getStamp().isPresent())
											? timeActualStamp.getStamp().get() : null,
									timeActualStamp.getNumberOfReflectionStamp() == null ? 0
											: timeActualStamp.getNumberOfReflectionStamp());
							timeLeavingWorks.get(i).setTimeLeavingWork(timeLeavingWorks.get(i).getWorkNo(),
									Optional.ofNullable(timeActualStamp2), timeLeavingWorks.get(i).getLeaveStamp());
						}

					} else if ("出勤".equals(attendanceClass) && !"実打刻".equals(actualStampClass)) {

						if (timeLeavingWorks.get(i).getAttendanceStamp() != null
								&& timeLeavingWorks.get(i).getAttendanceStamp().isPresent()) {
							timeLeavingWorks.get(i).getAttendanceStamp().get().setPropertyTimeActualStamp(
									timeActualStamp.getActualStamp(), Optional.ofNullable(stampOrActualStamp),
									timeActualStamp.getNumberOfReflectionStamp() == null ? 0
											: timeActualStamp.getNumberOfReflectionStamp());
						} else {
							TimeActualStamp timeActualStamp2 = new TimeActualStamp(
									(timeActualStamp.getActualStamp() != null
											&& timeActualStamp.getActualStamp().isPresent())
													? timeActualStamp.getActualStamp().get() : null,
									stampOrActualStamp, timeActualStamp.getNumberOfReflectionStamp() == null ? 0
											: timeActualStamp.getNumberOfReflectionStamp());
							timeLeavingWorks.get(i).setTimeLeavingWork(timeLeavingWorks.get(i).getWorkNo(),
									Optional.ofNullable(timeActualStamp2), timeLeavingWorks.get(i).getLeaveStamp());
						}

					} else if (!"出勤".equals(attendanceClass) && "実打刻".equals(actualStampClass)) {
						if (timeLeavingWorks.get(i).getLeaveStamp() != null
								&& timeLeavingWorks.get(i).getLeaveStamp().isPresent()) {
							timeLeavingWorks.get(i).getLeaveStamp().get().setPropertyTimeActualStamp(
									Optional.ofNullable(stampOrActualStamp), timeActualStamp.getStamp(),
									timeActualStamp.getNumberOfReflectionStamp() == null ? 0
											: timeActualStamp.getNumberOfReflectionStamp());
						} else {

							TimeActualStamp timeActualStamp2 = new TimeActualStamp(stampOrActualStamp,
									(timeActualStamp.getStamp() != null && timeActualStamp.getStamp().isPresent())
											? timeActualStamp.getStamp().get() : null,
									timeActualStamp.getNumberOfReflectionStamp() == null ? 0
											: timeActualStamp.getNumberOfReflectionStamp());
							timeLeavingWorks.get(i).setTimeLeavingWork(timeLeavingWorks.get(i).getWorkNo(),
									timeLeavingWorks.get(i).getAttendanceStamp(),
									Optional.ofNullable(timeActualStamp2));
						}

					} else {
						if (timeLeavingWorks.get(i).getLeaveStamp() != null
								&& timeLeavingWorks.get(i).getLeaveStamp().isPresent()) {
							timeLeavingWorks.get(i).getLeaveStamp().get().setPropertyTimeActualStamp(
									timeActualStamp.getActualStamp(), Optional.ofNullable(stampOrActualStamp),
									timeActualStamp.getNumberOfReflectionStamp() == null ? 0
											: timeActualStamp.getNumberOfReflectionStamp());
						} else {
							TimeActualStamp timeActualStamp2 = new TimeActualStamp(
									(timeActualStamp.getActualStamp() != null
											&& timeActualStamp.getActualStamp().isPresent())
													? timeActualStamp.getActualStamp().get() : null,
									stampOrActualStamp, timeActualStamp.getNumberOfReflectionStamp() == null ? 0
											: timeActualStamp.getNumberOfReflectionStamp());
							timeLeavingWorks.get(i).setTimeLeavingWork(timeLeavingWorks.get(i).getWorkNo(),
									timeLeavingWorks.get(i).getAttendanceStamp(),
									Optional.ofNullable(timeActualStamp2));
						}

					}
					break;

				}
			}

			return new TimeLeavingOfDailyPerformance(employeeId, new WorkTimes(worktNo), timeLeavingWorks, date);
		}

	}

	/*
	 * private WorkStamp getWorkStamp(GeneralDate date, String employeeId, int
	 * worktNo, String attendanceClass, String actualStampClass) { //8*
	 * Optional<TimeLeavingOfDailyPerformance> timeOptional =
	 * this.timeRepo.findByKey(employeeId, date);
	 * 
	 * //8* lấy từ a nam if (timeOptional.isPresent()) {
	 * TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance =
	 * timeOptional.get(); List<TimeLeavingWork> lstTimeLeavingWork =
	 * timeLeavingOfDailyPerformance.getTimeLeavingWorks(); int n =
	 * lstTimeLeavingWork.size(); for (int i = 0; i < n; i++) { TimeLeavingWork
	 * timeLeavingWork = lstTimeLeavingWork.get(i); if
	 * (timeLeavingWork.getWorkNo().v().intValue() == worktNo) { // 出勤
	 * TimeActualStamp attendanceStamp = null; if ("出勤".equals(attendanceClass))
	 * { attendanceStamp = timeLeavingWork.getAttendanceStamp(); } else {
	 * attendanceStamp = timeLeavingWork.getLeaveStamp(); } // 実打刻 if
	 * ("実打刻".equals(actualStampClass)) { return
	 * attendanceStamp.getActualStamp(); } return attendanceStamp.getStamp(); }
	 * }
	 * 
	 * } return null; }
	 */
//	private WorkStamp getWorkStamp(TimeLeavingOfDailyPerformance timeDailyPer, int worktNo, String attendanceClass,
//			String actualStampClass) {
//
//		if (timeDailyPer != null) {
//			List<TimeLeavingWork> lstTimeLeavingWork = timeDailyPer.getTimeLeavingWorks();
//			int n = lstTimeLeavingWork.size();
//			for (int i = 0; i < n; i++) {
//				TimeLeavingWork timeLeavingWork = lstTimeLeavingWork.get(i);
//				if (timeLeavingWork.getWorkNo().v().intValue() == worktNo) {
//					// 出勤
//					TimeActualStamp attendanceStamp = null;
//					if ("出勤".equals(attendanceClass)) {
//						attendanceStamp = (timeLeavingWork.getAttendanceStamp() != null
//								&& timeLeavingWork.getAttendanceStamp().isPresent())
//										? timeLeavingWork.getAttendanceStamp().get() : null;
//					} else {
//						attendanceStamp = (timeLeavingWork.getLeaveStamp() != null
//								&& timeLeavingWork.getLeaveStamp().isPresent()) ? timeLeavingWork.getLeaveStamp().get()
//										: null;
//					}
//					// 実打刻
//					if ("実打刻".equals(actualStampClass)) {
//						return (attendanceStamp.getActualStamp() != null
//								&& attendanceStamp.getActualStamp().isPresent())
//										? attendanceStamp.getActualStamp().get() : null;
//					}
//					return (attendanceStamp.getStamp() != null && attendanceStamp.getStamp().isPresent())
//							? attendanceStamp.getStamp().get() : null;
//				}
//			}
//
//		}
//		return null;
//	}

	private TimeActualStamp getTimeActualStamp(TimeLeavingOfDailyPerformance timeDailyPer, int worktNo,
			String attendanceClass) {
		List<TimeLeavingWork> lstTimeLeavingWork = timeDailyPer.getAttendance().getTimeLeavingWorks();
		int n = lstTimeLeavingWork.size();
		for (int i = 0; i < n; i++) {
			TimeLeavingWork timeLeavingWork = lstTimeLeavingWork.get(i);
			if (timeLeavingWork.getWorkNo().v().intValue() == worktNo) {
				// 出勤
				if ("出勤".equals(attendanceClass)) {
					return (timeLeavingWork.getAttendanceStamp() != null
							&& timeLeavingWork.getAttendanceStamp().isPresent())
									? timeLeavingWork.getAttendanceStamp().get() : null;
				} else {
					return (timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent())
							? timeLeavingWork.getLeaveStamp().get() : null;
				}
			}
		}
		return null;
	}

	private boolean checkTimeLeavingWorkExist(TimeLeavingOfDailyPerformance timeDailyPer, int worktNo,
			String attendanceClass) {
		List<TimeLeavingWork> lstTimeLeavingWork = timeDailyPer.getAttendance().getTimeLeavingWorks();
		int n = lstTimeLeavingWork.size();
		for (int i = 0; i < n; i++) {
			TimeLeavingWork timeLeavingWork = lstTimeLeavingWork.get(i);
			if (timeLeavingWork.getWorkNo().v().intValue() == worktNo) {
				if ((timeLeavingWork.getAttendanceStamp() != null && timeLeavingWork.getAttendanceStamp().isPresent())
						|| (timeLeavingWork.getLeaveStamp() != null && timeLeavingWork.getLeaveStamp().isPresent())) {
					return true;
				}
			}
		}
		return false;
	}

	// Phán đoán điều kiện phản ảnh 出退勤 của 通常打刻 (true reflect and false no
	// reflect)
	// 通常打刻の出退勤を反映する
	private boolean checkReflectNormal(WorkInfoOfDailyPerformance workInfo, String attendanceClass, Stamp stamp,
			TimePrintDestinationOutput timePrintDestinationOutput, GeneralDate date, String employeeId,
			String companyId) {
		if (timePrintDestinationOutput.getLocationCode() == null
				|| timePrintDestinationOutput.getStampSourceInfo() == null
				|| timePrintDestinationOutput.getTimeOfDay() == null) {
			return true;
		} else {
			if (timePrintDestinationOutput.getStampSourceInfo().value == 6
					|| timePrintDestinationOutput.getStampSourceInfo().value == 7) {
				// sua bang tay
				// return no reflect
				return false;

			} else if (timePrintDestinationOutput.getStampSourceInfo().value == 1) {
				// k phai sua bang tay
				// return no reflect
				return false;

			} else {
				// Phán đoán thứ tự uu tiên đơn xin và 打刻
				// 申請と打刻の優先順位を判断
				boolean checkStampPriority = checkStampPriority(timePrintDestinationOutput, companyId);
				if (checkStampPriority) {
					// Phán đoán thứ tự ưu tiên tự động check 打刻
					boolean checkPriorityAutoStamp = checkPriorityAutoStamp(timePrintDestinationOutput, companyId);
					if (checkPriorityAutoStamp) {
						// 3* 前優先後優先を見て反映するか確認する
						boolean confirmReflectPriority = confirmReflectPriority(companyId, workInfo, attendanceClass, stamp,
								timePrintDestinationOutput, date, employeeId);
						if (confirmReflectPriority) {
							return true;
						}
						// 3*
						return false;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}

		}
	}

	// 3* 前優先後優先を見て反映するか確認する (true reflect and false no reflect )
	private boolean confirmReflectPriority(String companyId, WorkInfoOfDailyPerformance workInfo, String attendanceClass, Stamp stamp,
			TimePrintDestinationOutput timePrintDestinationOutput, GeneralDate date, String employeeId) {
		Optional<WorkInfoOfDailyPerformance> WorkInfoOptional = workInfo != null ? Optional.of(workInfo) : this.workInforRepo.find(employeeId, date);
		if (WorkInfoOptional.isPresent()) {
			WorkInformation recordWorkInformation = WorkInfoOptional.get().getWorkInformation().getRecordInfo();
			WorkTimeCode workTimeCode = recordWorkInformation.getWorkTimeCode();

			PrioritySetting prioritySetting = this.getPrioritySetting(companyId, workTimeCode.v(),
					"退勤".equals(attendanceClass) ? StampPiorityAtr.LEAVE_WORK : StampPiorityAtr.GOING_WORK);
			MultiStampTimePiorityAtr priorityAtr = null;
			if (prioritySetting == null) {
				priorityAtr = MultiStampTimePiorityAtr.valueOf(0);
			} else {
				priorityAtr = prioritySetting.getPriorityAtr();
			}

			AttendanceTime attendanceTime = stamp.getAttendanceTime().isPresent()?
					stamp.getAttendanceTime().get():new AttendanceTime(stamp.getStampDateTime().clockHourMinute().v());
			TimeWithDayAttr timeDestination = timePrintDestinationOutput.getTimeOfDay();
			if (priorityAtr.value == 0) {
				if (attendanceTime.v().intValue() >= timeDestination.v().intValue()) {
					return false;
				} else {
					return true;
				}

			} else {
				if (attendanceTime.v().intValue() >= timeDestination.v().intValue()) {
					return true;
				} else {
					return false;
				}
			}

		}
		return true;
	}

	// 2* check tay ngày nghỉ) worktype thay đổi (true reflect and false no
	// reflect)
	// 休日打刻時に勤務種類を変更する
	private boolean checkHolidayChange(WorkInfoOfDailyPerformance WorkInfo, String companyId) {
		if (WorkInfo != null) {
			WorkInformation recordWorkInformation = WorkInfo.getWorkInformation().getRecordInfo();
			// Xác định phân loại 1日半日出勤・1日休日
			// 1日半日出勤・1日休日系の判定
			WorkStyle checkWorkDay = this.basicScheduleService
					.checkWorkDay(recordWorkInformation.getWorkTypeCode().v());
			// 休日系
			if (checkWorkDay.value == 0) {
				// 勤務情報を変更する
				if (!this.reflectWorkInformationDomainService.changeWorkInformation(WorkInfo, companyId)) {
					return false;
				}
			}
			return true;
		}
		// chưa xác nhận
		return true;
	}

	// Phán đoán thứ tự ưu tiên tự động check 打刻
	// 直行直帰の判断をする
	private boolean checkPriorityAutoStamp(TimePrintDestinationOutput timePrintDestinationOutput, String companyId) {
		// true (reflect) and false (no reflect)
		if (timePrintDestinationOutput.getStampSourceInfo().value == 4
				&& timePrintDestinationOutput.getStampSourceInfo().value == 5) {
			Optional<StampReflectionManagement> stampOptional = this.stampRepo.findByCid(companyId);
			if (stampOptional.isPresent()) {
				StampReflectionManagement stampReflectionManagement = stampOptional.get();
				if (stampReflectionManagement.getAutoStampReflectionClass().value == 0) {
					return false;
				}
				return true;
			}
			return true;
		}
		return true;
	}

	// Phán đoán thứ tự ueu tiên đơn xin và 打刻
	// 申請と打刻の優先順位を判断
	private boolean checkStampPriority(TimePrintDestinationOutput timePrintDestinationOutput, String companyId) {
		// true (reflect) and false (no reflect)
		Optional<StampReflectionManagement> stampOptional = this.stampRepo.findByCid(companyId);
		if (stampOptional.isPresent()) {
			StampReflectionManagement stampReflectionManagement = stampOptional.get();
			// 直行直帰・出張申請の打刻優先 and 直行直帰申請
			if (stampReflectionManagement.getActualStampOfPriorityClass().value == 1
					&& timePrintDestinationOutput.getStampSourceInfo().value == 4) {
				return false;
			}
			return true;
		}
		return true;
	}

	private String confirmReflectRange(Stamp stamp, StampReflectRangeOutput s) {
		// Chuyển đổi 打刻．時刻
		AttendanceTime attendanceTime = stamp.getAttendanceTime().isPresent()?
				stamp.getAttendanceTime().get():new AttendanceTime(stamp.getStampDateTime().clockHourMinute().v());
		ProcessTimeOutput processTimeOutput = new ProcessTimeOutput();
		processTimeOutput.setTimeOfDay(new TimeWithDayAttr(attendanceTime.v()));

		// in or outrange
		List<StampReflectTimezoneOutput> lstStampReflectTimezone = s.getLstStampReflectTimezone();
		if (lstStampReflectTimezone == null) {
			return "outrange";
		}

		int n = lstStampReflectTimezone.size();
		for (int i = 0; i < n; i++) {
			StampReflectTimezoneOutput stampReflectTimezone = lstStampReflectTimezone.get(i);
			if (stampReflectTimezone.getStartTime().v().intValue() <= processTimeOutput.getTimeOfDay().v().intValue()
					&& stampReflectTimezone.getEndTime().v().intValue() >= processTimeOutput.getTimeOfDay().v()
							.intValue()
					&& stampReflectTimezone.getClassification().value == 0) {
				if (stampReflectTimezone.getWorkNo().v().intValue() == 1) {
					return "range1";
				}
				if (stampReflectTimezone.getWorkNo().v().intValue() == 2) {
					return "range2";
				}
			}
		}
		return "outrange";
	}

	// làm tròn thời gian
	private RoundingSet getRoudingTime(String companyId, String workTimeCode, Superiority superiority) {
		Optional<WorkTimezoneCommonSet> workTimezoneCommonSet = GetCommonSet.workTimezoneCommonSet(requireService.createRequire(), companyId, workTimeCode);
		if (workTimezoneCommonSet.isPresent()) {
			WorkTimezoneStampSet stampSet = workTimezoneCommonSet.get().getStampSet();
			return stampSet.getRoundingTime().getRoundingSets().stream().filter(item -> item.getSection() == superiority).findFirst().isPresent() ?
					stampSet.getRoundingTime().getRoundingSets().stream().filter(item -> item.getSection() == superiority).findFirst().get() : null;
		}
		return null;
	}

	private PrioritySetting getPrioritySetting(String companyId, String workTimeCode, StampPiorityAtr stampPiorityAtr) {
		Optional<WorkTimezoneCommonSet> workTimezoneCommonSet = GetCommonSet.workTimezoneCommonSet(requireService.createRequire(), companyId, workTimeCode);
		if (workTimezoneCommonSet.isPresent()) {
			WorkTimezoneStampSet stampSet = workTimezoneCommonSet.get().getStampSet();
			if (stampSet.getPrioritySets().stream().filter(item -> item.getStampAtr() == stampPiorityAtr)
					.findFirst() != null
					&& stampSet.getPrioritySets().stream().filter(item -> item.getStampAtr() == stampPiorityAtr)
							.findFirst().isPresent()) {
				return stampSet.getPrioritySets().stream().filter(item -> item.getStampAtr() == stampPiorityAtr)
						.findFirst().get();
			}
			return null;

		}
		return null;
	}

	// 前優先後優先を見て反映するか確認する
	private boolean confirmReflectFirstOrAfterPriority(String companyId, WorkInfoOfDailyPerformance workInfo, String attendanceClass, Stamp stamp,
			ReflectEntryGateOutput reflectEntryGateOutput, GeneralDate date, String employeeId) {
		Optional<WorkInfoOfDailyPerformance> WorkInfoOptional = workInfo != null ? Optional.of(workInfo) : this.workInforRepo.find(employeeId, date);
		if (WorkInfoOptional.isPresent()) {
			WorkInformation recordWorkInformation = WorkInfoOptional.get().getWorkInformation().getRecordInfo();
			WorkTimeCode workTimeCode = recordWorkInformation.getWorkTimeCode();

			PrioritySetting prioritySetting = this.getPrioritySetting(companyId, workTimeCode.v(),
					"入門".equals(attendanceClass) ? StampPiorityAtr.ENTERING : StampPiorityAtr.EXIT);
			MultiStampTimePiorityAtr priorityAtr = null;
			if (prioritySetting == null) {
				priorityAtr = MultiStampTimePiorityAtr.valueOf(0);
			} else {
				priorityAtr = prioritySetting.getPriorityAtr();
			}
			AttendanceTime attendanceTime = stamp.getAttendanceTime().isPresent()?
					stamp.getAttendanceTime().get():new AttendanceTime(stamp.getStampDateTime().clockHourMinute().v());
			TimeWithDayAttr timeDestination = reflectEntryGateOutput.getTimeOfDay();
			if (priorityAtr.value == 0) {
				if (timeDestination != null && (attendanceTime.v().intValue() >= timeDestination.v().intValue())) {
					return false;
				} else {
					return true;
				}

			} else {
				if (timeDestination == null || (timeDestination != null && (attendanceTime.v().intValue() > timeDestination.v().intValue()))) {
					return true;
				} else {
					return false;
				}
			}

		}
		return true;
	}

	// true is same
	private boolean confirmStampAndTempTimeSame(TimeWithDayAttr stamp, TimeWithDayAttr tempTime,
			int timeTreatTempSame) {
		if (tempTime.v() <= stamp.v() && stamp.v() <= tempTime.v() + timeTreatTempSame) {
			return true;
		}
		return false;
	}

}
