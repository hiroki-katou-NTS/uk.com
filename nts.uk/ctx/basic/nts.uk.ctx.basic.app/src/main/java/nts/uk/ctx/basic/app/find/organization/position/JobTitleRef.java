package nts.uk.ctx.basic.app.find.organization.position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JobTitleRef {

	private String companyCode;
	
	private String historyId;
	
	private String jobCode;
	
	private String authCode;
	
	private int referenceSettings;
	
	public static JobTitleRef fromDomain(String companyCode, String historyId, String jobCode,
			String authCode, int referenceSettings) {
		return new JobTitleRef(companyCode, historyId, jobCode, authCode, referenceSettings);
	}
}
