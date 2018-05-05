package nts.uk.ctx.sys.assist.dom.category;

public enum SystemUsability {
	
	UNVAILABLE(0,"Enum_System_Usability_UNVAILABLE"),
	AVAILABLE(1,"Enum_System_Usability_AVAILABLE");
	
	
	public final int value;

	/** The name id. */
	public final String nameId;

	private SystemUsability(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
