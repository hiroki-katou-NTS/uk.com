package nts.uk.ctx.pr.proto.infra.entity.paymentdata;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Embeddable
public class QtxmtCommuNotaxLimitPK implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Column(name = "CCD")
	public String companyCode;
	
	@Column(name = "COMMU_NOTAX_LIMIT_CD")
	public String commuNotaxLimitCd;
	
	public QtxmtCommuNotaxLimitPK() {}
}
