package nts.uk.ctx.sys.portal.dom.flowmenu;

import java.util.Optional;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 *UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.フローメニュー作成.画像設定
 */
@Getter
public class ImageSetting extends ValueObject {

	/**
	 * 画像ファイルID
	 */
	private Optional<String> fileId;
	
	/**
	 * 画像ファイル名
	 */
	private Optional<FileName> fileName;
	
	/**
	 * 既定区分
	 */
	private FixedClassification isFixed;
	
	/**
	 * サイズと位置
	 */
	private SizeAndPosition sizeAndPosition;
	
	public static ImageSetting createFromMemento(MementoGetter memento) {
		ImageSetting domain = new ImageSetting();
		domain.getMemento(memento);
		return domain;
	}
	
	public void getMemento(MementoGetter memento) {
		this.fileId = Optional.ofNullable(memento.getFileId());
		this.sizeAndPosition = new SizeAndPosition(
				new HorizontalAndVerticalSize(memento.getColumn()), 
				new HorizontalAndVerticalSize(memento.getRow()), 
				new HorizontalAndVerticalSize(memento.getHeight()), 
				new HorizontalAndVerticalSize(memento.getWidth()));
		this.fileName = memento.getFileName() != null
							? Optional.of(new FileName(memento.getFileName()))
							: Optional.empty();
		this.isFixed = EnumAdaptor.valueOf(memento.isFixed(), FixedClassification.class);
	}
	
	public void setMemento(MementoSetter memento) {
		memento.setColumn(this.sizeAndPosition.getColumn().v());
		memento.setHeight(this.sizeAndPosition.getHeight().v());
		memento.setFileId(this.fileId.orElse(null));
		memento.setFixed(this.isFixed.value);
		memento.setRow(this.sizeAndPosition.getRow().v());
		memento.setFileName(this.fileName.map(FileName::v).orElse(null));
		memento.setWidth(this.sizeAndPosition.getWidth().v());
	}
	
	public static interface MementoGetter {
		String getFlowMenuCode();
		int getColumn();
		int getRow();
		String getFileId();
		String getFileName();
		int isFixed();
		int getWidth();
		int getHeight();
	}
	
	public static interface MementoSetter {
		void setCid(String cid);
		void setFlowMenuCode(String flowMenuCode);
		void setColumn(int column);
		void setRow(int row);
		void setFileId(String fileId);
		void setFileName(String fileName);
		void setFixed(int isFixed);
		void setWidth(int width);
		void setHeight(int height);
		void setContractCode(String contractCode);
	}
}
