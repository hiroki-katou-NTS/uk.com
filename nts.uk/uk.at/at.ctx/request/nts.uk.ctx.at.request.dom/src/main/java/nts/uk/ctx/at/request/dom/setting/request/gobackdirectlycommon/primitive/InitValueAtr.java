package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive;

public enum InitValueAtr {
	/* 事前 */
	PRE(0),
	/* 事後 */
	POST(1),
	/* 選択なし */
	NOCHOOSE(2);
	public final int value;

	InitValueAtr(int value) {
		this.value = value;
	}

}
