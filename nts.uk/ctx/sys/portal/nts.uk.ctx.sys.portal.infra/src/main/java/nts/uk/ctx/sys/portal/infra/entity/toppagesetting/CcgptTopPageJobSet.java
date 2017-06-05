package nts.uk.ctx.sys.portal.infra.entity.toppagesetting;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CCGPT_TOPPAGE_JOB_SET")
public class CcgptTopPageJobSet extends UkJpaEntity {
	
	@EmbeddedId
	public CcgptTopPageJobSetPK ccgptTopPageJobSetPK;	
	
	/** The person mission set. */
	@Column(name = "PERSON_PERMISSION_SET")
	public int personPermissionSet;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return ccgptTopPageJobSetPK;
	}
}
