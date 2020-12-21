package nts.uk.ctx.at.record.pub.monthly.agreement.export;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreementExcessInfo;

@Data
@Builder
@AllArgsConstructor
public class AgreementExcessInfoExport {

	/** 超過回数 */
	private int excessTimes;
	/** 残回数 */
	private int remainTimes;
	/** 年月リスト */
	private List<YearMonth> yearMonths;
	
	public static AgreementExcessInfoExport copy(AgreementExcessInfo domain) {
		
		return AgreementExcessInfoExport.builder()
				.excessTimes(domain.getExcessTimes())
				.remainTimes(domain.getRemainTimes())
				.yearMonths(domain.getYearMonths().stream().collect(Collectors.toList()))
				.build();
	}
}
