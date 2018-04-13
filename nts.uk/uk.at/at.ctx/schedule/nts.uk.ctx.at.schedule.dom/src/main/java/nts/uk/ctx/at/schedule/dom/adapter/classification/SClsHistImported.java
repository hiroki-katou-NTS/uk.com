package nts.uk.ctx.at.schedule.dom.adapter.classification;

import lombok.Getter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author sonnh1
 *
 */
@Getter
public class SClsHistImported {
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

	public SClsHistImported(DatePeriod period, String employeeId, String classificationCode,
			String classificationName) {
		super();
		this.period = period;
		this.employeeId = employeeId;
		this.classificationCode = classificationCode;
		this.classificationName = classificationName;
	}
}
