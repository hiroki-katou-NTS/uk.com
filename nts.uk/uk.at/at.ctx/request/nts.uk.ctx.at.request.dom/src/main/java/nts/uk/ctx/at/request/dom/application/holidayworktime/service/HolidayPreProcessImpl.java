package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.InstructionCategory;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.TimeWithCalculationImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstruct;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstructRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInputRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHolidayWorkPreAndReferDto;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkInstruction;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.IOvertimePreProcess;
import nts.uk.ctx.at.request.dom.application.overtime.service.SiftType;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeOvertime;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.time.TimeWithDayAttr;
@Stateless
public class HolidayPreProcessImpl implements HolidayPreProcess {
	final static String DATE_FORMAT = "yyyy/MM/dd";
	final static String ZEZO_TIME = "00:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
	final static String SPACE = " ";
	final static String MESSAGE = "の休出指示はありません。";
	final static String ZEZO = "0";
	final static String COLON = ":";
	@Inject
	private HolidayInstructRepository holidayInstructRepository;
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	@Inject
	private ApplicationRepository_New applicationRepository;
	@Inject
	private AppHolidayWorkRepository appHolidayWorkRepository;
	@Inject
	private HolidayWorkInputRepository holidayWorkInputRepository;
	@Inject
	private RecordWorkInfoAdapter recordWorkInfoAdapter;
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	@Inject
	private WorkTypeRepository workTypeRepository;
	@Inject
	private WorkdayoffFrameRepository workdayoffFrameRepository;
	@Inject
	private PredetemineTimeSettingRepository workTimeSetRepository;
	@Inject
	private IOvertimePreProcess iOvertimePreProcess;

