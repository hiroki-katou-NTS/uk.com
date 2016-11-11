package nts.uk.ctx.pr.proto.infra.entity.paymentdata.personalcommute;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PprmtPersonCommutePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CCD")
	public String ccd;
	
	@Column(name = "PID")
	public String pId;
	
	@Column(name = "STR_YM")
	public BigDecimal strYm;

}
