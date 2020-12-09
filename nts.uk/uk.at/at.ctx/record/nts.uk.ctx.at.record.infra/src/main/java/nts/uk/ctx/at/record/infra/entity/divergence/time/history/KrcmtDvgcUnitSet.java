package nts.uk.ctx.at.record.infra.entity.divergence.time.history;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * The persistent class for the KRCMT_DVGC_UNIT_SET database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="KRCMT_DVGC_UNIT_SET")
public class KrcmtDvgcUnitSet extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Id
	@Column(name="CID")
	private String cid;

	/** The worktype use set. */
	@Column(name="WORKTYPE_USE_SET")
	private BigDecimal worktypeUseSet;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.cid;
	}

}