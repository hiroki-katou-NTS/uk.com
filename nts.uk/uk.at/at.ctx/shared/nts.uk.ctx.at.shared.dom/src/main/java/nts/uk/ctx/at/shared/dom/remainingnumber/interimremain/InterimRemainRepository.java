package nts.uk.ctx.at.shared.dom.remainingnumber.interimremain;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface InterimRemainRepository {
	
	/**
	 * 暫定残数管理データ
	 * @param employeeId
	 * @param dateData ・対象日が指定期間内
	 * ・対象日≧INPUT.期間.開始年月日
	 * ・対象日≦INPUT.期間.終了年月日
	 * @return
	 */
	List<InterimRemain> getRemainBySidPriod(String employeeId, DatePeriod dateData, RemainType remainType);
	
	/**
	 * 
	 * @param remainId
	 * @return
	 */
	Optional<InterimRemain> getById(String remainId);
	
	/**
	 * 暫定残数管理データ を追加および更新
	 * @param domain
	 */
	void persistAndUpdateInterimRemain(InterimRemain domain);
	
	/**
	 * idから暫定残数管理データ　を削除する
	 * @param mngId
	 */
	void deleteById(String mngId);
	
	/**
	 * 社員ID, 期間, 残数種類から 暫定残数管理データ　を削除する
	 * @param employeeId
	 * @param dateData
	 * @param remainType
	 */
	void deleteBySidPeriodType(String employeeId, DatePeriod dateData, RemainType remainType);
	
	/**
	 * 社員ID, 期間 から 暫定残数管理データ　を削除する
	 * @param employeeId
	 * @param dateData
	 */
	void deleteBySidPeriod(String employeeId, DatePeriod dateData);
	/**
	 * 暫定残数管理データ を検索
	 * @param sid　社員ID
	 * @param baseDate　対象日
	 * @return
	 */
	List<InterimRemain> getDataBySidDates(String sid, List<GeneralDate> baseDates);
}
