package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.legaltransferorder;

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
	 * 登録および更新
	 * @param legalTransferOrderSetOfAggrMonthly 月次集計の法定内振替順設定
	 */
	void persistAndUpdate(LegalTransferOrderSetOfAggrMonthly legalTransferOrderSetOfAggrMonthly);
	
	/**
	 * 削除
	 * @param companyId 会社ID
	 */
	void remove(String companyId);
}
