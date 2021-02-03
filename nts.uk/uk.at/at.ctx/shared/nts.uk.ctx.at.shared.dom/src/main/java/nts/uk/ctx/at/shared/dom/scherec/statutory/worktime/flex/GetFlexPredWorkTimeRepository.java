package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex;

import java.util.Optional;

/**
 * リポジトリ：会社別フレックス勤務集計方法
 * @author shuichu_ishida
 */
public interface GetFlexPredWorkTimeRepository {

	/**
	 * 検索
	 * @param companyId 会社ID
	 * @return 会社別フレックス勤務集計方法
	 */
	Optional<GetFlexPredWorkTime> find(String companyId);

	/**
	 * 登録および更新
	 * @param roundingSetOfMonthly 会社別フレックス勤務集計方法
	 */
	void persistAndUpdate(GetFlexPredWorkTime getFlexPredWorkTime);
	
	/**
	 * 削除
	 * @param companyId 会社ID
	 */
	void remove(String companyId);
}
