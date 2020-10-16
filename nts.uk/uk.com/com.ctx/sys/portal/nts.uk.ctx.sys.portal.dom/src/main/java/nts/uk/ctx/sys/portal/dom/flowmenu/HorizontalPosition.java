package nts.uk.ctx.sys.portal.dom.flowmenu;

/**
 * 横の位置
 */
public enum HorizontalPosition {
	
	//左揃え
	LEFT(0),
	//中央揃え
	CENTER(1),
	//右揃え
	RIGHT(2);
	
	public int value;

	private HorizontalPosition(int value) {
		this.value = value;
	}
}
