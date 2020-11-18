package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SWkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport_Old;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculationImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OverTimeInstruct;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OvertimeInstructRepository;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.WorkingTimesheetCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.CPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.PSBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.WPBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.WTBonusPaySettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.BonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.CompanyBonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.PersonalBonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.WorkingTimesheetBonusPaySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.setting.WorkplaceBonusPaySetting;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class OvertimePreProcessImpl implements IOvertimePreProcess {

	final static String DATE_FORMAT = "yyyy/MM/dd";
	final static String ZEZO_TIME = "00:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
	final static String SPACE = " ";
	@Inject
	private OvertimeInstructRepository overtimeInstructRepository;
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	@Inject
	private WTBonusPaySettingRepository wTBonusPaySettingRepository;
	@Inject
	private PSBonusPaySettingRepository pSBonusPaySettingRepository;
	@Inject
	private WPBonusPaySettingRepository wPBonusPaySettingRepository;
	@Inject
	private CPBonusPaySettingRepository cPBonusPaySettingRepository;
	@Inject
	private BPSettingRepository bPSettingRepository;
	@Inject
	private OvertimeWorkFrameRepository overtimeFrameRepository;
	@Inject
	private WorkdayoffFrameRepository breaktimeFrameRep;
	@Inject
	private PredetemineTimeSettingRepository workTimeSetRepository;

	@Inject
	private RecordWorkInfoAdapter recordWorkInfoAdapter;
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private DailyAttendanceTimeCaculation dailyAttendanceTimeCaculation;
	
	@Inject
	private IOvertimePreProcess iOvertimePreProcess;
	
	@Override
	public OvertimeInstructInfomation getOvertimeInstruct(AppCommonSettingOutput appCommonSettingOutput, String appDate,
			String employeeID) {
		OvertimeInstructInfomation overtimeInstructInformation = new OvertimeInstructInfomation();
//		if (appCommonSettingOutput != null) {
//			if(appCommonSettingOutput.approvalFunctionSetting != null){
//				int useAtr = appCommonSettingOutput.approvalFunctionSetting.getInstructionUseSetting().getInstructionUseDivision().value;
//				if (useAtr == UseAtr.USE.value) {
//					if (appDate != null) {
//						overtimeInstructInformation.setDisplayOvertimeInstructInforFlg(true);
//						OverTimeInstruct overtimeInstruct = overtimeInstructRepository
//								.getOvertimeInstruct(GeneralDate.fromString(appDate, DATE_FORMAT), employeeID);
//						if (overtimeInstruct != null) {
//							TimeWithDayAttr startTime = new TimeWithDayAttr(
//									overtimeInstruct.getStartClock() == null ? -1 : overtimeInstruct.getStartClock().v());
//							TimeWithDayAttr endTime = new TimeWithDayAttr(
//									overtimeInstruct.getEndClock() == null ? -1 : overtimeInstruct.getEndClock().v());
//							overtimeInstructInformation
//									.setOvertimeInstructInfomation(overtimeInstruct.getInstructDate().toString() + " "
//											+ startTime.getDayDivision().description + " "
//											+ convert(overtimeInstruct.getStartClock().v()) + "~"
//											+ endTime.getDayDivision().description + " "
//											+ convert(overtimeInstruct.getEndClock().v()) + " "
//											+ employeeAdapter.getEmployeeName(overtimeInstruct.getTargetPerson()) + " ("
//											+ employeeAdapter.getEmployeeName(overtimeInstruct.getInstructor()) + ")");
//						} else {
//							overtimeInstructInformation.setOvertimeInstructInfomation(
//									GeneralDate.fromString(appDate, DATE_FORMAT) + "の残業指示はありません。");
//						}
//					}
//				} else {
//					overtimeInstructInformation.setDisplayOvertimeInstructInforFlg(false);
//				}
//			}
//		}
		return overtimeInstructInformation;
	}

	@Override
	public List<OvertimeWorkFrame> getOvertimeHours(int overtimeAtr, String companyID) {
		List<OvertimeWorkFrame> overtimeFrames = new ArrayList<>();
		List<OvertimeWorkFrame> overtimeFrameRegulars = this.overtimeFrameRepository.getOvertimeWorkFrameByFrameByCom(companyID, NotUseAtr.USE.value);
		overtimeFrames = overtimeFrameRegulars.stream().filter(x -> {
			return (x.getOvertimeWorkFrNo().v().intValue() == 2) || (x.getOvertimeWorkFrNo().v().intValue() == 3);
		}).sorted(Comparator.comparing(OvertimeWorkFrame::getOvertimeWorkFrNo)).collect(Collectors.toList());
		return overtimeFrames;
	}

	@Override
	public List<WorkdayoffFrame> getBreaktimeFrame(String companyID) {

		return this.breaktimeFrameRep.getAllWorkdayoffFrame(companyID).stream().filter(x -> x.getUseClassification().value == NotUseAtr.USE.value).collect(Collectors.toList());
	}

	@Override
	public AppOvertimeReference getResultContentActual(int prePostAtr, String workType, String siftCode, String companyID, String employeeID, String appDate) {
		AppOvertimeReference result = null;
		// Input．事前事後区分をチェック
		if(PrePostAtr.PREDICT.value == prePostAtr) {
			return result;
		}
		// 日付入力したかチェックする
		if(appDate == null){
			return result;
		}
		// Imported(申請承認)「勤務実績」を取得する
		RecordWorkInfoImport_Old recordWorkInfoImport = recordWorkInfoAdapter.getRecordWorkInfo(employeeID, GeneralDate.fromString(appDate, DATE_FORMAT));
		if(Strings.isBlank(recordWorkInfoImport.getWorkTypeCode())){
			return result;
		}
		// ドメインモデル「就業時間帯の設定」を取得する
		PredetemineTimeSetting workTimeSet = null;
		if (workTimeSetRepository.findByWorkTimeCode(companyID, siftCode).isPresent()) {
			workTimeSet = workTimeSetRepository.findByWorkTimeCode(companyID, siftCode).get();
		}
		
		if(checkTimeDay(appDate, workTimeSet)){
			return this.getResultCurrentDay(employeeID, GeneralDate.fromString(appDate, DATE_FORMAT), workType, siftCode, recordWorkInfoImport);
		} else {
			return this.getResultOtherDay(employeeID, GeneralDate.fromString(appDate, DATE_FORMAT), workType, siftCode, recordWorkInfoImport);
		}
	}
	
	@Override
	public AppOvertimeReference getResultCurrentDay(String employeeID, GeneralDate date, String workType, String workTime, RecordWorkInfoImport_Old recordWorkInfoImport) {
		String companyID = AppContexts.user().companyId();
		AppOvertimeReference result = new AppOvertimeReference();
		result.setAppDateRefer(date.toString(DATE_FORMAT));
		result.setWorkTypeRefer(new WorkTypeOvertime(workType, workTypeRepository.findByPK(companyID, workType).map(x -> x.getName().toString()).orElse(null)));
		result.setSiftTypeRefer(new SiftType(workTime, workTimeRepository.findByCode(companyID, workTime).map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v()).orElse(null)));
		// 打刻漏れチェック
		if(recordWorkInfoImport.getAttendanceStampTimeFirst()==null){
			// Input．実績内容．出勤打刻なし
			// set 00:00
			return result;
		}
		List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(0, companyID);
		List<CaculationTime> overTimeInputsRefer = overtimeFrames.stream()
				.map(x -> CaculationTime.builder().frameName(x.getOvertimeWorkFrName().toString()).build()).collect(Collectors.toList());
		
		if(recordWorkInfoImport.getLeaveStampTimeFirst()==null){
			// Input．実績内容．出勤打刻ある　AND　Input．実績内容．退勤打刻なし
			// request 23
			DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport = new DailyAttendanceTimeCaculationImport();
//					dailyAttendanceTimeCaculation.getCalculation(employeeID, date, workType, workTime, 
//					recordWorkInfoImport.getAttendanceStampTimeFirst(), recordWorkInfoImport.getLeaveStampTimeFirst(), Collections.emptyList(), Collections.emptyList());
			overTimeInputsRefer.stream().forEach(x -> {
				x.setApplicationTime(dailyAttendanceTimeCaculationImport.getOverTime().entrySet()
				.stream().filter(y -> y.getKey()==x.getFrameNo()).findAny()
				.map(z -> z.getValue().getCalTime()).orElse(null));
			});
			result.setOverTimeInputsRefer(overTimeInputsRefer);
			return result;
		}
		// 就業時間帯チェック
		if(recordWorkInfoImport.getWorkTimeCode().equals(workTime)){
			// Input．実績内容.就業時間帯コード=Input．就業時間帯コード
			result.setWorkClockFromTo1Refer(convertWorkClockFromTo(recordWorkInfoImport.getAttendanceStampTimeFirst(), recordWorkInfoImport.getLeaveStampTimeFirst()));
			/*overTimeInputsRefer.stream().forEach(x -> {
				x.setApplicationTime(recordWorkInfoImport.getOvertimeCaculation().stream()
						.filter(y -> y.getFrameNo()==x.getFrameNo()).findAny()
						.map(z -> z.getResultCaculation()).orElse(null));
			});*/
			result.setOverTimeInputsRefer(overTimeInputsRefer);
			return result;
		}
		// request 23
		DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport = new DailyAttendanceTimeCaculationImport();
//				dailyAttendanceTimeCaculation.getCalculation(employeeID, date, workType, workTime, 
//				recordWorkInfoImport.getAttendanceStampTimeFirst(), recordWorkInfoImport.getLeaveStampTimeFirst(), Collections.emptyList(), Collections.emptyList());
		result.setWorkClockFromTo1Refer(convertWorkClockFromTo(recordWorkInfoImport.getAttendanceStampTimeFirst(), recordWorkInfoImport.getLeaveStampTimeFirst()));
		overTimeInputsRefer.stream().forEach(x -> {
			x.setApplicationTime(dailyAttendanceTimeCaculationImport.getOverTime().entrySet()
			.stream().filter(y -> y.getKey()==x.getFrameNo()).findAny()
			.map(z -> z.getValue().getCalTime()).orElse(null));
		});
		result.setOverTimeInputsRefer(overTimeInputsRefer);
		return result;
	}

	@Override
	public AppOvertimeReference getResultOtherDay(String employeeID, GeneralDate date, String workType, String workTime, RecordWorkInfoImport_Old recordWorkInfoImport) {
		String companyID = AppContexts.user().companyId();
		AppOvertimeReference result = new AppOvertimeReference();
		result.setAppDateRefer(date.toString(DATE_FORMAT));
		result.setWorkTypeRefer(new WorkTypeOvertime(workType, workTypeRepository.findByPK(companyID, workType).map(x -> x.getName().toString()).orElse(null)));
		result.setSiftTypeRefer(new SiftType(workTime, workTimeRepository.findByCode(companyID, workTime).map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v()).orElse(null)));
		// 打刻漏れチェック
		if(recordWorkInfoImport.getAttendanceStampTimeFirst()==null || recordWorkInfoImport.getLeaveStampTimeFirst()==null){
			// Input．実績内容．出勤　OR　Input．実績内容．退勤打刻なし
			// set 00:00
			return result;
		}
		List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(0, companyID);
		List<CaculationTime> overTimeInputsRefer = overtimeFrames.stream()
				.map(x -> CaculationTime.builder().frameName(x.getOvertimeWorkFrName().toString()).build()).collect(Collectors.toList());
		// 就業時間帯チェック
		if(recordWorkInfoImport.getWorkTimeCode().equals(workTime)){
			// Input．実績内容.就業時間帯コード=Input．就業時間帯コード
			result.setWorkClockFromTo1Refer(convertWorkClockFromTo(recordWorkInfoImport.getAttendanceStampTimeFirst(), recordWorkInfoImport.getLeaveStampTimeFirst()));
			/*overTimeInputsRefer.stream().forEach(x -> {
				x.setApplicationTime(recordWorkInfoImport.getOvertimeCaculation().stream()
						.filter(y -> y.getFrameNo()==x.getFrameNo()).findAny()
						.map(z -> z.getResultCaculation()).orElse(null));
			});*/
			result.setOverTimeInputsRefer(overTimeInputsRefer);
			return result;
		}
		// request 23
		DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport = new DailyAttendanceTimeCaculationImport();
