/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KscmtMonthPattern.
 */

@Getter
@Setter
@Entity
@Table(name = "KSCMT_MONTHLY_PATTERN")
public class KscmtMonthPattern extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt month pattern PK. */
    @EmbeddedId
    protected KscmtMonthPatternPK kscmtMonthPatternPK;
    
    /** The m pattern name. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "NAME")
    private String mPatternName;

	/** The contract code. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "CONTRACT_CD")
	private String contractCd;

    /**
     * Instantiates a new kmpmt month pattern.
     */
    public KscmtMonthPattern() {
    	super();
    }

    public KscmtMonthPattern(KscmtMonthPatternPK kmpmtMonthPatternPK) {
        this.kscmtMonthPatternPK = kmpmtMonthPatternPK;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscmtMonthPatternPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((kscmtMonthPatternPK == null) ? 0 : kscmtMonthPatternPK.hashCode());
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
		KscmtMonthPattern other = (KscmtMonthPattern) obj;
		if (kscmtMonthPatternPK == null) {
			if (other.kscmtMonthPatternPK != null)
				return false;
		} else if (!kscmtMonthPatternPK.equals(other.kscmtMonthPatternPK))
			return false;
		return true;
	}

}
