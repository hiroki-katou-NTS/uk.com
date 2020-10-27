/**
 * 5:07:18 PM Dec 5, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.worktime;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcmtErAlCondition;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KRCMT_ERAL_WKTM_ACTUAL")
public class KrcmtEralWktmActual extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
    public KrcmtEralWktmPlanActualPK krcmtEralWktmPlanActualPK;

	@Override
	protected Object getKey() {
		return this.krcmtEralWktmPlanActualPK;
	}
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumns({
		@JoinColumn(name = "ERAL_CHECK_ID", referencedColumnName = "ERAL_CHECK_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false) 
	})
	public KrcmtErAlCondition krcmtErAlCondition;

	public KrcmtEralWktmActual(KrcmtEralWktmPlanActualPK krcmtEralWktmPlanActualPK) {
		super();
		this.krcmtEralWktmPlanActualPK = krcmtEralWktmPlanActualPK;
	}
}
