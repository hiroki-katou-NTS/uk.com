package nts.uk.ctx.sys.portal.dom.enums;

public enum DefClassAtr {
	/* New screen */
	New(0),

	/* Default screen */
	Default(1);

	private int value;

	DefClassAtr(int type) {
		this.value = type;
	}

	public int getValue() {
		return this.value;
	}

}
