package nts.uk.ctx.sys.portal.dom.flowmenu;

/**
 * 縦の位置
 */
public enum VerticalPosition {

	//上揃え
	TOP(0),
	//上下中央揃え
	CENTER(1),
	//下揃え
	BOTTOM(2);
	
	public int value;

	private VerticalPosition(int value) {
		this.value = value;
	}
}
