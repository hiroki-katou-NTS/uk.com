package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_VERTICAL_CNT_AGG")

public class KscmtVerticalCntAgg extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscmtVerticalCntAggPK kscstVerticalCntSetPK;
	
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstVerticalCntSetPK;
	}
}

