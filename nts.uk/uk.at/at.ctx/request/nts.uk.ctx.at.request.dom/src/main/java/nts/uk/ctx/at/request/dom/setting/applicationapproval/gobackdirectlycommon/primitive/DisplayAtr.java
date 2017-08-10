package nts.uk.ctx.at.request.dom.setting.applicationapproval.gobackdirectlycommon.primitive;

/** 表示区分 */
public enum DisplayAtr {
	/*表示しない*/
	NOTDISPLAY(0),
	/*表示する*/
	DISPLAY(1);

	public final int value;

	DisplayAtr(int value) {
		this.value = value;
	}
}
