package nts.uk.ctx.at.record.app.find.monthly.agreement.export;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * Dto 36協定超過情報
 */
@Data
@Builder
public class AgreementExcessInfoDto {
	/** 超過回数 */
	private int excessTimes;
	/** 年月リスト */
	private List<Integer> yearMonths;
}
