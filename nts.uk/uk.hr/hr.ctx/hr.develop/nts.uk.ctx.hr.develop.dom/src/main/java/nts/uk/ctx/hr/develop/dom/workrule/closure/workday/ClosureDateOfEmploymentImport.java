package nts.uk.ctx.hr.develop.dom.workrule.closure.workday;

import lombok.Builder;
import lombok.Data;
import nts.uk.shr.com.time.calendar.Day;

@Builder
@Data
public class ClosureDateOfEmploymentImport {
	// 雇用ｺｰﾄﾞ
	private String employmentCd;
	// 締め日
	private Day closureDay;
}
