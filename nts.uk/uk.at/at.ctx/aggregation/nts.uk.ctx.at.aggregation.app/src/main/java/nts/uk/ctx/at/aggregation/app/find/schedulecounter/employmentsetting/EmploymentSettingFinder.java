package nts.uk.ctx.at.aggregation.app.find.schedulecounter.employmentsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSettingRepository;

// 利用単位の設定を取得する
@Stateless
public class EmploymentSettingFinder {

	@Inject
	private CriterionAmountUsageSettingRepository criterionAmountUsageSettingRepository;
	
	public CriterionAmountUsageSettingDto getSetting(String cid) {
		
		return CriterionAmountUsageSettingDto.fromDomain(criterionAmountUsageSettingRepository.get(cid).orElse(null));
	}
	
}
