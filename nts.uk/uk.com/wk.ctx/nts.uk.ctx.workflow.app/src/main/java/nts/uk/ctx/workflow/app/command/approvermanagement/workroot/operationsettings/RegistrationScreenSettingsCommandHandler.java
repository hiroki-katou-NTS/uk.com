package nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApprovalLevelNo;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettings;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettingsRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverSettingScreenInfor;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.SettingTypeUsed;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.自分の承認者の運用設定.App.登録画面の設定の登録をする.登録画面の設定の登録をする
 * @author NWS-DungDV
 */
@Stateless
public class RegistrationScreenSettingsCommandHandler extends CommandHandler<RegistrationScreenSettingsCommand> {

	@Inject
	private ApproverOperationSettingsRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<RegistrationScreenSettingsCommand> context) {
		RegistrationScreenSettingsCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		
		Optional<ApproverOperationSettings> approverOperationSettingsOptional = this.repo.get(cid);
		
		if (!approverOperationSettingsOptional.isPresent()) {
			return;
		}
		
		ApproverOperationSettings domain = approverOperationSettingsOptional.get();
		
		ApprovalLevelNo approvalLevelNo = EnumAdaptor.valueOf(command.getApprovalLevelNo(), ApprovalLevelNo.class);
		List<SettingTypeUsed> settingTypeUseds = command.getSettingTypeUseds().stream()
				.map(x -> x.toValueObject())
				.collect(Collectors.toList());
		ApproverSettingScreenInfor approverSettingScreenInfor = command.getApproverSettingScreenInfor().toValueObject();
		domain.setApprovalLevelNo(approvalLevelNo);
		domain.setSettingTypeUseds(settingTypeUseds);
		domain.setApproverSettingScreenInfor(approverSettingScreenInfor);
		
		this.repo.update(domain);
	}
}
