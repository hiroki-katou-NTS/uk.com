package nts.uk.ctx.sys.shared.dom.toppagealarm;

import java.util.List;

public interface TopPageAlarmRepository {
	/**
	 * find top page alarm
	 * @param executionLogId
	 * @return
	 * @author yennth
	 */
	List<TopPageAlarm> findToppage(String companyId, String managerId, int rogerFlag);
	/**
	 * find top page alarm detail
	 * @return
	 * @author yennth
	 */
	List<TopPageAlarmDetail> findDetail(String executionLogId);
}
