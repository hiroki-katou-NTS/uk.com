/**
 * 
 */
package nts.uk.ctx.sys.portal.dom.enums;

/**
 * @author hieult
 *
 */
public enum MenuAtr {
	
	/**0:区切り線 */
	SeparatorLine(0),
	/**1:メニュー */
	Menu(1);
	
	public int value;

	MenuAtr(int type) {
		this.value = type;
	}

}
