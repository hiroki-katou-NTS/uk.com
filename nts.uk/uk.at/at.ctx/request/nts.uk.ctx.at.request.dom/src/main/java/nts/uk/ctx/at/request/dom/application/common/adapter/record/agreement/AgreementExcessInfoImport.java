package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.YearMonth;

@Getter
@AllArgsConstructor
public class AgreementExcessInfoImport {
	/** 超過回数 */
	private int excessTimes;
	/** 年月リスト */
	private List<YearMonth> yearMonths;
}
