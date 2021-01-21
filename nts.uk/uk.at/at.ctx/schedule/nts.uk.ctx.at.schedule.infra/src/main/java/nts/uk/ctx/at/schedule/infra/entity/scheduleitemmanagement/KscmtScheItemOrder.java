package nts.uk.ctx.at.schedule.infra.entity.scheduleitemmanagement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_SCHE_ITEM_ORDER")
public class KscmtScheItemOrder extends ContractUkJpaEntity implements Serializable {
private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KscmtScheItemOrderPK kscmtScheItemOrderPK;
	
	/* 順番 */
	@Column(name = "DISPORDER")
	public int dispOrder;
	
	@OneToOne
	@JoinColumns( {
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "SCHEDULE_ITEM_ID", referencedColumnName = "SCHEDULE_ITEM_ID", insertable = false, updatable = false)
    })
	public KscmtScheduleItem scheduleItem;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscmtScheItemOrderPK;
	}
	
	public KscmtScheItemOrder(KscmtScheItemOrderPK kscmtScheItemOrderPK, int dispOrder) {
		this.kscmtScheItemOrderPK = kscmtScheItemOrderPK;
		this.dispOrder = dispOrder;
	}
}
