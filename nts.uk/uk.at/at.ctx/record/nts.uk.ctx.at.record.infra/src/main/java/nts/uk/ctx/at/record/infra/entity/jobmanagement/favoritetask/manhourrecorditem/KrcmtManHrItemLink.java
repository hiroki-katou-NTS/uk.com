package nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.manhourrecorditem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_MAN_HR_ITEM_LINK")
public class KrcmtManHrItemLink extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtManHrItemLinkPk pk;
	
	@Column(name = "ATTENDANCE_ITEM_ID")
	public int attItemId;

	@Override
	protected Object getKey() {
		return this.pk;
	}
	
}
