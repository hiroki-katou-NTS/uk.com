/**
 * 
 */
package nts.uk.ctx.pereg.dom.setting.favoriteorder;

import lombok.Getter;

/**
 * @author danpv
 * @domain お気に入りの順番
 */
@Getter
public class FavoriteOrderItem {
	
	/**
	 * お気に入りID
	 */
	private String favoriteId;
	
	/**
	 * 順番
	 */
	private int orderNumber;

}
