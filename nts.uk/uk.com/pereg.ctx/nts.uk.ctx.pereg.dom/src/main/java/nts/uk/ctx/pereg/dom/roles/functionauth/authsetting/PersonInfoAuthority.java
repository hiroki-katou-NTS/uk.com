package nts.uk.ctx.pereg.dom.roles.functionauth.authsetting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class PersonInfoAuthority extends AggregateRoot{
	
	private String companyId;
	
	private String roleId;
	
	private int functionNo;
	
	private boolean available;

	private PersonInfoAuthority(String companyId, String roleId, int functionNo, boolean available) {
		super();
		this.companyId = companyId;
		this.roleId = roleId;
		this.functionNo = functionNo;
		this.available = available;
	}
	
	public static PersonInfoAuthority createFromJavaType(String companyId, String roleId, int functionNo, boolean available) {
		return new PersonInfoAuthority(companyId, roleId, functionNo, available); 
	}

}
