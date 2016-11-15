package nts.uk.ctx.pr.proto.infra.entity.paymentdata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Embeddable
public class QstmtStmtAllotCpPK implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Column(name = "CCD")
	public String companyCode;
	
	@Column(name = "STR_YM")
	public int startDate;
}
