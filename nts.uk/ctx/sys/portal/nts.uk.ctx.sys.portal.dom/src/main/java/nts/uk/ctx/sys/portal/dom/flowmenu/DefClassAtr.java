package nts.uk.ctx.sys.portal.dom.flowmenu;

public enum DefClassAtr {
	/* New screen */
	New(0),

	/* Default screen */
	Default(1);

	public int value;

	private DefClassAtr(int type) {
		this.value = type;
	}

}
