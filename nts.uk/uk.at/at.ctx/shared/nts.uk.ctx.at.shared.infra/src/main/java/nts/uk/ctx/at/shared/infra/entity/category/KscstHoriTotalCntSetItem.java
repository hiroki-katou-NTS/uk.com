package nts.uk.ctx.at.shared.infra.entity.category;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_HORI_TOTAL_CNT_SET")
public class KscstHoriTotalCntSetItem extends UkJpaEntity implements Serializable{
	@EmbeddedId
	public KscstHoriTotalCntSetPK kscstHoriTotalCntSetPK;

	@Override
	protected Object getKey() {
		return kscstHoriTotalCntSetPK;
	}
}
