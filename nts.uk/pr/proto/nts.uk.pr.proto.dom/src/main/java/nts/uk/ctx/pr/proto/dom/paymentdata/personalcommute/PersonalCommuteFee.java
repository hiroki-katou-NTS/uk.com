package nts.uk.ctx.pr.proto.dom.paymentdata.personalcommute;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.primitive.PersonId;

/**
 * Aggregate Root: Person commute fee
 *
 */
public class PersonalCommuteFee extends AggregateRoot {
	/**
	 * Commute no tax limit number;
	 */
	@Getter
	private BigDecimal commuteNoTaxLimitPrivateNo;

	/**
	 * Commute no tax limit publish number;
	 */
	@Getter
	private BigDecimal commuteNoTaxLimitPublishNo;

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

	/**
	 * Personal Id
	 */
	@Getter
	private PersonId personId;

	/**
	 * Constructor with parameter.
	 * @param commuteNoTaxLimitPrivateNo commute No Tax Limit Private No
	 * @param commuteNoTaxLimitPublishNo commute No Tax Limit Publish No
	 * @param commute1 other information
	 * @param commute2 other information
	 * @param commute3 other information
	 * @param commute4 other information
	 * @param commute5 other information
	 * @param personId person id
	 */
	public PersonalCommuteFee(BigDecimal commuteNoTaxLimitPrivateNo, BigDecimal commuteNoTaxLimitPublishNo,
			PersonalCommuteValue commute1, PersonalCommuteValue commute2, PersonalCommuteValue commute3,
			PersonalCommuteValue commute4, PersonalCommuteValue commute5, PersonId personId) {
		super();
		this.commuteNoTaxLimitPrivateNo = commuteNoTaxLimitPrivateNo;
		this.commuteNoTaxLimitPublishNo = commuteNoTaxLimitPublishNo;
		this.commute1 = commute1;
		this.commute2 = commute2;
		this.commute3 = commute3;
		this.commute4 = commute4;
		this.commute5 = commute5;
		this.personId = personId;
	}

	/**
	 * Create instance using Java type parameters.
	 * 
	 * @return PersonalCommuteFee
	 */
	public static PersonalCommuteFee createFromJavaType(BigDecimal commuteNoTaxLimitPrivateNo,
			BigDecimal commuteNoTaxLimitPublishNo, PersonalCommuteValue commute1, PersonalCommuteValue commute2,
			PersonalCommuteValue commute3, PersonalCommuteValue commute4, PersonalCommuteValue commute5,
			String personId) {
		return new PersonalCommuteFee(commuteNoTaxLimitPrivateNo, commuteNoTaxLimitPublishNo, commute1, commute2,
				commute3, commute4, commute5, new PersonId(personId));
	}

}
