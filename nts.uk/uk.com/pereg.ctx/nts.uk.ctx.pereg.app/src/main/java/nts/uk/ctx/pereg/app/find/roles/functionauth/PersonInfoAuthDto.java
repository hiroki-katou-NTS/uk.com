package nts.uk.ctx.pereg.app.find.roles.functionauth;

import lombok.Data;
import nts.uk.ctx.pereg.dom.roles.functionauth.AuthFullInfoObject;

@Data
public class PersonInfoAuthDto {
	
	private int functionNo;

	private String functionName;

	private boolean available;

	private String description;

	private int orderNumber;
	
	public PersonInfoAuthDto(AuthFullInfoObject object) {
		this.functionNo = object.getFunctionNo();
		this.functionName = object.getFunctionName();
		this.available = object.isAvailable();
		this.description = object.getDescription();
		this.orderNumber = object.getOrderNumber();
	} 
}
