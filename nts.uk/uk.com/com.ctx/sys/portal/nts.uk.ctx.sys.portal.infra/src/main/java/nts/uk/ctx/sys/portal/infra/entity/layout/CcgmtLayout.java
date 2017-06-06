package nts.uk.ctx.sys.portal.infra.entity.layout;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author LamDT
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CCGMT_LAYOUT")
public class CcgmtLayout extends UkJpaEntity {

	@EmbeddedId
	public CcgmtLayoutPK ccgmtLayoutPK;

	@Column(name = "PG_TYPE")
	public int pgType;

	@Override
	protected Object getKey() {
		return ccgmtLayoutPK;
	}

}
