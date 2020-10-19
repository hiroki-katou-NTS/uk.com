package nts.uk.ctx.sys.portal.dom.flowmenu;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.portal.dom.webmenu.ColorCode;
import nts.uk.ctx.sys.portal.dom.webmenu.MenuCode;

/**
 * メニュー設定
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class MenuSetting {
	
	/**
	 * サイズと位置
	 */
	private SizeAndPosition sizeAndPosition;
	
	/**
	 * システム区分
	 */
	private System systemType;
	
	/**
	 * メニューコード
	 */
	private MenuCode menuCode;
	
	/**
	 * メニュー分類
	 */
	private MenuClassification menuClassification;
	
	/**
	 * 文字の設定
	 */
	private FontSetting fontSetting;
	
	/**
	 * メニュー名称
	 */
	private DisplayName menuName;
	
	public static MenuSetting createFromMemento(MementoGetter memento) {
		MenuSetting domain = new MenuSetting();
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
		this.sizeAndPosition = new SizeAndPosition(
				new HorizontalAndVerticalSize(memento.getColumn()), 
				new HorizontalAndVerticalSize(memento.getRow()), 
				new HorizontalAndVerticalSize(memento.getHeight()), 
				new HorizontalAndVerticalSize(memento.getWidth()));
		this.systemType = EnumAdaptor.valueOf(memento.getSystemType(), System.class);
		this.menuCode = new MenuCode(memento.getMenuCode());
		this.menuName = new DisplayName(memento.getMenuName());
		this.menuClassification = EnumAdaptor.valueOf(memento.getMenuClassification(), MenuClassification.class);
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setBackgroundColor(this.fontSetting.getSizeAndColor().getBackgroundColor().map(ColorCode::v).orElse(null));
		memento.setBold(this.fontSetting.getSizeAndColor().isBold() ? 1 : 0);
		memento.setColumn(this.sizeAndPosition.getColumn().v());
		memento.setFontSize(this.fontSetting.getSizeAndColor().getFontSize().v());
		memento.setHeight(this.sizeAndPosition.getHeight().v());
		memento.setHorizontalPosition(this.fontSetting.getPosition().getHorizontalPosition().value);
		memento.setRow(this.sizeAndPosition.getRow().v());
		memento.setTextColor(this.fontSetting.getSizeAndColor().getFontColor().map(ColorCode::v).orElse(null));
		memento.setVerticalPosition(this.getFontSetting().getPosition().getVerticalPosition().value);
		memento.setWidth(this.sizeAndPosition.getWidth().v());
		memento.setSystemType(this.systemType.value);
		memento.setMenuCode(this.menuCode.v());
		memento.setMenuName(this.menuName.v());
		memento.setMenuClassification(this.menuClassification.value);
	}
	
	public static interface MementoGetter {
		String getFlowMenuCode();
		int getColumn();
		int getRow();
		int getWidth();
		int getHeight();
		int getFontSize();
		int getBold();
		String getTextColor();
		String getBackgroundColor();
		int getHorizontalPosition();
		int getVerticalPosition();
		int getSystemType();
		String getMenuCode();
		String getMenuName();
		int getMenuClassification();
	}
	
	public static interface MementoSetter {
		void setCid(String cid);
		void setFlowMenuCode(String flowMenuCode);
		void setColumn(int column);
		void setRow(int row);
		void setWidth(int width);
		void setHeight(int height);
		void setFontSize(int fontSize);
		void setBold(int bold);
		void setTextColor(String textColor);
		void setBackgroundColor(String backgroundColor);
		void setHorizontalPosition(int horizontalPosition);
		void setVerticalPosition(int verticalPosition);
		void setContractCode(String contractCode);
		void setSystemType(int system);
		void setMenuCode(String menuCode);
		void setMenuName(String menuName);
		void setMenuClassification(int menuClassification);
	}
}
