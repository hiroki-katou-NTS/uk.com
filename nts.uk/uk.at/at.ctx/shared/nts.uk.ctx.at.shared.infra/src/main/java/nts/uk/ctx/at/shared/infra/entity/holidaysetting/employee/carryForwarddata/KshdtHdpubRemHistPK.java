package nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee.carryForwarddata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;

public class KshdtHdpubRemHistPK implements Serializable{

	private static final long serialVersionUID = 1L;

	/** 社員ID */
    @Column(name = "SID")
    public String sid;
		
	/** 対象月 */
	@Column(name = "TAGETMONTH")
	@Basic(optional = false)
	public int tagetmonth;
}
