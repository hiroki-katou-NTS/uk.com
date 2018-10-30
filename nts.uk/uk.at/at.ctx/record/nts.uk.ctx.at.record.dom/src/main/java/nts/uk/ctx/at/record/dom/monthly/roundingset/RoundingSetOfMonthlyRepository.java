package nts.uk.ctx.at.record.dom.monthly.roundingset;

import java.util.List;
import java.util.Optional;

/**
 * リポジトリ：月別実績の丸め設定
 * @author shuichu_ishida
 */
public interface RoundingSetOfMonthlyRepository {

	/**
	 * 検索
	 * @param companyId 会社ID
	 * @return 月別実績の丸め設定
	 */
	Optional<RoundingSetOfMonthly> find(String companyId);

	/**
	 * 登録および更新
	 * @param roundingSetOfMonthly 月別実績の丸め設定
	 */
	void persistAndUpdate(RoundingSetOfMonthly roundingSetOfMonthly);
	
	/**
	 * 
	 * @param itemRounding
	 */
	void persistAndUpdateMonItemRound(List<ItemRoundingSetOfMonthly> lstItemRounding, String companyId);
	
	/**
	 * 削除
	 * @param companyId 会社ID
	 */
	void remove(String companyId);
	
	/**
	 * 検索.
	 *
	 * @param companyId 会社ID
	 * @return 月別実績の丸め設定
	 */
	Optional<TimeRoundingOfExcessOutsideTime> findExcout(String companyId);

	/**
	 * 登録および更新.
	 *
	 * @param roundingSetOfMonthly 月別実績の丸め設定
	 */
	void persistAndUpdate(TimeRoundingOfExcessOutsideTime roundingSetOfMonthly);
	
	/**
	 * 削除.
	 *
	 * @param companyId 会社ID
	 */
	void removeExcout(String companyId);
}
