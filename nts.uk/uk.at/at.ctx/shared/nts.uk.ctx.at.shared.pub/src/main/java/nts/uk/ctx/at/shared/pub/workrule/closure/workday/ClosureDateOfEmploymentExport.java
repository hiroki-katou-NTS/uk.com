package nts.uk.ctx.at.shared.pub.workrule.closure.workday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClosureDateOfEmploymentExport {
	// 雇用ｺｰﾄﾞ
	private String employmentCd;
	// 締め日
	private int closureDate;
}
