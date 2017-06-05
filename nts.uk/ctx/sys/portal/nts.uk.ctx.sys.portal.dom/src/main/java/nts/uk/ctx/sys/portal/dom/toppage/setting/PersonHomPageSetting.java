package nts.uk.ctx.sys.portal.dom.toppage.setting;

import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class PersonHomPageSetting.
 */
public class PersonHomPageSetting extends AggregateRoot {
	/** The company id. */
	private String cId;

	/** The employee id. */
	private String sId;
	
	/** The top page code. */
	private String code;

	/**
	 * Instantiates a new person home page setting.
	 *
	 * @param cId the company id
	 * @param sId the employee id
	 * @param code the top page code
	 */
	public PersonHomPageSetting(String cId, String sId, String code) {
		this.cId = cId;
		this.sId = sId;
		this.code = code;
	}
	
	/**
	 * Creates the from java type.
	 *
	 * @param cId the company id
	 * @param sId the employee id
	 * @param code the top page code
	 */
	public static PersonHomPageSetting createFromJavaType(String cId, String sId, String code) {
		return new PersonHomPageSetting(cId, sId, code);
	}
}
