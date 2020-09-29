package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex;

import java.util.Optional;

/**
 * リポジトリ：フレックス勤務の月別集計設定
 * @author shuichu_ishida
 */
public interface MonthlyAggrSetOfFlexRepository {

	/**
	 * 検索
	 * @param companyId 会社ID
	 * @return フレックス勤務の月別集計設定
	 */
	Optional<MonthlyAggrSetOfFlex> find(String companyId);

	/**
	 * 登録および更新
	 * @param monthlyAggrSetOfFlex フレックス勤務の月別集計設定
	 */
	void persistAndUpdate(MonthlyAggrSetOfFlex monthlyAggrSetOfFlex);
	
	/**
	 * 削除
	 * @param companyId 会社ID
	 */
	void remove(String companyId);
}
