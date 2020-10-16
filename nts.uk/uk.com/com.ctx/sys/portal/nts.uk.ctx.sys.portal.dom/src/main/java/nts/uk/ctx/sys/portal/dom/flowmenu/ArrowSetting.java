package nts.uk.ctx.sys.portal.dom.flowmenu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.sys.portal.dom.flowmenu.deprecated.FileName;

/**
 * 矢印設定
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ArrowSetting extends DomainObject {
	
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
