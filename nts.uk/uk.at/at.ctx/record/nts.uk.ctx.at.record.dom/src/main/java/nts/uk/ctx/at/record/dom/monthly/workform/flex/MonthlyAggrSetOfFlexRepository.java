package nts.uk.ctx.at.record.dom.monthly.workform.flex;

import java.util.Optional;

/**
 * リポジトリ�フレヂ�ス勤務�月別雨�設�
 * @author shuichu_ishida
 */
public interface MonthlyAggrSetOfFlexRepository {

	/**
	 * 検索
	 * @param companyId 会社ID
	 * @return フレヂ�ス勤務�月別雨�設�
	 */
	Optional<MonthlyAggrSetOfFlex> find(String companyId);

	/**
	 * 登録および更新
	 * @param roundingSetOfMonthly フレヂ�ス勤務�月別雨�設�
	 */
	void persistAndUpdate(MonthlyAggrSetOfFlex monthlyAggrSetOfFlex);
	
	/**
	 * 削除
	 * @param companyId 会社ID
	 */
	void remove(String companyId);
}
