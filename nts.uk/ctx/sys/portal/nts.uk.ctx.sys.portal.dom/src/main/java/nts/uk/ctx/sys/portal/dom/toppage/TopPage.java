/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.dom.toppage;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class TopPage.
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class TopPage extends AggregateRoot {
	
	/** The company id. */
	private String companyId;
	
	/** The top page code. */
	private TopPageCode topPageCode;
	
	/** The layout id. */
	private String layoutId;
	
	/** The top page name. */
	private TopPageName topPageName;
	
	/** The language number. */
	private Integer languageNumber;

	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param topPageCode the top page code
	 * @param layoutId the layout id
	 * @param topPageName the top page name
	 * @param languageNumber the language number
	 * @return the top page
	 */
	public static TopPage createFromJavaType(String companyId, String topPageCode, String layoutId, String topPageName,
			Integer languageNumber) {
		return new TopPage(companyId, new TopPageCode(topPageCode), layoutId,
				new TopPageName(topPageName), languageNumber);
	}
}
