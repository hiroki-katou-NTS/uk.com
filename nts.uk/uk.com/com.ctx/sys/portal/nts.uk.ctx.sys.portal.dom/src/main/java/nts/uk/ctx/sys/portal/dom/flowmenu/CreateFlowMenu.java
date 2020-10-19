package nts.uk.ctx.sys.portal.dom.flowmenu;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.フローメニュー作成.フローメニュー作成
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CreateFlowMenu extends AggregateRoot {
	
	/**
	 * フローメニューコード
	 */
	private TopPagePartCode flowMenuCode;
	
	/**
	 * フローメニュー名称
	 */
	private TopPagePartName flowMenuName;
	
	/**
	 * フローメニューレイアウト
	 */
	private Optional<FlowMenuLayout> flowMenuLayout;
	
	/**
	 * 会社ID
	 */
	private String cid;
	
	public static CreateFlowMenu createFromMemento(MementoGetter memento) {
		CreateFlowMenu domain = new CreateFlowMenu();
		domain.getMemento(memento);
		return domain;
	}
	
	public void getMemento(MementoGetter memento) {
		this.flowMenuCode = new TopPagePartCode(memento.getFlowMenuCode());
		this.cid = memento.getCid();
		this.flowMenuName = new TopPagePartName(memento.getFileId());
		this.flowMenuLayout = memento.getFileId() != null 
				? Optional.of(new FlowMenuLayout(
					memento.getFileId(),
					memento.getMenuSettings(),
					memento.getLabelSettings(),
					memento.getLinkSettings(),
					memento.getFileAttachmentSettings(),
					memento.getImageSettings(),
					memento.getArrowSettings()))
				: Optional.empty();
	}
	
	public void setMemento(MementoSetter memento, String contractCode) {
		memento.setCid(this.cid);
		memento.setFlowMenuCode(this.flowMenuCode.v());
		memento.setFlowMenuName(this.flowMenuName.v());
		memento.setContractCode(contractCode);
		memento.setArrowSettings(this.flowMenuLayout.map(FlowMenuLayout::getArrowSettings).orElse(null), contractCode);
		memento.setFileAttachmentSettings(this.flowMenuLayout.map(FlowMenuLayout::getFileAttachmentSettings).orElse(null), contractCode);
		memento.setFileId(this.flowMenuLayout.map(FlowMenuLayout::getFileId).orElse(null));
		memento.setImageSettings(this.flowMenuLayout.map(FlowMenuLayout::getImageSettings).orElse(null), contractCode);
		memento.setLabelSettings(this.flowMenuLayout.map(FlowMenuLayout::getLabelSettings).orElse(null), contractCode);
		memento.setLinkSettings(this.flowMenuLayout.map(FlowMenuLayout::getLinkSettings).orElse(null), contractCode);
		memento.setMenuSettings(this.flowMenuLayout.map(FlowMenuLayout::getMenuSettings).orElse(null), contractCode);
	}
	
	public static interface MementoGetter {
		String getFlowMenuCode();
		String getFlowMenuName();
		String getCid();
		String getFileId();
		List<MenuSetting> getMenuSettings();
		List<ArrowSetting> getArrowSettings();
		List<FileAttachmentSetting> getFileAttachmentSettings();
		List<ImageSetting> getImageSettings();
		List<LabelSetting> getLabelSettings();
		List<LinkSetting> getLinkSettings();
	}
	
	public static interface MementoSetter {
		void setFlowMenuCode(String flowMenuCode);
		void setFlowMenuName(String flowMenuName);
		void setContractCode(String contractCode);
		void setCid(String cid);
		void setFileId(String fileId);
		void setMenuSettings(List<MenuSetting> menuSettings, String contractCode);
		void setArrowSettings(List<ArrowSetting> arrowSettings, String contractCode);
		void setFileAttachmentSettings(List<FileAttachmentSetting> fileAttachmentSettings, String contractCode);
		void setImageSettings(List<ImageSetting> imageSettings, String contractCode);
		void setLabelSettings(List<LabelSetting> labelSettings, String contractCode);
		void setLinkSettings(List<LinkSetting> linkSettings, String contractCode);
	}
}
