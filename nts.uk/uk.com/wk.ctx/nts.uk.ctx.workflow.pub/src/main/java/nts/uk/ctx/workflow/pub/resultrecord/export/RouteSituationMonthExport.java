package nts.uk.ctx.workflow.pub.resultrecord.export;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@AllArgsConstructor
@Getter
public class RouteSituationMonthExport {
	private String employeeID;
	private YearMonth yearMonth;
	private Integer closureID;
	private ClosureDate closureDate;
	private Integer approverEmpState;
	private Optional<ApprovalStatusExport> approvalStatus;
}
