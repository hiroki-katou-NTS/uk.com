package nts.uk.ctx.sys.portal.dom.enums;

public enum MenuClassification {
	
	Standard(0),
	OptionalItemApplication(1),
	Mobile(2),
	Tablet(3),
	CodeName(4),
	GroupCompanyMenu(5),
	Customize(6),
	OfficeHelper(7),
	TopPage(8);

	public int value;

	private MenuClassification(int type) {
		this.value = type;
	}

}
