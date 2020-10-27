/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.holidaysetting.employment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtHdpubMonthdaysEmp.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_HDPUB_MONTHDAYS_EMP")
public class KshmtHdpubMonthdaysEmp extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt emp month day set PK. */
    @EmbeddedId
    protected KshmtHdpubMonthdaysEmpPK kshmtHdpubMonthdaysEmpPK;
   
    /** The in legal hd. */
    @Column(name = "IN_LEGAL_HD")
    private double inLegalHd;

    /**
     * Instantiates a new kshmt emp month day set.
     */
    public KshmtHdpubMonthdaysEmp() {
    	super();
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtHdpubMonthdaysEmpPK != null ? kshmtHdpubMonthdaysEmpPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtHdpubMonthdaysEmp)) {
            return false;
        }
        KshmtHdpubMonthdaysEmp other = (KshmtHdpubMonthdaysEmp) object;
        if ((this.kshmtHdpubMonthdaysEmpPK == null && other.kshmtHdpubMonthdaysEmpPK != null) || (this.kshmtHdpubMonthdaysEmpPK != null && !this.kshmtHdpubMonthdaysEmpPK.equals(other.kshmtHdpubMonthdaysEmpPK))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtHdpubMonthdaysEmpPK;
	}
    
}
