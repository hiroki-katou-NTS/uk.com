package nts.uk.ctx.sys.portal.dom.flowmenu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.portal.dom.flowmenu.ArrowSetting;

/**
 * フローメニューレイアウトの矢印設定DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
