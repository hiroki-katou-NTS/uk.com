package nts.uk.ctx.pr.screen.app.query.qpp005.result;

import java.util.List;

import lombok.Value;

@Value
public class LayoutMasterCategoryDto {

	/** カテゴリ区分 */
	int categoryAttribute;

	/** カテゴリ表示位置 */
	int categoryPosition;

	int lineCounts;

	List<DetailItemDto> details;

	public static LayoutMasterCategoryDto fromDomain(int categoryAttribute, int categoryPosition, int lineCounts,
			List<DetailItemDto> details) {
		return new LayoutMasterCategoryDto(categoryAttribute, categoryPosition, lineCounts, details);
	}
}
