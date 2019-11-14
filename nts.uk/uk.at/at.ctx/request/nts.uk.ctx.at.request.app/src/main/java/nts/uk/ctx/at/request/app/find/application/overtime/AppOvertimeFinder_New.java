package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculation;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.dailyattendancetime.DailyAttendanceTimeCaculationImport;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatus;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.ActualStatusCheckResult;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.CommonOvertimeHoliday;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeColorCheck;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorCheck;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreAppCheckResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
import nts.uk.ctx.at.request.dom.application.overtime.service.AppOvertimeService;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayPrePost;
import nts.uk.ctx.at.request.dom.application.overtime.service.IOvertimePreProcess;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeAndSiftType;
import nts.uk.ctx.at.request.dom.application.overtime.service.output.RecordWorkOutput;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.shr.com.context.AppContexts;

/**
 * refactorKAF005_ver3
 * @author Doan Duy Hung
 *
 */

@Stateless
public class AppOvertimeFinder_New {
	
	final static String DATE_FORMAT = "yyyy/MM/dd";
	
	@Inject
	private AppOvertimeService appOvertimeService;
	
	@Inject
	private CommonOvertimeHoliday commonOvertimeHoliday;
	
	@Inject
	private IOvertimePreProcess iOvertimePreProcess;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private DailyAttendanceTimeCaculation dailyAttendanceTimeCaculation;
	
	@Inject
	private PreActualColorCheck preActualColorCheck;
	
	@Inject
	private OvertimeService overtimeService;
	
	public void startNew(Integer overtimeAtr, Integer rootAtr, List<String> employeeLst, String appDate, 
			Integer startTime, Integer endTime, String appReason, Integer prePostAtr) {
		String companyID = AppContexts.user().companyId();
		GeneralDate generalAppDate = Strings.isBlank(appDate) ? null : GeneralDate.fromString(appDate, DATE_FORMAT);
		// 10_申請者を作成
		List<EmployeeInfoImport> employeeInfoLst = appOvertimeService.createApplicants(employeeLst);
		// 申請者社員IDを定める
		String employeeID =  employeeInfoLst.get(0).getSid();
		// 11_設定データを取得
		appOvertimeService.getSettingDatas(companyID, employeeID, rootAtr, ApplicationType.OVER_TIME_APPLICATION, generalAppDate);
		// 12_承認ルートを取得
		appOvertimeService.getApprovalRoute(companyID, employeeID, rootAtr, ApplicationType.OVER_TIME_APPLICATION, generalAppDate);
		// 01_初期データ取得
		// this.getInitData(companyID, employeeID, Optional.ofNullable(generalAppDate), applicationSetting, refDate, opStartTime, opEndTime, opAppReason);
	}
	
