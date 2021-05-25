package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.interimdata;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface TempPublicHplidayManagementRepository {
	/**
	 * 削除
	 * @param mngId
	 */
	public void deleteById(String mngId);
	/**
	 * 登録および更新
	 * @param dataMng
	 */
	public void persistAndUpdate(TempPublicHplidayManagement dataMng);
	/**
	 * 検索　 暫定公休管理データ
	 * @param sid　社員ID
	 * @param period 期間
	 * @return
	 */
	List<TempPublicHplidayManagement> getBySidPeriod(String sid, DatePeriod period);
	/**
	 * 暫定残数管理データ、暫定年休管理データを削除
	 * @param sid　社員ID
	 * @param period　期間
	 */
	public void deleteSidPeriod(String sid, DatePeriod period);
}
