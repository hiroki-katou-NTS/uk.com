package nts.uk.ctx.workflow.pub.resultrecord.export;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppEmpSttMonthExport {
	private String employeeID;
	private List<RouteSituationMonthExport> routeSituationLst;
}
