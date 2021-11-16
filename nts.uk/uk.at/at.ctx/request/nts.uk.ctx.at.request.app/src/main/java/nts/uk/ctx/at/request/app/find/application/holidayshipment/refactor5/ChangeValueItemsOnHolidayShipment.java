package nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.appabsence.AppAbsenceFinder;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoWithDateDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalPhaseStateForAppDto;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.ActualContentDisplayDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.ChangeWorkTypeResultDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.DisplayInforWhenStarting;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.service.AbsenceServiceProcess;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.VacationCheckOutput;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.CollectAchievement;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.MsgErrorOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.HolidayShipmentService;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.RecordDate;
import nts.uk.ctx.at.shared.app.find.common.TimeZoneWithWorkNoDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.TimeZone_NewDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhPV
 */
@Stateless
public class ChangeValueItemsOnHolidayShipment {
	
	@Inject
	private CommonAlgorithm commonAlgorithm;

	@Inject
	private HolidayShipmentScreenAFinder screenAFinder;
	
	@Inject
	private AbsenceServiceProcess absenceServiceProcess;
	
	@Inject
	private AppAbsenceFinder appAbsenceFinder;
	
	@Inject
	private HolidayShipmentService holidayShipmentService;
	
	@Inject
	private CollectAchievement collectAchievement;
	
	/**
	 * @name 振出日を変更する
	 * @param recDate 申請対象日
	 * @param absDate 振休日
	 * @param displayInforWhenStarting 起動時取得した「振休振出申請起動時の表示情報」
	 */
	public DisplayInforWhenStarting changeRecDate(GeneralDate recDate, Optional<GeneralDate> absDate, DisplayInforWhenStarting displayInforWhenStarting) {
		
		String companyId = AppContexts.user().companyId();
		List<GeneralDate> dateLst = new ArrayList<>();
		dateLst.add(recDate);
		absDate.ifPresent(c-> dateLst.add(c));
		//申請日を変更する(Thay đổi applicationDate)
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = commonAlgorithm.changeAppDateProcess(companyId, dateLst, ApplicationType.COMPLEMENT_LEAVE_APPLICATION, displayInforWhenStarting.appDispInfoStartup.getAppDispInfoNoDateOutput().toDomain(), displayInforWhenStarting.appDispInfoStartup.getAppDispInfoWithDateOutput().toDomain(), Optional.empty());
		//「振休振出申請起動時の表示情報」．申請表示情報．申請表示情報(基準日関係あり)=上記取得した「申請表示情報(基準日関係あり)」 ([DisplayInfo khi khởi động đơn xin nghỉ bù làm bù]. ApplicationDisplayInfo.ApplicationDisplayInfo(có liên quan BaseDate)= [ApplicationDisplayInfo(có liên quan BaseDate)] đã lấy ghi ở trên)
		displayInforWhenStarting.appDispInfoStartup.setAppDispInfoWithDateOutput(AppDispInfoWithDateDto.fromDomain(appDispInfoWithDateOutput));

		AppDispInfoStartupOutput appDispInfoStartupOutput = displayInforWhenStarting.appDispInfoStartup.toDomain();
		//INPUT．「申請表示情報(基準日関係なし) ．申請承認設定．申請設定」．承認ルートの基準日をチェックする(Check BaseDate của  INPUT.[ApplicationDisplayInfo(ko liên quan BaseDate). ApplicationApproveSetting. ApplicationSetting]. ApproveRoot)
		if (displayInforWhenStarting.appDispInfoStartup.getAppDispInfoNoDateOutput().getApplicationSetting().getRecordDate() == RecordDate.APP_DATE.value) {
			//「振休振出申請起動時の表示情報」．振出申請起動時の表示情報=上記取得した「振出申請起動時の表示情報」([DisplayInfo khi khởi đọng đơn xin nghỉ bù làm bù]. DisplayInfo khi khởi động đơn xin làm bù= [DisplayInfo khi khởi động đơn xin làm bù] đã lấy ở trên)
			displayInforWhenStarting.setApplicationForWorkingDay(
					//1.振出申請（新規）起動処理(申請対象日関係あり)/1.xử lý khởi động đơn xin làm bù(new)(có liên quan ApplicationTargetdate)
					this.screenAFinder.applicationForWorkingDay(companyId,
							appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(),
							appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate(),
							appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmpHistImport().getEmploymentCode(),
							appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpWorkTimeLst().isPresent() ? 
									appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpWorkTimeLst().get() : 
									new ArrayList<>())
					);
		}
		//振出日の休日区分により振休の勤務種類を取得する
		displayInforWhenStarting.applicationForHoliday.setWorkType(
				this.screenAFinder.getWorkTypeOfTheHoliday(companyId,
					appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().isPresent() ? 
							appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().isEmpty() ? 
									Optional.empty()
									: appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail().isPresent() ? 
											Optional.of(appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail().get().getWorkTypeCD())
											: Optional.empty()
							: Optional.empty(),
					displayInforWhenStarting.applicationForHoliday.getWorkTypeList().stream().map(c -> c.toDomain()).collect(Collectors.toList()))
				.orElse(null));
		return displayInforWhenStarting;
	}
	
