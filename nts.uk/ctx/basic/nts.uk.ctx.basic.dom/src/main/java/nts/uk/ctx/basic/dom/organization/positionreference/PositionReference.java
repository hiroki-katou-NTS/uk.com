package nts.uk.ctx.basic.dom.organization.positionreference;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.IdentifierUtil;


@Getter
public class PositionReference extends AggregateRoot {

	
 private String companyCode;
 private String historyID;
 private String jobCode;
 private String authorizationCode;
 private int referenceSetting;
 
 
 
	public PositionReference( String companyCode,String historyID,String jobCode,String  authorizationCode,int referenceSetting) {

		super();
		
		this.companyCode = companyCode;
		this.historyID = historyID;
		this.jobCode = jobCode;
		this.authorizationCode = authorizationCode;
		this.referenceSetting = referenceSetting;
	}

	public PositionReference( String companyCode,String jobCode,String authorizationCode,int referenceSetting) {

		super();
		
		this.companyCode = companyCode;
		this.jobCode = jobCode;
		this.historyID = IdentifierUtil.randomUniqueId();
		this.authorizationCode = authorizationCode;
		this.referenceSetting = referenceSetting;
	}
	public static PositionReference createFromJavaType( String companyCode,String historyID,String jobCode,String  authorizationCode,int referenceSetting) {
		return new PositionReference(historyID,jobCode,authorizationCode,referenceSetting);

	}

}
