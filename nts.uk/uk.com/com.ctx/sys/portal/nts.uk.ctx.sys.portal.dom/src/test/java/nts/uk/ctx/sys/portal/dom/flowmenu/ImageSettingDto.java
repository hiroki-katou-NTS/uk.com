package nts.uk.ctx.sys.portal.dom.flowmenu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  フローメニューレイアウトの画像設定
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageSettingDto implements ImageSetting.MementoSetter, ImageSetting.MementoGetter {
	
	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * フローメニューコード
	 */
	private String flowMenuCode;

	/**
	 * column
	 */
	private int column;

	/**
	 * row
	 */
	private int row;
	
	/**
	 * 画像ファイルID																								
	 */
	private String fileId;
	
	/**
	 * 画像ファイル名																	
	 */
	private String fileName;
	
	/**
	 * 既定区分
	 */
	private int isFixed;
	
	/**
	 * width
	 */
	private int width;
	
	/**
	 * height
	 */
	private int height;

	@Override
	public void setContractCode(String contractCode) {
		//NOT USED
	}

	@Override
	public int isFixed() {
		return isFixed;
	}

	@Override
	public void setFixed(int isFixed) {
		this.isFixed = isFixed;
	}
}
