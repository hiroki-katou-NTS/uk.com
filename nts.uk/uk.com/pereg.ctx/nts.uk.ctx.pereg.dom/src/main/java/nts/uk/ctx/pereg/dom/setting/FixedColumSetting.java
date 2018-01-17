/**
 * 
 */
package nts.uk.ctx.pereg.dom.setting;

import lombok.Getter;

/**
 * @author danpv
 * @Domain 固定列設定
 *
 */
@Getter
public class FixedColumSetting {
	
	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 雇用
	 */
	private Boolean employment;
	
	/**
	 * default: true
	 * 社員コード
	 */
	private Boolean employeeCode;
	
	/**
	 * default: true
	 * 社員名
	 */
	private Boolean employeeName;

	/**
	 * 職位
	 */
	private Boolean jobTitle;
	
	/**
	 * 職場
	 */
	private Boolean workplace;
	
	/**
	 * 部門
	 */
	private Boolean department;
	
	/**
	 * 分類
	 */
	private Boolean classification;
}
