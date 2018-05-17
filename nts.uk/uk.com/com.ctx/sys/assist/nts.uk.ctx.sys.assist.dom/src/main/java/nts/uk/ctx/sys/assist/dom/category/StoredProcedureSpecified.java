package nts.uk.ctx.sys.assist.dom.category;

public enum StoredProcedureSpecified {	
	
	IS_NOT_SPECIFIED(0,"Enum_StoredProcedureSpecified_IS_NOT_SPECIFIED"),
	SPECIFIED(1,"Enum_StoredProcedureSpecified_SPECIFIED");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private StoredProcedureSpecified(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
