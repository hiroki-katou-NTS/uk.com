package nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee.carryForwarddata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;

public class KshdtHdpubRemPK implements Serializable{

	private static final long serialVersionUID = 1L;

	/** 社員ID */
	@Column(name = "SID")
	public String employeeId;

	/** 締めID */
	@Column(name = "CLOSURE_ID")
	@Basic(optional = false)
	public int closureId;

	/** 締め日.日 */
	@Column(name = "CLOSURE_DAY")
	@Basic(optional = false)
	public int closeDay;

	/** 締め日.末日とする */
	@Column(name = "IS_LAST_DAY")
	@Basic(optional = false)
	public int isLastDay;
	
	/** 対象月 */
	@Column(name = "TAGETMONTH")
	public int tagetmonth;
}
