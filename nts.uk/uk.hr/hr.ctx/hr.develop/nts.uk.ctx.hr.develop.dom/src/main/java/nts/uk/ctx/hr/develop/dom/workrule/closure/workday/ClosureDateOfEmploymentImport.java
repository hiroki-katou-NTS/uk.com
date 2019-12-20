package nts.uk.ctx.hr.develop.dom.workrule.closure.workday;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClosureDateOfEmploymentImport {
	// 雇用ｺｰﾄﾞ
	private String employmentCd;
	// 締め日
	private int closureDay;
}
