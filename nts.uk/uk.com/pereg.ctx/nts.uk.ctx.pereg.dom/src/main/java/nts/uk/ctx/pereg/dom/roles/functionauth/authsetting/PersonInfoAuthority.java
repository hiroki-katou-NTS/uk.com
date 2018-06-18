package nts.uk.ctx.pereg.dom.roles.functionauth.authsetting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.roles.functionauth.primitive.AuthFunctionNumber;

/**
 * @author danpv
 * domain name: 個人情報の権限
 */
@Getter
public class PersonInfoAuthority extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * ロールID
	 */
	private String roleId;

	/**
	 * 機能NO
	 */
	private AuthFunctionNumber functionNo;

	/**
	 * 利用できる
	 */
	private boolean available;

	private PersonInfoAuthority(String companyId, String roleId, AuthFunctionNumber functionNo, boolean available) {
		super();
		this.companyId = companyId;
		this.roleId = roleId;
		this.functionNo = functionNo;
		this.available = available;
	}

	public static PersonInfoAuthority createFromJavaType(String companyId, String roleId, int functionNo,
			boolean available) {
		return new PersonInfoAuthority(companyId, roleId, new AuthFunctionNumber(functionNo), available);
	}

}