	/**
	 * @name 振休日を変更する
	 * @param recDate 申請対象日
	 * @param absDate 振休日
	 * @param displayInforWhenStarting 起動時取得した「振休振出申請起動時の表示情報」
	 */
	public DisplayInforWhenStarting changeAbsDate(Optional<GeneralDate> recDate, GeneralDate absDate, DisplayInforWhenStarting displayInforWhenStarting) {
		String companyId = AppContexts.user().companyId();
		List<GeneralDate> dateLst = new ArrayList<>();
		dateLst.add(absDate);
		recDate.ifPresent(c-> dateLst.add(c));
		//申請日を変更する(Thay đổi applicationDate)
		AppDispInfoWithDateOutput appDispInfoWithDateOutput = commonAlgorithm.changeAppDateProcess(companyId, dateLst, ApplicationType.COMPLEMENT_LEAVE_APPLICATION, displayInforWhenStarting.appDispInfoStartup.getAppDispInfoNoDateOutput().toDomain(), displayInforWhenStarting.appDispInfoStartup.getAppDispInfoWithDateOutput().toDomain(), Optional.empty());
		//「振休振出申請起動時の表示情報」．申請表示情報．申請表示情報(基準日関係あり)=上記取得した「申請表示情報(基準日関係あり)」 ([DisplayInfo khi khởi động đơn xin nghỉ bù làm bù]. ApplicationDisplayInfo.ApplicationDisplayInfo(có liên quan BaseDate)= [ApplicationDisplayInfo(có liên quan BaseDate)] đã lấy ghi ở trên)
		displayInforWhenStarting.appDispInfoStartup.setAppDispInfoWithDateOutput(AppDispInfoWithDateDto.fromDomain(appDispInfoWithDateOutput));

		AppDispInfoStartupOutput appDispInfoStartupOutput = displayInforWhenStarting.appDispInfoStartup.toDomain();
		//INPUT．「申請表示情報(基準日関係なし) ．申請承認設定．申請設定」．承認ルートの基準日をチェックする(Check BaseDate của  INPUT.[ApplicationDisplayInfo(ko liên quan BaseDate). ApplicationApproveSetting. ApplicationSetting]. ApproveRoot)
		if (displayInforWhenStarting.appDispInfoStartup.getAppDispInfoNoDateOutput().getApplicationSetting().getRecordDate() == RecordDate.APP_DATE.value) {
			//「振休振出申請起動時の表示情報」．振出申請起動時の表示情報=上記取得した「振出申請起動時の表示情報」([DisplayInfo khi khởi đọng đơn xin nghỉ bù làm bù]. DisplayInfo khi khởi động đơn xin làm bù= [DisplayInfo khi khởi động đơn xin làm bù] đã lấy ở trên)
			displayInforWhenStarting.setApplicationForHoliday(
					//1.振出申請（新規）起動処理(申請対象日関係あり)/1.xử lý khởi động đơn xin làm bù(new)(có liên quan ApplicationTargetdate)
					this.screenAFinder.applicationForHoliday(
							companyId, 
							appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmpHistImport().getEmploymentCode(),
							appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().isPresent() ? 
									appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().isEmpty() ? 
											Optional.empty() : 
												appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail().isPresent() ?
														Optional.of(appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get().get(0).getOpAchievementDetail().get().getWorkTypeCD())
														: Optional.empty()
									: Optional.empty()));
		}
		return displayInforWhenStarting;
	}
	
	/**
	 * @name 振出の勤務種類を変更する -- 振休の勤務種類を変更する
	 * @param workTypeBefore 変更前の勤務種類
	 * @param workTypeAfter 変更後の勤務種類
	 * @param workTimeCode 就業時間帯コード
	 * @param leaveComDayOffMana 振出振休紐付け管理<List>
	 * @param payoutSubofHDManagement 振出振休紐付け管理<List> -- 休出代休紐付け管理<List>
	 * @return
	 */
	public ChangeWorkTypeResultDto changeWorkType(WorkTypeDto workTypeBefore, WorkTypeDto workTypeAfter, Optional<String> workTimeCode, List<LeaveComDayOffManaDto> leaveComDayOffMana, List<PayoutSubofHDManagementDto> payoutSubofHDManagement) {
		List<TimeZoneWithWorkNoDto> workingHours = new ArrayList<>();
		if(workTimeCode.isPresent()) {
			String companyId = AppContexts.user().companyId();
			//勤務時間初期値の取得(lấy giá trị khởi tạo worktime)
			PrescribedTimezoneSetting prescribedTimezoneSetting = appAbsenceFinder.initWorktimeCode(companyId, workTypeAfter.getWorkTypeCode(), workTimeCode.get());
			if(prescribedTimezoneSetting != null) {
				for (TimezoneUse time : prescribedTimezoneSetting.getLstTimezone()) {
					if(time.isUsed()) {
						workingHours.add(new TimeZoneWithWorkNoDto(time.getWorkNo(), new TimeZone_NewDto(time.getStart().v(), time.getEnd().v())));
					}
				}
			}
		}
		//休暇紐付管理をチェックする
		VacationCheckOutput checkVacationTyingManage = absenceServiceProcess.checkVacationTyingManage(
				workTypeBefore != null ? workTypeBefore.toDomain(): null, 
				workTypeAfter.toDomain(), 
				leaveComDayOffMana.stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				payoutSubofHDManagement.stream().map(c->c.toDomain()).collect(Collectors.toList()));
		return new ChangeWorkTypeResultDto(checkVacationTyingManage, workingHours);
	}
	
