package nts.uk.ctx.sys.portal.dom.toppagesetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
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
	private System loginSystem;

	/** MenuClassification. */
	private MenuClassification menuClassification;

	public TopPagePersonSet(String companyId, String employeeId, TopMenuCode topMenuCode, TopMenuCode loginMenuCode,
			System loginSystem, MenuClassification menuClassification) {
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.topMenuCode = topMenuCode;
		this.loginMenuCode = loginMenuCode;
		this.loginSystem = loginSystem;
		this.menuClassification = menuClassification;
	}

	public static TopPagePersonSet createFromJavaType(String companyId, String employeeId, String topMenuCode,
			String loginMenuCode, int loginSystem, int menuClassification) {
		return new TopPagePersonSet(companyId, employeeId, new TopMenuCode(topMenuCode), new TopMenuCode(loginMenuCode),
				EnumAdaptor.valueOf(loginSystem, System.class),
				EnumAdaptor.valueOf(menuClassification, MenuClassification.class));
	}

	@Override
	public void validate() {
		super.validate();
		if (nts.gul.text.StringUtil.isNullOrEmpty(this.topMenuCode.v(), true)
				|| nts.gul.text.StringUtil.isNullOrEmpty(this.loginMenuCode.v(), true)) {
			throw new BusinessException("Msg_86");
		}
	}
}
