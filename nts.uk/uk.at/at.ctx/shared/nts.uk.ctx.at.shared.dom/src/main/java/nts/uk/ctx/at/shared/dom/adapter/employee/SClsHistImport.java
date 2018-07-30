package nts.uk.ctx.at.shared.dom.adapter.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SClsHistImport {
	/** The period. */
	// 配属期間
	private DatePeriod period;

	/** The employee id. */
	// 社員ID
	private String employeeId;

	/** The job title code. */
	// 分類コード
	private String classificationCode;

	/** The job title name. */
	// 分類名称
	private String classificationName;
}
