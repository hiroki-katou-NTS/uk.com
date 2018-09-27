package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface AppReflectManagerFromRecordImport {
	/**
	 * import 申請反映Mgrクラス
	 * @param workId
	 * @param workDate
	 * @param asyncContext
	 * @return
	 */
	ProcessState applicationRellect(String workId, DatePeriod workDate, AsyncCommandHandlerContext asyncContext);
}
