package nts.uk.ctx.sys.portal.dom.layout;

/**
 * @author lamdt 
 * 
 *  <br/>
 *  0: Top Page <br/>
 *  1: Title Menu <br/>
 *  2: My Page
 */
public enum PGType {
	/** Top Page トップページ */
	TopPage(0),
	/** Title Menu タイトルメニュー */
	TitleMenu(1),
	/** My Page マイページ */
	MyPage(2);
	
	public final int value;
	
	PGType(int type) {
		this.value = type;
	}
}