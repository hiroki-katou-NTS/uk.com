package nts.uk.ctx.pereg.infra.entity.roles.functionauth;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Table(name = "PPEMT_PER_INFO_AUTH")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PpemtPersonInfoAuth extends UkJpaEntity {

	@EmbeddedId
	public PpemtPersonInfoAuthPk key;
	
	@Column(name = "AVAILABLE")
	public int available; 

	@Override
	protected Object getKey() {
		return key;
	}

}
