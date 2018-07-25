package nts.uk.ctx.sys.assist.dom.categoryfieldmt;

public enum HistoryDiviSion {
	
	NO_HISTORY(0,"Enum_History_DiviSion_NO_HISTORY"),
	HAVE_HISTORY(1,"Enum_History_DiviSion_HAVE_HISTORY");
	
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private HistoryDiviSion(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	
}
