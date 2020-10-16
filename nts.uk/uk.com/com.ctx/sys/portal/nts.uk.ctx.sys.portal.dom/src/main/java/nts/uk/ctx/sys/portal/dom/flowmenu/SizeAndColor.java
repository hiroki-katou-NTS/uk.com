package nts.uk.ctx.sys.portal.dom.flowmenu;

import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.webmenu.ColorCode;

/**
 * サイズと色
 */
@Data
public class SizeAndColor {
	
	/**
	 * 太字
	 */
	private boolean isBold = false;
	
	/**
	 * 背景の色
	 */
	private Optional<ColorCode> backgroundColor;
	
	/**
	 * 文字の色
	 */
	private Optional<ColorCode> fontColor;
	
	/**
	 * 文字のサイズ
	 */
	private FontSize fontSize;

	public SizeAndColor(Optional<ColorCode> backgroundColor, Optional<ColorCode> fontColor, FontSize fontSize) {
		super();
		this.backgroundColor = backgroundColor;
		this.fontColor = fontColor;
		this.fontSize = fontSize;
	}
}
