package nts.uk.ctx.pr.core.infra.entity.paymentdata;

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
	
	@Column(name = "PROCESSING_NO")
	public int processingNo;

	public QpdmtPaydayProcessingPK(String ccd, int processingNo) {
		super();
		this.ccd = ccd;
		this.processingNo = processingNo;
	}
	
	public QpdmtPaydayProcessingPK() {}
}
