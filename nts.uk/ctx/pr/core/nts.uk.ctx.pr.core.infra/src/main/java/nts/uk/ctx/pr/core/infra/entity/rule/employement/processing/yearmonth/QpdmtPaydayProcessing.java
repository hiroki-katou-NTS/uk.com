package nts.uk.ctx.pr.core.infra.entity.rule.employement.processing.yearmonth;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.AggregateTableEntity;
import nts.uk.shr.infra.data.entity.TableEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPDMT_PAYDAY_PROCESSING")
public class QpdmtPaydayProcessing extends TableEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public QpdmtPaydayProcessingPK qpdmtPaydayProcessingPK;

	@Basic(optional = false)
	@Column(name = "PROCESSING_NAME")
	public String processingName;

	@Basic(optional = false)
	@Column(name = "DISP_ATR")
	public int dispAtr;

	@Basic(optional = false)
	@Column(name = "CURRENT_PROCESSING_YM")
	public int currentProcessingYm;

	@Basic(optional = false)
	@Column(name = "BONUS_ATR")
	public int bonusAtr;

	@Basic(optional = false)
	@Column(name = "B_CURRENT_PROCESSING_YM")
	public int bCurrentProcessingYm;
}
