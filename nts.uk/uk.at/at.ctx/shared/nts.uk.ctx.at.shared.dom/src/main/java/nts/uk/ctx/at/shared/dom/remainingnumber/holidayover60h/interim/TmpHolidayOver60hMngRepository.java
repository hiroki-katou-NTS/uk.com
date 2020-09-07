package nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.interim;

import java.util.List;
import java.util.Optional;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * 暫定60H超休管理データ リポジトリ
 * @author masaaki_jinno
 *
 */
public interface TmpHolidayOver60hMngRepository {
	/**
	 * 検索　By　ID
	 * @param mngId
	 * @return
	 */
	public Optional<TmpHolidayOver60hMng> getById(String mngId);
	/**
	 * 削除
	 * @param mngId
	 */
	public void deleteById(String mngId);
	/**
	 * 登録および更新
	 * @param dataMng
	 */
	public void persistAndUpdate(TmpHolidayOver60hMng dataMng);
	/**
	 * 検索　 暫定60H超休管理データ 
	 * @param sid　社員ID
	 * @param period 期間
	 * @return
	 */
	List<TmpHolidayOver60hMng> getBySidPeriod(String sid, DatePeriod period);
	
	/**
	 * ドメインモデル「暫定60H超休管理データ」を取得
	 *
	 * @param employee the employee
	 * @param date the date
	 * @param remainType the remain type
	 * @return the by employee id and date and remain type
	 */
	public List<TmpHolidayOver60hMng> getByEmployeeIdAndDatePeriodAndRemainType(String employee, DatePeriod period, int remainType);
}
