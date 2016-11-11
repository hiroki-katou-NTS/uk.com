package nts.uk.ctx.pr.proto.infra.entity.paymentdata.paymentdatemaster;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.layer.infra.data.entity.AggregateTableEntity;

@Entity
@Table(name="QPDMT_PAYDAY_PROCESSING")
public class QpdmtPaydayProcessing extends AggregateTableEntity {

	@EmbeddedId
    public QpdmtPaydayProcessingPK qpdmtPaydayProcessingPK;
	
	@Column(name = "CURRENT_PROCESSING_YM")
	public BigDecimal currentProcessingYm;
	
	@Column(name = "DISP_ATR")
	public int dispAtr;
	
	@Column(name = "PROCESSING_NAME")
	public String processingName;
}
