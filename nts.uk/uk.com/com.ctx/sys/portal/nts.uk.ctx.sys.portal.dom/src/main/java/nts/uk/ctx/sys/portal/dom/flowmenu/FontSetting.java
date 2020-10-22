package nts.uk.ctx.sys.portal.dom.flowmenu;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.フローメニュー作成.文字の設定
 */
@Getter
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
