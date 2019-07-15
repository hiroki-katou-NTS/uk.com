package nts.uk.ctx.pereg.app.command.process.checkdata;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategoryInfo {

	/**
	 * 個人情報カテゴリID
	 */
	private String personInfoCategoryId;

	/**
	 * カテゴリコード
	 */
	private String categoryCode;

	/**
	 * カテゴリ名
	 */
	private String categoryName;
}
