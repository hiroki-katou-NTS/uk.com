package nts.uk.ctx.pr.proto.infra.entity.paymentdata;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class QstmtStmtAllotPsPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "CCD")
	public String companyCode;

	@Column(name = "STR_YM")
	public int startDate;
	
	@Column(name = "PID")
	public String personId;
}
