package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive;

public enum WorkChangeFlg {
	DECIDECHANGE(0),

	DECIDENOTCHANGE(1),

	CHANGE(2),

	NOTCHANGE(3);

	public final int value;

	WorkChangeFlg(int value) {
		this.value = value;
	}

}
