package nts.uk.screen.at.app.query.kdp.kdp003.f;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleEmployeeStampingDto {

	// Id
	private String roleId;

	/** The role code. */
	// コード
	private String roleCode;

	/** The role type. */
	// ロール種類
	private int roleType;

	/** The employee reference range. */
	// 参照範囲
	private int employeeReferenceRange;

	/** The name. */
	// ロール名称
	private String name;

	/** The contract code. */
	// 契約コード
	private String contractCode;

	/** The assign atr. */
	// 担当区分
	private int assignAtr;

	/** The company id. */
	// 会社ID
	private String companyId;
	
}
