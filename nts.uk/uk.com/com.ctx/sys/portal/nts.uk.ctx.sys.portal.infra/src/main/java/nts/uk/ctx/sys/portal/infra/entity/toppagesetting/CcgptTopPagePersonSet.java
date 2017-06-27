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
	@Column(name = "TOP_MENU_CD")
	public String topMenuNo;

	/** The login menu no. */
	@Column(name = "LOGIN_MENU_CD")
	public String loginMenuNo;

	@Column(name = "LOGIN_SYSTEM")
	public int loginSystem;
	
	@Column(name = "LOGIN_MENU_CLS")
	public int loginMenuCls;

	@Override
	protected CcgptTopPagePersonSetPK getKey() {
		return this.ccgptTopPagePersonSetPK;
	}
}
