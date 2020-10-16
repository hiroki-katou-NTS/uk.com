package nts.uk.ctx.sys.portal.dom.flowmenu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * サイズと位置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeAndPosition {

	/**
	 * column
	 */
	private HorizontalAndVerticalSize column;
	
	/**
	 * row
	 */
	private HorizontalAndVerticalSize row;
	
	/**
	 * height
	 */
	private HorizontalAndVerticalSize height;
	
	/**
	 * width
	 */
	private HorizontalAndVerticalSize width;
}
