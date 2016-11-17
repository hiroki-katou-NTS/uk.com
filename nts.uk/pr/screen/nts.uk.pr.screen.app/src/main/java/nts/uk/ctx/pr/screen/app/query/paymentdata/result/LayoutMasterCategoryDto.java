package nts.uk.ctx.pr.screen.app.query.paymentdata.result;

import java.util.List;

import lombok.Data;

@Data
public class LayoutMasterCategoryDto {

	/** カテゴリ区分 */
	private int categoryAttribute;

	/** カテゴリ表示位置 */
	private int categoryPosition;

	private List<DetailItemDto> details;

}
