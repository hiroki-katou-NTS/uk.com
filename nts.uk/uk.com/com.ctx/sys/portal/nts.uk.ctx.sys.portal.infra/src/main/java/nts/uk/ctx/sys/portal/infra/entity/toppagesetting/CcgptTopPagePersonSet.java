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
@Table(name = "CCGPT_TOPPAGE_PERSON_SET")
public class CcgptTopPagePersonSet extends UkJpaEntity {
	
	@EmbeddedId
	public CcgptTopPagePersonSetPK ccgptTopPagePersonSetPK;		

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return ccgptTopPagePersonSetPK;
	}
}
