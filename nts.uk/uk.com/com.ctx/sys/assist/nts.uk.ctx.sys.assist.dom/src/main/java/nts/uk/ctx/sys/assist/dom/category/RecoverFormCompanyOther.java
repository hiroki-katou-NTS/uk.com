package nts.uk.ctx.sys.assist.dom.category;

public enum RecoverFormCompanyOther {
	
	IS_NOT_RE_OTHER_COMPANY(0,"Enum_RecoverFormCompanyOther_IS_NOT_RE_OTHER_COMPANY"),
	IS_RE_OTHER_COMPANY(1,"Enum_RecoverFormCompanyOther_IS_RE_OTHER_COMPANY");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private RecoverFormCompanyOther(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
