package nts.uk.ctx.at.shared.infra.entity.workplace;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author tutk
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KSHMT_WT_COM_WKP")
public class KshmtWorkTimeWorkplace extends ContractUkJpaEntity {
	
	@EmbeddedId
	public KshmtWorkTimeWorkplacePK kshmtWorkTimeWorkplacePK;

	@Override
	protected Object getKey() {
		return kshmtWorkTimeWorkplacePK;
	}

}
