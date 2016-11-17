package nts.uk.ctx.pr.proto.app.paymentdata.command;

import java.util.List;

import lombok.Value;

@Value
public class CategoryCommand {

	/** カテゴリ区分 */
	private int categoryAttribute;

	/** カテゴリ表示位置 */
	private int categoryPosition;

	private List<DetailItemCommand> details;

	public static CategoryCommand fromDomain(int categoryAttribute, int categoryPosition,
			List<DetailItemCommand> details) {
		return new CategoryCommand(categoryAttribute, categoryPosition, details);
	}
}
