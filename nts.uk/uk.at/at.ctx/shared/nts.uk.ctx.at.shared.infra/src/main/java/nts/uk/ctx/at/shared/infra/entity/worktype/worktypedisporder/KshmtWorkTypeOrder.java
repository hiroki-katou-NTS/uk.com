package nts.uk.ctx.at.shared.infra.entity.worktype.worktypedisporder;

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
import nts.uk.ctx.at.shared.infra.entity.worktype.KshmtWorkType;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name="KSHMT_WORKTYPE_ORDER")
@AllArgsConstructor
@NoArgsConstructor
public class KshmtWorkTypeOrder extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KshmtWorkTypeOrderPk  kshmtWorkTypeDispOrderPk;
	
	/*並び順*/
	@Column(name = "DISPORDER")
	public Integer dispOrder;
	
	@OneToOne
    @JoinColumns({
    	@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
    	@JoinColumn(name="WORKTYPE_CD", referencedColumnName="CD", insertable = false, updatable = false)
    })
	public KshmtWorkType workType;
	
	@Override
	protected Object getKey() {
		return kshmtWorkTypeDispOrderPk;
	}

	public KshmtWorkTypeOrder(KshmtWorkTypeOrderPk kshmtWorkTypeDispOrderPk, int dispOrder) {
		super();
		this.kshmtWorkTypeDispOrderPk = kshmtWorkTypeDispOrderPk;
		this.dispOrder = dispOrder;
	}
	
}
