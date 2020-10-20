package nts.uk.ctx.sys.portal.dom.flowmenu;

import java.util.Optional;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.webmenu.ColorCode;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.フローメニュー作成.添付ファイル設定
 */
@Getter
public class FileAttachmentSetting extends ValueObject {

	/**
	 * 表示名称
	 */
	private Optional<String> linkContent;
	
	/**
	 * 添付ファイルID
	 */
	private String fileId;
	
	/**
	 * サイズと位置
	 */
	private SizeAndPosition sizeAndPosition;
	
	/**
	 * 文字の設定
	 */
	private FontSetting fontSetting;
	
	public static FileAttachmentSetting createFromMemento(MementoGetter memento) {
		FileAttachmentSetting domain = new FileAttachmentSetting();
		domain.getMemento(memento);
		return domain;
	}
	
	public void getMemento(MementoGetter memento) {
		this.fontSetting = new FontSetting(
				new SizeAndColor(
						memento.getBold() == SizeAndColor.BOLD,
						memento.getBackgroundColor() != null 
							? Optional.of(new ColorCode(memento.getBackgroundColor()))
							: Optional.empty(),
							memento.getTextColor() != null 
							? Optional.of(new ColorCode(memento.getTextColor()))
							: Optional.empty(),
						new FontSize(memento.getFontSize())), 
				new HorizontalAndVerticalPosition(
						EnumAdaptor.valueOf(memento.getHorizontalPosition(), HorizontalPosition.class), 
						EnumAdaptor.valueOf(memento.getVerticalPosition(), VerticalPosition.class)));
		this.linkContent = Optional.ofNullable(memento.getLinkContent());
		this.sizeAndPosition = new SizeAndPosition(
				new HorizontalAndVerticalSize(memento.getColumn()), 
				new HorizontalAndVerticalSize(memento.getRow()), 
				new HorizontalAndVerticalSize(memento.getHeight()), 
				new HorizontalAndVerticalSize(memento.getWidth()));
		this.fileId = memento.getFileId();
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setBackgroundColor(this.fontSetting.getSizeAndColor().getBackgroundColor().map(ColorCode::v).orElse(null));
		memento.setBold(this.fontSetting.getSizeAndColor().isBold() ? 1 : 0);
		memento.setColumn(this.sizeAndPosition.getColumn().v());
		memento.setFontSize(this.fontSetting.getSizeAndColor().getFontSize().v());
		memento.setHeight(this.sizeAndPosition.getHeight().v());
		memento.setHorizontalPosition(this.fontSetting.getPosition().getHorizontalPosition().value);
		memento.setLinkContent(this.linkContent.orElse(null));
		memento.setRow(this.sizeAndPosition.getRow().v());
		memento.setTextColor(this.fontSetting.getSizeAndColor().getFontColor().map(ColorCode::v).orElse(null));
		memento.setFileId(this.fileId);
		memento.setVerticalPosition(this.getFontSetting().getPosition().getVerticalPosition().value);
		memento.setWidth(this.sizeAndPosition.getWidth().v());
	}
	
	public static interface MementoGetter {
		String getFlowMenuCode();
		int getColumn();
		int getRow();
		String getLinkContent();
		String getFileId();
		int getWidth();
		int getHeight();
		int getFontSize();
		int getBold();
		String getTextColor();
		String getBackgroundColor();
		int getHorizontalPosition();
		int getVerticalPosition();
	}
	
	public static interface MementoSetter {
		void setCid(String cid);
		void setFlowMenuCode(String flowMenuCode);
		void setColumn(int column);
		void setRow(int row);
		void setLinkContent(String displayName);
		void setFileId(String fileId);
		void setWidth(int width);
		void setHeight(int height);
		void setFontSize(int fontSize);
		void setBold(int bold);
		void setTextColor(String textColor);
		void setBackgroundColor(String backgroundColor);
		void setHorizontalPosition(int horizontalPosition);
		void setVerticalPosition(int verticalPosition);
		void setContractCode(String contractCode);
	}
}
