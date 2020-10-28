package nts.uk.ctx.sys.portal.dom.flowmenu;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.フローメニュー作成.矢印設定
 */
@Getter
@Builder
public class ArrowSetting extends DomainObject {
	
	/**
	 * 矢印ファイル名
	 */
	private FileName fileName;
	
	/**
	 * サイズと位置
	 */
	private SizeAndPosition sizeAndPosition;
}
