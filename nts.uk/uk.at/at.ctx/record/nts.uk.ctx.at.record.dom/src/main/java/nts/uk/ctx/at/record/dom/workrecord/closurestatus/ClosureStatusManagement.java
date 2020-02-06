package nts.uk.ctx.at.record.dom.workrecord.closurestatus;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT - 締め状態管理
 *
 */

@Getter
public class ClosureStatusManagement extends AggregateRoot {

	// 年月
	private YearMonth yearMonth;

	// 社員ID
	private String employeeId;

	// 締めID
	private ClosureId closureId;

	// 締め日
	private ClosureDate closureDate;

	// 期間
	private DatePeriod period;

	public ClosureStatusManagement(YearMonth yearMonth, String employeeId, int closureId, ClosureDate closureDate,
			DatePeriod period) {
		super();
		this.yearMonth = yearMonth;
		this.employeeId = employeeId;
		this.closureId = EnumAdaptor.valueOf(closureId, ClosureId.class);
		this.closureDate = closureDate;
		this.period = period;
	}

}
