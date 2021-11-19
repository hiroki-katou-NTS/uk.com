package nts.uk.ctx.at.shared.ac.employee;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;

/**
 * The Class DepartmentModel.
 */
//所属部門
@Data
@Builder
public class DepartmentModel {
	
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 削除フラグ
	 */
	private boolean deleteFlag;

	/**
	 * 部門履歴ID
	 */
	private String departmentHistoryId;

	/**
	 * 部門ID
	 */
	private String departmentId;

	/**
	 * 部門コード
	 */
	private String departmentCode;

	/**
	 * 部門名称
	 */
	private String departmentName;

	/**
	 * 部門総称
	 */
	private String departmentGeneric;

	/**
	 * 部門表示名
	 */
	private String departmentDisplayName;

	/**
	 * 階層コード
	 */
	private String hierarchyCode;

	/**
	 * 部門外部コード
	 */
	private String departmentExternalCode;
	

	
}