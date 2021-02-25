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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KscdtScheErrLog.
 */
// Entity: 	スケジュール作成エラーログ

@Getter
@Setter
@Entity
@Table(name = "KSCDT_BATCH_ERR_LOG")
public class KscdtScheErrLog extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscdt sche err log PK. */
    @EmbeddedId
    protected KscdtScheErrLogPK kscdtScheErrLogPK;
    
    /** The err content. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "ERR_CONTENT")
    private String errContent;
    /**
     * 契約コード
     */
    @NotNull
    @Column(name = "CONTRACT_CD")
    private String contractCd;
    /** The cid. */
    @NotNull
    @Column(name = "CID")
    private String cid;
    /**
     * Instantiates a new kscmt sch error log.
     */
    public KscdtScheErrLog() {
    }

    /**
     * Instantiates a new kscmt sch error log.
     *
     * @param kscmtSchErrorLogPK the kscmt sch error log PK
     */
    public KscdtScheErrLog(KscdtScheErrLogPK kscmtSchErrorLogPK) {
        this.kscdtScheErrLogPK = kscmtSchErrorLogPK;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscdtScheErrLogPK != null ? kscdtScheErrLogPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KscdtScheErrLog)) {
            return false;
        }
        KscdtScheErrLog other = (KscdtScheErrLog) object;
        if ((this.kscdtScheErrLogPK == null && other.kscdtScheErrLogPK != null) || (this.kscdtScheErrLogPK != null && !this.kscdtScheErrLogPK.equals(other.kscdtScheErrLogPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtSchErrorLog[ kscmtSchErrorLogPK=" + kscdtScheErrLogPK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kscdtScheErrLogPK;
	}
    
    
}

