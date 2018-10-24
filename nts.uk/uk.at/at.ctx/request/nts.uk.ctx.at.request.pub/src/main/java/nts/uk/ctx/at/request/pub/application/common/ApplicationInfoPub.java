package nts.uk.ctx.at.request.pub.application.common;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface ApplicationInfoPub {
	
	/**
	 * 社員、期間に一致する申請を取得する
	 * @param employeeIDLst
	 * @param period
	 * @return
	 */
	public List<ApplicationPeriodInfo> getAppInfoByPeriod(List<String> employeeIDLst, DatePeriod period);
	
}
