package nts.uk.ctx.at.record.infra.entity.jobmanagement.manhourinputdisplayformat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.jobmanagement.displayformat.DisplayManHrRecordItem;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_MAN_HR_FORMAT_TASK")
public class KrcmtManHrFormatDialogTask extends ContractCompanyUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtManHrFormatDialogTaskPk pk;

	@Column(name = "DISPORDER")
	public int dispOrder;

	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public KrcmtManHrFormatDialogTask(DisplayManHrRecordItem domain) {
		this.pk.manHrItemId = domain.getAttendanceItemId();
		this.dispOrder = domain.getOrder();
	}
	
	public DisplayManHrRecordItem toDomain() {
		return new DisplayManHrRecordItem(this.pk.manHrItemId, this.dispOrder);
	}
}