	/**
	 * 01_初期データ取得
	 * @param companyID
	 * @param employeeID
	 * @param opAppDate
	 * @param applicationSetting
	 * @param refDate
	 * @param opStartTime
	 * @param opEndTime
	 * @param opAppReason
	 */
	private void getInitData(String companyID, String employeeID, Optional<GeneralDate> opAppDate, ApplicationSetting applicationSetting,
			GeneralDate refDate, Optional<Integer> opStartTime, Optional<Integer> opEndTime, Optional<String> opAppReason) {
		// 01-13_事前事後区分を取得
		DisplayPrePost displayPrePost = commonOvertimeHoliday.getDisplayPrePost(companyID, ApplicationType.OVER_TIME_APPLICATION, 
				0, OverTimeAtr.ALL, refDate, applicationSetting.getDisplayPrePostFlg());
		// 14_表示データを取得
		this.getDisplayData(companyID, employeeID, opAppDate.get(), PrePostAtr.POSTERIOR, refDate, applicationSetting, opStartTime, opEndTime);;
		// 01-03_残業枠を取得
		List<OvertimeWorkFrame> overtimeFrames = iOvertimePreProcess.getOvertimeHours(0, companyID);
		AppTypeDiscreteSetting appTypeDiscreteSetting = null;
		// 01-05_申請定型理由を取得
		List<ApplicationReason> applicationReasons = otherCommonAlgorithm.getApplicationReasonType(
				companyID,
				appTypeDiscreteSetting.getTypicalReasonDisplayFlg(),
				ApplicationType.OVER_TIME_APPLICATION);
		// 01-06_申請理由を取得
		otherCommonAlgorithm.displayAppReasonContentFlg(appTypeDiscreteSetting.getDisplayReasonFlg());
		OvertimeRestAppCommonSetting otRestAppCommonSet = null;
		// 01-08_乖離定型理由を取得
		List<DivergenceReason> divergenceReasons = commonOvertimeHoliday
				.getDivergenceReasonForm(
						companyID,
						PrePostAtr.POSTERIOR,
						otRestAppCommonSet.getDivergenceReasonFormAtr(),
						ApplicationType.OVER_TIME_APPLICATION);
		// 01-07_乖離理由を取得
		commonOvertimeHoliday.displayDivergenceReasonInput(PrePostAtr.POSTERIOR, otRestAppCommonSet.getDivergenceReasonInputAtr());
		// 画面状態をチェックする
		if(opAppDate.isPresent() && opStartTime.isPresent() && opEndTime.isPresent()) {
			// Input．申請設定．2時刻計算利用区分をチェックする
			if(true) {
				// 19_計算処理
				// this.calc(appDate, employeeID, prePostAtr, workTypeCD, siftCD, startTime, endTime, overTimeLst, breakTimeLst, opAppBefore, beforeAppStatus, actualLst, actualStatus, preExcessDisplaySetting, performanceExcessAtr);
			}
		}
	}
	
	// 14_表示データを取得
	private void getDisplayData(String companyID, String employeeID, GeneralDate appDate, PrePostAtr prePostAtr, GeneralDate refDate, 
			ApplicationSetting applicationSetting, Optional<Integer> opStartTime, Optional<Integer> opEndTime) {
		// 01-01_残業通知情報を取得
		// OvertimeInstructInfomation overtimeInstructInfomation = iOvertimePreProcess.getOvertimeInstruct(appCommonSettingOutput, appDate, employeeID);
		// 01-02_時間外労働を取得
		Optional<AgreeOverTimeOutput> opAgreeOverTimeOutput = commonOvertimeHoliday.getAgreementTime(companyID, employeeID, ApplicationType.OVER_TIME_APPLICATION);
		// 07_勤務種類取得
		// List<WorkTypeOvertime> workTypeOvertimes = overtimeService.getWorkType(companyID, employeeID,approvalFunctionSetting,appEmploymentWorkType);
		// 08_就業時間帯取得
		// List<SiftType> siftTypes = overtimeService.getSiftType(companyID, employeeID, approvalFunctionSetting,appOverTime.getApplication().getAppDate());
		// 09_勤務種類就業時間帯の初期選択をセットする
		WorkTypeAndSiftType workTypeAndSiftType = overtimeService.getWorkTypeAndSiftTypeByPersonCon(companyID, employeeID, 
				appDate, Collections.emptyList(), Collections.emptyList());
		// 01-14_勤務時間取得(01 - 14 _ Working hours acquired)
		RecordWorkOutput recordWorkOutput = commonOvertimeHoliday.getWorkingHours(companyID, employeeID, "", appDate.toString(DATE_FORMAT), null, "", true);
		// 01-01_休憩時間を取得する
		// không làm
		// 13_フレックス時間を表示するかチェック
		boolean displayFlex = false;
		// Input．事前事後区分　AND　Input.申請日をチェック
		if(true) {
			// 07-01_事前申請状態チェック
			PreAppCheckResult preAppCheckResult = preActualColorCheck.preAppStatusCheck(
					companyID, 
					employeeID, 
					appDate, 
					ApplicationType.OVER_TIME_APPLICATION);
			// 07-02_実績取得・状態チェック
			ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck.actualStatusCheck(
					companyID, 
					employeeID, 
					appDate, 
					ApplicationType.OVER_TIME_APPLICATION, 
					"", 
					"", 
					null, 
					Optional.empty());
		}
	}
	
