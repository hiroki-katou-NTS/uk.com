package nts.uk.ctx.sys.log.dom.enums;

public enum WebMenuSetting {
	/** 0:表示しない */
	Notdisplay(0),

	/** 1:表示する */
	Display(1);

	public int value;

	WebMenuSetting(int type) {
		this.value = type;
	}
}
