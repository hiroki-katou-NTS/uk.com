/**
 * 
 */
package nts.uk.ctx.pereg.dom.setting.favoriteitem;

import lombok.Getter;

/**
 * @author danpv
 *
 */
@Getter
public class FavoriteItem {

	/**
	 * お気に入りID
	 */
	private String favoriteId;
	
	/**
	 * お気に入り項目ID
	 */
	private String favoriteItemId;
	
	/**
	 * 個人情報項目ID
	 */
	private String personInfoItemDefinitionID;
	
	/**
	 * 表示区分
	 */
	public Boolean display;
	
}
