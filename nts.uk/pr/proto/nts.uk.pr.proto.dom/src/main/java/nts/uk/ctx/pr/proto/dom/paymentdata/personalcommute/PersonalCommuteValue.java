package nts.uk.ctx.pr.proto.dom.paymentdata.personalcommute;

import java.math.BigDecimal;

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
	private int commuteCycle;

	/**
	 * Commute allowance
	 */
	@Getter
	private BigDecimal commuteAllowance;

	/**
	 * Commute means attribute
	 */
	@Getter
	private CommuteAtr commuteMeansAttribute;

	/**
	 * Payroll start year month
	 */
	@Getter
	private int payStartYm;

	/**
	 * Use or Not
	 */
	@Getter
	private UseOrNot useOrNot;

	public PersonalCommuteValue(int commuteCycle, BigDecimal commuteAllowance, CommuteAtr commuteMeansAttribute,
			int payStartYm, UseOrNot useOrNot) {
		super();
		this.commuteCycle = commuteCycle;
		this.commuteAllowance = commuteAllowance;
		this.commuteMeansAttribute = commuteMeansAttribute;
		this.payStartYm = payStartYm;
		this.useOrNot = useOrNot;
	}

	/**
	 * Create instance using Java type parameters.
	 * 
	 * @return PersonalCommuteValue
	 */
	public static PersonalCommuteValue createFromJavaType(int commuteCycle, BigDecimal commuteAllowance,
			int commuteMeansAttribute, int payStartYm, int userOrNot) {
		return new PersonalCommuteValue(commuteCycle, commuteAllowance,
				EnumAdaptor.valueOf(commuteMeansAttribute, CommuteAtr.class), payStartYm,
				EnumAdaptor.valueOf(userOrNot, UseOrNot.class));
	}
}
