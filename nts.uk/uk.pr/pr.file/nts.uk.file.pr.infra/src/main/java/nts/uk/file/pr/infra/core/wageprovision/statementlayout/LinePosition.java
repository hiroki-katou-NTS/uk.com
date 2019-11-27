package nts.uk.file.pr.infra.core.wageprovision.statementlayout;

public enum LinePosition {
	FIRST(0), 
	MIDDLE(1), 
	LAST(2),
	FIRST_AND_LAST(3);
	/** The value. */
	public final int value;

	private LinePosition(int value) {
		this.value = value;
	}
}
