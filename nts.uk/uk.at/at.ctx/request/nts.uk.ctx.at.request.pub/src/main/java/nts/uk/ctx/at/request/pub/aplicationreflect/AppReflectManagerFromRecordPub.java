package nts.uk.ctx.at.request.pub.aplicationreflect;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.calendar.period.DatePeriod;

public interface AppReflectManagerFromRecordPub {
	/**
	 * pub 申請反映Mgrクラス
	 * @param workId
	 * @param workDate
	 * @param asyncContext
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	ProcessStateReflectExport applicationRellect(String workId, DatePeriod workDate, AsyncCommandHandlerContext asyncContext);
	
	/**
	 * pub 社員の申請を反映
	 * @param workId
	 * @param sid
	 * @param datePeriod
	 * @return
	 */
	ProcessStateReflectExport reflectAppOfEmployeeTotal(String workId, String sid, DatePeriod datePeriod);
}
