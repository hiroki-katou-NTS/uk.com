package nts.uk.ctx.pr.proto.infra.entity;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import nts.arc.layer.infra.data.entity.AggregateTableEntity;

@Embeddable
public class QpdmtPaydayPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	@Column(name = "CCD")
	public String ccd;
	
	@Basic(optional = false)
	@Column(name = "PAY_BONUS_ATR")
	public int payBonusAtr;
	
	@Basic(optional = false)
	@Column(name = "PROCESSING_NO")
	public BigDecimal processingNo;
	
	@Basic(optional = false)
	@Column(name = "PROCESSING_YM")
	public BigDecimal processingYM;
	
	@Basic(optional = false)
	@Column(name = "SPARE_PAY_ATR")
	public int sparePayAtr;
	

	

	
}
	
	