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
	
	public JobTitleRef(ReferenceSettings referenceSettings, 
			String companyCode, String historyId,JobCode jobCode,AuthorizationCode authCode ) {
		super();
		this.referenceSettings = referenceSettings;
		this.historyId = historyId;
		this.companyCode = companyCode;
		this.jobCode = jobCode;
		this.authCode = authCode;
		;
	}

	public static JobTitleRef createFromJavaType( int referenceSettings, 
			 String companyCode, String historyId,String jobCode,String authCode)
	{
		return new JobTitleRef(EnumAdaptor.valueOf(referenceSettings, ReferenceSettings.class), 
				companyCode, historyId,new JobCode(jobCode),new AuthorizationCode(authCode) );
	}

}