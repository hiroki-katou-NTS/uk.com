package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagementRepo;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekStart;

/**
 * 実装：週開始を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetWeekStartImpl implements GetWeekStart {

	@Inject 
	private WeekRuleManagementRepo weekRuleManagementRepo;
	
	/** 週開始を取得する */
	@Override
	public Optional<WeekStart> algorithm(String companyId) {
		
		return weekRuleManagementRepo.find(companyId).map(c -> c.getWeekStart());
	}
}
