package nts.uk.ctx.exio.ws.exo.getinfotosmile;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm.closure.ClosureHistPeriod;
@AllArgsConstructor
@Data
public class ClosureHistPeriodDto {
	private int closureId;
	private GeneralDate startDate;
	private GeneralDate endDate;
	
	public ClosureHistPeriodDto(ClosureHistPeriod closureHist) {
		this.closureId = closureHist.getClosureId().value;
		this.startDate = closureHist.getPeriod().start();
		this.endDate = closureHist.getPeriod().end();
	}
	
}
