package nts.uk.ctx.workflow.dom.service;

import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingRepository;
import nts.uk.ctx.workflow.dom.service.output.SettingUseUnitOutput;

public class SettingUseUnitRegisterServiceImp implements SettingUseUnitRegisterService {
	
	@Inject
	private ApprovalSettingRepository approvalSettingRepository;
	
	@Inject
	private HrApprovalRouteSettingRepository hrApprovalRouteSettingRepository;
	
	@Override
	public void register(Integer systemCategory, SettingUseUnitOutput settingUseUnitOutput) {
		Optional<ApprovalSetting> approvalSettingOp = settingUseUnitOutput.getApprovalSetting();
		Optional<HrApprovalRouteSetting> hrApprovalRouteSettingOp = settingUseUnitOutput.getHrApprovalRouteSetting();
		if (systemCategory == SettingUseUnitServiceImp.EMPLOYMENT) {
			if (!approvalSettingOp.isPresent()) return;
			ApprovalSetting approvalSetting = approvalSettingOp.get();
			if (settingUseUnitOutput.getMode()) {
				// insert
				approvalSettingRepository.insert(approvalSetting);
			} else {
				// update
				approvalSettingRepository.update(approvalSetting);
			}
		} else if (systemCategory == SettingUseUnitServiceImp.HUMAN_RESOURCE) {
			if (!hrApprovalRouteSettingOp.isPresent()) return;
			HrApprovalRouteSetting hrApprovalRouteSetting = hrApprovalRouteSettingOp.get();
			if (settingUseUnitOutput.getMode()) {
				// insert
				hrApprovalRouteSettingRepository.insert(hrApprovalRouteSetting);
			} else {
				// update
				hrApprovalRouteSettingRepository.update(hrApprovalRouteSetting);
			}
		}

	}

}
