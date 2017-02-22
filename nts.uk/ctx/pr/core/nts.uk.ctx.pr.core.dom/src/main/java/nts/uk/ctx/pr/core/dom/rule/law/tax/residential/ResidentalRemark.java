/**
 * 
 */
package nts.uk.ctx.pr.core.dom.rule.law.tax.residential;

import nts.arc.layer.dom.DomainObject;

/**
 * @author lanlt
 *
 */
public class ResidentalRemark extends DomainObject{
	private Remark remark1;
	private Remark remark2;
	/**
	 * @param remark1
	 * @param remark2
	 */
	public ResidentalRemark(Remark remark1, Remark remark2) {
		super();
		this.remark1 = remark1;
		this.remark2 = remark2;
	}


}
