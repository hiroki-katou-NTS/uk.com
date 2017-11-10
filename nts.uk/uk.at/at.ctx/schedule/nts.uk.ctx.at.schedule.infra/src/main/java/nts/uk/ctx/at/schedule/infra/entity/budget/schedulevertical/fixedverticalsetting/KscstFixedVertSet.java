package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_FIXED_VERT_SET")

public class KscstFixedVertSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscstFixedVertSetPK kscstFixedVerticalSetPK;
	
	/* 付与基準日 */
	@Column(name = "USE_ATR")
	public int useAtr;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstFixedVerticalSetPK;
	}
}
