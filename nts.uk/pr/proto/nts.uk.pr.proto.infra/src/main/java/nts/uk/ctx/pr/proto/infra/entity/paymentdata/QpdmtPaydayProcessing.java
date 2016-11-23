package nts.uk.ctx.pr.proto.infra.entity.paymentdata;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.layer.infra.data.entity.AggregateTableEntity;

@Entity
@Table(name = "QPDMT_PAYDAY_PROCESSING")
public class QpdmtPaydayProcessing {

	@EmbeddedId
	public QpdmtPaydayProcessingPK qpdmtPaydayProcessingPK;

	@Column(name = "CURRENT_PROCESSING_YM")
	public int currentProcessingYm;

	@Column(name = "DISP_ATR")
	public int dispAtr;

	@Column(name = "PROCESSING_NAME")
	public String processingName;
}
