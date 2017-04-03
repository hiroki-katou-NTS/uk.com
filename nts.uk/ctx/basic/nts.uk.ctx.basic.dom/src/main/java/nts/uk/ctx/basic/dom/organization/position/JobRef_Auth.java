package nts.uk.ctx.basic.dom.organization.position;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
@Getter
@Setter
public class JobRef_Auth {

	private AuthorizationCode authCode;
	
	private AuthorizationName authName;
	
	private ReferenceSettings referenceSettings;
	
	public JobRef_Auth(AuthorizationCode authCode,
			AuthorizationName authName,ReferenceSettings referenceSettings) {
		super();
		this.authCode = authCode;
		this.authName = authName;
		this.referenceSettings = referenceSettings;
	}


	public static JobRef_Auth createSimpleFromJavaType(
			String authCode, 
			String authName, 
			int referenceSettings){
		return new JobRef_Auth(	new AuthorizationCode(authCode),
								new AuthorizationName(authName),
								EnumAdaptor.valueOf(referenceSettings, ReferenceSettings.class)
								);
	}
}
