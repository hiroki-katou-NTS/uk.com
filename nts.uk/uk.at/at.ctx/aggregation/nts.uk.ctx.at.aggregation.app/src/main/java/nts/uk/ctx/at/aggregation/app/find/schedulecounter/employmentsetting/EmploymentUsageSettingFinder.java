package nts.uk.ctx.at.aggregation.app.find.schedulecounter.employmentsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSettingRepository;

/**
 * 利用単位の設定を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.目安金額.App.利用単位の設定を取得する
 * @author hoangdd
 *
 */

@Stateless
public class EmploymentUsageSettingFinder {

	@Inject
	private CriterionAmountUsageSettingRepository criterionAmountUsageSettingRepository;
	
	public CriterionAmountUsageSettingDto getSetting(String cid) {
		
		return CriterionAmountUsageSettingDto.fromDomain(criterionAmountUsageSettingRepository.get(cid).orElse(null));
	}
	
}
