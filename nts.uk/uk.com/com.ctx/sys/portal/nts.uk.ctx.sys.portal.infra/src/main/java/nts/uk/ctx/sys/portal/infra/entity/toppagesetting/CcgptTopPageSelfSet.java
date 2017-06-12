package nts.uk.ctx.sys.portal.infra.entity.toppagesetting;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CCGPT_PERSON_HOMEPAGE_SET")
public class CcgptTopPageSelfSet extends UkJpaEntity {
	
	@EmbeddedId
	public CcgptTopPageSelfSetPK ccgptTopPageSelfSetPK;	

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return ccgptTopPageSelfSetPK;
	}
}
