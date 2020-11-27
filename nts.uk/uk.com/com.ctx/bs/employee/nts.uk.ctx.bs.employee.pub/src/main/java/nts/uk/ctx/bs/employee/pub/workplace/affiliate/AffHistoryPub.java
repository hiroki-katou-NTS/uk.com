package nts.uk.ctx.bs.employee.pub.workplace.affiliate;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
 * 社員（List）と期間から職位履歴を取得する
 */
public interface AffHistoryPub {

	AffHistoryExport getAffWkpHist(GeneralDate baseDate, List<String> employeeIds);

}
