package nts.uk.ctx.pereg.dom.workrule.closure;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClosureDateOfEmploymentImport {
	// 雇用ｺｰﾄﾞ
	private String employmentCd;
	// 締め日
	private int closureDate;
}
