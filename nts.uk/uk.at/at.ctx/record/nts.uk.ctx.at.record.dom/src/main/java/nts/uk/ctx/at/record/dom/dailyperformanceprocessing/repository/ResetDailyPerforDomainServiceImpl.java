package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.attendanceitem.util.AttendanceItemConvertFactory;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.AttendanceLeavingGateOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.PCLogOnInfoOfDailyRepo;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.AffiliationInforState;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.StampBeforeReflection;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ClosureOfDailyPerOutPut;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ReflectStampOutput;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemIdContainer;
import nts.uk.ctx.at.shared.dom.attendance.util.enu.DailyDomainGroup;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class ResetDailyPerforDomainServiceImpl implements ResetDailyPerforDomainService {

	@Inject
	private ReflectWorkInforDomainService reflectWorkInforDomainService;

	@Inject
	private WorkInformationRepository workInformationRepository;

	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;

	@Inject
	private AffiliationInforOfDailyPerforRepository affiliationInforOfDailyPerforRepository;

	@Inject
	private ReflectShortWorkingTimeDomainService reflectShortWorkingTimeDomainService;

	@Inject
	private ReflectBreakTimeOfDailyDomainService reflectBreakTimeOfDailyDomainService;

	@Inject
	private ReflectStampDomainService reflectStampDomainService;

	@Inject
	private RegisterDailyPerformanceInfoService registerDailyPerformanceInfoService;

	@Inject
	private ErrMessageInfoRepository errMessageInfoRepository;

	@Inject
	private BreakTimeOfDailyPerformanceRepository breakTimeOfDailyPerformanceRepository;

	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;

	@Inject
	private EditStateOfDailyPerformanceRepository editStateOfDailyPerformanceRepository;

	@Inject
	private TemporaryTimeOfDailyPerformanceRepository temporaryTimeOfDailyPerformanceRepository;

	@Inject
	private OutingTimeOfDailyPerformanceRepository outingTimeOfDailyPerformanceRepository;

	@Inject
	private AttendanceLeavingGateOfDailyRepo attendanceLeavingGateOfDailyRepo;

	@Inject
	private PCLogOnInfoOfDailyRepo pcLogOnInfoOfDailyRepo;

	@Inject
	private AttendanceItemConvertFactory attendanceItemConvertFactory;
	
	@Inject
	private WorkTypeRepository workTypeRepository;

	@Override
	public void resetDailyPerformance(String companyID, String employeeID, GeneralDate processingDate,
			String empCalAndSumExecLogID, ExecutionType reCreateAttr) {
		Optional<WorkInfoOfDailyPerformance> workInfoOfDailyPerformance = this.workInformationRepository
				.find(employeeID, processingDate);

		Optional<AffiliationInforOfDailyPerfor> affiliationInforOfDailyPerfor = this.affiliationInforOfDailyPerforRepository
				.findByKey(employeeID, processingDate);

		if (workInfoOfDailyPerformance.isPresent() && affiliationInforOfDailyPerfor.isPresent()) {
			// 再設定する区分を取得(get data 一部再設定区分, execution log)
			Optional<ExecutionLog> executionLog = this.empCalAndSumExeLogRepository
					.getByExecutionContent(empCalAndSumExecLogID, 0);

			WorkInfoOfDailyPerformance workInfoOfDailyPerformanceUpdate = workInfoOfDailyPerformance.get();
			CalAttrOfDailyPerformance calAttrOfDailyPerformance = null;
			AffiliationInforState affiliationInforState = null;
			AffiliationInforOfDailyPerfor affiliationInfor = null;
			SpecificDateAttrOfDailyPerfor specificDateAttrOfDailyPerfor = null;
			ShortTimeOfDailyPerformance shortTimeOfDailyPerformance = null;
			BreakTimeOfDailyPerformance breakTimeOfDailyPerformance = null;
			ReflectStampOutput stampOutput = new ReflectStampOutput();
			List<ErrMessageInfo> errMesInfos = new ArrayList<>();
			ClosureOfDailyPerOutPut closureOfDailyPerOutPut = new ClosureOfDailyPerOutPut();
			WorkInfoOfDailyPerformance dailyPerformance = null;
			if (executionLog.isPresent()) {
				if (executionLog.get().getDailyCreationSetInfo().isPresent()) {
					if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().isPresent()) {
						// 計算区分を日別実績に反映する(Reflect 計算区分 in 日別実績)
						if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().get()
								.getCalculationClassificationResetting() == true) {

							this.editStateOfDailyPerformanceRepository.delete(employeeID, processingDate);

							calAttrOfDailyPerformance = this.reflectWorkInforDomainService.reflectCalAttOfDaiPer(
									companyID, employeeID, processingDate, affiliationInforOfDailyPerfor.get());

						}
						// 所属情報を反映する(Reflect info 所属情報)
						if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().get()
								.getMasterReconfiguration() == true) {

							this.editStateOfDailyPerformanceRepository.delete(employeeID, processingDate);

							affiliationInforState = this.reflectWorkInforDomainService
									.createAffiliationInforOfDailyPerfor(companyID, employeeID, processingDate,
											empCalAndSumExecLogID);
							if (affiliationInforState.getErrMesInfos().isEmpty()) {
								affiliationInfor = affiliationInforState.getAffiliationInforOfDailyPerfor().get();
							} else {
								for (ErrMessageInfo errMessageInfo : affiliationInforState.getErrMesInfos()) {
									errMesInfos.add(errMessageInfo);
								}
							}
						}
						// 特定日を日別実績に反映する(Reflect 日別実績) 特定日を再設定する
						if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().get()
								.getSpecificDateClassificationResetting() == true) {
							this.editStateOfDailyPerformanceRepository.delete(employeeID, processingDate);

							specificDateAttrOfDailyPerfor = reflectWorkInforDomainService.reflectSpecificDate(companyID,
									employeeID, processingDate, affiliationInforOfDailyPerfor.get().getWplID());
						}
						// 短時間勤務時間帯を反映する(reflect 短時間勤務時間帯) 育児・介護短時間を再設定する
						if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().get()
								.getResetTimeChildOrNurseCare() == true) {
							this.editStateOfDailyPerformanceRepository.delete(employeeID, processingDate);

							shortTimeOfDailyPerformance = reflectShortWorkingTimeDomainService.reflect(companyID,
									processingDate, employeeID, workInfoOfDailyPerformanceUpdate, null);
						}
						// 休業再設定(reSetting 休業)
						if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().get()
								.getClosedHolidays() == true) {
							// 休業を日別実績に反映する
							closureOfDailyPerOutPut = this.reflectWorkInforDomainService.reflectHolidayOfDailyPerfor(
									companyID, employeeID, processingDate, empCalAndSumExecLogID,
									workInfoOfDailyPerformanceUpdate);
							if (closureOfDailyPerOutPut.getErrMesInfos().isEmpty()) {
								dailyPerformance = closureOfDailyPerOutPut.getWorkInfoOfDailyPerformance();
							} else {
								for (ErrMessageInfo errMessageInfo : closureOfDailyPerOutPut.getErrMesInfos()) {
									errMesInfos.add(errMessageInfo);
								}
							}
						}
						// 就業時間帯再設定(reSetting worktime)
						if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().get()
								.getResettingWorkingHours() == true) {
							this.editStateOfDailyPerformanceRepository.delete(employeeID, processingDate);
							this.breakTimeOfDailyPerformanceRepository.deleteByBreakType(employeeID, processingDate, 0);
							// ドメインモデル「日別実績の出退勤」を取得する
							Optional<TimeLeavingOfDailyPerformance> timeLeavingOpt = this.timeLeavingOfDailyPerformanceRepository
									.findByKey(employeeID, processingDate);
							// 就業時間帯の休憩時間帯を日別実績に反映する
							breakTimeOfDailyPerformance = this.reflectBreakTimeOfDailyDomainService.reflectBreakTime(
									companyID, employeeID, processingDate, empCalAndSumExecLogID,
									timeLeavingOpt.isPresent() ? timeLeavingOpt.get() : null,
									workInfoOfDailyPerformanceUpdate);
						}
						// 打刻を取得する(get info stamp) 打刻のみ再度反映をする
						if (executionLog.get().getDailyCreationSetInfo().get().getPartResetClassification().get()
								.getReflectsTheNumberOfFingerprintChecks() == true) {
							// 日別実績のドメインモデルを退避する
							StampBeforeReflection stampBeforeReflection = new StampBeforeReflection();
							// 日別実績の出退勤
							Optional<TimeLeavingOfDailyPerformance> timeleaving = this.timeLeavingOfDailyPerformanceRepository
									.findByKey(employeeID, processingDate);
							stampBeforeReflection.setTimeLeavingOfDailyPerformance(timeleaving.orElse(null));
								
							// 日別実績の外出時間帯
							Optional<OutingTimeOfDailyPerformance> outingTime = this.outingTimeOfDailyPerformanceRepository
									.findByEmployeeIdAndDate(employeeID, processingDate);
							stampBeforeReflection.setOutingTimeOfDailyPerformance(outingTime.orElse(null));
							
							// 日別実績の臨時出退勤
							Optional<TemporaryTimeOfDailyPerformance> temporaryTime = this.temporaryTimeOfDailyPerformanceRepository
									.findByKey(employeeID, processingDate);
							stampBeforeReflection.setTemporaryTimeOfDailyPerformance(temporaryTime.orElse(null));
							
							// 日別実績の入退門
							Optional<AttendanceLeavingGateOfDaily> attendanceLeaving = this.attendanceLeavingGateOfDailyRepo
									.find(employeeID, processingDate);
							stampBeforeReflection.setAttendanceLeavingGateOfDaily(attendanceLeaving.orElse(null));
							
							// 日別実績のPCログオン情報
							Optional<PCLogOnInfoOfDaily> pCLogOn = this.pcLogOnInfoOfDailyRepo.find(employeeID,
									processingDate);
							stampBeforeReflection.setPcLogOnInfoOfDaily(pCLogOn.orElse(null));
							
							// to show clear attendance item
							List<Integer> timeleavingAttItemIds = AttendanceItemIdContainer
									.getItemIdByDailyDomains(DailyDomainGroup.ATTENDACE_LEAVE);
							List<Integer> outingTimeAttItemIds = AttendanceItemIdContainer
									.getItemIdByDailyDomains(DailyDomainGroup.OUTING_TIME);
							List<Integer> temporaryTimeAttItemIds = AttendanceItemIdContainer
									.getItemIdByDailyDomains(DailyDomainGroup.TEMPORARY_TIME);
							List<Integer> attLeavingAttItemIds = AttendanceItemIdContainer
									.getItemIdByDailyDomains(DailyDomainGroup.ATTENDANCE_LEAVE_GATE);
							List<Integer> pcLogOnAttItemIds = AttendanceItemIdContainer
									.getItemIdByDailyDomains(DailyDomainGroup.PC_LOG_INFO);
							List<Integer> attItemIds = new ArrayList<>();
//							attItemIds.addAll(AttendanceItemIdContainer
//									.getItemIdByDailyDomains(DailyDomainGroup.ATTENDACE_LEAVE));
//							attItemIds.addAll(AttendanceItemIdContainer
//									.getItemIdByDailyDomains(DailyDomainGroup.OUTING_TIME));
//							attItemIds.addAll(AttendanceItemIdContainer
//									.getItemIdByDailyDomains(DailyDomainGroup.TEMPORARY_TIME));
//							attItemIds.addAll(AttendanceItemIdContainer
//									.getItemIdByDailyDomains(DailyDomainGroup.ATTENDANCE_LEAVE_GATE));
//							attItemIds.addAll(AttendanceItemIdContainer
//									.getItemIdByDailyDomains(DailyDomainGroup.PC_LOG_INFO));
							attItemIds.addAll(timeleavingAttItemIds);
							attItemIds.addAll(outingTimeAttItemIds);
							attItemIds.addAll(temporaryTimeAttItemIds);
							attItemIds.addAll(attLeavingAttItemIds);
							attItemIds.addAll(pcLogOnAttItemIds);

							// 日別実績のドメインモデルを削除する
							// 日別実績の出退勤
							this.timeLeavingOfDailyPerformanceRepository.delete(employeeID, processingDate);
							// 日別実績の外出時間帯
							this.outingTimeOfDailyPerformanceRepository.delete(employeeID, processingDate);
							// 日別実績の臨時出退勤
							this.temporaryTimeOfDailyPerformanceRepository.delete(employeeID, processingDate);
							// 日別実績の入退門
							this.attendanceLeavingGateOfDailyRepo.removeByKey(employeeID, processingDate);
							// 日別実績のPCログオン情報
							this.pcLogOnInfoOfDailyRepo.removeByKey(employeeID, processingDate);

							// 打刻を取得して反映する
							stampOutput = this.reflectStampDomainService.reflectStampInfo(companyID, employeeID,
									processingDate, workInfoOfDailyPerformanceUpdate, null, empCalAndSumExecLogID,
									reCreateAttr, Optional.of(calAttrOfDailyPerformance), affiliationInforOfDailyPerfor,
									Optional.empty());

							DailyRecordToAttendanceItemConverter converter = attendanceItemConvertFactory
									.createDailyConverter();
							
							DailyRecordToAttendanceItemConverter converter2 = attendanceItemConvertFactory
									.createDailyConverter();

							// ドメインモデル「日別実績の編集状態」を取得する
							List<EditStateOfDailyPerformance> attItemIdStateOfTimeLeaving = this.editStateOfDailyPerformanceRepository
									.findByItems(employeeID, processingDate, attItemIds);
							
							// set data in converter, for update value of attendance item id 
							// set data of stampBeforeReflection in converter
							converter.withTimeLeaving(stampBeforeReflection.getTimeLeavingOfDailyPerformance()).employeeId(employeeID).workingDate(processingDate);
							converter.withOutingTime(stampBeforeReflection.getOutingTimeOfDailyPerformance());
							converter.withTemporaryTime(stampBeforeReflection.getTemporaryTimeOfDailyPerformance());
							converter.withAttendanceLeavingGate(stampBeforeReflection.getAttendanceLeavingGateOfDaily());
							converter.withPCLogInfo(stampBeforeReflection.getPcLogOnInfoOfDaily());
							
							// set data of stampOutPut in converter
							converter2.withTimeLeaving(stampOutput.getTimeLeavingOfDailyPerformance()).employeeId(employeeID).workingDate(processingDate);
							converter2.withOutingTime(stampOutput.getOutingTimeOfDailyPerformance());
							converter2.withTemporaryTime(stampOutput.getTemporaryTimeOfDailyPerformance());
							converter2.withAttendanceLeavingGate(stampOutput.getAttendanceLeavingGateOfDaily());
							converter2.withPCLogInfo(stampOutput.getPcLogOnInfoOfDaily());							
							
							List<ItemValue> valueList = new ArrayList<>();

							for (Integer timeLeavingId : attItemIds) {
								// state of attItemId
								Optional<EditStateOfDailyPerformance> editState = attItemIdStateOfTimeLeaving.stream()
										.filter(item -> item.getAttendanceItemId() == timeLeavingId).findFirst();
								if (editState.isPresent()) {
									// get data from DB
									valueList.add(converter.convert(timeLeavingId).get());
								}
							}
//							valueList.removeIf(item -> item.value() == null);
							// update value of Attendance Item Id
							converter2.merge(valueList);
							// set data stampOutPut
							stampOutput.setTimeLeavingOfDailyPerformance(converter2.toDomain().getAttendanceLeave().get());
							stampOutput.setOutingTimeOfDailyPerformance(converter2.toDomain().getOutingTime().get());
							stampOutput.setTemporaryTimeOfDailyPerformance(converter2.toDomain().getTempTime().get());
							stampOutput.setAttendanceLeavingGateOfDaily(converter2.toDomain().getAttendanceLeavingGate().get());
							stampOutput.setPcLogOnInfoOfDaily(converter2.toDomain().getPcLogOnInfo().get());
						}
						stampOutput.setShortTimeOfDailyPerformance(shortTimeOfDailyPerformance);
					}
				}
			}

			if (errMesInfos.isEmpty()) {
				this.registerDailyPerformanceInfoService.registerDailyPerformanceInfo(companyID, employeeID,
						processingDate, stampOutput, affiliationInfor, dailyPerformance, specificDateAttrOfDailyPerfor,
						calAttrOfDailyPerformance, null, breakTimeOfDailyPerformance);
			} else {
				errMesInfos.forEach(action -> {
					this.errMessageInfoRepository.add(action);
				});
			}
		}
	}

}
