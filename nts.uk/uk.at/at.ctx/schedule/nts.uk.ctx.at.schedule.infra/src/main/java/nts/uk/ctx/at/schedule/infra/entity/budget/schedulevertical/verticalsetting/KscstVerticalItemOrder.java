package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting;

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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_VERTICAL_ITEM_ORDER")
public class KscstVerticalItemOrder extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KscstVerticalItemOrderPK kscstVerticalItemOrderPK;
	
	/* 並び順 */
	@Column(name = "DISPORDER")
	public int dispOrder;
	
	@OneToOne
	@JoinColumns( {
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "VERTICAL_CAL_CD", referencedColumnName = "VERTICAL_CAL_CD", insertable = false, updatable = false),
        @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID", insertable = false, updatable = false)
    })
	public KscstVerticalCalItem kscstVerticalCalItem;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstVerticalItemOrderPK;
	}
	
	public KscstVerticalItemOrder(KscstVerticalItemOrderPK kscstVerticalItemOrderPK, int dispOrder) {
		this.kscstVerticalItemOrderPK = kscstVerticalItemOrderPK;
		this.dispOrder = dispOrder;
	}
}
