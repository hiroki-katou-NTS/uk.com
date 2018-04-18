package nts.uk.ctx.exio.app.find.exi.category;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 外部受入カテゴリ
 */
@AllArgsConstructor
@Value
public class ExAcpCategoryDto {

	/**
	 * カテゴリID
	 */
	private String categoryId;

	/**
	 * カテゴリ名
	 */
	private String categoryName;

}
