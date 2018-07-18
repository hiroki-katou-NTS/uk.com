package nts.uk.ctx.at.shared.dom.adapter.employment;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SharedSidPeriodDateEmploymentImport {
	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The aff period emp code exports. */
	// 所属期間と雇用コード
	private List<AffPeriodEmpCodeImport> affPeriodEmpCodeExports;
}
