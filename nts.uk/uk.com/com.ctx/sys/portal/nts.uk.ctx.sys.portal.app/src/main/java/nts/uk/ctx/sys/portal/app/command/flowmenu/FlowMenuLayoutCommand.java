package nts.uk.ctx.sys.portal.app.command.flowmenu;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.ArrowSettingDto;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.FileAttachmentSettingDto;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.ImageSettingDto;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.LabelSettingDto;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.LinkSettingDto;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.MenuSettingDto;
import nts.uk.ctx.sys.portal.dom.flowmenu.ArrowSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.CreateFlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.DisplayName;
import nts.uk.ctx.sys.portal.dom.flowmenu.FileAttachmentSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.FileName;
import nts.uk.ctx.sys.portal.dom.flowmenu.FixedClassification;
import nts.uk.ctx.sys.portal.dom.flowmenu.FontSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.FontSize;
import nts.uk.ctx.sys.portal.dom.flowmenu.HorizontalAndVerticalPosition;
import nts.uk.ctx.sys.portal.dom.flowmenu.HorizontalAndVerticalSize;
import nts.uk.ctx.sys.portal.dom.flowmenu.HorizontalPosition;
import nts.uk.ctx.sys.portal.dom.flowmenu.ImageSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.LabelContent;
import nts.uk.ctx.sys.portal.dom.flowmenu.LabelSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.LinkSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.MenuClassification;
import nts.uk.ctx.sys.portal.dom.flowmenu.MenuSetting;
import nts.uk.ctx.sys.portal.dom.flowmenu.SizeAndColor;
import nts.uk.ctx.sys.portal.dom.flowmenu.SizeAndPosition;
import nts.uk.ctx.sys.portal.dom.flowmenu.System;
import nts.uk.ctx.sys.portal.dom.flowmenu.URL;
import nts.uk.ctx.sys.portal.dom.flowmenu.VerticalPosition;
import nts.uk.ctx.sys.portal.dom.webmenu.ColorCode;
import nts.uk.ctx.sys.portal.dom.webmenu.MenuCode;

