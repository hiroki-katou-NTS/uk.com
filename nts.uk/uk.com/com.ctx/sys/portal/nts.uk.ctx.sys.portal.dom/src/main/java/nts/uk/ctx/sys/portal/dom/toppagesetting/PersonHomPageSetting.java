package nts.uk.ctx.sys.portal.dom.toppagesetting;

import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class PersonHomPageSetting.
 */
public class PersonHomPageSetting extends AggregateRoot {
	/** The company id. */
	private String companyId;
	
	/** The employee id. */
	private String employeeId;
	
	/** The top page code. */
	private String code;

	/**
	 * Instantiates a new person home page setting.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param code the top page code
	 */
	public PersonHomPageSetting(String companyId, String employeeId, String code) {
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.code = code;
	}
	
	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param code the top page code
	 */
	public static PersonHomPageSetting createFromJavaType(String companyId, String employeeId, String code) {
		return new PersonHomPageSetting(companyId, employeeId, code);
	}
}
