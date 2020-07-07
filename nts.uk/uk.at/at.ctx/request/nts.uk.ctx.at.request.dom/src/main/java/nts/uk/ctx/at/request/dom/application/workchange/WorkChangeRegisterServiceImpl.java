package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;

@Stateless
@Transactional
public class WorkChangeRegisterServiceImpl implements IWorkChangeRegisterService {

	@Inject
	private RegisterAtApproveReflectionInfoService_New registerService;

	@Inject
	ApplicationApprovalService_New appRepository;

	@Inject
	NewAfterRegister_New newAfterRegister;

	@Inject
	private BasicScheduleService basicScheduleService;

	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	@Inject
	private OtherCommonAlgorithm otherCommonAlg;
	
	@Inject
	private IWorkChangeUpdateService workChangeUpdateService;

	@Override
	public ProcessResult registerData(String companyId, Application application, AppWorkChange workChange,
			List<GeneralDate> lstDateHd, Boolean isMail) {

		// アルゴリズム「2-2.新規画面登録時承認反映情報の整理」を実行する
		 registerService.newScreenRegisterAtApproveInfoReflect(application.getEmployeeID(), application);
		 
		// ドメインモデル「勤務変更申請設定」の新規登録をする
//		 appRepository.insert(application);
		// ドメインモデル「勤務変更申請」の新規登録をする
		// KAFS07
		// workChangeRepository.add(workChange);

		// 年月日Listを作成する
		GeneralDate startDateParam = application.getOpAppStartDate().isPresent()
				? application.getOpAppStartDate().get().getApplicationDate()
				: application.getAppDate().getApplicationDate();
		GeneralDate endDateParam = application.getOpAppEndDate().isPresent()
				? application.getOpAppEndDate().get().getApplicationDate()
				: application.getAppDate().getApplicationDate();
		List<GeneralDate> listDate = new ArrayList<>();

		for (GeneralDate loopDate = startDateParam; loopDate
				.beforeOrEquals(endDateParam); loopDate = loopDate.addDays(1)) {
			if (!lstDateHd.contains(loopDate)) {
				listDate.add(loopDate);
			}
		}

		// 暫定データの登録
		interimRemainDataMngRegisterDateChange.registerDateChange(companyId, application.getEmployeeID(), listDate);

		// 共通アルゴリズム「2-3.新規画面登録後の処理」を実行する
//		 return newAfterRegister.processAfterRegister(application);
		return null;
	}

	@Override
	public void checkWorkHour(AppWorkChange_Old workChange) {
		// 就業時間（開始時刻：終了時刻）
		// 開始時刻 ＞ 終了時刻
		if (workChange.getWorkTimeStart1() > workChange.getWorkTimeEnd1()) {
			// エラーメッセージ(Msg_579)
			// エラーリストにセットする
			throw new BusinessException("Msg_579");
		}
		// 就業時間２（開始時刻：終了時刻）
		// 開始時刻 ＞ 終了時刻
		if (workChange.getWorkTimeStart2() > workChange.getWorkTimeEnd2()) {
			// エラーメッセージ(Msg_580)
			// エラーリストにセットする
			throw new BusinessException("Msg_580");
		}
		// 就業時間：就業時間
		// 就業時間（終了時刻） > 就業時刻２（開始時刻）
		if (workChange.getWorkTimeEnd1() > workChange.getWorkTimeStart2()) {
			// エラーメッセージ(Msg_581)
			// エラーリストにセットする
			throw new BusinessException("Msg_581");
		}
	}

	@Override
	public void checkBreakTime1(AppWorkChange_Old workChange) {
		// 開始時刻 ＞ 終了時刻
		if (workChange.getBreakTimeStart1() > workChange.getBreakTimeEnd1()) {
			// エラーメッセージ(Msg_582)
			// エラーリストにセットする
			throw new BusinessException("Msg_582");
		}
	}

	@Override
	public boolean isTimeRequired(String workTypeCD) {
		SetupType setupType = basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCD);
		if (setupType == SetupType.REQUIRED) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void registerProcess(Boolean mode, String companyId, Application application, AppWorkChange appWorkchange,
			List<GeneralDate> lstDates, Boolean isMail) {
		if (mode) {
			this.registerData(companyId, application, appWorkchange, lstDates, isMail);
		}else {
			workChangeUpdateService.updateWorkChange(companyId, application, appWorkchange);
		}
		
	}
}
