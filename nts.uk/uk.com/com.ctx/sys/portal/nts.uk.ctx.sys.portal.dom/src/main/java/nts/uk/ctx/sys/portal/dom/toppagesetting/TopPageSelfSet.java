package nts.uk.ctx.sys.portal.dom.toppagesetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class PersonHomPageSetting.
 */
@Getter
public class TopPageSelfSet extends AggregateRoot {
	/** The employee id. */
	private String employeeId;
	
	/** The top page code. */
	private String code;

	/** The top page code. */
	private TopPageSelfDivision division;
	
	/**
	 * Instantiates a new person home page setting.
	 *
	 * @param employeeId the employee id
	 * @param code the top page code
	 * @param division the top page division
	 */
	public TopPageSelfSet(String employeeId, String code, TopPageSelfDivision division) {
		this.employeeId = employeeId;
		this.code = code;
		this.division = division;
	}
	
	/**
	 * Creates the from java type.
	 *
	 * @param employeeId the employee id
	 * @param code the top page code
	 * @param division the top page division
	 */
	public static TopPageSelfSet createFromJavaType(String employeeId, String code, int division) {
		return new TopPageSelfSet(employeeId, code, EnumAdaptor.valueOf(division, TopPageSelfDivision.class));
	}
}
