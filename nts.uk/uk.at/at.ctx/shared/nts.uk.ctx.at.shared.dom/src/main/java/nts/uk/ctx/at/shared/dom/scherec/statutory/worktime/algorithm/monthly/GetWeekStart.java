package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekStart;

/**
 * 週開始を取得する
 * @author shuichu_ishida
 */
public interface GetWeekStart {

	/**
	 * 週開始を取得する
	 * @param companyId 会社ID
	 * @return 週開始
	 */
	Optional<WeekStart> algorithm(String companyId);
}
