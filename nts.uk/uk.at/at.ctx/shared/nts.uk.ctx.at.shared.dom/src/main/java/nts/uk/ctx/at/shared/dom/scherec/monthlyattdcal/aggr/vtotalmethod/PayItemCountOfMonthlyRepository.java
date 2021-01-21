package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

import java.util.Optional;

/**
 * リポジトリ：月別実績の給与項目カウント
 * @author shuichu_ishida
 */
public interface PayItemCountOfMonthlyRepository {
	
	/**
	 * 検索
	 * @param companyId 会社ID
	 * @return 月別実績の給与項目カウント
	 */
	Optional<PayItemCountOfMonthly> find(String companyId);

	/**
	 * 登録および更新
	 * @param payItemCountOfMonthly 月別実績の給与項目カウント
	 */
	void persistAndUpdate(PayItemCountOfMonthly payItemCountOfMonthly);
	
	/**
	 * 削除
	 * @param companyId 会社ID
	 */
	void remove(String companyId);
}
