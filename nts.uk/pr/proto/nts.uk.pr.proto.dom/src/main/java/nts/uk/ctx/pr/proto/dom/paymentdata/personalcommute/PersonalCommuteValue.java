package nts.uk.ctx.pr.proto.dom.paymentdata.personalcommute;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.proto.dom.enums.CommuteAtr;
import nts.uk.ctx.pr.proto.dom.paymentdata.UseOrNot;

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
	private UseOrNot useOrNot;

	public PersonalCommuteValue(String commuteCycle, long commuteAllowance, CommuteAtr commuteMeansAttribute,
			long payStartDate, UseOrNot useOrNot) {
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
			int commuteMeansAttribute, long payStartDate, int userOrNot) {
		return new PersonalCommuteValue(commuteCycle, commuteAllowance,
				EnumAdaptor.valueOf(commuteMeansAttribute, CommuteAtr.class), payStartDate,
				EnumAdaptor.valueOf(userOrNot, UseOrNot.class));
	}
}
