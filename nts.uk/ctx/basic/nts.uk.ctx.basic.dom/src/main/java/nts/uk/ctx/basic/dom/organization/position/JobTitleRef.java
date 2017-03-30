package nts.uk.ctx.basic.dom.organization.position;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
@Setter
@Getter
public class JobTitleRef extends AggregateRoot{
	
	private AuthorizationCode authorizationCode;
			
	private String historyId;
	
	private String companyCode;
	
	private JobCode jobCode;
	
	private ReferenceSettings referenceSettings;
	
	public JobTitleRef(ReferenceSettings referenceSettings, 
			String companyCode, String historyId,JobCode jobCode,AuthorizationCode authorizationCode ) {
		super();
		this.authorizationCode = authorizationCode;
		this.historyId = historyId;
		this.companyCode = companyCode;
		this.jobCode = jobCode;
		this.referenceSettings = referenceSettings;
	}

	public static JobTitleRef createFromJavaType(String authorizationCode, 
			 String companyCode, String historyId,String jobCode, int referenceSettings)
	{
		return new JobTitleRef(ReferenceSettings.valueOf(String.valueOf(referenceSettings)),
				companyCode, historyId,new JobCode(jobCode),new AuthorizationCode(authorizationCode) );
	}

}