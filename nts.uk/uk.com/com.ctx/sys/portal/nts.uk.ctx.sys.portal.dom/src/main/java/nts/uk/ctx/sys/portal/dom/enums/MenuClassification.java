package nts.uk.ctx.sys.portal.dom.enums;

public enum MenuClassification {
	
	OfficeHelper(0),
	Customize(1),
	GroupCompanyMenu(2),
	CodeName(3),
	Tablet(4),
	TopPage(5),
	MobilePhone(6),
	OptionalItemApplication(7),
	Standard(7);

	public int value;

	private MenuClassification(int type) {
		this.value = type;
	}

}
