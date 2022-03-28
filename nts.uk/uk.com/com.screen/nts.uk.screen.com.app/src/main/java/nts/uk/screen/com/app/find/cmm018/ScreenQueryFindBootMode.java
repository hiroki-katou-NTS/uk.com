package nts.uk.screen.com.app.find.cmm018;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings.ApproverOperationSettingsDto;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettings;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettingsRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM018_承認者の登録.CMM018_承認者の登録（就業・人事）.X：目次.ユースケース.モードを調べて起動する.Ｘ：起動するモードを調べる
 * @author NWS-DungDV
 */
@Stateless
public class ScreenQueryFindBootMode {

	@Inject
	private ApproverOperationSettingsRepository approverOperationSettingsRepo;
	
	public ApproverOperationSettingsDto get() {
		String cid = AppContexts.user().companyId();
		
		Optional<ApproverOperationSettings> approverOperationSettings = this.approverOperationSettingsRepo.get(cid);
		
		if (!approverOperationSettings.isPresent()) {
			return null;
		}
		
		return ApproverOperationSettingsDto.fromDomain(approverOperationSettings.get());
	}
	
}
