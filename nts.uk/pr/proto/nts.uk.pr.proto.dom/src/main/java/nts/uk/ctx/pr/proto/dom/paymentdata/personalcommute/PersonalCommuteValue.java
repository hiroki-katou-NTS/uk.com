package nts.uk.ctx.pr.proto.dom.paymentdata.personalcommute;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.proto.dom.enums.CommuteAtr;

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
	private CommuteAtr commuteMeansAttribute;

	/**
	 * Payroll start date
	 */
	@Getter
	private long payStartDate;

	/**
	 * Use or Not
	 */
	@Getter
	private boolean useOrNot;

	public PersonalCommuteValue(String commuteCycle, long commuteAllowance, CommuteAtr commuteMeansAttribute,
			long payStartDate, boolean useOrNot) {
		super();
		this.commuteCycle = commuteCycle;
		this.commuteAllowance = commuteAllowance;
		this.commuteMeansAttribute = commuteMeansAttribute;
		this.payStartDate = payStartDate;
		this.useOrNot = useOrNot;
	}

	/**
	 * Create instance using Java type parameters.
	 * 
	 * @return PersonalCommuteValue
	 */
	public static PersonalCommuteValue createFromJavaType(String commuteCycle, long commuteAllowance,
			int commuteMeansAttribute, long payStartDate, boolean userOrNot) {
		return new PersonalCommuteValue(commuteCycle, commuteAllowance,
				EnumAdaptor.valueOf(commuteMeansAttribute, CommuteAtr.class), payStartDate, userOrNot);
	}
}
