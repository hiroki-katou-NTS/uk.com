package nts.uk.ctx.at.schedule.app.find.shift.shiftcondition.shiftcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftConditionCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftConditionCategoryDto {
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
	private String categoryName;

	public static ShiftConditionCategoryDto fromDomain(ShiftConditionCategory domain) {
		return new ShiftConditionCategoryDto(domain.getCompanyId(), domain.getCategoryNo(),
				domain.getCategoryName().v());
	}
}
