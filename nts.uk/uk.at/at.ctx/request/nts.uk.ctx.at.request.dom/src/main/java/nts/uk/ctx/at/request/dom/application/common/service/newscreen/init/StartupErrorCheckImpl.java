package nts.uk.ctx.at.request.dom.application.common.service.newscreen.init;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class StartupErrorCheckImpl implements StartupErrorCheckService {

	@Override
	public void startupErrorCheck(GeneralDate baseDate, ApprovalRootContentImport_New approvalRootContentImport) {
		// ドメインモデル「申請承認機能設定」．「申請利用設定」．利用区分をチェックする
		UseAtr useAtr = UseAtr.USE;
		if(useAtr.equals(UseAtr.NOTUSE)){
			throw new BusinessException("Msg_323");
		}
		
		// ドメインモデル「申請設定」．承認ルートの基準日をチェックする
		BaseDateFlg baseDateFlg = BaseDateFlg.SYSTEM_DATE;
		if(baseDateFlg.equals(BaseDateFlg.APP_DATE)){
			return;
		}
		switch (approvalRootContentImport.getErrorFlag()) {
			case NO_APPROVER:
				throw new BusinessException("Msg_324");
			case APPROVER_UP_10:
				throw new BusinessException("Msg_237");
			case NO_CONFIRM_PERSON:
				throw new BusinessException("Msg_238");
			default:
				break;
		}
	}

}
