package nts.uk.ctx.pr.proto.dom.paymentcreatedata;

import lombok.Getter;
import nts.uk.ctx.pr.proto.dom.enums.CommuteMeansAttribute;

/**
 * Value Object: Person commute
 *
 */
public class PersonalCommuteValue {
	/**
	 * Commute cycle
	 */
	@Getter
	private String commuteCycle;

	/**
	 * Commute allowance
	 */
	@Getter
	private long commuteAllowance;

	/**
	 * Commute means attribute
	 */
	@Getter
	private CommuteMeansAttribute commuteMeansAttribute;

	/**
	 * Payroll start date
	 */
	@Getter
	private long payStartDate;

	/**
	 * User or Not
	 */
	@Getter
	private boolean userOrNot;

	public PersonalCommuteValue(String commuteCycle, long commuteAllowance, CommuteMeansAttribute commuteMeansAttribute,
			long payStartDate, boolean userOrNot) {
		super();
		this.commuteCycle = commuteCycle;
		this.commuteAllowance = commuteAllowance;
		this.commuteMeansAttribute = commuteMeansAttribute;
		this.payStartDate = payStartDate;
		this.userOrNot = userOrNot;
	}

	/**
	 * Create instance using Java type parameters.
	 * 
	 * @return PersonalCommuteValue
	 */
	public static PersonalCommuteValue createFromJavaType(String commuteCycle, long commuteAllowance,
			int commuteMeansAttribute, long payStartDate, boolean userOrNot) {
		return new PersonalCommuteValue(commuteCycle, commuteAllowance,
				CommuteMeansAttribute.valueOf(commuteMeansAttribute), payStartDate, userOrNot);
	}
}
