/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscmtSchCreator.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_SCH_CREATOR")
public class KscmtSchCreator implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt sch creator PK. */
    @EmbeddedId
    protected KscmtSchCreatorPK kscmtSchCreatorPK;
    
    /** The exe status. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXE_STATUS")
    private int exeStatus;

    /**
     * Instantiates a new kscmt sch creator.
     */
    public KscmtSchCreator() {
    }

    /**
     * Instantiates a new kscmt sch creator.
     *
     * @param kscmtSchCreatorPK the kscmt sch creator PK
     */
    public KscmtSchCreator(KscmtSchCreatorPK kscmtSchCreatorPK) {
        this.kscmtSchCreatorPK = kscmtSchCreatorPK;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscmtSchCreatorPK != null ? kscmtSchCreatorPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KscmtSchCreator)) {
            return false;
        }
        KscmtSchCreator other = (KscmtSchCreator) object;
        if ((this.kscmtSchCreatorPK == null && other.kscmtSchCreatorPK != null) || (this.kscmtSchCreatorPK != null && !this.kscmtSchCreatorPK.equals(other.kscmtSchCreatorPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtSchCreator[ kscmtSchCreatorPK=" + kscmtSchCreatorPK + " ]";
    }
    
    
}
