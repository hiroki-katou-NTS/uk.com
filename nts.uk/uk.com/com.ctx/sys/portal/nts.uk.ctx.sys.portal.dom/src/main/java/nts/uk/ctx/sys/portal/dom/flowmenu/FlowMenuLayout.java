package nts.uk.ctx.sys.portal.dom.flowmenu;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.フローメニュー作成.フローメニューレイアウト
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FlowMenuLayout extends DomainObject {
	
	/**
	 * ファイルID
	 */
	private String fileId; 
	
	/**
	 * メニュー設定
	 */
	private List<MenuSetting> menuSettings;
	
	/**
	 * ラベル設定
	 */
	private List<LabelSetting> labelSettings;
	
	/**
	 * リンク設定
	 */
	private List<LinkSetting> linkSettings;
	
	/**
	 * 添付ファイル設定
	 */
	private List<FileAttachmentSetting> fileAttachmentSettings;
	
	/**
	 * 画像設定
	 */
	private List<ImageSetting> imageSettings;
	
	/**
	 * 矢印設定
	 */
	private List<ArrowSetting> arrowSettings;
}
