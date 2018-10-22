package nts.uk.ctx.at.record.dom.monthly.mergetable;

import java.util.List;
import java.util.Optional;

import nts.arc.time.YearMonth;

/**
 * リポジトリ：残数系
 * @author shuichi_ishida
 */
public interface RemainMergeRepository {

	/**
	 * 検索
	 * @param key 月別実績プライマリキー
	 * @return 残数系データ
	 */
	Optional<RemainMerge> find(MonthMergeKey key);
	
	/**
	 * 検索
	 * @param employeeIds list employee ids
	 * @param yearMonths list yearMonths
	 * @return 残数系データ
	 */
	List<RemainMerge> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths);
	
	/**
	 * 登録および更新
	 * @param key 月別実績プライマリキー
	 * @param domains 残数系データ
	 */
	void persistAndUpdate(MonthMergeKey key, RemainMerge domains);
	
	/**
	 * 削除
	 * @param key 月別実績プライマリキー
	 */
	void remove(MonthMergeKey key);
}
