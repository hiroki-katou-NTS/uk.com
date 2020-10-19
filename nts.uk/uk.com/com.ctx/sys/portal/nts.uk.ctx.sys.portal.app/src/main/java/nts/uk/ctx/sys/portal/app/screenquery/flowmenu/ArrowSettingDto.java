package nts.uk.ctx.sys.portal.app.screenquery.flowmenu;

import lombok.Data;
import nts.uk.ctx.sys.portal.dom.flowmenu.ArrowSetting;

/**
 * フローメニューレイアウトの矢印設定DTO
 */
@Data
public class ArrowSettingDto implements ArrowSetting.MementoSetter, ArrowSetting.MementoGetter {

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
	 * 矢印ファイル名																								
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

	@Override
	public void setContractCode(String contractCode) {
		//NOT USED
	}
}
