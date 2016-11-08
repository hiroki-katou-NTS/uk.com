package nts.uk.ctx.pr.proto.dom.createdata;

/**
 * Enum: Payroll system
 *
 */
public enum PayrollSystem {
	MONTHLY(1), DAILY(2), DAY(3), HOURS(4);

	public final int value;

	PayrollSystem(int value) {
		this.value = value;
	}
}
