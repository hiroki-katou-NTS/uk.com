/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.holidaysetting.company;

import java.io.Serializable;
//import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * The Class KshmtHdpubDPerMCom.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_HDPUB_D_PER_M_COM")
public class KshmtHdpubDPerMCom extends ContractUkJpaEntity implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshmt com month day set PK. */
    @EmbeddedId
    protected KshmtHdpubDPerMComPK kshmtHdpubDPerMComPK;

    /** The in legal hd. */
    @Column(name = "IN_LEGAL_HD")
    private double inLegalHd;

    /**
     * Instantiates a new kshmt com month day set.
     */
    public KshmtHdpubDPerMCom() {
    	super();
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtHdpubDPerMComPK != null ? kshmtHdpubDPerMComPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtHdpubDPerMCom)) {
            return false;
        }
        KshmtHdpubDPerMCom other = (KshmtHdpubDPerMCom) object;
        if ((this.kshmtHdpubDPerMComPK == null && other.kshmtHdpubDPerMComPK != null) || (this.kshmtHdpubDPerMComPK != null && !this.kshmtHdpubDPerMComPK.equals(other.kshmtHdpubDPerMComPK))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtHdpubDPerMComPK;
	}
    
}