//				dailyAttendanceTimeCaculation.getCalculation(employeeID, date, workType, workTime, 
//				recordWorkInfoImport.getAttendanceStampTimeFirst(), recordWorkInfoImport.getLeaveStampTimeFirst(), Collections.emptyList(), Collections.emptyList());
		result.setWorkClockFromTo1Refer(convertWorkClockFromTo(recordWorkInfoImport.getAttendanceStampTimeFirst(), recordWorkInfoImport.getLeaveStampTimeFirst()));
		overTimeInputsRefer.stream().forEach(x -> {
			x.setApplicationTime(dailyAttendanceTimeCaculationImport.getOverTime().entrySet()
			.stream().filter(y -> y.getKey()==x.getFrameNo()).findAny()
			.map(z -> z.getValue().getCalTime()).orElse(null));
		});
		result.setOverTimeInputsRefer(overTimeInputsRefer);
		return result;
	}
	
	private List<OvertimeRefer> convertOTReferFromReal(RecordWorkInfoImport_Old recordWorkInfoImport){
		List<OvertimeRefer> overtimeReferLst = new ArrayList<>();
		/*overtimeReferLst.addAll(recordWorkInfoImport.getOvertimeCaculation().stream().map(x -> new OvertimeRefer(x.getFrameNo(), x.getResultCaculation())).collect(Collectors.toList()));
		overtimeReferLst.add(new OvertimeRefer(11, recordWorkInfoImport.getShiftNightCaculation()));
		overtimeReferLst.add(new OvertimeRefer(12, recordWorkInfoImport.getFlexCaculation()));*/
		return overtimeReferLst;
	}
	
	private List<OvertimeRefer> convertOTReferFromCalc(DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport){
		List<OvertimeRefer> overtimeReferLst = new ArrayList<>();
		overtimeReferLst.addAll(dailyAttendanceTimeCaculationImport.getOverTime().entrySet().stream().map(x -> new OvertimeRefer(x.getKey(), x.getValue().getCalTime())).collect(Collectors.toList()));
		overtimeReferLst.add(new OvertimeRefer(11, dailyAttendanceTimeCaculationImport.getMidNightTime().getCalTime()));
		overtimeReferLst.add(new OvertimeRefer(12, dailyAttendanceTimeCaculationImport.getFlexTime().getCalTime()));
		return overtimeReferLst;
	}
	
	private String convertWorkClockFromTo(Integer startTime, Integer endTime){
		String WorkClockFromTo = "";
		if(startTime == null && endTime != null){
			TimeWithDayAttr endTimeWithDay = new TimeWithDayAttr(endTime);
			WorkClockFromTo = "　"
					+  "　~　"
					+ endTimeWithDay.getDayDivision().description
					+ convertForWorkClock(endTime);
		}
		if(startTime != null && endTime != null){
			TimeWithDayAttr startTimeWithDay = new TimeWithDayAttr(startTime);
			TimeWithDayAttr endTimeWithDay = new TimeWithDayAttr(endTime);
		 WorkClockFromTo = startTimeWithDay.getDayDivision().description 
							+ convertForWorkClock(startTime) + "　~　"
							+ endTimeWithDay.getDayDivision().description
							+ convertForWorkClock(endTime);
		}
		return WorkClockFromTo;
	}
	private String convert(int minute) {
		String hourminute = Strings.EMPTY;
		if (minute == -1) {
			return null;
		} else if (minute == 0) {
			hourminute = ZEZO_TIME;
		} else {
			int hour = Math.abs(minute) / 60;
			int hourInDay = hour % 24;
			int minutes =  Math.abs(minute) % 60;
			hourminute = (hourInDay < 10 ? ("0" + hourInDay) : hourInDay) + ":" + (minutes < 10 ? ("0" + minutes) : minutes);
		}
		return hourminute;
	}
	private String convertForWorkClock(int minute) {
		String hourminute = Strings.EMPTY;
		if (minute == -1) {
			return null;
		} else if (minute == 0) {
			hourminute = ZEZO_TIME;
		} else {
			int hour = Math.abs(minute) / 60;
			int hourInDay = hour % 24;
			int minutes =  Math.abs(minute) % 60;
			hourminute = hourInDay + ":" + (minutes < 10 ? ("0" + minutes) : minutes);
		}
		return hourminute;
	}

	/**
	 * @param appDate
	 * @param workTimeSet
	 * @return
	 */
	public boolean checkTimeDay(String appDate, PredetemineTimeSetting workTimeSet) {
		GeneralDateTime appDateGeneralstart = GeneralDateTime.fromString(appDate + SPACE + ZEZO_TIME, DATE_TIME_FORMAT);
		GeneralDateTime appDateGeneralEnd = GeneralDateTime.fromString(appDate + SPACE + ZEZO_TIME, DATE_TIME_FORMAT);

		if (null != workTimeSet && workTimeSet.getPrescribedTimezoneSetting() != null) {
			if (workTimeSet.getPrescribedTimezoneSetting().getLstTimezone() != null) {
				appDateGeneralstart = appDateGeneralstart.addHours(workTimeSet.getPrescribedTimezoneSetting().getLstTimezone().get(0).getStart().hour());
				appDateGeneralstart = appDateGeneralstart.addMinutes(workTimeSet.getPrescribedTimezoneSetting().getLstTimezone().get(0).getStart().minute());
				appDateGeneralEnd = appDateGeneralEnd.addHours(workTimeSet.getPrescribedTimezoneSetting().getLstTimezone().get(0).getEnd().hour());
				appDateGeneralEnd = appDateGeneralEnd.addMinutes(workTimeSet.getPrescribedTimezoneSetting().getLstTimezone().get(0).getEnd().minute());
				GeneralDateTime appDateGeneralSystem = GeneralDateTime.fromString(GeneralDateTime.now().toString(DATE_TIME_FORMAT), DATE_TIME_FORMAT);
				if(appDateGeneralSystem.after(appDateGeneralstart) && appDateGeneralSystem.before(appDateGeneralEnd)){
					return true;
				}
			}

		}
		return false;
	}

	@Override
	public Optional<BonusPaySetting> getBonusPaySetting(String employeeID, String siftCode, String companyID,
			SWkpHistImport sWkpHistImport) {
		Optional<BonusPaySetting> bonusPaySetting = Optional.empty();
		Optional<WorkingTimesheetBonusPaySetting> workingTimesheetBonusPaySetting = Optional.empty();

		workingTimesheetBonusPaySetting = this.wTBonusPaySettingRepository.getWTBPSetting(companyID,
				new WorkingTimesheetCode(siftCode));

		if (!workingTimesheetBonusPaySetting.isPresent()) {
			Optional<PersonalBonusPaySetting> personalBonusPaySetting = this.pSBonusPaySettingRepository
					.getPersonalBonusPaySetting(employeeID);

			if (!personalBonusPaySetting.isPresent()) {
				Optional<WorkplaceBonusPaySetting> workplaceBonusPaySetting = this.wPBonusPaySettingRepository
						.getWPBPSetting(companyID, new WorkplaceId(sWkpHistImport.getWorkplaceId()));
				if (!workplaceBonusPaySetting.isPresent()) {
					Optional<CompanyBonusPaySetting> companyBonusPaySetting = this.cPBonusPaySettingRepository
							.getSetting(companyID);
					if (!companyBonusPaySetting.isPresent()) {
						return bonusPaySetting;
					} else {
						bonusPaySetting = bPSettingRepository.getBonusPaySetting(companyID,
								companyBonusPaySetting.get().getBonusPaySettingCode());
					}
				} else {
					bonusPaySetting = bPSettingRepository.getBonusPaySetting(companyID,
							workplaceBonusPaySetting.get().getBonusPaySettingCode());
				}
			} else {
				bonusPaySetting = bPSettingRepository.getBonusPaySetting(companyID,
						personalBonusPaySetting.get().getBonusPaySettingCode());
			}
		} else {
			bonusPaySetting = bPSettingRepository.getBonusPaySetting(companyID,
					workingTimesheetBonusPaySetting.get().getBonusPaySettingCode());
		}
		return null;
	}

	@Override
	public boolean displayBreaktime() {
		// TODO 
		
		return false;
	}

}
