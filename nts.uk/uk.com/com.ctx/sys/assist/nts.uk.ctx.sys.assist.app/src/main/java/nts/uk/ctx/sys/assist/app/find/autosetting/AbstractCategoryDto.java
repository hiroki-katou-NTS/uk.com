package nts.uk.ctx.sys.assist.app.find.autosetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractCategoryDto {
	
	/**
	 * カテゴリID
	 */
	protected String categoryId;
	
	/**
	 * カテゴリ名称
	 */
	protected String categoryName;
	
	/**
	 * システム種類
	 */
	protected int systemType;
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AbstractCategoryDto) {
			return ((AbstractCategoryDto) obj).getCategoryId().equals(this.categoryId)
					&& ((AbstractCategoryDto) obj).getSystemType() == this.systemType;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
