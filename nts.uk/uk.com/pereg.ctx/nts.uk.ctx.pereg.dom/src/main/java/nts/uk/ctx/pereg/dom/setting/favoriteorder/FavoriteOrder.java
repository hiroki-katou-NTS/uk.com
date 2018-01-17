/**
 * 
 */
package nts.uk.ctx.pereg.dom.setting.favoriteorder;

import java.util.List;

import lombok.Getter;

/**
 * @author danpv
 * @domain お気に入りの並び順
 */
@Getter
public class FavoriteOrder {
	
	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 順番
	 */
	private List<FavoriteOrderItem> orderItems;

}
