package nts.uk.ctx.at.record.app.command.divergence.time.history;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DivergenceReferenceTimeUsageUnitCommand{
	
	private BigDecimal workTypeUseSet;
	
	public DivergenceReferenceTimeUsageUnitCommand() {
		super();
	}
	
	/**
	 * Instantiates a new divergence divergence reference time usage unit command.
	 *
	 * @param listDataSetting the list data setting
	 */
	public DivergenceReferenceTimeUsageUnitCommand(BigDecimal workTypeUseSet){
		this.workTypeUseSet = workTypeUseSet;
	}
}
