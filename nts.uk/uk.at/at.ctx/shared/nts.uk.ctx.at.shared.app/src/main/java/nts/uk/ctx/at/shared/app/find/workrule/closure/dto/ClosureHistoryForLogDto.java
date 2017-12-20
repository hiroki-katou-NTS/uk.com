package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClosureHistoryForLogDto {
	

	/** The closure id. */
	// 締めＩＤ
	private int closureId;

	/** The closure name. */
	// 名称: 締め名称
	private String closureName;

	/** The end year month. */
	// 終了年月: 年月
	@Setter
	private int endYearMonth;

	/** The start year month. */
	// 開始年月: 年月
	private int startYearMonth;
	
	public static ClosureHistoryForLogDto fromDomain(ClosureHistory domain ) {
		return new ClosureHistoryForLogDto(
					domain.getClosureId().value,
					domain.getClosureName().v(),
					domain.getEndYearMonth().v(),
					domain.getStartYearMonth().v()
				);
	}

}
