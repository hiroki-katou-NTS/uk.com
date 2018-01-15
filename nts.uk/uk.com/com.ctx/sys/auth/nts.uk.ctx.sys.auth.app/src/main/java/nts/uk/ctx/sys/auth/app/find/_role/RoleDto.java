package nts.uk.ctx.sys.auth.app.find._role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

	/** The role id. */
	// Id
	private String roleId;
	
	/** The role code. */
	// コード
	private String roleCode;

	/** The name. */
	// ロール名称
	private String name;

}
