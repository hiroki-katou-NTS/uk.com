package nts.uk.ctx.workflow.dom.service;

import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingWF;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingWFRepository;
import nts.uk.ctx.workflow.dom.service.output.SettingUseUnitOutput;


public class SettingUseUnitRegisterServiceImp implements SettingUseUnitRegisterService {
	
	@Inject
	private ApprovalSettingRepository approvalSettingRepository;
	
	@Inject
	private HrApprovalRouteSettingWFRepository hrApprovalRouteSettingRepository;
	
	@Override
	public void register(Integer systemCategory, SettingUseUnitOutput settingUseUnitOutput) {
		Optional<ApprovalSetting> approvalSettingOp = settingUseUnitOutput.getApprovalSetting();
		Optional<HrApprovalRouteSettingWF> hrApprovalRouteSettingOp = settingUseUnitOutput.getHrApprovalRouteSetting();
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
			HrApprovalRouteSettingWF hrApprovalRouteSetting = hrApprovalRouteSettingOp.get();
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
