package nts.uk.ctx.sys.portal.dom.mypage;

import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class MyPage.
 */
public class MyPage extends AggregateRoot {

	/** The company id. */
	private String companyId;
	
	/** The employee id. */
	private String employeeId;
	
	/** The layout id. */
	private String layoutId;

	/**
	 * Instantiates a new my page.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param layoutId the layout id
	 */
	public MyPage(String companyId, String employeeId, String layoutId) {		
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.layoutId = layoutId;
	}	
	
	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param layoutId the layout id
	 */
	public static MyPage createFromJavaType(String companyId, String employeeId, String LayoutId) {
		return new MyPage(companyId, employeeId, LayoutId);
	}
}
