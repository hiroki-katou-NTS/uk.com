package nts.uk.ctx.pr.proto.dom.paymentcreatedata;

import lombok.Getter;
import nts.uk.ctx.pr.proto.dom.enums.CommuteMeansAttribute;
import nts.uk.ctx.pr.proto.dom.enums.UserOrNot;
import nts.uk.shr.com.primitive.PersonId;

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
	private UserOrNot userOrNot;

	/**
	 * Personal Id
	 */
	@Getter
	private PersonId personId;

	public PersonalCommuteValue(String commuteCycle, long commuteAllowance, CommuteMeansAttribute commuteMeansAttribute,
			long payStartDate, UserOrNot userOrNot, PersonId personId) {
		super();
		this.commuteCycle = commuteCycle;
		this.commuteAllowance = commuteAllowance;
		this.commuteMeansAttribute = commuteMeansAttribute;
		this.payStartDate = payStartDate;
		this.userOrNot = userOrNot;
		this.personId = personId;
	}

	/**
	 * Create instance using Java type parameters.
	 * 
	 * @param remainDays
	 *            remain Days
	 * @param remainTime
	 *            remain Time
	 * @param personId
	 *            personId
	 * @return HolidayPaid
	 */
	public static PersonalCommuteValue createFromJavaType(String commuteCycle, long commuteAllowance,
			int commuteMeansAttribute, long payStartDate, int userOrNot, String personId) {
		return new PersonalCommuteValue(commuteCycle, commuteAllowance,
				CommuteMeansAttribute.valueOf(commuteMeansAttribute), payStartDate, UserOrNot.valueOf(userOrNot),
				new PersonId(personId));
	}
}
