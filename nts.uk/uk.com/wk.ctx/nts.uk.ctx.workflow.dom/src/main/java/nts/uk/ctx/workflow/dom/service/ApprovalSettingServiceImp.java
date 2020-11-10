package nts.uk.ctx.workflow.dom.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.BooleanUtils;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApproverRegisterSet;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingWF;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingWFRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.UseClassification;
@Stateless
public class ApprovalSettingServiceImp implements ApprovalSettingService {
	
	@Inject
	private ApprovalSettingRepository approvalSettingRepository;
	
	@Inject
	private HrApprovalRouteSettingWFRepository hrApprovalRouteSettingRepository;
	@Override
	public ApproverRegisterSet getSettingUseUnit(String companyId, Integer systemCategory) {
		// 「承認単位の利用設定」の初期値を作成する
		ApproverRegisterSet approverRegsterSet = new ApproverRegisterSet(UseClassification.DO_NOT_USE,
				UseClassification.DO_NOT_USE,
				UseClassification.DO_NOT_USE);
		Optional<ApprovalSetting> approvalSettingOp = Optional.empty();
		Optional<HrApprovalRouteSettingWF> hrApprovalOp = Optional.empty();
		// INPUT.システム区分をチェックする
		if (systemCategory == SettingUseUnitServiceImp.EMPLOYMENT) {
			approvalSettingOp = approvalSettingRepository.getApprovalByComId(companyId);
			
		} else if (systemCategory == SettingUseUnitServiceImp.HUMAN_RESOURCE) {
			hrApprovalOp = hrApprovalRouteSettingRepository.getDomainByCid(companyId);
			
		}
		// データがあるかチェックする
		if (approvalSettingOp.isPresent() || hrApprovalOp.isPresent()) {
			if (systemCategory == SettingUseUnitServiceImp.EMPLOYMENT) {
				approverRegsterSet = approvalSettingOp.get().getApproverRegsterSet();
			} else {
				HrApprovalRouteSettingWF hrApprovalRouteSetting = hrApprovalOp.get();
				approverRegsterSet = new ApproverRegisterSet(
						EnumAdaptor.valueOf(BooleanUtils.toInteger(hrApprovalRouteSetting.comMode), UseClassification.class),
						EnumAdaptor.valueOf(BooleanUtils.toInteger(hrApprovalRouteSetting.devMode), UseClassification.class),
						EnumAdaptor.valueOf(BooleanUtils.toInteger(hrApprovalRouteSetting.empMode), UseClassification.class));
			}
		}
		
		return approverRegsterSet;
	}

}
