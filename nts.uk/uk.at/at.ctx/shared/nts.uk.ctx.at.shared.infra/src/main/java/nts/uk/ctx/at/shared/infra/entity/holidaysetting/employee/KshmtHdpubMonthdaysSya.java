/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtHdpubMonthdaysSya.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_HDPUB_MONTHDAYS_SYA")
public class KshmtHdpubMonthdaysSya extends ContractUkJpaEntity implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt employee month day set PK. */
    @EmbeddedId
    protected KshmtHdpubMonthdaysSyaPK kshmtHdpubMonthdaysSyaPK;

    /** The in legal hd. */
    @Column(name = "IN_LEGAL_HD")
    private double inLegalHd;

    /**
     * Instantiates a new kshmt employee month day set.
     */
    public KshmtHdpubMonthdaysSya() {
    	super();
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtHdpubMonthdaysSyaPK != null ? kshmtHdpubMonthdaysSyaPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtHdpubMonthdaysSya)) {
            return false;
        }
        KshmtHdpubMonthdaysSya other = (KshmtHdpubMonthdaysSya) object;
        if ((this.kshmtHdpubMonthdaysSyaPK == null && other.kshmtHdpubMonthdaysSyaPK != null) || (this.kshmtHdpubMonthdaysSyaPK != null && !this.kshmtHdpubMonthdaysSyaPK.equals(other.kshmtHdpubMonthdaysSyaPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entities.KshmtHdpubMonthdaysSya[ kshmtHdpubMonthdaysSyaPK=" + kshmtHdpubMonthdaysSyaPK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtHdpubMonthdaysSyaPK;
	}
    
}
