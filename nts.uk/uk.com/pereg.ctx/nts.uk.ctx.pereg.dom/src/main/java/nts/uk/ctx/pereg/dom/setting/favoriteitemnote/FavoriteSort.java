/**
 * 
 */
package nts.uk.ctx.pereg.dom.setting.favoriteitemnote;

import lombok.Getter;

/**
 * @author danpv
 * @Domain ソート
 *
 */
@Getter
public class FavoriteSort {
	
	/**
	 * お気に入り項目ID
	 */
	private String favoriteItemId;
	
	/**
	 * ソート順
	 */
	private int orderNumber;
	
	/**
	 * ソート対象
	 */
	private SortObject sortObject;

}
