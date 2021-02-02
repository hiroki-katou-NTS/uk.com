package nts.uk.screen.at.app.command.kmk.kmk004.a;

/**
 * 
 * @author sonnlb
 *
 */
public enum ScreenMode {

	COMPANY(0),

	WORK_PLACE(1),

	EMPLOYMENT(2),

	EMPLOYEE(3);

	/** The value. */
	public final int value;

	private ScreenMode(int value) {
		this.value = value;
	}
}
