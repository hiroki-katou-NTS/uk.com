package nts.uk.ctx.sys.auth.pub.wkpmanager;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.auth.pub.workplace.WorkplaceManagerExport;

import java.util.List;

/**
 * The Interface WorkplaceManagerPub.
 */
public interface WorkplaceManagerPub {

	/**
	 * 管理者未登録を確認する
	 */
	List<WorkplaceManagerExport> findByPeriodAndWkpIds(List<String> wkpId, DatePeriod datePeriod);
}

