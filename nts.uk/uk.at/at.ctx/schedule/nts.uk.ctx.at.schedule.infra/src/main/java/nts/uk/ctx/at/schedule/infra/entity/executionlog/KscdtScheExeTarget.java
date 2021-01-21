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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KscdtScheExeTarget.
 */
// Entity: スケジュール作成対象者

@Getter
@Setter
@Entity
@Table(name = "KSCDT_BATCH_TARGET_SYA")
public class KscdtScheExeTarget extends ContractUkJpaEntity implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The kscdt sche exe target PK.
     */
    @EmbeddedId
    protected KscdtScheExeTargetPK kscdtScheExeTargetPK;

    /**
     * The exe status.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXE_STATUS")
    private int exeStatus;

    /**
     * The cid.
     */
    @NotNull
    @Column(name = "CID")
    private String cid;

    /**
     * Instantiates a new kscmt sch creator.
     */
    public KscdtScheExeTarget() {
    }

    /**
     * Instantiates a new kscmt sch creator.
     *
     * @param kscmtSchCreatorPK the kscmt sch creator PK
     */
    public KscdtScheExeTarget(KscdtScheExeTargetPK kscmtSchCreatorPK) {
        this.kscdtScheExeTargetPK = kscmtSchCreatorPK;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscdtScheExeTargetPK != null ? kscdtScheExeTargetPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // not set
        if (!(object instanceof KscdtScheExeTarget)) {
            return false;
        }
        KscdtScheExeTarget other = (KscdtScheExeTarget) object;
        if ((this.kscdtScheExeTargetPK == null && other.kscdtScheExeTargetPK != null)
                || (this.kscdtScheExeTargetPK != null
                && !this.kscdtScheExeTargetPK.equals(other.kscdtScheExeTargetPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtSchCreator[ kscmtSchCreatorPK=" + kscdtScheExeTargetPK + " ]";
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
     */
    @Override
    protected Object getKey() {
        return this.kscdtScheExeTargetPK;
    }


}
