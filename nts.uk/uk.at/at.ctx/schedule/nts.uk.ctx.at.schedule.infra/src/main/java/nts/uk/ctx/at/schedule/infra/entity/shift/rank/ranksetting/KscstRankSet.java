package nts.uk.ctx.at.schedule.infra.entity.shift.rank.ranksetting;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_RANK_SET")
public class KscstRankSet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscstRankSetPk kscstRankSetPk;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstRankSetPk;
	}

}
