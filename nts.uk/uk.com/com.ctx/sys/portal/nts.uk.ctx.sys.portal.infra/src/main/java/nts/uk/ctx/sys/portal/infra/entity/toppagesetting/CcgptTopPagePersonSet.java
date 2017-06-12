package nts.uk.ctx.sys.portal.infra.entity.toppagesetting;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author sonnh1
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CCGPT_TOPPAGE_PERSON_SET")
public class CcgptTopPagePersonSet extends UkJpaEntity {

	@EmbeddedId
	public CcgptTopPagePersonSetPK ccgptTopPagePersonSetPK;

	/** The top menu no. */
	@Column(name = "TOP_MENU_NO")
	public String topMenuNo;

	/** The login menu no. */
	@Column(name = "LOGIN_MENU_NO")
	public String loginMenuNo;

	@Column(name = "SYSTEM")
	public int system;

	@Override
	protected CcgptTopPagePersonSetPK getKey() {
		return this.ccgptTopPagePersonSetPK;
	}
}
