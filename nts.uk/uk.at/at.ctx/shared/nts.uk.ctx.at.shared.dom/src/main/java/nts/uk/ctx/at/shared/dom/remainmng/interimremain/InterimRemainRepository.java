package nts.uk.ctx.at.shared.dom.remainmng.interimremain;

import java.util.List;

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
	List<InterimRemain> getRemainBySidPriod(String employeeId, DatePeriod dateData);
}
