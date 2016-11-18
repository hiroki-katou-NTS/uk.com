package nts.uk.ctx.pr.proto.infra.entity.paymentdata;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class QpdmtPaydayProcessingPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CCD")
	public String ccd;
	
	@Column(name = "PAY_BONUS_ATR")
	public int payBonusAtr;
	
	@Column(name = "PROCESSING_NO")
	public int processingNo;

	public QpdmtPaydayProcessingPK(String ccd, int payBonusAtr, int processingNo) {
		super();
		this.ccd = ccd;
		this.payBonusAtr = payBonusAtr;
		this.processingNo = processingNo;
	}
	
}
