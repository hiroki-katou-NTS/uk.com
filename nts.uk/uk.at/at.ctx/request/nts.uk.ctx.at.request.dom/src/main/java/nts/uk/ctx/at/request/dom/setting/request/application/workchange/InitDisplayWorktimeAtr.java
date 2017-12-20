package nts.uk.ctx.at.request.dom.setting.request.application.workchange;

public enum InitDisplayWorktimeAtr {
	/* 定時 */
	FIXEDTIME(0),
	/* 空白 */
	BLANK(1);
	
	public final int value;

	InitDisplayWorktimeAtr(int value) {
		this.value = value;
	}
}
