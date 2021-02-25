package nts.uk.ctx.workflow.dom.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingWF;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingWFRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.UseClassification;
import nts.uk.ctx.workflow.dom.service.output.SettingUseUnitOutput;

@Stateless
public class SettingUseUnitServiceImp implements SettingUseUnitService{
	public static Integer EMPLOYMENT = 0;  // 就業の場合
	public static Integer HUMAN_RESOURCE = 1; // 人事の場合
	@Inject
	private ApprovalSettingRepository approvalSettingRepository;
	
	@Inject
	private HrApprovalRouteSettingWFRepository hrApprovalRouteSettingRepository;
	@Override
	public SettingUseUnitOutput start(String companyId, Integer systemCategory) {
		Optional<ApprovalSetting> approvalSettingOp = Optional.empty();
		Optional<HrApprovalRouteSettingWF> hrApprovalOp = Optional.empty();
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
	@Override
	public void checkBeforeRegister(UseClassification companyUnit, UseClassification workplaceUnit,
			UseClassification employeeUnit) {
		// INPUTの「会社単位、職場部門単位、個人単位」3つ単位をチェックする
		if (companyUnit == UseClassification.DO_NOT_USE 
			&& workplaceUnit ==  UseClassification.DO_NOT_USE
			&& employeeUnit == UseClassification.DO_NOT_USE) { // 3つ単位は全て表示しないを設定する場合
			throw new BusinessException("Msg_1985");
		}
		
	}

}
