package nts.uk.ctx.pr.proto.app.command.paymentdata.base;

import java.util.List;

import lombok.Value;

@Value
public class CategoryCommandBase {

	/** カテゴリ区分 */
	private int categoryAttribute;

	/** カテゴリ表示位置 */
	private int categoryPosition;

	private List<DetailItemCommandBase> details;

	public static CategoryCommandBase fromDomain(int categoryAttribute, int categoryPosition,
			List<DetailItemCommandBase> details) {
		return new CategoryCommandBase(categoryAttribute, categoryPosition, details);
	}
}
