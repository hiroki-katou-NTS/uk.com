package nts.uk.ctx.at.auth.dom.employmentrole;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * 就業ロール
 */
@AllArgsConstructor
@Getter
public class EmploymentRole {

	private String companyId;

	private String roleId;

	public static EmploymentRole createFromJavaType(String companyID, String roleId) {
		return new EmploymentRole(companyID, roleId);
	}

}
