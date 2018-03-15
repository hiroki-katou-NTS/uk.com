package nts.uk.ctx.at.record.app.command.divergence.time.history;

import lombok.Getter;

@Getter
public class DivergenceReferenceTimeUsageUnitCommand{
	
	/*The divergence reference time usage unit*/
	private DivergenceReferenceTimeUsageUnitDto divergenceReferenceTimeUsageUnitDto;
	
	
	/**
	 * Instantiates a new divergence divergence reference time usage unit command.
	 *
	 * @param listDataSetting the list data setting
	 */
	public DivergenceReferenceTimeUsageUnitCommand(DivergenceReferenceTimeUsageUnitDto divergenceReferenceTimeUsageUnitDto){
		this.divergenceReferenceTimeUsageUnitDto = divergenceReferenceTimeUsageUnitDto;
	}
}
