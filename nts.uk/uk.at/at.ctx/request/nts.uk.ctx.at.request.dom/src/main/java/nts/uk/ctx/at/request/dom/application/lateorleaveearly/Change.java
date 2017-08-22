package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

public enum Change {

	/** しない */
	NOT(0),
	/** する  */
	DO(1);

	public int value;

	Change(int type) {
		this.value = type;
	}
}
