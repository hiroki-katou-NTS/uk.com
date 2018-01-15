package nts.uk.ctx.at.function.dom.dailyperformanceformat;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AuthorityFormatInitialDisplay extends AggregateRoot {
	
	private String companyId;

	private DailyPerformanceFormatCode dailyPerformanceFormatCode;

	public AuthorityFormatInitialDisplay(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode) {
		super();
		this.companyId = companyId;
		this.dailyPerformanceFormatCode = dailyPerformanceFormatCode;
	}
	
}
