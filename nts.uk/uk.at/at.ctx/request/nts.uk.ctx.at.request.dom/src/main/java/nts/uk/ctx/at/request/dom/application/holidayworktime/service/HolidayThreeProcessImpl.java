package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInputRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeCheckResult;
import nts.uk.ctx.at.request.dom.application.overtime.service.CaculationTime;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class HolidayThreeProcessImpl implements HolidayThreeProcess {
	@Inject
	private HolidayWorkInputRepository holidayWorkInputRepository;
	@Inject
	private ApplicationRepository_New appRepository;
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	@Inject
	private WorkTypeRepository workTypeRep;

	@Override
	public List<CaculationTime> checkOutSideTimeTheDay(String companyID, String employeeID, String appDate,
			ApprovalFunctionSetting approvalFunctionSetting, String siftCD, List<CaculationTime> breakTimes,
			RecordWorkInfoImport recordWorkInfoImport) {
		Optional<WorkType> workType = workTypeRep.findByPK(companyID, recordWorkInfoImport.getWorkTypeCode());
		if(workType.isPresent()){
			if(workType.get().getDailyWork().isHolidayWork()){
				// 03-02-2-1_当日以外_休日出勤の場合
			}else{
				// 03-02-2-2_当日以外_休日の場合
			}
		}
		return null;
	}

	@Override
	public OvertimeCheckResult preApplicationExceededCheck(String companyId, GeneralDate appDate,
			GeneralDateTime inputDate, PrePostAtr prePostAtr, int attendanceId, List<HolidayWorkInput> holidayWorkInputs) {
		OvertimeCheckResult result = new OvertimeCheckResult();
		result.setFrameNo(-1);
		// 社員ID
		// String EmployeeId = AppContexts.user().employeeId();
		// チェック条件を確認
		if (!this.confirmCheck(companyId, prePostAtr)) {
			result.setErrorCode(0);
			return result;
		}
		// ドメインモデル「申請」を取得
		// 事前申請漏れチェック
		List<Application_New> beforeApplication = appRepository.getBeforeApplication(companyId, appDate, inputDate,
				ApplicationType.BREAK_TIME_APPLICATION.value, PrePostAtr.PREDICT.value);
		if (beforeApplication.isEmpty()) {
			// TODO: QA Pending
			result.setErrorCode(1);
			return result;
		}
		// 事前申請否認チェック
		// 否認以外：
		// 反映情報.実績反映状態＝ 否認、差し戻し
		ReflectedState_New refPlan = beforeApplication.get(0).getReflectionInformation().getStateReflectionReal();
		if (refPlan.equals(ReflectedState_New.DENIAL) || refPlan.equals(ReflectedState_New.REMAND)) {
			// 背景色を設定する
			result.setErrorCode(1);
			return result;
		}
		String beforeCid = beforeApplication.get(0).getCompanyID();
		String beforeAppId = beforeApplication.get(0).getAppID();

		// 事前申請の申請時間
		List<HolidayWorkInput> beforeOvertimeInputs = holidayWorkInputRepository.getHolidayWorkInputByAppID(beforeCid, beforeAppId)
				.stream()
				.filter(item -> item.getAttendanceType() == EnumAdaptor.valueOf(attendanceId, AttendanceType.class))
				.collect(Collectors.toList());
		if (beforeOvertimeInputs.isEmpty()) {
			result.setErrorCode(0);
			return result;
		}
		// 残業時間１～１０、加給時間
		// すべての残業枠をチェック
		for (int i = 0; i < holidayWorkInputs.size(); i++) {
			HolidayWorkInput afterTime = holidayWorkInputs.get(i);
			int frameNo = afterTime.getFrameNo();
			Optional<HolidayWorkInput> beforeTimeOpt = beforeOvertimeInputs.stream()
					.filter(item -> item.getFrameNo() == frameNo).findFirst();
			if (!beforeTimeOpt.isPresent()) {
				continue;
			}
			HolidayWorkInput beforeTime = beforeTimeOpt.get();
			if (null == beforeTime) {
				continue;
			}
			// 事前申請の申請時間＞事後申請の申請時間
			if (beforeTime.getApplicationTime().v() < afterTime.getApplicationTime().v()) {
				// 背景色を設定する
				result.setErrorCode(1);
				result.setFrameNo(frameNo);
				return result;
			}
		}
		result.setErrorCode(0);
		return result;
	}
	/**
	 * チェック条件
	 *  03-01-1_チェック条件
	 * @return True：チェックをする, False：チェックをしない
	 */
	private boolean confirmCheck(String companyId, PrePostAtr prePostAtr) {
		// 事前事後区分チェック
		if (prePostAtr.equals(PrePostAtr.PREDICT)) {
			return false;
		}
		// ドメインモデル「残業休出申請共通設定」を取得
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = this.overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyId, ApplicationType.OVER_TIME_APPLICATION.value);
		if (overtimeRestAppCommonSet.isPresent()) {
			// 残業休出申請共通設定.事前表示区分＝表示する
			if (overtimeRestAppCommonSet.get().getPreDisplayAtr().equals(UseAtr.USE)) {
				// 表示する:Trueを返す
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayThreeProcess#checkHolidayWorkOnDay(java.lang.String, java.lang.String, java.lang.String, nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting, java.lang.String, java.util.List, nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport)
	 */
	@Override
	public List<CaculationTime> checkHolidayWorkOnDay(String companyID, String employeeID, String appDate,
			ApprovalFunctionSetting approvalFunctionSetting, String siftCD, List<CaculationTime> breakTimes,
			RecordWorkInfoImport recordWorkInfoImport) {
		
		return null;
	}

	/* (non-Javadoc)
	 * 03-02-2-1_当日以外_休日出勤の場合
	 * @see nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayThreeProcess#checkOutSideTimeTheDayForHoliday(java.lang.String, java.lang.String, java.lang.String, nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting, java.lang.String, java.util.List, nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport)
	 */
	@Override
	public List<CaculationTime> checkOutSideTimeTheDayForHoliday(String companyID, String employeeID, String appDate,
			ApprovalFunctionSetting approvalFunctionSetting, String siftCD, List<CaculationTime> breakTimes,
			RecordWorkInfoImport recordWorkInfoImport) {
		if(recordWorkInfoImport != null){
			//打刻漏れチェック
			if(recordWorkInfoImport.getAttendanceStampTimeFirst() != null && recordWorkInfoImport.getLeaveStampTimeFirst() != null){
				//就業時間帯チェック
				 if(siftCD.equals(recordWorkInfoImport.getWorkTimeCode())){
					 
				 }else{
					 //Imported(申請承認)「実績内容」.就業時間帯コード != 画面上の就業時間帯
					
				 }
			}else{
				throw new BusinessException("Msg_423");
			}
		}
		return null;
	}

}
