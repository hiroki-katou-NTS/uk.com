package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampToSuppress;

/**
 * @author anhdt
 *
 */
@Data
public class StampToSuppressDto {
	private boolean goingToWork;
	private boolean departure;
	private boolean goOut;
	private boolean turnBack;
	
	public StampToSuppressDto (StampToSuppress dom) {
		this.goingToWork = dom.isGoingToWork();
		this.departure = dom.isDeparture();
		this.goOut = dom.isGoOut();
		this.turnBack = dom.isTurnBack();
	}
}
