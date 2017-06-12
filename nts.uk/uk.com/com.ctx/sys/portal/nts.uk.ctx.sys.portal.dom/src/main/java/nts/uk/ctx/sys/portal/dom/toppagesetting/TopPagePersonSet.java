package nts.uk.ctx.sys.portal.dom.toppagesetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.portal.dom.enums.System;

/**
 * 
 * @author sonnh1
 *
 */
@Getter
public class TopPagePersonSet extends AggregateRoot {

	/** The company id. */
	private String companyId;

	/** The employee id. */
	private String employeeId;

	/** The top menu code. */
	private TopMenuCode topMenuCode;

	/** The login menu code. */
	private TopMenuCode loginMenuCode;
	
	/** System */
	private System system;

	public TopPagePersonSet(String companyId, String employeeId, TopMenuCode topMenuCode, TopMenuCode loginMenuCode,
			System system) {
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.topMenuCode = topMenuCode;
		this.loginMenuCode = loginMenuCode;
		this.system = system;
	}

	public static TopPagePersonSet createFromJavaType(String companyId, String employeeId, String topMenuCode,
			String loginMenuCode, int system) {
		return new TopPagePersonSet(companyId, employeeId, new TopMenuCode(topMenuCode),
				new TopMenuCode(loginMenuCode), EnumAdaptor.valueOf(system, System.class));
	}
}
