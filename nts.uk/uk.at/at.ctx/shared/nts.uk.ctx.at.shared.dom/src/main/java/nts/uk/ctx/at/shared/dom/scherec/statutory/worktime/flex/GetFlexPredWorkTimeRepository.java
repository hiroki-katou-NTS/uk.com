package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex;

import java.util.Optional;

/**
 * リポジトリ：フレックス勤務所定労働時間取得
 * @author shuichu_ishida
 */
public interface GetFlexPredWorkTimeRepository {

	/**
	 * 検索
	 * @param companyId 会社ID
	 * @return フレックス勤務所定労働時間取得
	 */
	Optional<GetFlexPredWorkTime> find(String companyId);

	/**
	 * 登録および更新
	 * @param roundingSetOfMonthly フレックス勤務所定労働時間取得
	 */
	void persistAndUpdate(GetFlexPredWorkTime getFlexPredWorkTime);
	
	/**
	 * 削除
	 * @param companyId 会社ID
	 */
	void remove(String companyId);
}
