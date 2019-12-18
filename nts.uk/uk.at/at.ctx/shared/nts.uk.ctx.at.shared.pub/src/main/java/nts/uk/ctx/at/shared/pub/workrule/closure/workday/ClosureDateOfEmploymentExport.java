package nts.uk.ctx.at.shared.pub.workrule.closure.workday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.calendar.Day;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClosureDateOfEmploymentExport {
	// 雇用ｺｰﾄﾞ
	private String employmentCd;
	// 日
	private Day closureDay;
}
