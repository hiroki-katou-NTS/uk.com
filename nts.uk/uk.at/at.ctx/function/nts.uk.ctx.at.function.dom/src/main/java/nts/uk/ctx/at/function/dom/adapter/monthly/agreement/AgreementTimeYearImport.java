package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgreementTimeYearImport {

	/** 法定上限対象時間 */
	private AgreTimeOfMonthlyImport limitTime;

	/** 36協定対象時間 */
	private AgreTimeOfMonthlyImport recordTime;

	/** 状態 */
	private int status;
}
