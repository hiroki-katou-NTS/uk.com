package nts.uk.ctx.sys.assist.app.find.autosetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.SystemUsability;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageSelectionCategory;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;

/**
 * カテゴリDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto implements DataStorageSelectionCategory.MementoGetter {
	
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
	
	public static CategoryDto fromDomain(Category domain) {
		CategoryDto dto = new CategoryDto();
		dto.setCategoryId(domain.getCategoryId().v());
		dto.setCategoryName(domain.getCategoryName().v());
		if (domain.getAttendanceSystem().equals(SystemUsability.AVAILABLE))
			dto.setSystemType(SystemType.ATTENDANCE_SYSTEM.value);
		else if (domain.getPaymentAvailability().equals(SystemUsability.AVAILABLE))
			dto.setSystemType(SystemType.PAYROLL_SYSTEM.value);
		else if (domain.getPossibilitySystem().equals(SystemUsability.AVAILABLE))
			dto.setSystemType(SystemType.PERSON_SYSTEM.value);
		else if (domain.getSchelperSystem().equals(SystemUsability.AVAILABLE))
			dto.setSystemType(SystemType.OFFICE_HELPER.value);
		return dto;
	}
}
