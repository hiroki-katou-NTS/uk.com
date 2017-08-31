package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.registerapprovereflection.service.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.ProcessBeforeRegisterNewScreenService;
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
	ProcessBeforeRegisterNewScreenService processBeforeRegister;
	@Inject
	GoBackDirectlyCommonSettingRepository goBackDirectCommonSetRepo;

	@Override
	public void register(int approvalRoot, String employeeID, Application application, GoBackDirectly goBackDirectly) {
		String companyID = AppContexts.user().companyId();
		/**
		 * アルゴリズム「直行直帰登録前チェック」を実行する
		 */
		// Goi thang 2.1 len
		processBeforeRegister.processBeforeRegisterNewScreen(companyID, employeeID,
				application.getInputDate().toString(), goBackDirectly.getWorkTimeEnd1().toString(),
				application.getPrePostAtr().value, approvalRoot, application.getApplicationType().toString());
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

		}

		return null;
	}

	@Override
	public CheckValidOutput goBackLateEarlyCheckValidity(GoBackDirectly goBackDirectly,
			GoBackDirectlyCommonSetting goBackCommonSet) {
		// TODO Auto-generated method stub
		CheckValidOutput result = new CheckValidOutput();
		result.isCheck = false;
		if (goBackCommonSet.getWorkChangeFlg() == WorkChangeFlg.CHANGE) {

		}
		return result;
	}

}
