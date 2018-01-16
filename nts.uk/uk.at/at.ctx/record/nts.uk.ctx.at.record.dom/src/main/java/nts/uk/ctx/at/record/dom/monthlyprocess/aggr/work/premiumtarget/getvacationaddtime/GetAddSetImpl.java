package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;

/**
 * 加算設定を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetAddSetImpl implements GetAddSet {

	/** 取得 */
	@Override
	public Optional<AddSet> get(String companyId, WorkingSystem workingSystem, PremiumAtr premiumAtr) {
		// TODO Auto-generated method stub
		return null;
	}
}
