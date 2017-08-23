package nts.uk.ctx.at.shared.infra.entity.worktype.worktypedisporder;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name="KSHMT_WORKTYPE_ORDER")
@AllArgsConstructor
@NoArgsConstructor
public class KshmtWorkTypeDispOrder extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KshmtWorkTypeDispOrderPk  kshmtWorkTypeDispOrderPk;
	
	/*並び順*/
	@Column(name = "DISPORDER")
	public int dispOrder;
	
	
	@Override
	protected Object getKey() {
		return kshmtWorkTypeDispOrderPk;
	}
}
