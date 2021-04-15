package nts.uk.ctx.at.request.app.find.application.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tutk
 *
 */

@Stateless
public class GetDataApprovalRoot {
	
//	@Inject
//	private ApplicationSettingRepository appSettingRepo;

	public void getDataApprovalRoot() {
		String companyId = AppContexts.user().companyId();
//		Optional<ApplicationSetting> appSet = appSettingRepo.getApplicationSettingByComID(companyId);
		//ドメインモデル「申請設定」．承認ルートの基準日をチェックする (Kiểm tra ドメインモデル「申請設定」．承認ルートの基準日)
		//if = 0(system date) else if = 1	
		
//		if (appSet.map(x -> x.getBaseDateFlg()).get().value == 0) {
			
//		}else {
			// 「申請設定」．承認ルートの基準日が申請対象日時点の場合
//		}
	}

}
