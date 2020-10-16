package nts.uk.ctx.sys.portal.dom.flowmenu;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Width;

/**
 * サイズ
 */
@Data
public class Size {
	
	/**
	 * 横サイズ
	 */
	private Width width;
	
	/**
	 * 縦サイズ
	 */
	private Size size;
}
