package nts.uk.ctx.pr.proto.dom.paymentcreatedata;

import lombok.Getter;
import nts.uk.ctx.pr.proto.dom.enums.CommuteMeansAttribute;
import nts.uk.ctx.pr.proto.dom.enums.UserOrNot;

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
}
