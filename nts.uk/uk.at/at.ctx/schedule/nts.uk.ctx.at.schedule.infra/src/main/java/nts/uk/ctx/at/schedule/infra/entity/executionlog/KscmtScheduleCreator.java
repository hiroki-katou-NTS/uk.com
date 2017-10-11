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

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KscmtScheduleCreator.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_SCHEDULE_CREATOR")
public class KscmtScheduleCreator extends UkJpaEntity  implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt schedule creator PK. */
    @EmbeddedId
    protected KscmtScheduleCreatorPK kscmtScheduleCreatorPK;
    
    /** The exe status. */
    @Basic(optional = false)
    @Column(name = "EXE_STATUS")
    private Integer exeStatus;

    /**
     * Instantiates a new kscmt schedule creator.
     */
    public KscmtScheduleCreator() {
    }

    /**
     * Instantiates a new kscmt schedule creator.
     *
     * @param kscmtScheduleCreatorPK the kscmt schedule creator PK
     */
    public KscmtScheduleCreator(KscmtScheduleCreatorPK kscmtScheduleCreatorPK) {
        this.kscmtScheduleCreatorPK = kscmtScheduleCreatorPK;
    }


    /**
     * Instantiates a new kscmt schedule creator.
     *
     * @param exeId the exe id
     * @param sid the sid
     */
    public KscmtScheduleCreator(String exeId, String sid) {
        this.kscmtScheduleCreatorPK = new KscmtScheduleCreatorPK(exeId, sid);
    }

    /**
     * Gets the kscmt schedule creator PK.
     *
     * @return the kscmt schedule creator PK
     */
    public KscmtScheduleCreatorPK getKscmtScheduleCreatorPK() {
        return kscmtScheduleCreatorPK;
    }

    /**
     * Sets the kscmt schedule creator PK.
     *
     * @param kscmtScheduleCreatorPK the new kscmt schedule creator PK
     */
    public void setKscmtScheduleCreatorPK(KscmtScheduleCreatorPK kscmtScheduleCreatorPK) {
        this.kscmtScheduleCreatorPK = kscmtScheduleCreatorPK;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kscmtScheduleCreatorPK != null ? kscmtScheduleCreatorPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KscmtScheduleCreator)) {
			return false;
		}
		KscmtScheduleCreator other = (KscmtScheduleCreator) object;
		if ((this.kscmtScheduleCreatorPK == null && other.kscmtScheduleCreatorPK != null)
				|| (this.kscmtScheduleCreatorPK != null
						&& !this.kscmtScheduleCreatorPK.equals(other.kscmtScheduleCreatorPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KscmtScheduleCreator[ kscmtScheduleCreatorPK=" + kscmtScheduleCreatorPK
				+ " ]";
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	@Override
	protected Object getKey() {
		return this.kscmtScheduleCreatorPK;
	}

}
