package nts.uk.ctx.basic.app.find.organization.position;

import lombok.Data;

@Data
public class AuthorizationLevelDto {

	private String AuthScopeAtr;
	private String AuthorizationCode;
	private String AuthorizationName;
	private int EmpScopeAtr;
	private int InChargeAtr;
}