	/**
	 * 19_計算処理
	 * @param appDate
	 * @param employeeID
	 * @param prePostAtr
	 * @param workTypeCD
	 * @param siftCD
	 * @param startTime
	 * @param endTime
	 * @param overTimeLst
	 * @param breakTimeLst
	 * @param opAppBefore
	 * @param beforeAppStatus
	 * @param actualLst
	 * @param actualStatus
	 * @param preExcessDisplaySetting
	 * @param performanceExcessAtr
	 */
	private void calc(GeneralDate appDate, String employeeID, PrePostAtr prePostAtr, String workTypeCD, String siftCD, Integer startTime, Integer endTime,
			List<OvertimeColorCheck> overTimeLst, List<OvertimeColorCheck> breakTimeLst, 
			Optional<Application_New> opAppBefore, boolean beforeAppStatus, 
			List<OvertimeColorCheck> actualLst, ActualStatus actualStatus,
			UseAtr preExcessDisplaySetting, AppDateContradictionAtr performanceExcessAtr) {
		// 計算処理を実行する
		DailyAttendanceTimeCaculationImport dailyAttendanceTimeCaculationImport = dailyAttendanceTimeCaculation.getCalculation(employeeID,
				appDate, workTypeCD, siftCD, startTime, endTime, Collections.emptyList(), Collections.emptyList());
		// 色表示チェック
		preActualColorCheck.preActualColorCheck(preExcessDisplaySetting, performanceExcessAtr, ApplicationType.OVER_TIME_APPLICATION, 
				prePostAtr, Collections.emptyList(), overTimeLst, opAppBefore, beforeAppStatus, actualLst, actualStatus);
	}
	
	/**
	 * 17_申請日付を変更 
	 * @param overtimeAtr 残業区分
	 * @param rootAtr 就業ルート区分
	 * @param overtimeInstructionAtr 残業指示の利用区分
	 * @param employeeID 申請者ID
	 * @param appDate 申請日
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 * @param prePostAtr 事前事後区分
	 */
	public void changeAppDate(Integer overtimeAtr, Integer rootAtr, Integer overtimeInstructionAtr, String employeeID, String appDate, 
			Integer startTime, Integer endTime, Integer prePostAtr) {
		String companyID = AppContexts.user().companyId();
		GeneralDate generalAppDate = Strings.isBlank(appDate) ? null : GeneralDate.fromString(appDate, DATE_FORMAT);
		// 11_設定データを取得
		appOvertimeService.getSettingDatas(companyID, employeeID, rootAtr, ApplicationType.OVER_TIME_APPLICATION, generalAppDate);
		// 12_承認ルートを取得
		appOvertimeService.getApprovalRoute(companyID, employeeID, rootAtr, ApplicationType.OVER_TIME_APPLICATION, generalAppDate);
		// 01-01_残業通知情報を取得
		// OvertimeInstructInfomation overtimeInstructInfomation = iOvertimePreProcess.getOvertimeInstruct(appCommonSettingOutput, appDate, employeeID);
		if(true) {
			// 3.事前事後の判断処理(事前事後非表示する場合)
			PrePostAtr prePostAtrJudgment = otherCommonAlgorithm.preliminaryJudgmentProcessing(ApplicationType.OVER_TIME_APPLICATION, generalAppDate, 0);
		}
		// 14_表示データを取得
		this.getDisplayData(companyID, employeeID, generalAppDate, PrePostAtr.POSTERIOR, generalAppDate, null, Optional.ofNullable(startTime), Optional.ofNullable(endTime));;
	}
}
