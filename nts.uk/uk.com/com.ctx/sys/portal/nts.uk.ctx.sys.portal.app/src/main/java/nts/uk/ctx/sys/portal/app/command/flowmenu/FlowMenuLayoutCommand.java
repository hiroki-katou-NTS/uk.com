package nts.uk.ctx.sys.portal.app.command.flowmenu;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.ArrowSettingDto;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.FileAttachmentSettingDto;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.ImageSettingDto;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.LabelSettingDto;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.LinkSettingDto;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.MenuSettingDto;
import nts.uk.ctx.sys.portal.dom.flowmenu.ArrowSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.FileAttachmentSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.FlowMenuLayout;
import nts.uk.ctx.sys.portal.dom.flowmenu.ImageSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.LabelSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.LinkSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.MenuSetting;

@Value
public class FlowMenuLayoutCommand implements FlowMenuLayout.MementoGetter {
	
	/**
	 * ファイルID
	 */
	private String fileId; 
	
	/**
	 * メニュー設定
	 */
	private List<MenuSettingDto> menuSettings;
	
	/**
	 * ラベル設定
	 */
	private List<LabelSettingDto> labelSettings;
	
	/**
	 * リンク設定
	 */
	private List<LinkSettingDto> linkSettings;
	
	/**
	 * 添付ファイル設定
	 */
	private List<FileAttachmentSettingDto> fileAttachmentSettings;
	
	/**
	 * 画像設定
	 */
	private List<ImageSettingDto> imageSettings;
	
	/**
	 * 矢印設定
	 */
	private List<ArrowSettingDto> arrowSettings;

	@Override
	public List<MenuSetting> getMenuSettings() {
		return this.menuSettings.stream().map(MenuSetting::createFromMemento).collect(Collectors.toList());
	}

	@Override
	public List<LabelSetting> getLabelSettings() {
		return this.labelSettings.stream().map(LabelSetting::createFromMemento).collect(Collectors.toList());
	}

	@Override
	public List<LinkSetting> getLinkSettings() {
		return this.linkSettings.stream().map(LinkSetting::createFromMemento).collect(Collectors.toList());
	}

	@Override
	public List<FileAttachmentSetting> getFileAttachmentSettings() {
		return this.fileAttachmentSettings.stream().map(FileAttachmentSetting::createFromMemento).collect(Collectors.toList());
	}

	@Override
	public List<ImageSetting> getImageSettings() {
		return this.imageSettings.stream().map(ImageSetting::createFromMemento).collect(Collectors.toList());
	}

	@Override
	public List<ArrowSetting> getArrowSettings() {
		return this.arrowSettings.stream().map(ArrowSetting::createFromMemento).collect(Collectors.toList());
	}
}
