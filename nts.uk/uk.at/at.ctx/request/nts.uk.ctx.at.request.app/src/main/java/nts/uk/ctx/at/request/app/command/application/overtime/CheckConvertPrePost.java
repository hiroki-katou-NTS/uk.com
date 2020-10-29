package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

/*import org.apache.logging.log4j.util.Strings;*/

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.DivergenceReasonDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OverTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeInputDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeSettingData;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeSettingDataDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.PreAppOvertimeDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatus;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.CommonOvertimeHoliday;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeColorCheck;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorCheck;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime_Old;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.IOvertimePreProcess;
import nts.uk.ctx.at.request.dom.application.overtime.service.SiftType;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeOvertime;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class CheckConvertPrePost {
	static final String DATE_FORMAT = "yyyy/MM/dd";
	final static String ZEZO_TIME = "0:00";
	
	@Inject
	private IOvertimePreProcess iOvertimePreProcess;
	
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private OvertimeWorkFrameRepository overtimeFrameRepository;
	
	@Inject
	private CommonOvertimeHoliday commonOvertimeHoliday;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private PreActualColorCheck preActualColorCheck;
	
	public OverTimeDto convertPrePost(int prePostAtr, String appDate, String siftCD, List<CaculationTime> overtimeHours, String workTypeCode, 
			Integer startTime, Integer endTime, List<Integer> startTimeRests, List<Integer> endTimeRests, OvertimeSettingDataDto overtimeSettingDataDto,
			ApplicationDto opAppBeforeUI, boolean beforeAppStatusUI, int actualStatusUI, List<OvertimeColorCheck> actualLstUI){
		List<OvertimeColorCheck> otTimeLst = overtimeHours.stream()
				.map(x -> OvertimeColorCheck.createApp(x.getAttendanceID(), x.getFrameNo(), x.getApplicationTime()))
				.collect(Collectors.toList());
		OvertimeSettingData overtimeSettingData = overtimeSettingDataDto.toDomain();
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		PreAppOvertimeDto preAppOvertimeDto = new PreAppOvertimeDto();
		OverTimeDto result = new OverTimeDto();
		OvertimeRestAppCommonSetting overtimeRestAppCommonSet = overtimeSettingData.overtimeRestAppCommonSet;
		AppOvertimeSetting appOvertimeSetting = overtimeSettingData.appOvertimeSetting;
		UseAtr preExcessDisplaySetting = overtimeRestAppCommonSet.getPreExcessDisplaySetting();
		AppDateContradictionAtr performanceExcessAtr = overtimeRestAppCommonSet.getPerformanceExcessAtr();
		Optional<Application> opAppBefore = Optional.empty();
		boolean beforeAppStatus = false;
		ActualStatus actualStatus = ActualStatus.NO_ACTUAL;
		List<OvertimeColorCheck> actualLst = Collections.emptyList();
//		if(prePostAtr == 1){
//			if(overtimeRestAppCommonSet.getPerformanceDisplayAtr().value == UseAtr.USE.value){
//				result.setReferencePanelFlg(true);
//				// 01-18_実績の内容を表示し直す
//				AppOvertimeReference appOvertimeReference = new AppOvertimeReference();
//				if(appDate==null) {
//					result.setAppOvertimeReference(appOvertimeReference);
//				} else {
//					// 07-01_事前申請状態チェック
//					if(opAppBeforeUI==null) {
//						PreAppCheckResult preAppCheckResult = preActualColorCheck.preAppStatusCheck(
//								companyID, 
//								employeeID, 
//								GeneralDate.fromString(appDate, DATE_FORMAT), 
//								ApplicationType.OVER_TIME_APPLICATION);
//						opAppBefore = preAppCheckResult.opAppBefore;
//						beforeAppStatus = preAppCheckResult.beforeAppStatus;
//					} else {
//						opAppBefore = Optional.of(ApplicationDto_New.toEntity(opAppBeforeUI));
//					}
//					// 07-02_実績取得・状態チェック
//					if(CollectionUtil.isEmpty(actualLstUI)) {
//						ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck
//								.actualStatusCheck(companyID, employeeID, GeneralDate.fromString(appDate, DATE_FORMAT), ApplicationType.OVER_TIME_APPLICATION, 
//										result.getWorkType() == null ? null : result.getWorkType().getWorkTypeCode(), 
//										result.getSiftType() ==  null ? null : result.getSiftType().getSiftCode(), 
//										appOvertimeSetting.getPriorityStampSetAtr(), Optional.empty(), Collections.emptyList());
//						actualStatus = actualStatusCheckResult.actualStatus;
//						actualLst = actualStatusCheckResult.actualLst;
//						result.setOpAppBefore(opAppBefore.map(x -> ApplicationDto_New.fromDomain(x)).orElse(null));
//						result.setBeforeAppStatus(beforeAppStatus);
//						result.setActualStatus(actualStatus.value);
//						result.setActualLst(actualLst);
//						List<CaculationTime> overTimeInputsRefer = new ArrayList<>();
//						List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(0, companyID);
//						for(OvertimeWorkFrame overtimeFrame :overtimeFrames){
//							overTimeInputsRefer.add(CaculationTime.builder()
//									.attendanceID(1)
//									.frameNo(overtimeFrame.getOvertimeWorkFrNo().v().intValue())
//									.frameName(overtimeFrame.getOvertimeWorkFrName().toString())
//									.build());
//						}
//						if(actualStatus==ActualStatus.NO_ACTUAL) {
//							appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
//							result.setAppOvertimeReference(appOvertimeReference);
//						} else {
//							appOvertimeReference.setWorkTypeRefer(
//									new WorkTypeOvertime(actualStatusCheckResult.workType, 
//											workTypeRepository.findByPK(companyID, actualStatusCheckResult.workType).map(x -> x.getName().toString()).orElse(null)));
//							appOvertimeReference.setSiftTypeRefer(
//									new SiftType(actualStatusCheckResult.workTime, 
//											workTimeRepository.findByCode(companyID, actualStatusCheckResult.workTime).map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v()).orElse(null)));
//							appOvertimeReference.setWorkClockFromTo1Refer(convertWorkClockFromTo(actualStatusCheckResult.startTime, actualStatusCheckResult.endTime));
//							for(CaculationTime caculationTime : overTimeInputsRefer) {
//								caculationTime.setApplicationTime(actualLst.stream()
//										.filter(x -> x.attendanceID == caculationTime.getAttendanceID() && x.frameNo == caculationTime.getFrameNo())
//										.findAny().map(y -> y.actualTime).orElse(null));
//							}
//							appOvertimeReference.setOverTimeInputsRefer(overTimeInputsRefer);
//							appOvertimeReference.setOverTimeShiftNightRefer(actualLst.stream()
//									.filter(x -> x.attendanceID == 1 && x.frameNo == 11)
//									.findAny().map(y -> y.actualTime).orElse(null));
//							appOvertimeReference.setFlexExessTimeRefer(actualLst.stream()
//									.filter(x -> x.attendanceID == 1 && x.frameNo == 12)
//									.findAny().map(y -> y.actualTime).orElse(null));
//							result.setAppOvertimeReference(appOvertimeReference);
//						}
//					} else {
//						actualStatus = EnumAdaptor.valueOf(actualStatusUI, ActualStatus.class);
//						actualLst = actualLstUI;
//					}
//					appOvertimeReference.setAppDateRefer(appDate);
//				}
//			}
//			if(overtimeRestAppCommonSet.getPreDisplayAtr().value== UseAtr.USE.value){
//				result.setAllPreAppPanelFlg(true);
//				AppOverTime appOverTime = otherCommonAlgorithm.getPreApplication(
//						employeeID,
//						EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class),
//						overtimeRestAppCommonSet.getPreDisplayAtr(), 
//						appDate == null ? null : GeneralDate.fromString(appDate, DATE_FORMAT),
//						ApplicationType.OVER_TIME_APPLICATION);
//				if(appOverTime != null){
//					convertOverTimeDto(companyID,preAppOvertimeDto,result,appOverTime);
//					result.setPreAppPanelFlg(true);
//				}else{
//					result.setPreAppPanelFlg(false);
//				}
//			}else{
//				result.setAllPreAppPanelFlg(false);
//			}
//		}else if(prePostAtr == 0){
//			// chi du bao them.EA khong co(ngay 05/12/2017)
//			//01-08_乖離定型理由を取得
//			if(overtimeRestAppCommonSet.getDivergenceReasonFormAtr().value == UseAtr.USE.value){
//				result.setDisplayDivergenceReasonForm(true);
//				List<DivergenceReason> divergenceReasons = commonOvertimeHoliday
//						.getDivergenceReasonForm(
//								companyID,
//								EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class),
//								overtimeRestAppCommonSet.getDivergenceReasonFormAtr(),
//								ApplicationType.OVER_TIME_APPLICATION);
//				convertToDivergenceReasonDto(divergenceReasons,result);
//			}else{
//				result.setDisplayDivergenceReasonForm(false);
//			}
//			//01-07_乖離理由を取得
//			result.setDisplayDivergenceReasonInput(
//					commonOvertimeHoliday.displayDivergenceReasonInput(
//							EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), 
//							overtimeRestAppCommonSet.getDivergenceReasonInputAtr()));
//		} else {
//			if(overtimeRestAppCommonSet.getPerformanceDisplayAtr().value == UseAtr.USE.value){
//				result.setReferencePanelFlg(false);
//				//to do....
//			}
//			if(overtimeRestAppCommonSet.getPreDisplayAtr().value == UseAtr.USE.value){
//				result.setAllPreAppPanelFlg(false);
//				//to do....
//			}
//			// chi du bao them.EA khong co(ngay 05/12/2017)
//			result.setDisplayDivergenceReasonForm(false);
//			result.setDisplayDivergenceReasonInput(false);
//		}
		// 07_事前申請・実績超過チェック(07_đơn xin trước. check vượt quá thực tế )
		PreActualColorResult preActualColorResult = preActualColorCheck.preActualColorCheck(
				preExcessDisplaySetting, 
				performanceExcessAtr, 
				ApplicationType.OVER_TIME_APPLICATION, 
				EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), 
				Collections.emptyList(),
				otTimeLst,
				opAppBefore,
				beforeAppStatus,
				actualLst,
				actualStatus);
		result.setResultLst(preActualColorResult.resultLst);
		return result;
	}
	private void convertOverTimeDto(String companyID,PreAppOvertimeDto preAppOvertimeDto, OverTimeDto result,AppOverTime_Old appOvertime){
		if(appOvertime.getApplication() != null){
			if(appOvertime.getApplication().getAppDate() != null){
				preAppOvertimeDto.setAppDatePre(appOvertime.getApplication().getAppDate().getApplicationDate().toString(DATE_FORMAT));
			}
		}
		
		if (appOvertime.getWorkTypeCode() != null) {
			WorkTypeOvertime workTypeOvertime = new WorkTypeOvertime();
			workTypeOvertime.setWorkTypeCode(appOvertime.getWorkTypeCode().toString());
			Optional<WorkType> workType = workTypeRepository.findByPK(companyID,
					appOvertime.getWorkTypeCode().toString());
			if (workType.isPresent()) {
				workTypeOvertime.setWorkTypeName(workType.get().getName().toString());
			}
			preAppOvertimeDto.setWorkTypePre(workTypeOvertime);
		}
		if (appOvertime.getSiftCode() != null) {
			SiftType siftType = new SiftType();

			siftType.setSiftCode(appOvertime.getSiftCode().toString().equals("000")? "" : appOvertime.getSiftCode().toString());
			Optional<WorkTimeSetting> workTime = workTimeRepository.findByCode(companyID,
					appOvertime.getSiftCode().toString());
			if (workTime.isPresent()) {
				siftType.setSiftName(workTime.get().getWorkTimeDisplayName().getWorkTimeName().toString());
			}
			preAppOvertimeDto.setSiftTypePre(siftType);
		}
		preAppOvertimeDto.setWorkClockFromTo1Pre(convertWorkClockFromTo(appOvertime.getWorkClockFrom1(),appOvertime.getWorkClockTo1()));
		preAppOvertimeDto.setWorkClockFromTo2Pre(convertWorkClockFromTo(appOvertime.getWorkClockFrom2(),appOvertime.getWorkClockTo2()));
		
		List<OvertimeInputDto> overtimeInputDtos = new ArrayList<>();
		List<OverTimeInput> overtimeInputs = appOvertime.getOverTimeInput();
		if (overtimeInputs != null && !overtimeInputs.isEmpty()) {
			List<Integer> frameNo = new ArrayList<>();
			for (OverTimeInput overTimeInput : overtimeInputs) {
				OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
				overtimeInputDto.setAttendanceID(overTimeInput.getAttendanceType().value);
				overtimeInputDto.setFrameNo(overTimeInput.getFrameNo());
				overtimeInputDto.setStartTime(overTimeInput.getStartTime()== null ? null : overTimeInput.getStartTime().v());
				overtimeInputDto.setEndTime(overTimeInput.getEndTime() == null ? null : overTimeInput.getEndTime().v());
				overtimeInputDto.setApplicationTime(overTimeInput.getApplicationTime() == null ? null : overTimeInput.getApplicationTime().v());
				overtimeInputDtos.add(overtimeInputDto);
				frameNo.add(overTimeInput.getFrameNo());
			}
			List<OvertimeWorkFrame> overtimeFrames = this.overtimeFrameRepository.getOvertimeWorkFrameByFrameNos(companyID,frameNo);
			for (OvertimeInputDto overtimeInputDto : overtimeInputDtos) {
				for (OvertimeWorkFrame overtimeFrame : overtimeFrames) {
					if (overtimeInputDto.getFrameNo() == overtimeFrame.getOvertimeWorkFrNo().v().intValueExact()) {
						overtimeInputDto.setFrameName(overtimeFrame.getOvertimeWorkFrName().toString());
						continue;
					}
				}
			}
			preAppOvertimeDto.setOverTimeInputsPre(overtimeInputDtos);
			preAppOvertimeDto.setOverTimeShiftNightPre(appOvertime.getOverTimeShiftNight());
			preAppOvertimeDto.setFlexExessTimePre(appOvertime.getFlexExessTime());
			
		}
		result.setPreAppOvertimeDto(preAppOvertimeDto);
	}
	
	/**
	 * @param divergenceReasons
	 * @param result
	 */
	private void convertToDivergenceReasonDto(List<DivergenceReason> divergenceReasons, OverTimeDto result){
		List<DivergenceReasonDto> divergenceReasonDtos = new ArrayList<>();
		for(DivergenceReason divergenceReason : divergenceReasons){
			DivergenceReasonDto divergenceReasonDto = new DivergenceReasonDto();
			divergenceReasonDto.setDivergenceReasonID(divergenceReason.getReasonTypeItem().getReasonID());
			divergenceReasonDto.setReasonTemp(divergenceReason.getReasonTypeItem().getReasonTemp().toString());
			// divergenceReasonDto.setDivergenceReasonIdDefault(divergenceReason.getReasonTypeItem().getDefaultFlg().value);
			
			divergenceReasonDtos.add(divergenceReasonDto);
		}
		result.setDivergenceReasonDtos(divergenceReasonDtos);
	}
	private String convertWorkClockFromTo(Integer startTime, Integer endTime){
		String WorkClockFromTo = "";
		if(startTime == null && endTime != null){
			TimeWithDayAttr endTimeWithDay = new TimeWithDayAttr(endTime);
			WorkClockFromTo = " "
					+  "　~　"
					+ endTimeWithDay.getDayDivision().description
					+ convert(endTime);
		}
		if(startTime != null && endTime != null){
			TimeWithDayAttr startTimeWithDay = new TimeWithDayAttr(startTime);
			TimeWithDayAttr endTimeWithDay = new TimeWithDayAttr(endTime);
		 WorkClockFromTo = startTimeWithDay.getDayDivision().description
							+ convert(startTime) + "　~　"
							+ endTimeWithDay.getDayDivision().description
							+ convert(endTime);
		}
		return WorkClockFromTo;
	}
	private String convert(Integer minute) {
		if(minute == null){
			return null;
		}
		TimeWithDayAttr timeConvert = new TimeWithDayAttr(minute);
		return timeConvert.getInDayTimeWithFormat();
	}
}
