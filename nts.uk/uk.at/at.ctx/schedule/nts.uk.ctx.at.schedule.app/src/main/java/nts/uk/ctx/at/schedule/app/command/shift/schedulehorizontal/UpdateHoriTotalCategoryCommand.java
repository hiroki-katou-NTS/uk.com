package nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateHoriTotalCategoryCommand {
	/** カテゴリコード */
	private String categoryCode;
	/** カテゴリ名称 */
	private String categoryName;
	/** メモ */
	private String memo;
	private List<TotalEvalOrderCommand> totalEvalOrders;
}
