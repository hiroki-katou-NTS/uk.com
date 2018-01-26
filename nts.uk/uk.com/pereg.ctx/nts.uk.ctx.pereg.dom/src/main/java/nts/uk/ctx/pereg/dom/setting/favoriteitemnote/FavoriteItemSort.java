/**
 * 
 */
package nts.uk.ctx.pereg.dom.setting.favoriteitemnote;

import java.util.List;

import lombok.Getter;

/**
 * @author danpv
 * @Domain お気に入り項目のソート
 */
@Getter
public class FavoriteItemSort {
	
	/**
	 * お気に入りID
	 */
	private String favoriteId;
	
	/**
	 * ソート
	 */
	private List<FavoriteSort> sortList;

}
