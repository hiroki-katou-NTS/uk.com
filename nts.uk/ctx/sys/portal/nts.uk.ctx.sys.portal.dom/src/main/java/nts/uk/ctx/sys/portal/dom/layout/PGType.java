package nts.uk.ctx.sys.portal.dom.layout;

/**
 *  0: My Page <br/>
 *  1: Title Menu <br/>
 *  2: My Page
 *  */
public enum PGType {
	/** Top Page トップページ */
	TopPage(0, "トップページ"),
	/** Title Menu タイトルメニュー */
	TitleMenu(1, "タイトルメニュー"),
	/** My Page マイページ */
	MyPage(2, "マイページ");
	
	public final int value;
	public final String name;
	
	PGType(int type, String name) {
		this.value = type;
		this.name = name;
	}
}