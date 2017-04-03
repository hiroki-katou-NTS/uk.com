package nts.uk.ctx.basic.app.find.organization.position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.basic.dom.organization.position.JobRef_Auth;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobRefAuthDto {

	
	private String authCode;
	
	private String authName;
	
	private int referenceSettings;
	
	public static JobRefAuthDto fromDomain(String authCode, String authName, int referenceSettings) {
		return new JobRefAuthDto(authCode, authName, referenceSettings);
	}
	public static JobRefAuthDto fromDomain(JobRef_Auth domain) {
		return new JobRefAuthDto(
				domain.getAuthCode().v(),
				domain.getAuthName().v(),
				domain.getReferenceSettings().value);
	}
}
