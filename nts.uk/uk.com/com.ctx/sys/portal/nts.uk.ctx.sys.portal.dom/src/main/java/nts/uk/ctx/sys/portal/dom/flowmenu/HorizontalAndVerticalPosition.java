package nts.uk.ctx.sys.portal.dom.flowmenu;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 横縦の位置
 */
@Data
@AllArgsConstructor
public class HorizontalAndVerticalPosition {
	
	/**
	 * 横の位置
	 */
	private HorizontalPosition horizontalPosition;
	
	/**
	 * 縦の位置
	 */
	private VerticalPosition verticalPosition;
}
