package nts.uk.ctx.workflow.dom.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingWF;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingWFRepository;
import nts.uk.ctx.workflow.dom.service.output.SettingUseUnitOutput;

@Stateless
public class SettingUseUnitRegisterServiceImp implements SettingUseUnitRegisterService {
	
	@Inject
	private ApprovalSettingRepository approvalSettingRepository;
	
	@Inject
	private HrApprovalRouteSettingWFRepository hrApprovalRouteSettingRepository;
	
	@Override
	public void register(Integer systemCategory, SettingUseUnitOutput settingUseUnitOutput) {
		Optional<ApprovalSetting> approvalSettingOp = settingUseUnitOutput.getApprovalSetting();
		Optional<HrApprovalRouteSettingWF> hrApprovalRouteSettingOp = settingUseUnitOutput.getHrApprovalRouteSetting();
		if (systemCategory == SettingUseUnitServiceImp.EMPLOYMENT) { // 就業の場合
			if (!approvalSettingOp.isPresent()) return;
			ApprovalSetting approvalSetting = approvalSettingOp.get();
			if (settingUseUnitOutput.getMode()) {
				// ドメイン「承認設定」をInsertする
				approvalSettingRepository.insert(approvalSetting);
			} else {
				// ドメイン「承認設定」をUpdateする
				approvalSettingRepository.updateForUnit(approvalSetting);
			}
		} else if (systemCategory == SettingUseUnitServiceImp.HUMAN_RESOURCE) { // 人事の場合
			if (!hrApprovalRouteSettingOp.isPresent()) return;
			HrApprovalRouteSettingWF hrApprovalRouteSetting = hrApprovalRouteSettingOp.get();
			if (settingUseUnitOutput.getMode()) {
				// ドメイン「人事承認ルート設定」をInsertする
				hrApprovalRouteSettingRepository.insert(hrApprovalRouteSetting);
			} else {
				// ドメイン「人事承認ルート設定」をUpdateする
				hrApprovalRouteSettingRepository.update(hrApprovalRouteSetting);
			}
		}

	}

}
