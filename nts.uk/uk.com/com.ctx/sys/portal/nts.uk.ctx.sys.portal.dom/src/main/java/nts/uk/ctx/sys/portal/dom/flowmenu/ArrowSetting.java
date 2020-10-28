package nts.uk.ctx.sys.portal.dom.flowmenu;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.フローメニュー作成.矢印設定
 */
@Getter
public class ArrowSetting extends ValueObject {
	
	/**
	 * 矢印ファイル名
	 */
	private FileName fileName;
	
	/**
	 * サイズと位置
	 */
	private SizeAndPosition sizeAndPosition;
	
	public static ArrowSetting createFromMemento(MementoGetter memento) {
		ArrowSetting domain = new ArrowSetting();
		domain.getMemento(memento);
		return domain;
	}
	
	public void getMemento(MementoGetter memento) {
		this.sizeAndPosition = new SizeAndPosition(
				new HorizontalAndVerticalSize(memento.getColumn()), 
				new HorizontalAndVerticalSize(memento.getRow()), 
				new HorizontalAndVerticalSize(memento.getHeight()), 
				new HorizontalAndVerticalSize(memento.getWidth()));
		this.fileName = new FileName(memento.getFileName());
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setColumn(this.sizeAndPosition.getColumn().v());
		memento.setHeight(this.sizeAndPosition.getHeight().v());
		memento.setRow(this.sizeAndPosition.getRow().v());
		memento.setFileName(this.fileName.v());
		memento.setWidth(this.sizeAndPosition.getWidth().v());
	}
	
	public static interface MementoGetter {
		String getFlowMenuCode();
		int getColumn();
		int getRow();
		String getFileName();
		int getWidth();
		int getHeight();
	}
	
	public static interface MementoSetter {
		void setCid(String cid);
		void setFlowMenuCode(String flowMenuCode);
		void setColumn(int column);
		void setRow(int row);
		void setFileName(String fileName);
		void setWidth(int width);
		void setHeight(int height);
		void setContractCode(String contractCode);
	}
}
