package nts.uk.ctx.pr.proto.dom.paymentcreatedata;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * Aggregate Root: Person commute fee
 *
 */
public class PersonalCommuteFee extends AggregateRoot {
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
	private PersonalCommuteValue commute1;
	
	@Getter
	private PersonalCommuteValue commute2;
	
	@Getter
	private PersonalCommuteValue commute3;
	
	@Getter
	private PersonalCommuteValue commute4;
	
	@Getter
	private PersonalCommuteValue commute5;
}
