package nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee.carryForwarddata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;

public class KshdtHdpubRemHistPK implements Serializable{

	private static final long serialVersionUID = 1L;
	
    /** 会社ID  */
    @Column(name = "CID")
    private String cid;
   
    /** 社員ID */
    @Column(name = "SID")
    private String sid;
    
	/** 年月 */
	@Column(name = "YM")
	@Basic(optional = false)
	public Integer yearMonth;
	
	/** 対象月 */
	@Column(name = "TAGETMONTH")
	@Basic(optional = false)
	public Integer tagetmonth;
}
