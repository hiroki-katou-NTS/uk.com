package nts.uk.ctx.pr.screen.app.query.paymentdata.result;

import java.util.List;

import lombok.Value;

@Value
public class CategoryDto {

	/** カテゴリ区分 */
	private int categoryAttribute;

	/** カテゴリ表示位置 */
	private int categoryPosition;

	private List<LineDto> lines;
	

	public static CategoryDto fromDomain(int categoryAttribute, int categoryPosition,
			List<LineDto> lines) {
		return new CategoryDto(categoryAttribute, categoryPosition, lines);
	}
}
