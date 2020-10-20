package nts.uk.ctx.sys.portal.dom.flowmenu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.portal.dom.flowmenu.ImageSetting;

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
	 * width
	 */
	private int width;
	
	/**
	 * height
	 */
	private int height;
	
	/**
	 * 文字のサイズ									
	 */
	private int fontSize;
	
	/**
	 * 太字
	 */
	private int bold;
	
	/**
	 * 文字の色									
	 */
	private String textColor;
	
	/**
	 * 背景の色									
	 */
	private String backgroundColor;
	
	/**
	 * 横の位置
	 */
	private int horizontalPosition;
	
	/**
	 * 縦の位置
	 */
	private int verticalPosition;

	@Override
	public void setContractCode(String contractCode) {
		//NOT USED
	}
}
