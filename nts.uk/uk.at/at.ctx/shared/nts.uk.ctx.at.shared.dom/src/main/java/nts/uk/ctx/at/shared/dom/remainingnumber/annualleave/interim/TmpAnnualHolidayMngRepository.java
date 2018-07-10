package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim;

import java.util.Optional;

public interface TmpAnnualHolidayMngRepository {
	/**
	 * 検索　By　ID
	 * @param mngId
	 * @return
	 */
	public Optional<TmpAnnualHolidayMng> getById(String mngId);
	/**
	 * 削除
	 * @param mngId
	 */
	public void deleteById(String mngId);
	/**
	 * 登録および更新
	 * @param dataMng
	 */
	public void persistAndUpdate(TmpAnnualHolidayMng dataMng);
}