@Value
public class FlowMenuLayoutCommand implements CreateFlowMenu.MementoGetter {
	
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
		return this.menuSettings.stream()
				.map(dto -> MenuSetting.builder()
						.fontSetting(new FontSetting(
								new SizeAndColor(dto.getBold() == SizeAndColor.BOLD, Optional.empty(), Optional.empty(), new FontSize(dto.getFontSize())), 
								new HorizontalAndVerticalPosition(
										EnumAdaptor.valueOf(dto.getHorizontalPosition(), HorizontalPosition.class), 
										EnumAdaptor.valueOf(dto.getVerticalPosition(), VerticalPosition.class))))
						.menuClassification(EnumAdaptor.valueOf(dto.getMenuClassification(), MenuClassification.class))
						.menuCode(new MenuCode(dto.getMenuCode()))
						.menuName(new DisplayName(dto.getMenuName()))
						.sizeAndPosition(new SizeAndPosition(
								new HorizontalAndVerticalSize(dto.getColumn()),
								new HorizontalAndVerticalSize(dto.getRow()),
								new HorizontalAndVerticalSize(dto.getHeight()),
								new HorizontalAndVerticalSize(dto.getWidth())))
						.systemType(EnumAdaptor.valueOf(dto.getSystemType(), System.class))
						.build())
				.collect(Collectors.toList());
	}

	@Override
	public List<ArrowSetting> getArrowSettings() {
		return this.arrowSettings.stream()
				.map(dto -> ArrowSetting.builder()
						.fileName(new FileName(dto.getFileName()))
						.sizeAndPosition(new SizeAndPosition(
								new HorizontalAndVerticalSize(dto.getColumn()),
								new HorizontalAndVerticalSize(dto.getRow()),
								new HorizontalAndVerticalSize(dto.getHeight()),
								new HorizontalAndVerticalSize(dto.getWidth())))
						.build())
				.collect(Collectors.toList());
	}

	@Override
	public List<FileAttachmentSetting> getFileAttachmentSettings() {
		return this.fileAttachmentSettings.stream()
				.map(dto -> FileAttachmentSetting.builder()
						.fileId(dto.getFileId())
						.fontSetting(new FontSetting(
								new SizeAndColor(dto.getBold() == SizeAndColor.BOLD, Optional.empty(), Optional.empty(), new FontSize(dto.getFontSize())), 
								new HorizontalAndVerticalPosition(
										EnumAdaptor.valueOf(dto.getHorizontalPosition(), HorizontalPosition.class), 
										EnumAdaptor.valueOf(dto.getVerticalPosition(), VerticalPosition.class))))
						.linkContent(Optional.ofNullable(dto.getLinkContent()))
						.sizeAndPosition(new SizeAndPosition(
								new HorizontalAndVerticalSize(dto.getColumn()),
								new HorizontalAndVerticalSize(dto.getRow()),
								new HorizontalAndVerticalSize(dto.getHeight()),
								new HorizontalAndVerticalSize(dto.getWidth())))
						.build())
				.collect(Collectors.toList());
	}

	@Override
	public List<ImageSetting> getImageSettings() {
		return this.imageSettings.stream()
				.map(dto -> ImageSetting.builder()
						.fileId(Optional.ofNullable(dto.getFileId()))
						.fileName(dto.getFileName() != null ? Optional.of(new FileName(dto.getFileName())) : Optional.empty())
						.isFixed(EnumAdaptor.valueOf(dto.getIsFixed(), FixedClassification.class))
						.sizeAndPosition(new SizeAndPosition(
								new HorizontalAndVerticalSize(dto.getColumn()),
								new HorizontalAndVerticalSize(dto.getRow()),
								new HorizontalAndVerticalSize(dto.getHeight()),
								new HorizontalAndVerticalSize(dto.getWidth())))
						.build())
				.collect(Collectors.toList());
	}

	@Override
	public List<LabelSetting> getLabelSettings() {
		return this.labelSettings.stream()
				.map(dto -> LabelSetting.builder()
						.fontSetting(new FontSetting(
								new SizeAndColor(
										dto.getBold() == SizeAndColor.BOLD,
										Optional.of(new ColorCode(dto.getBackgroundColor())),
										Optional.of(new ColorCode(dto.getTextColor())),
										new FontSize(dto.getFontSize())), 
								new HorizontalAndVerticalPosition(
										EnumAdaptor.valueOf(dto.getHorizontalPosition(), HorizontalPosition.class), 
										EnumAdaptor.valueOf(dto.getVerticalPosition(), VerticalPosition.class))))
						.labelContent(dto.getLabelContent() != null 
								? Optional.of(new LabelContent(dto.getLabelContent())) 
								: Optional.empty())
						.sizeAndPosition(new SizeAndPosition(
								new HorizontalAndVerticalSize(dto.getColumn()),
								new HorizontalAndVerticalSize(dto.getRow()),
								new HorizontalAndVerticalSize(dto.getHeight()),
								new HorizontalAndVerticalSize(dto.getWidth())))
						.build())
				.collect(Collectors.toList());
	}

	@Override
	public List<LinkSetting> getLinkSettings() {
		return this.linkSettings.stream()
				.map(dto -> LinkSetting.builder()
						.fontSetting(new FontSetting(
								new SizeAndColor(
										dto.getBold() == SizeAndColor.BOLD,
										Optional.empty(),
										Optional.empty(),
										new FontSize(dto.getFontSize())), 
								new HorizontalAndVerticalPosition(
										EnumAdaptor.valueOf(dto.getHorizontalPosition(), HorizontalPosition.class), 
										EnumAdaptor.valueOf(dto.getVerticalPosition(), VerticalPosition.class))))
						.linkContent(Optional.ofNullable(dto.getLinkContent()))
						.sizeAndPosition(new SizeAndPosition(
								new HorizontalAndVerticalSize(dto.getColumn()),
								new HorizontalAndVerticalSize(dto.getRow()),
								new HorizontalAndVerticalSize(dto.getHeight()),
								new HorizontalAndVerticalSize(dto.getWidth())))
						.url(new URL(dto.getUrl()))
						.build())
				.collect(Collectors.toList());
	}

	@Override
	public String getFlowMenuCode() {
		//NOT USED
		return null;
	}

	@Override
	public String getFlowMenuName() {
		//NOT USED
		return null;
	}

	@Override
	public String getCid() {
		//NOT USED
		return null;
	}
}
