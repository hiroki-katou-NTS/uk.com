package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare;

import java.util.Optional;

/**
 * リポジトリ：申告設定
 * @author shuichi_ishida
 */
public interface DeclareSetRepository {

	/**
	 * 取得
	 * @param companyId 会社ID
	 * @return 申告設定
	 */
	Optional<DeclareSet> find(String companyId);
	
	/**
	 * 追加または更新
	 * @param declareSet 申告設定
	 */
	void addOrUpdate(DeclareSet declareSet);
}
