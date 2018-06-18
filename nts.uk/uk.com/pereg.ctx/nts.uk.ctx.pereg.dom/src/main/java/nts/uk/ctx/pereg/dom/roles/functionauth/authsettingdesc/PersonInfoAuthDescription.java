package nts.uk.ctx.pereg.dom.roles.functionauth.authsettingdesc;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.roles.functionauth.primitive.AuthFunctionDescription;
import nts.uk.ctx.pereg.dom.roles.functionauth.primitive.AuthFunctionName;
import nts.uk.ctx.pereg.dom.roles.functionauth.primitive.AuthFunctionNumber;

/**
 * @author danpv
 * domain name: 個人情報の機能
 */
@Getter
public class PersonInfoAuthDescription extends AggregateRoot {

	/**
	 * No
	 */
	private AuthFunctionNumber functionNo;

	/**
	 * 表示名
	 */
	private AuthFunctionName functionName;

	/**
	 * 説明文
	 */
	private AuthFunctionDescription description;

	/**
	 * 表示順
	 */
	private int orderNumber;

	/**
	 * 初期値
	 */
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
