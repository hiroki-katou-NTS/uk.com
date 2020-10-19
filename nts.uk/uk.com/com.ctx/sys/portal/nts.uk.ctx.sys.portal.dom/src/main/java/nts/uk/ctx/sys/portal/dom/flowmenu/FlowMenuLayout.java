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
	
	public static FlowMenuLayout createFromMemento(MementoGetter memento) {
		FlowMenuLayout domain = new FlowMenuLayout();
		domain.getMemento(memento);
		return domain;
	}
	
	public void getMemento(MementoGetter memento) {
		this.fileId = memento.getFileId();
		this.arrowSettings = memento.getArrowSettings();
		this.fileAttachmentSettings = memento.getFileAttachmentSettings();
		this.imageSettings = memento.getImageSettings();
		this.labelSettings = memento.getLabelSettings();
		this.linkSettings = memento.getLinkSettings();
		this.menuSettings = memento.getMenuSettings();
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setFileId(this.fileId);
		memento.setArrowSettings(this.arrowSettings);
		memento.setFileAttachmentSettings(this.fileAttachmentSettings);
		memento.setImageSettings(this.imageSettings);
		memento.setLabelSettings(this.labelSettings);
		memento.setLinkSettings(this.linkSettings);
		memento.setMenuSettings(this.menuSettings);
	}
	
	public static interface MementoGetter {
		String getFileId();
		List<MenuSetting> getMenuSettings();
		List<LabelSetting> getLabelSettings();
		List<LinkSetting> getLinkSettings();
		List<FileAttachmentSetting> getFileAttachmentSettings();
		List<ImageSetting> getImageSettings();
		List<ArrowSetting> getArrowSettings();
	}
	
	public static interface MementoSetter {
		void setFileId(String fileId);
		void setMenuSettings(List<MenuSetting> menuSettings);
		void setLabelSettings(List<LabelSetting> labelSettings);
		void setLinkSettings(List<LinkSetting> linkSettings);
		void setFileAttachmentSettings(List<FileAttachmentSetting> fileAttachmentSettings);
		void setImageSettings(List<ImageSetting> imageSettings);
		void setArrowSettings(List<ArrowSetting> arrowSettings);
	}
}
