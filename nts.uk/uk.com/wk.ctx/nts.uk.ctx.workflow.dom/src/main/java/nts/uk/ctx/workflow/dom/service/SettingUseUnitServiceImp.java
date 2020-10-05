package nts.uk.ctx.workflow.dom.service;

import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingRepository;
import nts.uk.ctx.workflow.dom.service.output.SettingUseUnitOutput;

public class SettingUseUnitServiceImp implements SettingUseUnitService{
	public static Integer EMPLOYMENT = 0;  // 就業の場合
	public static Integer HUMAN_RESOURCE = 0; // 人事の場合
	@Inject
	private ApprovalSettingRepository approvalSettingRepository;
	
	@Inject
	private HrApprovalRouteSettingRepository hrApprovalRouteSettingRepository;
	@Override
	public SettingUseUnitOutput start(String companyId, Integer systemCategory) {
		Optional<ApprovalSetting> approvalSettingOp = Optional.empty();
		Optional<HrApprovalRouteSetting> hrApprovalOp = Optional.empty();
		Boolean mode;
		// INPUT.システム区分をチェックする
		if (systemCategory == EMPLOYMENT) {
			approvalSettingOp = approvalSettingRepository.getApprovalByComId(companyId);
			
		} else if (systemCategory == HUMAN_RESOURCE) {
			hrApprovalOp = hrApprovalRouteSettingRepository.getDomainByCid(companyId);
			
		}
		// データがあるかチェックする
		if (approvalSettingOp.isPresent() || hrApprovalOp.isPresent()) { // データがある
			mode = false;
		} else { // データがない
			mode = true;
		}
		
		return new SettingUseUnitOutput(mode, approvalSettingOp, hrApprovalOp);
	}

}
