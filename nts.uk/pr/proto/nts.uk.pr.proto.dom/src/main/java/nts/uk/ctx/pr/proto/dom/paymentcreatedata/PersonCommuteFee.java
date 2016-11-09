package nts.uk.ctx.pr.proto.dom.paymentcreatedata;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * Aggregate Root: Person commute fee
 *
 */
public class PersonCommuteFee extends AggregateRoot {
	/**
	 * Commute no tax limit number;
	 */
	@Getter
	private long commuteNoTaxLimitPrivateNo;
	
	/**
	 * Commute no tax limit publish number;
	 */
	@Getter
	private long commuteNoTaxLimitPublishNo;
	
	@Getter
	private PersonCommuteValue commute1;
	
	@Getter
	private PersonCommuteValue commute2;
	
	@Getter
	private PersonCommuteValue commute3;
	
	@Getter
	private PersonCommuteValue commute4;
	
	@Getter
	private PersonCommuteValue commute5;
}
