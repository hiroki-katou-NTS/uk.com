package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeProcessRegister;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.UseAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.CheckAtr;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive.WorkChangeFlg;
import nts.uk.shr.com.context.AppContexts;

/**
 * 直行直帰登録
 * 
 * @author ducpm
 */
@Stateless
public class GoBackDirectlyDefault implements GoBackDirectlyService {
	@Inject
	RegisterAtApproveReflectionInfoService registerAppReplection;
	@Inject
	GoBackDirectlyRepository goBackDirectRepo;
	@Inject
	ApplicationRepository appRepo;
	@Inject
	NewBeforeProcessRegister processBeforeRegister;
	@Inject
	GoBackDirectlyCommonSettingRepository goBackDirectCommonSetRepo;

	/**
	 * 
	 */
	@Override
	// public void register(int approvalRoot, String employeeID, Application
	// application, GoBackDirectly goBackDirectly) {
	public void register(int approvalRoot, String employeeID, String appID) {
		String companyID = AppContexts.user().companyId();
		/**
		 * アルゴリズム「直行直帰登録前チェック」を実行する
		 */
		GoBackDirectly goBackDirectly = goBackDirectRepo.findByApplicationID(companyID, appID).get();
		Application application = appRepo.getAppById(companyID, appID).get();
		GeneralDate date = application.getApplicationDate();
		PrePostAtr prePost = application.getPrePostAtr();
		ApplicationType appType = application.getApplicationType();
		processBeforeRegister.processBeforeRegister(companyID, employeeID, date, prePost, approvalRoot, appType.value);
		// if hasError return
		// if no Error
		// アルゴリズム「直行直帰するチェック」を実行する
		if (this.goBackDirectCheck(goBackDirectly) == GoBackDirectAtr.IS) {
			// アルゴリズム「直行直帰遅刻早退のチェック」を実行する

		} else {
			// メッセージ（Msg_338）を表示する
			throw new BusinessException("Msg_338");
		}

		/**
		 * 2-2.新規画面登録時承認反映情報の整理
		 */
		registerAppReplection.newScreenRegisterAtApproveInfoReflect(employeeID, application);
		/**
		 * ドメインモデル「直行直帰申請」の新規登録する
		 */
		goBackDirectRepo.insert(goBackDirectly);
	}

	@Override
	public GoBackDirectAtr goBackDirectCheck(GoBackDirectly goBackDirectly) {
		// //勤務直行1の内容
		// boolean mergeNode1 = false;
		// boolean mergeNode2 = false;
		// if(goBackDirectly.getGoWorkAtr1() == UseAtr.NOTUSE) {
		// //勤務直帰1の内容
		// if(goBackDirectly.getBackHomeAtr1() == UseAtr.NOTUSE) {
		// //勤務直行2の内容
		// if(goBackDirectly.getGoWorkAtr2() == UseAtr.NOTUSE) {
		// //勤務直帰2の内容
		// if(goBackDirectly.getBackHomeAtr2() == UseAtr.NOTUSE) {
		// return GoBackDirectAtr.NOT;
		// }
		// }else {
		// mergeNode2 = true;
		// }
		// }else {
		// mergeNode1 = true;
		// }
		// }else {
		// mergeNode1 = true;
		// }
		if (goBackDirectly.getGoWorkAtr1() == UseAtr.NOTUSE && goBackDirectly.getBackHomeAtr1() == UseAtr.NOTUSE
				&& goBackDirectly.getGoWorkAtr2() == UseAtr.NOTUSE
				&& goBackDirectly.getBackHomeAtr2() == UseAtr.NOTUSE) {
			return GoBackDirectAtr.NOT;
		} else {
			return GoBackDirectAtr.IS;
		}
	}

	@Override
	public GoBackDirectLateEarlyOuput goBackDirectLateEarlyCheck(GoBackDirectly goBackDirectly) {
		// ドメインモデル「直行直帰申請共通設定」を取得する
		String companyID = AppContexts.user().companyId();
		GoBackDirectlyCommonSetting goBackCommonSet = goBackDirectCommonSetRepo.findByCompanyID(companyID).get();

		// 設定：直行直帰申請共通設定.早退遅刻設定
		if (goBackCommonSet.getLateLeaveEarlySettingAtr() != CheckAtr.NOTCHECK) {
			// check Valid 1
			CheckValidOutput validOut = this.goBackLateEarlyCheckValidity(goBackDirectly, goBackCommonSet);
			if (validOut.isCheckValid1 && validOut.isCheckValid2) {
				// アルゴリズム「1日分の勤怠時間を仮計算」を実行する
				// GOI 1日分の勤怠時間を仮計算 ben HIbetsuJisseki
				int attendanceTime = 0;
				if (attendanceTime < 0) {
					throw new BusinessException("Msg_296");
				} else {
					// Lai check thang Time lan nua
					// Merge Node 1
					if (attendanceTime < 0) {
						throw new BusinessException("Msg_295");
					}
				}
			}
		}

		return null;
	}

	@Override
	public CheckValidOutput goBackLateEarlyCheckValidity(GoBackDirectly goBackDirectly,
			GoBackDirectlyCommonSetting goBackCommonSet) {
		CheckValidOutput result = new CheckValidOutput();
		result.isCheckValid1 = false;
		result.isCheckValid2 = false;
		//

		if (goBackCommonSet.getWorkChangeFlg() == WorkChangeFlg.CHANGE
				&& goBackCommonSet.getWorkChangeFlg() == WorkChangeFlg.DECIDECHANGE) {

		} else if (goBackCommonSet.getWorkChangeFlg() == WorkChangeFlg.NOTCHANGE
				&& goBackCommonSet.getWorkChangeFlg() == WorkChangeFlg.DECIDENOTCHANGE) {
			// ・出張申請.勤務種類 ＝空白
			// ・出張申請.就業時間帯 ＝空白
			// 勤務種類及び銃所時間帯はチェック対象外
		}
		// MERGE NODE 1
		if (goBackDirectly.getGoWorkAtr1() == UseAtr.USE) {
			// 入力なし
			if (goBackDirectly.getWorkTimeStart1().v() == 0) {
				// Gan endDate = ""
			}
		}

		// 申請時に決める
		// If dc check thi tra ra gia tri
		return result;
	}

}