	@Override
	public HolidayWorkInstruction getHolidayInstructionInformation(AppCommonSettingOutput appCommonSettingOutput,
			String appDate, String employeeID) {
		HolidayWorkInstruction holidayInstructInformation = new HolidayWorkInstruction();
		if (appCommonSettingOutput != null) {
			if(appCommonSettingOutput.approvalFunctionSetting != null){
				int useAtr = appCommonSettingOutput.approvalFunctionSetting.getInstructionUseSetting().getInstructionUseDivision().value;
				if (useAtr == UseAtr.USE.value && appCommonSettingOutput.approvalFunctionSetting.getInstructionUseSetting().getInstructionAtr().value == InstructionCategory.HOLIDAYWORK.value) {
					if (appDate != null) {
						holidayInstructInformation.setDisplayHolidayWorkInstructInforFlg(true);
						HolidayInstruct overtimeInstruct = holidayInstructRepository
								.getHolidayWorkInstruct(GeneralDate.fromString(appDate, DATE_FORMAT), employeeID);
						if (overtimeInstruct != null) {
							TimeWithDayAttr startTime = new TimeWithDayAttr(
									overtimeInstruct.getStartClock() == null ? -1 : overtimeInstruct.getStartClock().v());
							TimeWithDayAttr endTime = new TimeWithDayAttr(
									overtimeInstruct.getEndClock() == null ? -1 : overtimeInstruct.getEndClock().v());
							holidayInstructInformation
									.setHolidayWorkInstructInfomation(overtimeInstruct.getInstructDate().toString() + " "
											+ startTime.getDayDivision().description + " "
											+ convert(overtimeInstruct.getStartClock().v()) + "~"
											+ endTime.getDayDivision().description + " "
											+ convert(overtimeInstruct.getEndClock().v()) + " "
											+ employeeAdapter.getEmployeeName(overtimeInstruct.getTargetPerson()) + " ("
											+ employeeAdapter.getEmployeeName(overtimeInstruct.getInstructor()) + ")");
						} else {
							holidayInstructInformation.setHolidayWorkInstructInfomation(
									GeneralDate.fromString(appDate, DATE_FORMAT) + MESSAGE);
						}
					}
				} else {
					holidayInstructInformation.setDisplayHolidayWorkInstructInforFlg(false);
				}
			}
		}
		
		return holidayInstructInformation;
	}
	private String convert(int minute) {
		String hourminute = Strings.EMPTY;
		if (minute == -1) {
			return null;
		} else if (minute == 0) {
			hourminute = ZEZO_TIME;
		} else {
			int hour = minute / 60;
			int hourInDay = hour % 24;
			int minutes = minute % 60;
			hourminute = (hourInDay < 10 ? (ZEZO + hourInDay) : hourInDay) + COLON + (minutes < 10 ? (ZEZO + minutes) : minutes);
		}
		return hourminute;
	}
	@Override
	public AppHolidayWorkPreAndReferDto getPreApplicationHoliday(String companyID, String employeeId,
			Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet, String appDate, int prePostAtr) {
		AppHolidayWorkPreAndReferDto result = new AppHolidayWorkPreAndReferDto();
			if(overtimeRestAppCommonSet.isPresent() && overtimeRestAppCommonSet.get().getPreDisplayAtr().value == UseAtr.USE.value){
				List<Application_New> application = this.applicationRepository.getApp(employeeId,
						appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT), PrePostAtr.PREDICT.value,
						ApplicationType.BREAK_TIME_APPLICATION.value);
				if(!CollectionUtil.isEmpty(application)){
					result.setAppDate(application.get(0).getAppDate());
					Optional<AppHolidayWork> appHolidayWork = this.appHolidayWorkRepository
							.getAppHolidayWork(application.get(0).getCompanyID(), application.get(0).getAppID());
					if (appHolidayWork.isPresent()) {
						WorkTypeOvertime workTypeHolidayWork = new WorkTypeOvertime();
						if(appHolidayWork.get().getWorkTypeCode() != null){
							workTypeHolidayWork.setWorkTypeCode(appHolidayWork.get().getWorkTypeCode().v());
						}
						if (workTypeHolidayWork.getWorkTypeCode() != null) {
							
							Optional<WorkType> workType = workTypeRepository.findByPK(companyID,
									workTypeHolidayWork.getWorkTypeCode());
							if (workType.isPresent()) {
								workTypeHolidayWork.setWorkTypeName(workType.get().getName().toString());
							}
							result.setWorkType(workTypeHolidayWork);
						}
						SiftType workTime = new SiftType();
						workTime.setSiftCode(appHolidayWork.get().getWorkTimeCode().toString());
						
						if (workTime.getSiftCode() != null) {
							Optional<WorkTimeSetting> workTimeSetting = workTimeRepository.findByCode(companyID,
									workTime.getSiftCode());
							if (workTimeSetting.isPresent()) {
								workTime.setSiftName(workTimeSetting.get().getWorkTimeDisplayName().getWorkTimeName().toString());
							}
							result.setWorkTime(workTime);
						}
						result.setWorkClockStart1(appHolidayWork.get().getWorkClock1().getStartTime() == null ? null : appHolidayWork.get().getWorkClock1().getStartTime().v());
						result.setWorkClockStart2(appHolidayWork.get().getWorkClock2().getStartTime() ==  null ? null : appHolidayWork.get().getWorkClock2().getStartTime().v());
						result.setWorkClockEnd1(appHolidayWork.get().getWorkClock1().getEndTime() == null ? null : appHolidayWork.get().getWorkClock1().getEndTime().v());
						result.setWorkClockEnd2(appHolidayWork.get().getWorkClock2().getEndTime() == null ? null : appHolidayWork.get().getWorkClock2().getEndTime().v());
						
						List<HolidayWorkInput> holidayWorkInputs = holidayWorkInputRepository.getHolidayWorkInputByAttendanceType(appHolidayWork.get().getCompanyID(), appHolidayWork.get().getAppID(),
								AttendanceType.BREAKTIME.value);
						if(!CollectionUtil.isEmpty(holidayWorkInputs)){
							List<CaculationTime> caculationTimes = new ArrayList<>();
							List<Integer> frameNos = new ArrayList<>();
							for(HolidayWorkInput holidayWorkInput : holidayWorkInputs){
								CaculationTime caculation = new CaculationTime();
								caculation.setAttendanceID(holidayWorkInput.getAttendanceType().value);
								caculation.setFrameNo(holidayWorkInput.getFrameNo());
								caculation.setApplicationTime(holidayWorkInput.getApplicationTime().v());
								caculationTimes.add(caculation);
								frameNos.add(holidayWorkInput.getFrameNo());
							}
							List<WorkdayoffFrame> workDayoffFrames = this.workdayoffFrameRepository.getWorkdayoffFrameBy(companyID,frameNos);
							for (CaculationTime caculation : caculationTimes) {
								for (WorkdayoffFrame workdayoffFrame : workDayoffFrames) {
									if (caculation.getFrameNo() == workdayoffFrame.getWorkdayoffFrNo().v().intValueExact()) {
										caculation.setFrameName(workdayoffFrame.getWorkdayoffFrName().toString());
										continue;
									}
								}
							}
							result.setHolidayWorkInputs(caculationTimes);;
						}
						
						return result;
				}
			}
			
		}
		return result;
	}
	@Override
	public AppHolidayWorkPreAndReferDto getResultContentActual(int prePostAtr, String siftCode, String companyID, String employeeID,
			String appDate, ApprovalFunctionSetting approvalFunctionSetting, List<CaculationTime> breakTimes) {
		AppHolidayWorkPreAndReferDto result = new AppHolidayWorkPreAndReferDto();
		// 事前事後区分チェック, 申請日入力チェック
		if(prePostAtr == PrePostAtr.PREDICT.value && appDate == null){
			return result;
		}
		//Imported(申請承認)「勤務実績」を取得する
		RecordWorkInfoImport recordWorkInfoImport = recordWorkInfoAdapter.getRecordWorkInfo(employeeID,GeneralDate.fromString(appDate, DATE_FORMAT));
		if (!StringUtil.isNullOrEmpty(recordWorkInfoImport.getWorkTypeCode(), false)) {
			WorkTypeOvertime workTypeOvertime = new WorkTypeOvertime();
			workTypeOvertime.setWorkTypeCode(recordWorkInfoImport.getWorkTypeCode().toString());
			Optional<WorkType> workType = workTypeRepository.findByPK(companyID,
					recordWorkInfoImport.getWorkTypeCode().toString());
			if (workType.isPresent()) {
				workTypeOvertime.setWorkTypeName(workType.get().getName().toString());
			}
			result.setWorkType(workTypeOvertime);
		}
		if (!StringUtil.isNullOrEmpty(recordWorkInfoImport.getWorkTimeCode(), false)) {
			SiftType siftType = new SiftType();

			siftType.setSiftCode(recordWorkInfoImport.getWorkTimeCode());
			Optional<WorkTimeSetting> workTime = workTimeRepository.findByCode(companyID,
					recordWorkInfoImport.getWorkTimeCode().toString());
			if (workTime.isPresent()) {
				siftType.setSiftName(workTime.get().getWorkTimeDisplayName().getWorkTimeName().toString());
			}
			result.setWorkTime(siftType);
		}
		result.setWorkClockStart1(recordWorkInfoImport.getAttendanceStampTimeFirst());
		result.setWorkClockEnd1(recordWorkInfoImport.getLeaveStampTimeFirst());
		result.setWorkClockStart2(recordWorkInfoImport.getAttendanceStampTimeSecond());
		result.setWorkClockEnd2(recordWorkInfoImport.getLeaveStampTimeSecond());
		result.setAppDate(GeneralDate.fromString(appDate, DATE_FORMAT));
		//申請日の判断
		Optional<PredetemineTimeSetting> workTimeSetOtp = workTimeSetRepository.findByWorkTimeCode(companyID, siftCode);
		if (workTimeSetOtp.isPresent()) {
			PredetemineTimeSetting workTimeSet = workTimeSetOtp.get();
			
			if(iOvertimePreProcess.checkTimeDay(appDate,workTimeSet)){
				//当日の場合, 03-02-3_当日の場合
				
			}else{
				// 当日以外の場合,03-02-2_当日以外の場合
			}
		}
		return result;
	}
	@Override
	// TODO
	//01-10_0時跨ぎチェック
	public CaculationTime getOverTimeHourCal(Map<Integer, TimeWithCalculationImport> holidayWorkCal) {
		for(Map.Entry<Integer, TimeWithCalculationImport> entry : holidayWorkCal.entrySet()){
			
			if(entry.getValue().getCalTime() == null || entry.getValue().getCalTime() == 0){
				return null;
			}
		}
		return null;
	}
}
