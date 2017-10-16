package nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class AddHoriTotalCategoryCommand {
	/**会社ID**/
	private String companyId;
	/** カテゴリコード */
	private String categoryCode;
	/** カテゴリ名称 */
	private String categoryName;
	/** メモ */
	private String memo;
	private List<TotalEvalOrderCommand> totalEvalOrders;
}
