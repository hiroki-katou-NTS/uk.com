package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
public interface InterimBreakDayOffMngRepository {
	/**
	 * 暫定休出管理データ
	 * @param breakManaId
	 * @return
	 */
	Optional<InterimBreakMng> getBreakManaBybreakMngId(String breakManaId);
	/**
	 * 暫定代休管理データ
	 * @param dayOffManaId
	 * @return
	 */
	Optional<InterimDayOffMng> getDayoffById(String dayOffManaId);
	/**
	 * 暫定休出代休紐付け管理
	 * @param mngId
	 * @param breakDay: True: 休出管理データ, false: 代休管理データ
	 * @return
	 */
	Optional<InterimBreakDayOffMng> getBreakDayOffMng(String mngId, boolean breakDay, DataManagementAtr mngAtr);
	/**
	 * 
	 * @param mngId
	 * @param unUseDays ・未使用日数＞unUseDays
	 * @param dateData ・使用期限日が指定期間内
	 * ・使用期限日≧INPUT.期間.開始年月日
	 * ・使用期限日≦INPUT.期間.終了年月日
	 * @return
	 */
	List<InterimBreakMng> getByPeriod(List<String> mngId, Double unUseDays, DatePeriod dateData);
}
