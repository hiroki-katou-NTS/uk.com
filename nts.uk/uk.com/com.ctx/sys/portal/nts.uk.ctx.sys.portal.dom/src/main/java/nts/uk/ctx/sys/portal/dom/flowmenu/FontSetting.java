package nts.uk.ctx.sys.portal.dom.flowmenu;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 文字の設定
 */
@Data
@AllArgsConstructor
public class FontSetting {

	/**
	 * サイズと色
	 */
	private SizeAndColor sizeAndColor;
	
	/**
	 * 横縦の位置
	 */
	private HorizontalAndVerticalPosition position;
}
