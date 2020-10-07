package nts.uk.ctx.sys.assist.app.find.autosetting.deletion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.categoryfordelete.CategoryForDelete;
import nts.uk.ctx.sys.assist.dom.categoryfordelete.SystemUsability;
import nts.uk.ctx.sys.assist.dom.deletedata.DataDeletionSelectionCategory;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCategoryDto implements DataDeletionSelectionCategory.MementoGetter {
	
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
	
	/**
	 * パータンコード
	 */
	private String patternCode;
	
	/**
	 * パターン区分
	 */
	private int patternClassification = 0;
	
	/**
	 * 契約コード
	 */
	private String contractCode;
	
	/**
	 * 保存期間区分
	 */
	private String retentionPeriod;
	
	public static DeleteCategoryDto fromDomain(CategoryForDelete domain) {
		DeleteCategoryDto dto = new DeleteCategoryDto();
		dto.categoryId = domain.getCategoryId().v();
		dto.categoryName = domain.getCategoryName().v();
		if (domain.getAttendanceSystem().equals(SystemUsability.AVAILABLE)) {
			dto.setSystemType(SystemType.ATTENDANCE_SYSTEM.value);
		} else if (domain.getPaymentAvailability().equals(SystemUsability.AVAILABLE)) {
			dto.setSystemType(SystemType.PAYROLL_SYSTEM.value);
		} else if (domain.getPossibilitySystem().equals(SystemUsability.AVAILABLE)) {
			dto.setSystemType(SystemType.PERSON_SYSTEM.value);
		} else if (domain.getSchelperSystem().equals(SystemUsability.AVAILABLE)) {
			dto.setSystemType(SystemType.OFFICE_HELPER.value); 
		}
		dto.retentionPeriod = domain.getTimeStore().nameId;
		return dto;
	}
}
