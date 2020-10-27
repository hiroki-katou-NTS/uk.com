package nts.uk.ctx.sys.portal.dom.flowmenu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * フローメニューレイアウトのリンク設定DTo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkSettingDto implements LinkSetting.MementoSetter, LinkSetting.MementoGetter {

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
	 * ラベル内容									
	 */
	private String linkContent;
	
	/**
	 * URL									
	 */
	private String url;
	
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
