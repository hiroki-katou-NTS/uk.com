package nts.uk.ctx.at.record.dom.adapter.workrule.closure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClosureDateOfEmploymentImport {
	// 雇用ｺｰﾄﾞ
	private String employmentCd;
	// 締め日
	private int closureDate;
}
