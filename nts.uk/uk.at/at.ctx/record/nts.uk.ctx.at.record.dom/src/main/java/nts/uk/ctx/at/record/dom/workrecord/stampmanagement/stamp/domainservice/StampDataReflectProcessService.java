package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;

/**
 * @author ThanhNX
 *
 *         打刻データ反映処理
 */
@Stateless
public class StampDataReflectProcessService {

	// [1] 反映する
	public StampDataReflectResult reflect(Require require, Optional<String> employeeId, StampRecord stampRecord,
			Optional<Stamp> stamp) {
		return null;
	}
	
	//	[prv-3] いつの日別実績に反映するか
	/**
	 * @param require
	 * @param employeeId
	 * @param stamp
	 */
	private Optional<GeneralDate> reflectDailyResult(Require require, Optional<String> employeeId,
			Optional<Stamp> stamp) {
		//	if 社員ID.isEmpty	
		//  if 打刻.isEmpty
		if (!employeeId.isPresent() || !stamp.isPresent())
			return Optional.empty();
		//TODO: return 打刻データがいつの日別実績に反映するか.判断する(require, 社員ID, 打刻)
		return null;
	}

	public static interface Require {

		// [R-1] 打刻記録を追加する JpaStampDakokuRepository
		public void insert(StampRecord stampRecord);

		// [R-2] 打刻を追加する JpaStampDakokuRepository
		public void insert(Stamp stamp);

		// [R-3] 日別を作成する CreateDailyResultDomainService
		@SuppressWarnings("rawtypes")
		public ProcessState createDailyResult(AsyncCommandHandlerContext asyncContext, List<String> emloyeeIds,
				DatePeriod periodTime, ExecutionAttr executionAttr, String companyId, String empCalAndSumExecLogID,
				Optional<ExecutionLog> executionLog);

	}
}
