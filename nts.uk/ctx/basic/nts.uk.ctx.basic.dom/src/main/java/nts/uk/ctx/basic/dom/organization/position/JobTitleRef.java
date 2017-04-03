package nts.uk.ctx.basic.dom.organization.position;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
@Setter
@Getter
public class JobTitleRef extends AggregateRoot{
	
	private AuthorizationCode authCode;
			
	private String historyId;
	
	private String companyCode;
	
	private JobCode jobCode;
	
	private ReferenceSettings referenceSettings;
	
	public JobTitleRef(String companyCode, String historyId,JobCode jobCode,AuthorizationCode authCode,ReferenceSettings referenceSettings ) {
		super();
		this.referenceSettings = referenceSettings;
		this.historyId = historyId;
		this.companyCode = companyCode;
		this.jobCode = jobCode;
		this.authCode = authCode;
		;
	}

	public static JobTitleRef createFromJavaType(  
			 String companyCode, String historyId,String jobCode,String authCode,int referenceSettings)
	{
		return new JobTitleRef( 
				companyCode, historyId,new JobCode(jobCode),new AuthorizationCode(authCode),EnumAdaptor.valueOf(referenceSettings, ReferenceSettings.class) );
	}

}