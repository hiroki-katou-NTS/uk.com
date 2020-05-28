package nts.uk.ctx.at.auth.dom.adapter.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoleImport {
	
	/** The company id. */
	// 会社ID
	private String companyId;
	
	/** The role id. */
	// Id
	private String roleId;

	/** The role code. */
	// コード
	private String roleCode;
	
	/** The name. */
	// ロール名称
	private String name;
	
	/** The assign atr. */
	// 担当区分
	private Integer assignAtr;

	/** The employee reference range. */
	// 参照範囲
	private Integer employeeReferenceRange;
	
}
