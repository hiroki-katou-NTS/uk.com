package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface TmpResereLeaveMngRepository {
	/**
	 * get 暫定積立年休管理データ by id
	 * @param ResereMngId
	 * @return
	 */
	public Optional<TmpResereLeaveMng> getById(String resereMngId);
	/**
	 * delete 暫定積立年休管理データ by id
	 * @param resereMngId
	 */
	public void deleteById(String resereMngId);

	/**
	 * 登録および更新
	 * @param dataMng
	 */
	public void persistAndUpdate(TmpResereLeaveMng dataMng);
	/**
	 * 社員ID、期間から暫定積立年休管理データ を検索
	 * @param sid
	 * @param period
	 */
	public List<TmpResereLeaveMng> findBySidPriod(String sid, DatePeriod period);
	/**
	 * 暫定残数管理データ、暫定積立年休管理データを削除
	 * @param sid
	 * @param period
	 */
	public void deleteSidPeriod(String sid, DatePeriod period);
	
	/**
	 * 年月日より前全て削除
	 * @param sid
	 * @param ymd
	 */
	public void deleteBySidBeforeTheYmd(String sid, GeneralDate ymd);

}
