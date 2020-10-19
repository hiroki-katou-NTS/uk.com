package nts.uk.ctx.sys.portal.dom.flowmenu;

import java.util.Optional;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.webmenu.ColorCode;

/**
 * 画像設定
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ImageSetting extends ValueObject {

	/**
	 * 画像ファイルID
	 */
	private String fileId;
	
	/**
	 * 画像ファイル名
	 */
	private FileName fileName;
	
	/**
	 * サイズと位置
	 */
	private SizeAndPosition sizeAndPosition;
	
	/**
	 * 文字の設定
	 */
	private FontSetting fontSetting;
	
	public static ImageSetting createFromMemento(MementoGetter memento) {
		ImageSetting domain = new ImageSetting();
		domain.getMemento(memento);
		return domain;
	}
	
	public void getMemento(MementoGetter memento) {
		this.fontSetting = new FontSetting(
				new SizeAndColor(
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
		this.fileId = memento.getFileId();
		this.sizeAndPosition = new SizeAndPosition(
				new HorizontalAndVerticalSize(memento.getColumn()), 
				new HorizontalAndVerticalSize(memento.getRow()), 
				new HorizontalAndVerticalSize(memento.getHeight()), 
				new HorizontalAndVerticalSize(memento.getWidth()));
		this.fileName = new FileName(memento.getFileName());
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setBackgroundColor(this.fontSetting.getSizeAndColor().getBackgroundColor().map(ColorCode::v).orElse(null));
		memento.setBold(this.fontSetting.getSizeAndColor().isBold() ? 1 : 0);
		memento.setColumn(this.sizeAndPosition.getColumn().v());
		memento.setFontSize(this.fontSetting.getSizeAndColor().getFontSize().v());
		memento.setHeight(this.sizeAndPosition.getHeight().v());
		memento.setHorizontalPosition(this.fontSetting.getPosition().getHorizontalPosition().value);
		memento.setFileId(this.fileId);
		memento.setRow(this.sizeAndPosition.getRow().v());
		memento.setTextColor(this.fontSetting.getSizeAndColor().getFontColor().map(ColorCode::v).orElse(null));
		memento.setFileName(this.fileName.v());
		memento.setVerticalPosition(this.getFontSetting().getPosition().getVerticalPosition().value);
		memento.setWidth(this.sizeAndPosition.getWidth().v());
	}
	
	public static interface MementoGetter {
		String getFlowMenuCode();
		int getColumn();
		int getRow();
		String getFileId();
		String getFileName();
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
		void setFileId(String fileId);
		void setFileName(String fileName);
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
