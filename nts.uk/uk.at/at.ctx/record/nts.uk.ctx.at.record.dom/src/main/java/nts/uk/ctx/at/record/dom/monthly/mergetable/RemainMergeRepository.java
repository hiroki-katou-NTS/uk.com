package nts.uk.ctx.at.record.dom.monthly.mergetable;

import java.util.Optional;

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
