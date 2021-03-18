package nts.uk.screen.com.app.find.ccg005;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {

	/** The role id. */
	// Id
	private String roleId;

	/** The role code. */
	// コード
	private String roleCode;

	/** The role type. */
	// ロール種類
	private Integer roleType;

	/** The employee reference range. */
	// 参照範囲
	private Integer employeeReferenceRange;

	/** The name. */
	// ロール名称
	private String name;

	/** The contract code. */
	// 契約コード
	private String contractCode;

	/** The assign atr. */
	// 担当区分
	private Integer assignAtr;

	/** The company id. */
	// 会社ID
	private String companyId;
}
