package nts.uk.ctx.sys.portal.dom.mypage;

import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class MyPage.
 */
public class MyPage extends AggregateRoot {

	/** The company id. */
	private String cId;
	
	/** The employee id. */
	private String sId;
	
	/** The layout id. */
	private String layoutId;

	/**
	 * Instantiates a new my page.
	 *
	 * @param cId the company id
	 * @param sId the employee id
	 * @param layoutId the layout id
	 */
	public MyPage(String cId, String sId, String layoutId) {		
		this.cId = cId;
		this.sId = sId;
		this.layoutId = layoutId;
	}	
	
	/**
	 * Creates the from java type.
	 *
	 * @param cId the company id
	 * @param sId the employee id
	 * @param layoutId the layout id
	 */
	public static MyPage createFromJavaType(String cId, String sId, String LayoutId) {
		return new MyPage(cId, sId, LayoutId);
	}
}
