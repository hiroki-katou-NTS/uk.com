package nts.uk.ctx.at.request.dom.application.common.service.newscreen;

import javax.ejb.Stateless;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;

/**
 * 2-2.新規画面登録時承認反映情報の整理
 * 
 * @author ducpm
 *
 */
@Stateless
public class RegisterAtApproveReflectionInfoDefault_New implements RegisterAtApproveReflectionInfoService_New {
	
	@Override
	public void newScreenRegisterAtApproveInfoReflect(String SID, Application_New application) {
		// アルゴリズム「承認情報の整理」を実行する
		//TODO: waiting new pub
		// アルゴリズム「実績反映状態の判断」を実行する
		//TODO: waiting new pub
		//承認完了フラグがtrue
		boolean approvalCompletionFlag = true;
		if (approvalCompletionFlag) {
			// 「反映情報」．実績反映状態を「反映待ち」にする
			application.getReflectionInformation().setStateReflectionReal(ReflectedState_New.WAITREFLECTION);
		}
	}
}
