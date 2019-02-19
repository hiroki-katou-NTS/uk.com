package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClosureHistoryHaveDateDto {

	/** The close name. */
	private String closeName;

	/** The closure id. */
	private int closureId;

	/** The end date. */
	private int endDate;

	/** The closure date. */
	private ClosureDateDto closureDate;

	/** The start date. */
	private int startDate;

	public static ClosureHistoryHaveDateDto fromDomain(ClosureHistory x) {
		return new ClosureHistoryHaveDateDto(x.getClosureName().v(), x.getClosureId().value, x.getEndYearMonth().v(),
				ClosureDateDto.fromDomain(x.getClosureDate()), x.getStartYearMonth().v());
	}
}