	/**
	 * @name 振出の就業時間帯を選択する -- 振休の就業時間帯を選択する
	 * @param companyId 申請者会社ID
	 * @param opWorkTimeLst List＜就業時間帯の設定＞
	 * @param recDate 振出日
	 * @param absDate 振休日
	 * @param workTypeCode 勤務種類コード
	 * @param WorkTimeCode 就業時間帯コード
	 */
	public void selectWorkingHours(String companyId, List<WorkTimeSetting> opWorkTimeLst, GeneralDate recDate, Optional<GeneralDate> absDate, String workTypeCode, String WorkTimeCode) {
		//QA: http://192.168.50.4:3000/issues/113564#note-5
		//QA: http://192.168.50.4:3000/issues/113580 
	}
	
	/**
	 * @name 申請日の変更
	 * @param companyId
	 * @param newDate
	 * @param displayInforWhenStarting 振休振出申請起動時の表示情報 
	 * @return 振休振出申請起動時の表示情報
	 */
	public DisplayInforWhenStarting changeDateCScreen(GeneralDate newDate, DisplayInforWhenStarting displayInforWhenStarting) {
		
		String companyId = AppContexts.user().companyId();
		Optional<GeneralDate> referDate = holidayShipmentService.detRefDate(displayInforWhenStarting.existRec()?Optional.of(displayInforWhenStarting.rec.application.toDomain().getAppDate().getApplicationDate()):Optional.empty(), Optional.of(newDate));
		if(referDate.isPresent() && displayInforWhenStarting.appDispInfoStartup.getAppDispInfoNoDateOutput().getApplicationSetting().toDomain().getRecordDate() == RecordDate.APP_DATE) {
			ApprovalRootContentImport_New approvalRootContentImport_New = commonAlgorithm.getApprovalRoot(companyId, displayInforWhenStarting.appDispInfoStartup.getAppDetailScreenInfo().getApplication().getEmployeeID(), EmploymentRootAtr.APPLICATION, ApplicationType.COMPLEMENT_LEAVE_APPLICATION, newDate);
			displayInforWhenStarting.appDispInfoStartup.getAppDispInfoWithDateOutput().setOpListApprovalPhaseState(approvalRootContentImport_New.getApprovalRootState().getListApprovalPhaseState().stream().map(c->ApprovalPhaseStateForAppDto.fromApprovalPhaseStateImport(c)).collect(Collectors.toList()));
			List<MsgErrorOutput> msgErrorLst = new ArrayList<>();
			switch (approvalRootContentImport_New.getErrorFlag()) {
			case NO_CONFIRM_PERSON:
				msgErrorLst.add(new MsgErrorOutput("Msg_238", Collections.emptyList()));
				break;
			case APPROVER_UP_10:
				msgErrorLst.add(new MsgErrorOutput("Msg_237", Collections.emptyList()));
				break;
			case NO_APPROVER:
				msgErrorLst.add(new MsgErrorOutput("Msg_324", Collections.emptyList()));
				break;
			default:
				break;
			}
			displayInforWhenStarting.appDispInfoStartup.getAppDispInfoWithDateOutput().setOpMsgErrorLst(msgErrorLst);
		} else {
			displayInforWhenStarting.appDispInfoStartup
									.getAppDispInfoWithDateOutput()
									.setOpListApprovalPhaseState(displayInforWhenStarting.getAppDispInfoStartup().getAppDetailScreenInfo().getApprovalLst());
		}
		
		List<ActualContentDisplay> achievementContents = collectAchievement.getAchievementContents(companyId, displayInforWhenStarting.appDispInfoStartup.getAppDetailScreenInfo().getApplication().getEmployeeID(), Arrays.asList(newDate), ApplicationType.COMPLEMENT_LEAVE_APPLICATION);
		if(!achievementContents.isEmpty()) {
			displayInforWhenStarting.appDispInfoStartup.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().removeIf(c->c.toDomain().getDate().equals(displayInforWhenStarting.abs.application.toDomain().getAppDate().getApplicationDate()));
			displayInforWhenStarting.appDispInfoStartup.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().add(ActualContentDisplayDto.fromDomain(achievementContents.get(0)));
		}
		
		return displayInforWhenStarting;
	}

}
