package nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShiftConditionCategory {
	/**
	 * 会社ID
	 */
	private String companyId;
	/**
	 * カテゴリNO
	 */
	private int categoryNo;
	/**
	 * カテゴリ名称
	 */
	private ShiftConditionCategoryName categoryName;

	public static ShiftConditionCategory createFromJavaType(String companyId, int categoryNo, String categoryName) {
		return new ShiftConditionCategory(companyId, categoryNo, new ShiftConditionCategoryName(categoryName));
	}
}
