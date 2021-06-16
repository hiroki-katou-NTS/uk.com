package nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee.carryForwarddata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class KshdtHdpubRemPK implements Serializable{

	private static final long serialVersionUID = 1L;

    /** 会社ID  */
	@Column(name = "CID")
	public String cId;
	
	@Id
	@Column(name = "ID")
	public String ID;
	
	/** 社員ID */
	@Column(name = "SID")
	public String employeeId;

	/** 対象月 */
	@Column(name = "TAGETMONTH")
	public int tagetmonth;
}
