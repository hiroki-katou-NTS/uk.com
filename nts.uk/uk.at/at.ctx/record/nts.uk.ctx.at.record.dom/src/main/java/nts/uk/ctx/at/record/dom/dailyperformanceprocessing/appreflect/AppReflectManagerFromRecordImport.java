package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.ProcessState;

public interface AppReflectManagerFromRecordImport {
	/**
	 * import 申請反映Mgrクラス
	 * @param workId
	 * @param workDate
	 * @param asyncContext
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	ProcessState applicationRellect(String workId, DatePeriod workDate, AsyncCommandHandlerContext asyncContext);
}
