package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.premiumtarget.getvacationaddtime;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;

/**
 * 加算設定を取得する
 * @author shuichu_ishida
 */
public interface GetAddSet {

	/**
	 * 取得
	 * @param companyId 会社ID
	 * @param workingSystem 労働制
	 * @param premiumAtr 割増区分
	 * @return 加算設定
	 */
	Optional<AddSet> get(String companyId, WorkingSystem workingSystem, PremiumAtr premiumAtr);
}
