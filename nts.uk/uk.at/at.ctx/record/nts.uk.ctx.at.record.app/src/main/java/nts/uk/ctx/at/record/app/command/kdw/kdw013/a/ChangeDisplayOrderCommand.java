package nts.uk.ctx.at.record.app.command.kdw.kdw013.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeDisplayOrderCommand {

	public String sId;

	public List<FavoriteDisplayOrder> favoriteDisplayOrder;

}

@AllArgsConstructor
@NoArgsConstructor
@Data
class FavoriteDisplayOrder {
	/** お気に入りID */
	public String favId;

	/** 表示順 */
	public int order;
}
