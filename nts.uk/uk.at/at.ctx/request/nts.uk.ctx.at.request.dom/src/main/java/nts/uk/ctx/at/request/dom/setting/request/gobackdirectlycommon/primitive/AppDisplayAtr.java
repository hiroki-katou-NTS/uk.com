package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive;

/** 表示区分 */
public enum AppDisplayAtr {
	/*表示しない*/
	NOTDISPLAY(0),
	/*表示する*/
	DISPLAY(1);

	public final int value;

	AppDisplayAtr(int value) {
		this.value = value;
	}
}
