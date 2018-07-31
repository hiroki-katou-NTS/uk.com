package nts.uk.ctx.exio.dom.exo.category;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.permit.DescriptionOfAvailabilityPermissionBase;

/**
 * 外部出力カテゴリ
 */
@Getter
public class ExOutCtg extends DescriptionOfAvailabilityPermissionBase {

	/**
	 * カテゴリID
	 */
	private CategoryCd categoryId;

	/**
	 * オフィスヘルパシステム区分
	 */
	private SystemUsability officeHelperSysAtr;

	/**
	 * カテゴリ名
	 */
	private CategoryName categoryName;

	/**
	 * カテゴリ設定
	 */
	private CategorySetting categorySet;

	/**
	 * 人事システム区分
	 */
	private SystemUsability personSysAtr;

	/**
	 * 勤怠システム区分
	 */
	private SystemUsability attendanceSysAtr;

	/**
	 * 給与システム区分
	 */
	private SystemUsability payrollSysAtr;

	public ExOutCtg(int categoryId, int officeHelperSysAtr, String categoryName, int categorySet,
			int personSysAtr, int attendanceSysAtr, int payrollSysAtr, int functionNo, String functionName,
			String explanation, int displayOrder, boolean defaultValue) {
		super(functionNo, functionName, explanation, displayOrder, defaultValue);
		this.categoryId = new CategoryCd(categoryId);
		this.officeHelperSysAtr = EnumAdaptor.valueOf(officeHelperSysAtr, SystemUsability.class);
		this.categoryName = new CategoryName(categoryName);
		this.categorySet = EnumAdaptor.valueOf(categorySet, CategorySetting.class);
		this.personSysAtr = EnumAdaptor.valueOf(personSysAtr, SystemUsability.class);
		this.attendanceSysAtr = EnumAdaptor.valueOf(attendanceSysAtr, SystemUsability.class);
		this.payrollSysAtr = EnumAdaptor.valueOf(payrollSysAtr, SystemUsability.class);
	}
}
