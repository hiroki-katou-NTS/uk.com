package nts.uk.ctx.sys.assist.app.find.autosetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.category.Category;

/**
 * カテゴリDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	
	/**
	 * カテゴリID
	 */
	private String categoryId;
	
	/**
	 * カテゴリ名称
	 */
	private String categoryName;
	
	/**
	 * システム種類
	 */
	private int systemType;
	
	public static CategoryDto fromDomain(Category domain) {
		return new CategoryDto(domain.getCategoryId().v(), domain.getCategoryName().v(), domain.get)
	}
}
