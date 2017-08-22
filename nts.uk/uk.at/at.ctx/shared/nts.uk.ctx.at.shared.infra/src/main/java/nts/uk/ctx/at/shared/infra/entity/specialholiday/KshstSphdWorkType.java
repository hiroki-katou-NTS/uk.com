package nts.uk.ctx.at.shared.infra.entity.specialholiday;

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
@Table(name = "KSHST_SPHD_WORKTYPE")
public class KshstSphdWorkType extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
		/* 主キー */
		@EmbeddedId
		public KshstSphdWorkTypePK kshstSphdWorkTypePK;
		
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
}