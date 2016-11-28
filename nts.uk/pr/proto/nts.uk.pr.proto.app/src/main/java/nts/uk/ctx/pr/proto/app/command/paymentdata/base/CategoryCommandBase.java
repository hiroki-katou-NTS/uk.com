package nts.uk.ctx.pr.proto.app.command.paymentdata.base;

import java.util.List;

import lombok.Value;

@Value
public class CategoryCommandBase {

	/** カテゴリ区分 */
	int categoryAttribute;
	/** カテゴリ名 */
	String categoryName;
	/** カテゴリ表示位置 */
	int categoryPosition;

	private List<LineCommandBase> lines;

	public static CategoryCommandBase fromDomain(int categoryAttribute, String categoryName, int categoryPosition,
			List<LineCommandBase> lines) {
		return new CategoryCommandBase(categoryAttribute, categoryName, categoryPosition, lines);
	}
}
