package nts.uk.ctx.at.record.app.find.monthlyclosureupdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author HungTT
 *
 */
@AllArgsConstructor
@Data
public class ClosureInforDto {
	/** The closure id. */
	// 締めＩＤ
	private int closureId;

	/** The closure name. */
	// 締め名称
	private String closureName;

	/** The closure month. */
	// 当月
	private int closureMonth;

	/** The period. */
	// 期間
	private GeneralDate periodStart;
	private GeneralDate periodEnd;

	/** The closure date. */
	// 締め日
	private int closureDay;
	private boolean isLastDay;
	
	private Integer targetYm;
	
	private GeneralDateTime executionDt;

}
