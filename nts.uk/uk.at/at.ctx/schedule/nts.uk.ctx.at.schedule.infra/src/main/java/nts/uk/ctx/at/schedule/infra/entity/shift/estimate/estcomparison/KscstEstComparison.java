/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.estcomparison;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KscstEstComparison.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCST_EST_COMPARISON")
public class KscstEstComparison extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
    /** The cid. */
    @Id
    @Column(name = "CID")
    private String cid;
    
    /** The comparison atr. */
    @Column(name = "COMPARISON_ATR")
    private int comparisonAtr;
    
    /**
     * Instantiates a new kscst est comparison.
     */
    public KscstEstComparison() {
		super();
	}

    /**
     * Instantiates a new kscst est comparison.
     *
     * @param cid the cid
     * @param comparisonAtr the comparison atr
     */
    public KscstEstComparison(String cid, int comparisonAtr) {
		super();
		this.cid = cid;
		this.comparisonAtr = comparisonAtr;
	}
    
	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.cid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + comparisonAtr;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		KscstEstComparison other = (KscstEstComparison) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (comparisonAtr != other.comparisonAtr)
			return false;
		return true;
	}

}
