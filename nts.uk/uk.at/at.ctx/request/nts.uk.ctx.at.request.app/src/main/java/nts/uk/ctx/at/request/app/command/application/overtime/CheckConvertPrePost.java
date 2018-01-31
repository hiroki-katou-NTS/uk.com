package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.DivergenceReasonDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OverTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.OvertimeInputDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.PreAppOvertimeDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.service.AppOvertimeReference;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.IOvertimePreProcess;
import nts.uk.ctx.at.request.dom.application.overtime.service.SiftType;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeOvertime;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckConvertPrePost {
	final String DATE_FORMAT = "yyyy/MM/dd";
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	
	@Inject
	private IOvertimePreProcess iOvertimePreProcess;
	
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private OvertimeWorkFrameRepository overtimeFrameRepository;
	
	@Inject
	private BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;
	
	public OverTimeDto convertPrePost(int prePostAtr,String appDate,String siftCD,List<CaculationTime> overtimeHours){
		
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		PreAppOvertimeDto preAppOvertimeDto = new PreAppOvertimeDto();
		OverTimeDto result = new OverTimeDto();
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository.getOvertimeRestAppCommonSetting(companyID, ApplicationType.OVER_TIME_APPLICATION.value);
		AppCommonSettingOutput appCommonSettingOutput = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID,
				employeeID,
				1, EnumAdaptor.valueOf(ApplicationType.OVER_TIME_APPLICATION.value, ApplicationType.class),appDate == null? null : GeneralDate.fromString(appDate, DATE_FORMAT));
		if(prePostAtr == 1){
			if(overtimeRestAppCommonSet.isPresent()){
				if(overtimeRestAppCommonSet.get().getPerformanceDisplayAtr().value == UseAtr.USE.value){
					result.setReferencePanelFlg(true);
					// 01-18_実績の内容を表示し直す
					ApprovalFunctionSetting approvalFunctionSetting = appCommonSettingOutput.approvalFunctionSetting;
					AppOvertimeReference appOvertimeReference = iOvertimePreProcess.getResultContentActual(prePostAtr, siftCD, companyID,employeeID, appDate,approvalFunctionSetting,overtimeHours);
				}
				if(overtimeRestAppCommonSet.get().getPreDisplayAtr().value== UseAtr.USE.value){
					result.setAllPreAppPanelFlg(true);
					AppOverTime appOverTime = iOvertimePreProcess.getPreApplication(companyID, employeeID, overtimeRestAppCommonSet, appDate, prePostAtr);
					if(appOverTime != null){
						convertOverTimeDto(companyID,preAppOvertimeDto,result,appOverTime);
						result.setPreAppPanelFlg(true);
					}else{
						result.setPreAppPanelFlg(false);
					}
				}else{
					result.setAllPreAppPanelFlg(false);
				}
				// chi du bao them.EA khong co(ngay 05/12/2017)
				if(overtimeRestAppCommonSet.isPresent()){
					//01-08_乖離定型理由を取得
					if(overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr().value == UseAtr.USE.value){
						result.setDisplayDivergenceReasonForm(true);
						List<DivergenceReason> divergenceReasons = iOvertimePreProcess.getDivergenceReasonForm(companyID,ApplicationType.OVER_TIME_APPLICATION.value,overtimeRestAppCommonSet);
						convertToDivergenceReasonDto(divergenceReasons,result);
					}else{
						result.setDisplayDivergenceReasonForm(false);
					}
					//01-07_乖離理由を取得
					result.setDisplayDivergenceReasonInput(iOvertimePreProcess.displayDivergenceReasonInput(overtimeRestAppCommonSet));
				}
			}
		}else if(prePostAtr == 0){
			if(overtimeRestAppCommonSet.isPresent()){
				if(overtimeRestAppCommonSet.get().getPerformanceDisplayAtr().value == UseAtr.USE.value){
					result.setReferencePanelFlg(false);
					//to do....
				}
				if(overtimeRestAppCommonSet.get().getPreDisplayAtr().value == UseAtr.USE.value){
					result.setAllPreAppPanelFlg(false);
					//to do....
				}
				// chi du bao them.EA khong co(ngay 05/12/2017)
				result.setDisplayDivergenceReasonForm(false);
				result.setDisplayDivergenceReasonInput(false);
			}
		}
		return result;
	}
	private void convertOverTimeDto(String companyID,PreAppOvertimeDto preAppOvertimeDto, OverTimeDto result,AppOverTime appOvertime){
		if(appOvertime.getApplication() != null){
			if(appOvertime.getApplication().getAppDate() != null){
				preAppOvertimeDto.setAppDatePre(appOvertime.getApplication().getAppDate().toString(DATE_FORMAT));
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

			siftType.setSiftCode(appOvertime.getSiftCode().toString());
			Optional<WorkTimeSetting> workTime = workTimeRepository.findByCode(companyID,
					appOvertime.getSiftCode().toString());
			if (workTime.isPresent()) {
				siftType.setSiftName(workTime.get().getWorkTimeDisplayName().getWorkTimeName().toString());
			}
			preAppOvertimeDto.setSiftTypePre(siftType);
		}
		preAppOvertimeDto.setWorkClockFrom1Pre(appOvertime.getWorkClockFrom1());
		preAppOvertimeDto.setWorkClockTo1Pre(appOvertime.getWorkClockTo1());
		preAppOvertimeDto.setWorkClockFrom2Pre(appOvertime.getWorkClockFrom2());
		preAppOvertimeDto.setWorkClockTo2Pre(appOvertime.getWorkClockTo2());
		
		List<OvertimeInputDto> overtimeInputDtos = new ArrayList<>();
		List<OverTimeInput> overtimeInputs = appOvertime.getOverTimeInput();
		if (overtimeInputs != null && !overtimeInputs.isEmpty()) {
			List<Integer> frameNo = new ArrayList<>();
			for (OverTimeInput overTimeInput : overtimeInputs) {
				OvertimeInputDto overtimeInputDto = new OvertimeInputDto();
				overtimeInputDto.setAttendanceID(overTimeInput.getAttendanceType().value);
				overtimeInputDto.setFrameNo(overTimeInput.getFrameNo());
				overtimeInputDto.setStartTime(overTimeInput.getStartTime().v());
				overtimeInputDto.setEndTime(overTimeInput.getEndTime().v());
				overtimeInputDto.setApplicationTime(overTimeInput.getApplicationTime().v());
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
			divergenceReasonDto.setDivergenceReasonIdDefault(divergenceReason.getReasonTypeItem().getDefaultFlg().value);
			
			divergenceReasonDtos.add(divergenceReasonDto);
		}
		result.setDivergenceReasonDtos(divergenceReasonDtos);
	}
	
}
