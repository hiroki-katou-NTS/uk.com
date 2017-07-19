/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.JpaEntity;

/**
 * The Class KmpstMonthPattern.
 */

@Getter
@Setter
@Entity
@Table(name = "KMPST_MONTH_PATTERN")
public class KmpstMonthPattern extends JpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kmpst month pattern PK. */
    @EmbeddedId
    protected KmpstMonthPatternPK kmpstMonthPatternPK;
    
    /** The pattern name. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "PATTERN_NAME")
    private String patternName;

    /**
     * Instantiates a new kmpst month pattern.
     */
    public KmpstMonthPattern() {
    }

    /**
     * Instantiates a new kmpst month pattern.
     *
     * @param kmpstMonthPatternPK the kmpst month pattern PK
     */
    public KmpstMonthPattern(KmpstMonthPatternPK kmpstMonthPatternPK) {
        this.kmpstMonthPatternPK = kmpstMonthPatternPK;
    }

    /**
     * Instantiates a new kmpst month pattern.
     *
     * @param cid the cid
     * @param patternCd the pattern cd
     */
    public KmpstMonthPattern(String cid, String patternCd) {
        this.kmpstMonthPatternPK = new KmpstMonthPatternPK(cid, patternCd);
    }

    /**
     * Gets the kmpst month pattern PK.
     *
     * @return the kmpst month pattern PK
     */
    public KmpstMonthPatternPK getKmpstMonthPatternPK() {
        return kmpstMonthPatternPK;
    }

    /**
     * Sets the kmpst month pattern PK.
     *
     * @param kmpstMonthPatternPK the new kmpst month pattern PK
     */
    public void setKmpstMonthPatternPK(KmpstMonthPatternPK kmpstMonthPatternPK) {
        this.kmpstMonthPatternPK = kmpstMonthPatternPK;
    }


    /**
     * Gets the pattern name.
     *
     * @return the pattern name
     */
    public String getPatternName() {
        return patternName;
    }

    /**
     * Sets the pattern name.
     *
     * @param patternName the new pattern name
     */
    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kmpstMonthPatternPK;
	}
    
}
