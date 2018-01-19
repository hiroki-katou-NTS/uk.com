package nts.uk.ctx.at.record.dom.monthlyaggrmethod;

import java.util.Optional;

/**
 * リポジトリ：月次集計の法定内振替順設定
 * @author shuichu_ishida
 */
public interface LegalTransferOrderSetOfAggrMonthlyRepository {

	/**
	 * 検索
	 * @param companyId 会社ID
	 * @return 月次集計の法定内振替順設定
	 */
	Optional<LegalTransferOrderSetOfAggrMonthly> find(String companyId);
	
	/**
	 * 追加
	 * @param legalTransferOrderSetOfAggrMonthly 月次集計の法定内振替順設定
	 */
	void insert(LegalTransferOrderSetOfAggrMonthly legalTransferOrderSetOfAggrMonthly);
	
	/**
	 * 更新
	 * @param legalTransferOrderSetOfAggrMonthly 月次集計の法定内振替順設定
	 */
	void update(LegalTransferOrderSetOfAggrMonthly legalTransferOrderSetOfAggrMonthly);
	
	/**
	 * 削除
	 * @param companyId 会社ID
	 */
	void remove(String companyId);
}
