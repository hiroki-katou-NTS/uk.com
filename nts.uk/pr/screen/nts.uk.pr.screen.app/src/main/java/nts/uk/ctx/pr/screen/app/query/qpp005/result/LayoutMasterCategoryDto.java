package nts.uk.ctx.pr.screen.app.query.qpp005.result;

import java.util.List;

import lombok.Value;

@Value
public class LayoutMasterCategoryDto {

	/** カテゴリ区分 */
	private int categoryAttribute;

	/** カテゴリ表示位置 */
	private int categoryPosition;

	private List<DetailItemDto> details;

	public static LayoutMasterCategoryDto fromDomain(int categoryAttribute, int categoryPosition,
			List<DetailItemDto> details) {
		return new LayoutMasterCategoryDto(categoryAttribute, categoryPosition, details);
	}
}
