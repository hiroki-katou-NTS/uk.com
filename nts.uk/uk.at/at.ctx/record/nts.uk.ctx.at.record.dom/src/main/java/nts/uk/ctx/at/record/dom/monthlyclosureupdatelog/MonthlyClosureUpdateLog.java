package nts.uk.ctx.at.record.dom.monthlyclosureupdatelog;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT - 月締め更新ログ
 *
 */

@Getter
public class MonthlyClosureUpdateLog extends AggregateRoot {

	// ID
	private String id;

	// 会社ID
	private String companyId;

	// 実行状況
	private MonthlyClosureExecutionStatus executionStatus;

	// 完了状態
	private MonthlyClosureCompleteStatus completeStatus;

	// 実行日時
	private GeneralDateTime executionDateTime;

	// 実行期間
	private DatePeriod executionPeriod;

	// 実行社員ID
	private String executeEmployeeId;

	// 対象年月
	private YearMonth targetYearMonth;

	// 締めID
	private ClosureId closureId;

	// 締め日
	private ClosureDate closureDate;

	public MonthlyClosureUpdateLog(String id, String companyId, int executionStatus, int completeStatus,
			GeneralDateTime executionDateTime, DatePeriod executionPeriod, String executeEmployeeId,
			YearMonth targetYearMonth, int closureId, ClosureDate closureDate) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.executionStatus = EnumAdaptor.valueOf(executionStatus, MonthlyClosureExecutionStatus.class);
		this.completeStatus = EnumAdaptor.valueOf(completeStatus, MonthlyClosureCompleteStatus.class);
		this.executionDateTime = executionDateTime;
		this.executionPeriod = executionPeriod;
		this.executeEmployeeId = executeEmployeeId;
		this.targetYearMonth = targetYearMonth;
		this.closureId = EnumAdaptor.valueOf(closureId, ClosureId.class);
		this.closureDate = closureDate;
	}

	public void updateCompleteStatus(MonthlyClosureCompleteStatus status) {
		this.completeStatus = status;
	}

	public void updateExecuteStatus(MonthlyClosureExecutionStatus status) {
		this.executionStatus = status;
	}

}
