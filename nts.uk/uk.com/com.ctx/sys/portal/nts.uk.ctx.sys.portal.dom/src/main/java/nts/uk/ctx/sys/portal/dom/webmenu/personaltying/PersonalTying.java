package nts.uk.ctx.sys.portal.dom.webmenu.personaltying;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuCode;

@Getter
public class PersonalTying extends AggregateRoot {
	
	@Getter
	private String companyId;
    
	@Getter
	private WebMenuCode webMenuCode;
	
	@Getter
	private String employeeId;
	
	public static PersonalTying createFromJavaType(String companyId, String webMenuCode, String employeeId){
		return new PersonalTying(companyId, new WebMenuCode(webMenuCode), employeeId);
	}
	public PersonalTying (String companyId, WebMenuCode webMenuCode, String employeeId){
		super();
		this.companyId = companyId;
		this.webMenuCode = webMenuCode;
		this.employeeId = employeeId;
		
	}
}
