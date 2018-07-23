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

	public AuthFullInfoObject(PersonInfoAuthDescription desc) {
		this.functionNo = desc.getFunctionNo();
		this.functionName = desc.getName();
		this.available = desc.getDefaultValue();
		this.description = desc.getExplanation();
		this.orderNumber = desc.getDisplayOrder();
	}

	public AuthFullInfoObject(PersonInfoAuthDescription desc, PersonInfoAuthority auth) {
		this.functionNo = desc.getFunctionNo();
		this.functionName = desc.getName();
		this.available = auth.isAvailable();
		this.description = desc.getExplanation();
		this.orderNumber = desc.getDisplayOrder();
	}

}
