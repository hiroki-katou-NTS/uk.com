package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex;

import java.util.Optional;

/**
 * リポジトリ：フレックス勤務の時短日割適用日数
 * @author shuichu_ishida
 */
public interface TSDRApplyDaysOfFlexRepository {

	/**
	 * 検索
	 * @param companyId 会社ID
	 * @return フレックス勤務の時短日割適用日数
	 */
	Optional<TimeSavDayRateApplyDaysOfFlex> find(String companyId);

	/**
	 * 登録および更新
	 * @param timeSavDayRateApplyDaysOfFlex フレックス勤務の時短日割適用日数
	 */
	void persistAndUpdate(TimeSavDayRateApplyDaysOfFlex timeSavDayRateApplyDaysOfFlex);
	
	/**
	 * 削除
	 * @param companyId 会社ID
	 */
	void remove(String companyId);
}
