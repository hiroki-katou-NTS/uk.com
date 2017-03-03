package nts.uk.ctx.pr.core.infra.entity.paymentdata;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.uk.shr.infra.data.entity.TableEntity;

@AllArgsConstructor
@Entity
@Table(name = "QPDMT_PAYDAY_PROCESSING")
public class QpdmtPaydayProcessing extends TableEntity {

	@EmbeddedId
	public QpdmtPaydayProcessingPK qpdmtPaydayProcessingPK;

	@Column(name = "CURRENT_PROCESSING_YM")
	public int currentProcessingYm;

	@Column(name = "DISP_ATR")
	public int dispAtr;
	
	@Column(name = "BONUS_ATR")
    public int bonusAtr;

	@Column(name = "PROCESSING_NAME")
	public String processingName;
	 
	@Column(name = "B_CURRENT_PROCESSING_YM")
	public int bCurrentProcessingYm;
	 
	public QpdmtPaydayProcessing() {
	}

}
