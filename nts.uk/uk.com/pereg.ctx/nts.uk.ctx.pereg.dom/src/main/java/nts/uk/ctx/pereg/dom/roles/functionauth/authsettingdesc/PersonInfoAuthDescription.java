package nts.uk.ctx.pereg.dom.roles.functionauth.authsettingdesc;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class PersonInfoAuthDescription extends AggregateRoot{
	
	private int functionNo;
	
	private String functionName;
	
	private String description;
	
	private int orderNumber;
	
	private boolean defaultAvailable;

	private PersonInfoAuthDescription(int functionNo, String functionName, String description, int orderNumber,
			boolean defaultAvailable) {
		super();
		this.functionNo = functionNo;
		this.functionName = functionName;
		this.description = description;
		this.orderNumber = orderNumber;
		this.defaultAvailable = defaultAvailable;
	}
	
	public static PersonInfoAuthDescription createFromJavaType(int functionNo, String functionName, String description, int orderNumber,
			boolean defaultAvailable) {
		return new PersonInfoAuthDescription(functionNo, functionName, description, orderNumber, defaultAvailable);
	}

}
