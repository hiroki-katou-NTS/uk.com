package nts.uk.ctx.pereg.dom.roles.functionauth.authsettingdesc;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.roles.functionauth.primitive.AuthFunctionDescription;
import nts.uk.ctx.pereg.dom.roles.functionauth.primitive.AuthFunctionName;
import nts.uk.ctx.pereg.dom.roles.functionauth.primitive.AuthFunctionNumber;

@Getter
public class PersonInfoAuthDescription extends AggregateRoot {

	private AuthFunctionNumber functionNo;

	private AuthFunctionName functionName;

	private AuthFunctionDescription description;

	private int orderNumber;

	private boolean defaultAvailable;

	private PersonInfoAuthDescription(AuthFunctionNumber functionNo, AuthFunctionName functionName,
			AuthFunctionDescription description, int orderNumber, boolean defaultAvailable) {
		super();
		this.functionNo = functionNo;
		this.functionName = functionName;
		this.description = description;
		this.orderNumber = orderNumber;
		this.defaultAvailable = defaultAvailable;
	}

	public static PersonInfoAuthDescription createFromJavaType(int functionNo, String functionName, String description,
			int orderNumber, boolean defaultAvailable) {
		return new PersonInfoAuthDescription(new AuthFunctionNumber(functionNo), new AuthFunctionName(functionName),
				new AuthFunctionDescription(description), orderNumber, defaultAvailable);
	}

}
