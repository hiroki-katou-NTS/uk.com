package nts.uk.ctx.at.shared.app.find.workrule.func;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSetting;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkMntSetRepository;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkSet;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManageRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author HoangNDH
 * The Class SelectFunctionFinder.
 */
@Stateless
public class SelectFunctionFinder {
	
	/** The agg setting repo. */
	@Inject
	AggDeformedLaborSettingRepository aggSettingRepo;
	
	/** The work multiple repo. */
	@Inject
	WorkManagementMultipleRepository workMultipleRepo;
	
	/** The temp work repo. */
	@Inject
	TemporaryWorkUseManageRepository tempWorkRepo;
	
	/** The flex work repo. */
	@Inject
	FlexWorkMntSetRepository flexWorkRepo;
	
	/**
	 * Find all setting.
	 *
	 * @return the select function dto
	 */
	public SelectFunctionDto findAllSetting() {
		String companyId = AppContexts.user().companyId();
		
		SelectFunctionDto dto = new SelectFunctionDto();
		
		// ドメインモデル「フレックス勤務の設定」を取得する
		Optional<FlexWorkSet> optFlexWorkSet = flexWorkRepo.find(companyId);
		
		if (optFlexWorkSet.isPresent()) {
			dto.setFlexWorkManagement(optFlexWorkSet.get().getUseFlexWorkSetting().value);
		}
		
		// ドメインモデル「変形労働の集計設定」を取得する
		Optional<AggDeformedLaborSetting> optAggSetting = aggSettingRepo.findByCid(companyId);
		
		if (optAggSetting.isPresent()) {
			dto.setUseAggDeformedSetting(optAggSetting.get().getUseDeformedLabor().value);
		}
		
		// ドメインモデル「臨時勤務利用管理」を取得する
		Optional<TemporaryWorkUseManage> optTempWorkUse = tempWorkRepo.findByCid(companyId);
		
		if (optTempWorkUse.isPresent()) {
			dto.setUseTempWorkUse(optTempWorkUse.get().getUseClassification().value);
		}
		
		// ドメインモデル「複数回勤務管理」を取得する
		Optional<WorkManagementMultiple> optWorkMultiple = workMultipleRepo.findByCode(companyId);
		
		if (optWorkMultiple.isPresent()) {
			dto.setUseWorkManagementMultiple(optWorkMultiple.get().getUseATR().value);
		}
		
		return dto;
	}
}
