package nts.uk.ctx.sys.portal.infra.entity.flowmenu;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.sys.portal.dom.flowmenu.ArrowSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.CreateFlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.FileAttachmentSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.ImageSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.LabelSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.LinkSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.MenuSetting;

/**
 * フローメニュー作成																			
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "SPTMT_FLOWMENU_CREATE")
public class SptmtCreateFlowMenu implements Serializable,
											CreateFlowMenu.MementoGetter,
											CreateFlowMenu.MementoSetter {

	private static final long serialVersionUID = 1L;

	// column 排他バージョン
	@Version
	@Column(name = "EXCLUS_VER")
	private long version;
	
	@EmbeddedId
	private SptmtCreateFlowMenuPk pk;
	
	/**
	 * 契約コード									
	 */
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	private String contractCode;
	
	/**
	 * フローメニュー名称									
	 */
	@Basic(optional = false)
	@Column(name = "FLOW_MENU_NAME")
	private String flowMenuName;
	
	/**
	 * ファイルID									
	 */
	@Basic(optional = true)
	@Column(name = "FILE_ID")
	private String fileId;
	
	/**
	 * フローメニューレイアウトの矢印設定
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "flowMenu", orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "SPTMT_FLOW_LAYOUT_ARROW")
	private List<SptmtFlowLayoutArrow> arrowSettings;
	
	/**
	 * フローメニューレイアウトの添付ファイル設定
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "flowMenu", orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "SPTMT_FLOW_LAYOUT_ATT_LINK")
	private List<SptmtFlowLayoutFileAttachment> fileAttachmentSettings;
	
	/**
	 * フローメニューレイアウトの画像設定
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "flowMenu", orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "SPTMT_FLOW_LAYOUT_IMG")
	private List<SptmtFlowLayoutImage> imageSettings;
	
	/**
	 * フローメニューレイアウトのラベル設定
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "flowMenu", orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "SPTMT_FLOW_LAYOUT_LABEL")
	private List<SptmtFlowLayoutLabel> labelSettings;
	
	/**
	 * フローメニューレイアウトのリンク設定
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "flowMenu", orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "SPTMT_FLOW_LAYOUT_LINK")
	private List<SptmtFlowLayoutLink> linkSettings;
	
	/**
	 * フローメニューレイアウトのメニュー設定
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "flowMenu", orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "SPTMT_FLOW_LAYOUT_MENU")
	private List<SptmtFlowLayoutMenu> menuSettings;

	@Override
	public void setFlowMenuCode(String flowMenuCode) {
		if (pk == null)
			pk = new SptmtCreateFlowMenuPk();
		pk.flowMenuCode = flowMenuCode;
	}

	@Override
	public void setCid(String cid) {
		if (pk == null)
			pk = new SptmtCreateFlowMenuPk();
		pk.cid = cid;
	}

	@Override
	public void setMenuSettings(List<MenuSetting> menuSettings, String contractCode) {
		this.menuSettings = menuSettings.stream()
				.map(domain -> {
					SptmtFlowLayoutMenu entity = new SptmtFlowLayoutMenu();
					domain.setMemento(entity);
					entity.setContractCode(contractCode);
					return entity;
				}).collect(Collectors.toList());
	}

	@Override
	public void setArrowSettings(List<ArrowSetting> arrowSettings, String contractCode) {
		this.arrowSettings = arrowSettings.stream()
				.map(domain -> {
					SptmtFlowLayoutArrow entity = new SptmtFlowLayoutArrow();
					domain.setMemento(entity);
					entity.setContractCode(contractCode);
					return entity;
				}).collect(Collectors.toList());
	}

	@Override
	public void setFileAttachmentSettings(List<FileAttachmentSetting> fileAttachmentSettings, String contractCode) {
		this.fileAttachmentSettings = fileAttachmentSettings.stream()
				.map(domain -> {
					SptmtFlowLayoutFileAttachment entity = new SptmtFlowLayoutFileAttachment();
					domain.setMemento(entity);
					entity.setContractCode(contractCode);
					return entity;
				}).collect(Collectors.toList());
	}

	@Override
	public void setImageSettings(List<ImageSetting> imageSettings, String contractCode) {
		this.imageSettings = imageSettings.stream()
				.map(domain -> {
					SptmtFlowLayoutImage entity = new SptmtFlowLayoutImage();
					domain.setMemento(entity);
					entity.setContractCode(contractCode);
					return entity;
				}).collect(Collectors.toList());
	}

	@Override
	public void setLabelSettings(List<LabelSetting> labelSettings, String contractCode) {
		this.labelSettings = labelSettings.stream()
				.map(domain -> {
					SptmtFlowLayoutLabel entity = new SptmtFlowLayoutLabel();
					domain.setMemento(entity);
					entity.setContractCode(contractCode);
					return entity;
				}).collect(Collectors.toList());
	}

	@Override
	public void setLinkSettings(List<LinkSetting> linkSettings, String contractCode) {
		this.linkSettings = linkSettings.stream()
				.map(domain -> {
					SptmtFlowLayoutLink entity = new SptmtFlowLayoutLink();
					domain.setMemento(entity);
					entity.setContractCode(contractCode);
					return entity;
				}).collect(Collectors.toList());
	}

	@Override
	public String getFlowMenuCode() {
		if (pk != null)
			return pk.flowMenuCode;
		return null;
	}

	@Override
	public String getCid() {
		if (pk != null)
			return pk.cid;
		return null;
	}

	@Override
	public List<MenuSetting> getMenuSettings() {
		return this.menuSettings.stream()
				.map(MenuSetting::createFromMemento)
				.collect(Collectors.toList());
	}

	@Override
	public List<ArrowSetting> getArrowSettings() {
		return this.arrowSettings.stream()
				.map(ArrowSetting::createFromMemento)
				.collect(Collectors.toList());
	}

	@Override
	public List<FileAttachmentSetting> getFileAttachmentSettings() {
		return this.fileAttachmentSettings.stream()
				.map(FileAttachmentSetting::createFromMemento)
				.collect(Collectors.toList());
	}

	@Override
	public List<ImageSetting> getImageSettings() {
		return this.imageSettings.stream()
				.map(ImageSetting::createFromMemento)
				.collect(Collectors.toList());
	}

	@Override
	public List<LabelSetting> getLabelSettings() {
		return this.labelSettings.stream()
				.map(LabelSetting::createFromMemento)
				.collect(Collectors.toList());
	}

	@Override
	public List<LinkSetting> getLinkSettings() {
		return this.linkSettings.stream()
				.map(LinkSetting::createFromMemento)
				.collect(Collectors.toList());
	}
}
