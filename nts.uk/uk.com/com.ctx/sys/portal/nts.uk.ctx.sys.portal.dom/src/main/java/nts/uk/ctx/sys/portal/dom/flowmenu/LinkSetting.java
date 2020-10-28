package nts.uk.ctx.sys.portal.dom.flowmenu;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.フローメニュー作成.リンク設定
 */
@Getter
public class LinkSetting extends DomainObject {

	/**
	 * URL
	 */
	private URL url;
	
	/**
	 * サイズと位置
	 */
	private SizeAndPosition sizeAndPosition;
	
	/**
	 * 文字の設定
	 */
	private FontSetting fontSetting;
	
	/**
	 * リンク内容
	 */
	private Optional<String> linkContent;
	
	public static LinkSetting createFromMemento(MementoGetter memento) {
		LinkSetting domain = new LinkSetting();
		domain.getMemento(memento);
		return domain;
	}
	
	public void getMemento(MementoGetter memento) {
		this.fontSetting = new FontSetting(
				new SizeAndColor(
						memento.getBold() == SizeAndColor.BOLD,
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
		this.url = new URL(memento.getUrl());
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setBold(this.fontSetting.getSizeAndColor().isBold() ? 1 : 0);
		memento.setColumn(this.sizeAndPosition.getColumn().v());
		memento.setFontSize(this.fontSetting.getSizeAndColor().getFontSize().v());
		memento.setHeight(this.sizeAndPosition.getHeight().v());
		memento.setHorizontalPosition(this.fontSetting.getPosition().getHorizontalPosition().value);
		memento.setLinkContent(linkContent.orElse(null));
		memento.setRow(this.sizeAndPosition.getRow().v());
		memento.setUrl(this.url.v());
		memento.setVerticalPosition(this.getFontSetting().getPosition().getVerticalPosition().value);
		memento.setWidth(this.sizeAndPosition.getWidth().v());
	}
	
	public static interface MementoGetter {
		String getFlowMenuCode();
		int getColumn();
		int getRow();
		String getLinkContent();
		String getUrl();
		int getWidth();
		int getHeight();
		int getFontSize();
		int getBold();
		int getHorizontalPosition();
		int getVerticalPosition();
	}
	
	public static interface MementoSetter {
		void setCid(String cid);
		void setFlowMenuCode(String flowMenuCode);
		void setColumn(int column);
		void setRow(int row);
		void setLinkContent(String linkContent);
		void setUrl(String url);
		void setWidth(int width);
		void setHeight(int height);
		void setFontSize(int fontSize);
		void setBold(int bold);
		void setHorizontalPosition(int horizontalPosition);
		void setVerticalPosition(int verticalPosition);
		void setContractCode(String contractCode);
	}
}
