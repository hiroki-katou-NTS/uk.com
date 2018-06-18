package nts.uk.ctx.pereg.dom.roles.functionauth;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthority;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsettingdesc.PersonInfoAuthDescription;

@Getter
public class AuthFullInfoObject {

	private int functionNo;

	private String functionName;

	private boolean available;

	private String description;

	private int orderNumber;

	public AuthFullInfoObject(int functionNo, String functionName, boolean available, String description,
			int orderNumber) {
		super();
		this.functionNo = functionNo;
		this.functionName = functionName;
		this.available = available;
		this.description = description;
		this.orderNumber = orderNumber;
	}

	public AuthFullInfoObject(PersonInfoAuthDescription desc, PersonInfoAuthority auth) {
		this.functionNo = desc.getFunctionNo().v();
		this.functionName = desc.getFunctionName().v();
		this.available = auth.isAvailable();
		this.description = desc.getDescription().v();
		this.orderNumber = desc.getOrderNumber();
	}

	public AuthFullInfoObject(PersonInfoAuthDescription desc) {
		this.functionNo = desc.getFunctionNo().v();
		this.functionName = desc.getFunctionName().v();
		this.available = desc.isDefaultAvailable();
		this.description = desc.getDescription().v();
		this.orderNumber = desc.getOrderNumber();
	}

}
