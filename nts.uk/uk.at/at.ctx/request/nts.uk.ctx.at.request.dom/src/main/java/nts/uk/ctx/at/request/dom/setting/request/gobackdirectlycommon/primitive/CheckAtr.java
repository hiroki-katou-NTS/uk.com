package nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.primitive;
/** 矛盾チェック区分*/
public enum CheckAtr {
	/**0: チェックしない */
	NOTCHECK(0),
	/**1: チェックする（登録不可) */
	CHECKNOTREGISTER(1),
	/**2: チェックする（登録可) */
	CHECKREGISTER(2);
	public final int value;

	CheckAtr(int value) {
		this.value = value;
	}

}
