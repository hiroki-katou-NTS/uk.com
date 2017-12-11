package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
@Stateless
@Transactional
public class WorkChangeRegisterServiceImpl implements IWorkChangeRegisterService {

	@Inject
	private NewBeforeRegister newBeforeRegister;

	@Inject
	private RegisterAtApproveReflectionInfoService registerService;

	@Inject
	ApplicationRepository appRepository;
	
	@Inject 
	NewAfterRegister newAfterRegister;
	
	@Inject
	private IAppWorkChangeRepository workChangeRepository;
	
	@Override
	public String registerData(AppWorkChange workChange, Application app) {
		// アルゴリズム「勤務変更申請就業時間チェックの内容」を実行する
		//checkWorkHour(workChange);
		
		// アルゴリズム「勤務変更申請休憩時間１チェックの内容」を実行する
		//checkBreakTime1(workChange);
		
		// アルゴリズム「2-1.新規画面登録前の処理」を実行する
		newBeforeRegister.processBeforeRegister(app);
		
		// アルゴリズム「2-2.新規画面登録時承認反映情報の整理」を実行する
		registerService.newScreenRegisterAtApproveInfoReflect(app.getApplicantSID(), app);
		
		// ドメインモデル「勤務変更申請設定」の新規登録をする
		appRepository.addApplication(app);
		workChangeRepository.add(workChange);
		
		//共通アルゴリズム「2-3.新規画面登録後の処理」を実行する
		String mails = newAfterRegister.processAfterRegister(app);
		
		return mails;
	}

	@Override
	public void checkWorkHour(AppWorkChange workChange) {
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
	public void checkBreakTime1(AppWorkChange workChange) {
		// 開始時刻 ＞ 終了時刻
		if (workChange.getBreakTimeStart1() > workChange.getBreakTimeEnd1()) {
			// エラーメッセージ(Msg_582)
			// エラーリストにセットする
			throw new BusinessException("Msg_582");
		}
	}
}
